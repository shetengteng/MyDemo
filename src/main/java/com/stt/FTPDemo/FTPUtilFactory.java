package com.stt.FTPDemo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.Util;

/**
 * FTP工具类
 * @author Administrator
 */
public class FTPUtilFactory {

	/** 默认缓冲区大小 ,设置8K则会有问题 */
	private final static int DEFAULT_BUF_KB8 = 1024 * 8;
	/** 默认 FTP 读取数据超时时间设置 */
	private final static int DEDAULT_FTP_DATA_TIMEOUT = 3 * 60 * 1000;
	/** 默认编码 */
	private final static String DEFAULT_ENCODING = "UTF8";

	/** 模式选择 */
	public enum ConnectionMode {
		ACTIVE_LOCAL, ACTIVE_REMOTE, PASSIVE_LOCAL, PASSIVE_REMOTE;
	}

	private static final FTPUtilFactory fTPUtilFactory = new FTPUtilFactory();
	private static final ThreadLocal<FTPClient> threadLocal = new ThreadLocal<>();

	private FTPUtilFactory() {}

	/**
	 * 返回实例对象
	 * @throws Exception
	 */
	public static FTPUtil getFTPUtilInstance(String hostname, int port, String username, String password, ConnectionMode connMode, int dataPort) throws Exception {
		return getFTPUtilInstance(hostname, port, username, password, connMode, DEFAULT_ENCODING, DEFAULT_BUF_KB8, DEDAULT_FTP_DATA_TIMEOUT, dataPort);
	}

	public static FTPUtil getFTPUtilInstance(String hostname, int port, String username, String password, ConnectionMode connMode, int dataPort, String character)
			throws Exception {
		return getFTPUtilInstance(hostname, port, username, password, connMode, character, DEFAULT_BUF_KB8, DEDAULT_FTP_DATA_TIMEOUT, dataPort);
	}

	public static FTPUtil getFTPUtilInstance(String hostname, int port, String username, String password, ConnectionMode connMode, int dataPort, String character,
			int bufferSize) throws Exception {
		return getFTPUtilInstance(hostname, port, username, password, connMode, character, bufferSize, DEDAULT_FTP_DATA_TIMEOUT, dataPort);
	}

