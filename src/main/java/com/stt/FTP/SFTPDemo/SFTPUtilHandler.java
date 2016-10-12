package com.stt.FTP.SFTPDemo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.stt.FTP.SFTPDemo.FTPUtilFactory.FTPUtil;
import com.stt.FTP.SFTPDemo.FTPUtilFactory.PretreatmentHandler;

public class SFTPUtilHandler extends PretreatmentHandler implements FTPUtil {

	/** 默认 FTP 读取数据超时时间设置 */
	private final static int DEDAULT_TIMEOUT = 3 * 60 * 1000;
	private String hostname;
	private int port;
	private String username;
	private String password;
	private int timeOut;

	private static final ThreadLocal<ChannelSftp> threadLocal = new ThreadLocal<>();

	public SFTPUtilHandler() {}

	public SFTPUtilHandler(String hostname, int port, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.timeOut = DEDAULT_TIMEOUT;
	}

	public SFTPUtilHandler(String hostname, int port, String username, String password, int timeOut) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.timeOut = timeOut;
	}

	/**
	 * 建立链接，将初始化的对象放入到threadLocal中
	 * @param hostname
	 * @param port
	 * @param username
	 * @param password
	 * @param timeOut
	 * @return
	 */
	public boolean connect(String hostname, int port, String username, String password, int timeOut) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.timeOut = timeOut;
		return connect();

	}

	/**
	 * 在使用有参数的构造函数后使用
	 */
	@Override
	public boolean connect() {
		JSch jsch = new JSch();
		Session sshSession = null;
		ChannelSftp sftpChannel = null;
		try {
			sshSession = jsch.getSession(username, hostname, port);
			sshSession.setPassword(password);
			// 设置不验证 HostKey
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(config);
			// 设置超时时间
			sshSession.setTimeout(timeOut);
			// 建立连接
			sshSession.connect();
			// 打开SFTP连接通道
			Channel channel = sshSession.openChannel("sftp");
			// 通道建立链接
			channel.connect();
			sftpChannel = (ChannelSftp) channel;

			threadLocal.remove();
			threadLocal.set(sftpChannel);

			return true;
		} catch (JSchException e) {
			e.printStackTrace();
			if (sshSession != null && sshSession.isConnected()) {
				sshSession.disconnect();
			}
			return false;
		}
	}

	private ChannelSftp getSFTPChannel() {
		ChannelSftp ftpClient = threadLocal.get();
		return ftpClient;
	}

	/**
	 * 关闭通道
	 */
	public void disconnect() {
		ChannelSftp sftpChannel = getSFTPChannel();
		if (null != sftpChannel && sftpChannel.isConnected()) {
			sftpChannel.exit();
			threadLocal.remove();
		}
	}

	/**
	 * 判断文件夹，文件是否存在，文件夹可以用/做结尾,也可以不用,根可以用/ 开始，也可以省略
	 * 注意：文件分割符号，只识别/
	 * @param directory
	 */
	public boolean isExist(String filePath) {
		try {
			getSFTPChannel().ls(filePath);
		} catch (SftpException e) {
			return false;
		}
		return true;
	}

	/**
	 * 下载文件
	 * @param localFile
	 * @param remoteFile
	 * @return
	 * @throws Exception
	 */
	public boolean download(File localFile, File remoteFile) throws Exception {
		BufferedOutputStream out = null;
		try {
			// 如果本地文件夹不存在，则创建目录
			if (!localFile.getParentFile().exists()) {
				localFile.getParentFile().mkdirs();
			}
			// 判断远程文件是否存在,切换工作路径，如果不存在，则报错
			// 注意是文件分割符号是/ ,不论是windows还是Linux下
			getSFTPChannel().cd(checkPath(remoteFile.getParent()));
			out = new BufferedOutputStream(new FileOutputStream(localFile));
			getSFTPChannel().get(remoteFile.getName(), out);
			return true;
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

	public boolean download(String localPathAndFileName, String remotePathAndFileName) throws Exception {
		return download(new File(localPathAndFileName), new File(remotePathAndFileName));
	}

	@Override
	public boolean download(String localPath, String localFileName, String remotePath, String remoteFileName) throws Exception {
		localPath = addFileSeparator(localPath);
		remotePath = addFileSeparator(remotePath);
		return download(new File(localPath + localFileName), new File(remotePath + remoteFileName));
	}

	@Override
	public byte[] downloadBytes(File remoteFile) throws Exception {
		// TODO Auto-generated method stub
		ByteArrayOutputStream out = null;
		try {

			// 判断远程文件是否存在,切换工作路径，如果不存在，则报错
			// 注意是文件分割符号是/ ,不论是windows还是Linux下
			getSFTPChannel().cd(checkPath(remoteFile.getParent()));
			out = new ByteArrayOutputStream();
			getSFTPChannel().get(remoteFile.getName(), out);
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

	/**
	 * 上传文件
	 * @param localFile
	 * @param remoteFile
	 * @return
	 * @throws Exception
	 */
	public boolean upload(File localFile, File remoteFile) throws Exception {
		InputStream input = null;
		try {
			// 如果上传的路径不存在，创建目录
			if (!isExist(checkPath(remoteFile.getParent()))) {
				createDirecroty(remoteFile.getParentFile());
			}
			// 切换到工作路径：注意是文件分割符号是/ ,不论是windows还是Linux下
			getSFTPChannel().cd(checkPath(remoteFile.getParent()));
			// 上传文件
			input = new FileInputStream(localFile);
			getSFTPChannel().put(input, remoteFile.getName());
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					throw new Exception(e);
				}
			}
		}
	}

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
	public boolean upload(byte[] localByte, File remoteFile) throws Exception {
		InputStream input = null;
		try {
			// 如果上传的路径不存在，创建目录
			if (!isExist(checkPath(remoteFile.getParent()))) {
				createDirecroty(remoteFile.getParentFile());
			}
			// 切换到工作路径：注意是文件分割符号是/ ,不论是windows还是Linux下
			getSFTPChannel().cd(checkPath(remoteFile.getParent()));
			// 上传字节
			input = new ByteArrayInputStream(localByte);
			getSFTPChannel().put(input, remoteFile.getName());
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					throw new Exception(e);
				}
			}
		}
	}

	@Override
	public boolean upload(byte[] localByte, String remotePathAndFileName) throws Exception {
		return upload(localByte, new File(remotePathAndFileName));
	}

	@Override
	public boolean upload(byte[] localByte, String remotePath, String remoteFileName) throws Exception {
		remotePath = addFileSeparator(remotePath);
		return upload(localByte, new File(remotePath + remoteFileName));
	}

	/**
	 * 从aa/bb/cc/dd/ee开始判断创建文件夹,递归创建
	 */
	@Override
	public boolean createDirecroty(File remoteFile) throws Exception {

		String remotePath = checkPath(remoteFile.getPath());
		// 如果已经存在则返回,此操作在upload中进行
		if (isExist(remotePath)) {
			return true;
		}
		String[] directoryName = remotePath.split("/");
		StringBuilder sb = new StringBuilder();

		try {
			boolean isStart = false;
			for (int i = 0; i < directoryName.length; i++) {
				sb.append(directoryName[i]).append("/");
				if (!isStart) {
					if (!isExist(sb.toString())) {
						// 没有开始创建文件夹，则不用进行判断
						isStart = true;
					} else {
						continue;
					}
				}
				getSFTPChannel().mkdir(sb.toString());
			}
			return true;
		} catch (SftpException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 判断是否是文件
	 * @param remoteFile
	 * @return
	 * @throws Exception
	 */
	public boolean isFile(File remoteFile) throws Exception {
		try {
			// 通过异常判断是否存在该文件
			Vector<?> ls = getSFTPChannel().ls(checkPath(remoteFile.getPath()));
			// 如果是一个，那么就是文件
			return ls.size() == 1;
		} catch (SftpException e) {
			// 文件不存在时抛出异常
			throw new Exception(e);
		}

	}

	/**
	 * 判断是否是文件夹:不是文件就是文件夹
	 * @param remoteFile
	 * @return
	 * @throws Exception
	 */
	public boolean isDirectory(File remoteFile) throws Exception {
		return !isFile(remoteFile);
	}

	/**
	 * 由于JSch中的文件分隔符是/,因此需要替换
	 * @param path
	 * @return
	 */
	private String checkPath(String path) {
		if (path.contains("\\")) {
			return path.replace("\\", "/");
		}
		return path;
	}

	/**
	 * 删除操作
	 */
	@Override
	public boolean remove(File remoteFile) throws Exception {
		try {
			// 先判断是否存在
			if (!isExist(checkPath(remoteFile.getPath()))) {
				return true;
			}
			boolean result = true;
			ChannelSftp sftp = getSFTPChannel();
			String remote = checkPath(remoteFile.getPath());
			Vector<?> ls = sftp.ls(remote);
			if (ls.size() == 1) {
				// 是文件，直接删除,先切换到所属目录
				sftp.cd(checkPath(remoteFile.getParent()));
				// 再删除
				sftp.rm(remote);
				return true;
			} else if (ls.size() == 0) {
				// 是空文件夹
				sftp.cd(checkPath(remoteFile.getParent()));
				sftp.rmdir(remote);
				return true;
			} else {
				// 是一个非空文件夹，删除该文件夹下的文件以及文件夹，最后删除自己
				for (Object item : ls) {
					String fileNameStr = item.toString();
					// 获取文件名
					String fileName = fileNameStr.substring(fileNameStr.lastIndexOf(" ") + 1);
					File file = new File(addFileSeparator(remoteFile.getPath()) + fileName);
					result &= remove(file);
				}
				// 删除自己的这个空文件夹
				result &= remove(remoteFile);
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
