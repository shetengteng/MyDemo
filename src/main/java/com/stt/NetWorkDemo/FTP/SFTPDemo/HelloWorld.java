package com.stt.NetWorkDemo.FTP.SFTPDemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class HelloWorld {

	private String username = "shetengteng";
	private String password = "stt123456";
	private String hostname = "127.0.0.1";
	private int port = 22;

	private ChannelSftp sftpChannel = null;
	private JSch jsch = new JSch();
	private Session sshSession = null;

	@Before
	public void init() throws JSchException {
		try {
			// 建立会话
			sshSession = jsch.getSession(username, hostname, port);
			sshSession.setPassword(password);
			// 设置不验证 HostKey
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(config);
			// 设置超时时间
			sshSession.setTimeout(1000);
			// 建立连接
			sshSession.connect();

			// 打开SFTP连接通道
			Channel channel = sshSession.openChannel("sftp");
			// 通道建立链接
			channel.connect();

			System.out.println("--------");

			sftpChannel = (ChannelSftp) channel;
			System.out.println("connection of session is finish.");

		} catch (JSchException e) {
			// 如果连接已打开，需要销毁连接
			if (sshSession.isConnected()) {
				sshSession.disconnect();
			}
			e.printStackTrace();
			throw new JSchException("Connect to the server failed.Please check HOST[" + hostname + "],PORT[" + port
					+ "],USERNAME[" + username + "],PASSWORD[" + password
					+ "].And check the network connection is working or if the request was denied by the firewall:"
					+ e.getMessage());

		}
	}

	// 上传测试
	@Test
	public void upload() {
		String localPath = "D:\\";
		String localFileName = "ee.ini";
		// String remotePath = "\\proxy\\ceshi\\stt\\ddd\\";
		String remotePath = "/test";
		String remoteFileName = "test_sftp" + System.currentTimeMillis() + ".zip";
		try {
			// 切换到工作路径
			// 注意是文件分割符号是/ ,不论是windows还是Linux下
			sftpChannel.cd(remotePath);
			// 上传文件
			InputStream input = new FileInputStream(localPath + localFileName);
			sftpChannel.put(input, remoteFileName);

		} catch (SftpException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void download() {
		String localPath = "D:\\kkklll\\";
		String localFileName = "sftp_ee.ini";
		// String remotePath = "\\proxy\\ceshi\\stt";
		String remotePath = "/proxy/ceshi/stt/ddd";
		String remoteFileName = "sss.zip";

		try {
			// 切换到工作路径
			// 注意是文件分割符号是/ ,不论是windows还是Linux下
			sftpChannel.cd(remotePath);
			// 下载文件
			OutputStream output = new FileOutputStream(localPath + localFileName);
			sftpChannel.get(remoteFileName, output);

		} catch (SftpException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 注意创建的文件夹，只能一个一个创建
	@Test
	public void createDirectory() {
		// String remotePath = "/proxy/sftp/stt/ddd";
		String remotePath = "/proxy/sftp";
		try {
			sftpChannel.mkdir(remotePath);
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void isExist() {
		// String remotePath = "/proxy/ceshi/stt2";
		// String remotePath = "/lll";
		String remotePath = "/proxy/sftp/test";
		try {

			// 通过异常判断是否存在该文件
			Vector<?> ls = sftpChannel.ls(remotePath);
			System.out.println("------" + ls.size());
			for (Object object : ls) {
				System.out.println(object.toString());

				String fileNameStr = object.toString();

				// 通过第一个字符判断是文件还是文件夹
				String firstStr = fileNameStr.substring(0, 1);
				// 获取文件名
				String fileName = fileNameStr.substring(fileNameStr.lastIndexOf(" ") + 1);
				if ("d".equalsIgnoreCase(firstStr)) {
					System.out.println("文件夹：" + fileName);
				} else {
					System.out.println("文件：" + fileName);
				}
			}
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从aa/bb/cc/dd/ee开始判断创建文件夹
	 */
	@Test
	public void mkdirs() {
		String remotePath = "/proxy/ss/ddd/dfff/stt2";
		String[] directoryName = remotePath.split("/");

		StringBuilder sb = new StringBuilder();

		// 如果已经存在则返回,此操作在upload中进行
		if (isExist(remotePath)) {
			return;
		}

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
				sftpChannel.mkdir(sb.toString());
			}
		} catch (SftpException e) {
			e.printStackTrace();
			// return false;
		}

	}

	@Test
	public void isExistTest() {
		String str = "/proxy/ceshi/stt2/yyy.png";
		String str2 = "/proxy/ceshi/stt2/";

		System.out.println("1" + isExist(str));
		System.out.println("2" + isExist(str2));

	}

	/**
	 * 判断文件夹，文件是否存在，文件夹可以用/做结尾,也可以不用,根可以用/ 开始，也可以省略
	 * 注意：文件分割符号，只识别/
	 * @param directory
	 */
	private boolean isExist(String filePath) {
		try {
			sftpChannel.ls(filePath);
		} catch (SftpException e) {
			return false;
		}
		return true;
	}

	@Test
	public void remove() {

		// 需要级联删除
		String remotePath = "/proxy/ceshi/stt2";
		try {
			// 删除文件之前，切换到工作路径
			sftpChannel.cd(remotePath);
			// 删除文件夹:文件夹必须要为空
			sftpChannel.rmdir(remotePath);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 删除文件
		// sftpChannel.rm(path);

	}

	@After
	public void destroy() {

		try {
			sftpChannel.getSession().disconnect();
			sftpChannel.disconnect();
			sftpChannel.exit();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