	/**
	 * @param hostname 主机地址
	 * @param port 命令端口
	 * @param username 用户名
	 * @param password 密码
	 * @param connMode 连接模式
	 * @param character 字符集默认UTF8
	 * @param bufferSize 缓冲区大小
	 * @param dataTimeOut 读超时时间
	 * @param dataPort 主动模式的数据端口
	 * @return
	 * @throws Exception
	 */
	public static FTPUtil getFTPUtilInstance(String hostname, int port, String username, String password, ConnectionMode connMode, String character, int bufferSize,
			int dataTimeOut, int dataPort) throws Exception {
		try {
			FTPUtilImpl target = fTPUtilFactory.new FTPUtilImpl(bufferSize);
			FTPInvocationHandler handler = fTPUtilFactory.new FTPInvocationHandler(target, hostname, port, username, password, connMode, character, bufferSize,
					dataTimeOut, dataPort);
			FTPUtil fTPUtil = (FTPUtil) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
			return fTPUtil;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 获取ftpClient对象
	 */
	private static FTPClient getFTPClient() {
		// TODO 获取对象
		FTPClient ftpClient = threadLocal.get();
		if (ftpClient == null) {
			ftpClient = new FTPClient();
			threadLocal.set(ftpClient);
		}
		return ftpClient;
	}

	/**
	 * 建立链接
	 */
	private static boolean connect(String hostname, int port, String username, String password, ConnectionMode connMode, String character, int bufferSize,
			int dataTimeOut, int dataPort) {
		// TODO 判断链接
		FTPClient ftpClient = getFTPClient();
		try {
			ftpClient.setDataTimeout(dataTimeOut);// 设置超时时间，单位ms
			ftpClient.setControlEncoding(character);// 设置编码字符集
			switch (connMode) {// 设置被动模式:有本地的主动和远端的主动模式
			case ACTIVE_LOCAL:
				ftpClient.enterLocalActiveMode();
				break;
			case PASSIVE_LOCAL:
				ftpClient.enterLocalPassiveMode();
				break;
			// 设置本地的没有返回值，而设置远程的有boolean类型的返回值
			case ACTIVE_REMOTE:
				InetAddress host = InetAddress.getByName(hostname);
				if (!ftpClient.enterRemoteActiveMode(host, dataPort)) {
					throw new RuntimeException("远端主动模式设置异常");
				}
				break;
			case PASSIVE_REMOTE:
				if (!ftpClient.enterRemotePassiveMode()) {
					throw new RuntimeException("远端被动模式设置异常");
				}
				break;
			}
			// 进行链接操作
			ftpClient.connect(hostname, port);
			// 连接的返回码是220:对新用户服务准备好  
			int replyCode = ftpClient.getReplyCode();
			// 获取返回值在200与300 之间返回true,同时进行登录操作
			if (FTPReply.isPositiveCompletion(replyCode) && ftpClient.login(username, password)) {
				return true;
			} else {
				throw new RuntimeException("验证不通过:用户名或者密码错误");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 释放链接
	 */
	private static void disconnect() {
		// TODO 释放链接
		FTPClient ftpClient = getFTPClient();
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				// 注销
				ftpClient.logout();
				// 关闭链接
				ftpClient.disconnect();
			}
			ftpClient = null;
			threadLocal.remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * FTPClient代理处理器类
	 */
	private class FTPInvocationHandler implements InvocationHandler {

		private String hostname;
		private int port;
		private String username;
		private String password;
		private ConnectionMode connMode;
		private String character;
		private int bufferSize;
		private int dataTimeOut;
		private int dataPort;// 主动模式，数据端口
		private Object target = null;

		public FTPInvocationHandler(Object target, String hostname, int port, String username, String password, ConnectionMode connMode, String character,
				int bufferSize, int dataTimeOut, int dataPort) {
			this.target = target;
			this.hostname = hostname;
			this.port = port;
			this.username = username;
			this.password = password;
			this.connMode = connMode;
			this.character = character;
			this.bufferSize = bufferSize;
			this.dataTimeOut = dataTimeOut;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// TODO 处理器类进行invoke方法调用
			try {
				if (connect(hostname, port, username, password, connMode, character, bufferSize, dataTimeOut, dataPort)) {
					return method.invoke(target, args);
				} else {
					throw new RuntimeException("FTP链接失败");
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				disconnect();
			}
		}
	}

	public interface FTPUtil {

		public boolean download(File localFile, File remoteFile) throws IOException;

		public boolean download(String localPathAndFileName, String remotePathAndFileName) throws IOException;

		public boolean download(String localPath, String localFileName, String remotePath, String remoteFileName) throws IOException;

		public boolean upload(File localFile, File remoteFile) throws Exception;

		public boolean upload(String localPathAndFileName, String remotePathAndFileName) throws Exception;

		public boolean upload(String localPath, String localFileName, String remotePath, String remoteFileName) throws Exception;

		public boolean createDirecroty(File remoteFile) throws IOException;

		/** 删除文件，以及文件夹 ，递归删除 */
		public boolean remove(File remoteFile) throws Exception;

		public boolean remove(String remotePathAndFileName) throws Exception;

		public boolean remove(String remotePath, String remoteFileName) throws Exception;

	}

	private class FTPUtilImpl implements FTPUtil {

		private int bufferSize;

		public FTPUtilImpl(int bufferSize) {

			this.bufferSize = bufferSize;
		}

		@Override
		public boolean download(File localFile, File remoteFile) throws IOException {
			BufferedOutputStream out = null;
			try {
				// 如果本地文件夹不存在，则创建目录
				if (!localFile.getParentFile().exists()) {
					localFile.getParentFile().mkdirs();
				}
				// 判断远程文件是否存在
				// 获取所有的远程文件,通过绝对路径
				FTPFile[] listFiles = getFTPClient().listFiles(remoteFile.getPath());
				if (listFiles.length == 0) {
					throw new RuntimeException("服务器上文件不存在!");
				}
				InputStream in = null;
				out = new BufferedOutputStream(new FileOutputStream(localFile));
				in = getFTPClient().retrieveFileStream(remoteFile.getPath());
				// 返回值是拷贝的文件大小
				Util.copyStream(in, out, bufferSize);
				return getFTPClient().completePendingCommand();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		@Override
		public boolean download(String localPathAndFileName, String remotePathAndFileName) throws IOException {
			return download(new File(localPathAndFileName), new File(remotePathAndFileName));
		}

		@Override
		public boolean download(String localPath, String localFileName, String remotePath, String remoteFileName) throws IOException {
			localPath = addFileSeparator(localPath);
			remotePath = addFileSeparator(remotePath);
			return download(new File(localPath + localFileName), new File(remotePath + remoteFileName));
		}

		// 通过自定义缓冲大小，上传数据
		@Override
		public boolean upload(File localFile, File remoteFile) throws Exception {
			if (createDirecroty(remoteFile)) {
				InputStream input = null;
				OutputStream output = null;
				try {
					getFTPClient().changeWorkingDirectory(remoteFile.getParentFile().getPath());
					input = new FileInputStream(localFile);
					// 注意这里是文件名，不是文件路径名
					output = getFTPClient().storeFileStream(remoteFile.getName());
					Util.copyStream(input, output, bufferSize);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(e);
				} finally {
					if (input != null) {
						try {
							input.close();
						} catch (Exception e) {
							throw new Exception(e);
						}
					}
					if (output != null) {
						try {
							output.close();
						} catch (Exception e) {
							throw new Exception(e);
						}
					}
				}
				// 注意：一定要在关闭流之后，获取状态，否则会一直停留在返回码150，直到超时
				return getFTPClient().completePendingCommand();
			} else {
				return false;
			}
		}

		@Override
		public boolean createDirecroty(File remoteFile) throws IOException {
			// 如果创建失效则抛出异常, 先判断是否远端存在该路径
			String remotePath = remoteFile.getParentFile().getPath();
			boolean isExist = getFTPClient().changeWorkingDirectory(remotePath);
			if (!isExist) {
				String fileSeqarator = getFileSeparator(remotePath);
				// 不存在则创建
				String[] pathNames = remotePath.split(getSplitSeparator(remotePath));
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < pathNames.length; i++) {
					sb.append(pathNames[i]).append(fileSeqarator);
					if (!getFTPClient().changeWorkingDirectory(sb.toString())) {
						// 如果创建成功
						if (getFTPClient().makeDirectory(sb.toString())) {
							// 在这里切换目录的目的在于，最后一次，ftp的当前路径是传入的路径
							getFTPClient().changeWorkingDirectory(sb.toString());
						} else {
							throw new RuntimeException("创建目录失败--目录：" + sb.toString());
						}
					}
				}
				isExist = true;
			}
			return isExist;
		}

		@Override
		public boolean upload(String localPathAndFileName, String remotePathAndFileName) throws Exception {
			return upload(new File(localPathAndFileName), new File(remotePathAndFileName));
		}

		@Override
		public boolean upload(String localPath, String localFileName, String remotePath, String remoteFileName) throws Exception {
			localPath = addFileSeparator(localPath);
			remotePath = addFileSeparator(remotePath);
			return upload(new File(localPath + localFileName), new File(remotePath + remoteFileName));
		}

		@Override
		public boolean remove(File remoteFile) throws Exception {
			try {
				boolean result = true;
				FTPClient client = getFTPClient();
				String remote = remoteFile.getPath();
				FTPFile[] files = client.listFiles(remoteFile.getPath());
				if (files.length == 0) {
					// 不存在
					return true;
				} else if (files.length == 1) {
					// 是文件
					return client.deleteFile(remote);
				} else if (files.length == 2) {
					// 是空文件夹
					return client.removeDirectory(remote);
				} else {
					// 是一个非空文件夹，删除该文件夹下的文件以及文件夹，最后删除自己
					String filePath = addFileSeparator(remote);
					for (FTPFile file : files) {
						// 含有2个根路径，要排除
						if (".".equals(file.getName()) || "..".equals(file.getName())) {
							continue;
						}
						if (file.isFile()) {
							result &= getFTPClient().deleteFile(filePath + file.getName());
						} else if (file.isDirectory()) {
							// 递归调用
							result &= remove(new File(filePath + file.getName()));
						}
					}
					// 清空文件后，删除目录
					result &= getFTPClient().removeDirectory(remote);
				}
				return result;
			} catch (Exception e) {
				throw new Exception(e);
			}
		}

		@Override
		public boolean remove(String remotePathAndFileName) throws Exception {
			return remove(new File(remotePathAndFileName));
		}

		@Override
		public boolean remove(String remotePath, String remoteFileName) throws Exception {
			remotePath = addFileSeparator(remotePath);
			return remove(new File(remotePath + remoteFileName));
		}

		private String addFileSeparator(String original) {
			// 判断original中的文件分隔符，并在最后添加一个，如果没有的话
			String fileSeqarator = getFileSeparator(original);
			if (original.contains(fileSeqarator)) {
				if (original.lastIndexOf(fileSeqarator) != original.length() - 1) {
					return original + fileSeqarator;
				}
			}
			return original;
		}

		private String getFileSeparator(String original) {
			String fileSeqarator = "/";
			if (original.contains("\\")) {
				fileSeqarator = "\\";
			}
			return fileSeqarator;
		}

		private String getSplitSeparator(String original) {
			String splitSeparator = "/";
			if (original.contains("\\")) {
				splitSeparator = "\\\\";
			}
			return splitSeparator;
		}
	}
}
