package com.stt.NetWorkDemo.FTP.SFTPDemo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stt.NetWorkDemo.FTP.SFTPDemo.FTPUtilFactory.FTPUtil;
import com.stt.NetWorkDemo.FTP.SFTPDemo.FTPUtilFactory.PretreatmentHandler;

public class FTPUtilHandler extends PretreatmentHandler implements FTPUtil {
	private static final Logger logger = LoggerFactory.getLogger(FTPUtilHandler.class);
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

	private String hostname;
	private int port;
	private String username;
	private String password;
	private ConnectionMode connMode;
	private String character;
	private int bufferSize;
	private int dataTimeOut;
	private int dataPort;// 主动模式，数据端口

	private static final ThreadLocal<FTPClient> threadLocal = new ThreadLocal<>();

	public FTPUtilHandler() {
	}

	public FTPUtilHandler(String hostname, int port, String username, String password, ConnectionMode connMode,
			String character, int bufferSize, int dataTimeOut, int dataPort) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.connMode = connMode;
		this.character = character;
		this.bufferSize = bufferSize;
		this.dataTimeOut = dataTimeOut;
		this.dataPort = dataPort;
	}

	public FTPUtilHandler(String hostname, int port, String username, String password, ConnectionMode connMode,
			String character, int dataPort) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.connMode = connMode;
		this.character = character;
		this.bufferSize = DEFAULT_BUF_KB8;
		this.dataTimeOut = DEDAULT_FTP_DATA_TIMEOUT;
		this.dataPort = dataPort;
	}

	public FTPUtilHandler(String hostname, int port, String username, String password, ConnectionMode connMode,
			String character, int bufferSize, int dataPort) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.connMode = connMode;
		this.character = character;
		this.bufferSize = bufferSize;
		this.dataTimeOut = DEDAULT_FTP_DATA_TIMEOUT;
		this.dataPort = dataPort;
	}

	public FTPUtilHandler(String hostname, int port, String username, String password, ConnectionMode connMode,
			int dataPort) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.connMode = connMode;
		this.character = DEFAULT_ENCODING;
		this.bufferSize = DEFAULT_BUF_KB8;
		this.dataTimeOut = DEDAULT_FTP_DATA_TIMEOUT;
		this.dataPort = dataPort;
	}

	/**
	 * 获取ftpClient对象
	 */
	private FTPClient getFTPClient() {
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
	@SuppressWarnings("unused")
	private boolean connect(String hostname, int port, String username, String password, ConnectionMode connMode,
			String character, int bufferSize, int dataTimeOut, int dataPort) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.connMode = connMode;
		this.character = character;
		this.bufferSize = bufferSize;
		this.dataTimeOut = dataTimeOut;
		this.dataPort = dataPort;
		return connect();
	}

	@Override
	public boolean connect() {
		FTPClient ftpClient = getFTPClient();
		try {
			ftpClient.setDataTimeout(dataTimeOut);// 设置超时时间，单位ms
			ftpClient.setConnectTimeout(dataTimeOut);
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
				// 设置为二进制传输，防止文件以文本传输，格式改变
				// 注意：要在登陆之后设置
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				return true;
			} else {
				throw new RuntimeException("验证不通过:用户名或者密码错误");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void disconnect() {
		FTPClient ftpClient = getFTPClient();
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				// 注销
				ftpClient.logout();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (ftpClient != null && ftpClient.isConnected()) {
				// 关闭链接
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				ftpClient = null;
				threadLocal.remove();
			}
		}
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
	public boolean download(String localPath, String localFileName, String remotePath, String remoteFileName)
			throws IOException {
		localPath = addFileSeparator(localPath);
		remotePath = addFileSeparator(remotePath);
		return download(new File(localPath + localFileName), new File(remotePath + remoteFileName));
	}

	@Override
	public byte[] downloadBytes(File remoteFile) throws Exception {
		ByteArrayOutputStream out = null;
		try {

			FTPFile[] listFiles = getFTPClient().listFiles(remoteFile.getPath());
			if (listFiles.length == 0) {
				throw new RuntimeException("服务器上文件不存在!");
			}
			InputStream in = null;
			out = new ByteArrayOutputStream();
			in = getFTPClient().retrieveFileStream(remoteFile.getPath());
			// 返回值是拷贝的文件大小
			Util.copyStream(in, out, bufferSize);
			return out.toByteArray();
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
	public byte[] downloadBytes(String remotePathAndFileName) throws Exception {
		return downloadBytes(new File(remotePathAndFileName));
	}

	@Override
	public byte[] downloadBytes(String remotePath, String remoteFileName) throws Exception {
		remotePath = addFileSeparator(remotePath);
		return downloadBytes(new File(remotePath + remoteFileName));
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
	public boolean upload(String localPath, String localFileName, String remotePath, String remoteFileName)
			throws Exception {
		localPath = addFileSeparator(localPath);
		remotePath = addFileSeparator(remotePath);
		return upload(new File(localPath + localFileName), new File(remotePath + remoteFileName));
	}

	@Override
	public boolean upload(byte[] localByte, File remoteFile) throws Exception {
		if (createDirecroty(remoteFile)) {
			InputStream input = null;
			OutputStream output = null;
			try {
				getFTPClient().changeWorkingDirectory(remoteFile.getParentFile().getPath());
				input = new ByteArrayInputStream(localByte);
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
	public boolean upload(byte[] localByte, String remotePathAndFileName) throws Exception {
		// TODO 上传byte
		return upload(localByte, new File(remotePathAndFileName));
	}

	@Override
	public boolean upload(byte[] localByte, String remotePath, String remoteFileName) throws Exception {
		// TODO 上传byte
		remotePath = addFileSeparator(remotePath);
		return upload(localByte, new File(remotePath + remoteFileName));
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

}
