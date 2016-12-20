package com.stt.NetWorkDemo.FTP.FTPDemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.Util;

public class HelloWorld {

	private static String ENCODE_GBK = "GBK";
	private static String ENCODE_UTF8 = "UTF-8";

	private static String hostname = "192.168.14.61";
	private static int port = 21;
	private static String username = "shetengteng";
	private static String password = "stt123456";
	// 文件路径分割符
	/*
	 * private static String fileSeparator =
	 * System.getProperty("file.separator");
	 */

	private static String fileSeparator = File.separator;

	private static String splitStr = "/";
	static {
		if ("\\".equalsIgnoreCase(fileSeparator)) {
			splitStr = "\\\\";
		} else if ("/".equalsIgnoreCase(fileSeparator)) {
			splitStr = "/";
		}

	}

	private static FTPClient ftpClient = new FTPClient();

	public static void main(String[] args) {

		try {
			// 设置ftpClient
			// 设置超时时间，单位ms
			ftpClient.setDataTimeout(5 * 60 * 1000);

			// 设置编码字符集
			ftpClient.setControlEncoding("GBK");

			// 设置被动模式:
			// 有本地的主动和远端的主动模式
			// 设置本地的没有返回值，而设置远程的有boolean类型的返回值
			ftpClient.enterLocalPassiveMode();

			// 进行链接操作
			ftpClient.connect(hostname, port);

			// 连接的返回码是220:对新用户服务准备好  
			int replyCode = ftpClient.getReplyCode();
			System.out.println(replyCode);

			// 获取返回值
			// 返回值在200与300 之间返回true
			// 同时进行登录操作
			if (FTPReply.isPositiveCompletion(replyCode) && ftpClient.login(username, password)) {
				System.out.println("登录成功！！");
			}

			upload(ftpClient);

			// String localPath = "D:" + fileSeparator + "lll" + fileSeparator;
			String localPath = "D:" + fileSeparator;
			String localFileName = "ee" + System.currentTimeMillis() + ".ini";
			// String remotePath = fileSeparator + "proxy" + fileSeparator +
			// "ceshi" + fileSeparator + "stt"
			// + fileSeparator;
			String remotePath = "/proxy/";
			String remoteFileName = "test1473840412890.zip";

			// 下载测试
			File localFile = new File(localPath + localFileName);
			File remoteFile = new File(remotePath + remoteFileName);
			/*
			 * if(download(localFile,remoteFile)){ System.out.println("下载成功01");
			 * } if(download2(localFile,remoteFile)){
			 * System.out.println("下载成功02"); }
			 */

			// 传入的文件路径名称，和文件名，组合成一个file，那么组合过程中，需要判断斜杠，文件最后一个有没有
			// 没有则需要添加

			// 删除测试
			// 删除文件，
			// 删除文件夹，失败，有文件存在
			// 删除空文件夹，尝试
			remotePath = fileSeparator + "proxy" + fileSeparator + "ceshi" + fileSeparator + "ddd" + fileSeparator;
			remoteFileName = "";
			remoteFile = new File(remotePath + remoteFileName);
			/*
			 * if(delete(remoteFile)){ System.out.println("删除成功！"); }
			 */

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ftpClient != null && ftpClient.isConnected()) {
					// 注销
					ftpClient.logout();
					// 关闭链接
					ftpClient.disconnect();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private static boolean delete(File remoteFile) throws IOException {

		// 远程判断该文件是否存在，不存在返回true
		FTPFile[] files = ftpClient.listFiles(remoteFile.getPath());
		if (files.length == 0) {
			return true;
		}
		for (FTPFile ftpFile : files) {
			if (ftpFile.isFile()) {
				ftpClient.deleteFile(ftpFile.getName());
			}

		}

		// 返回的是命令码结果

		int dele = ftpClient.dele(remoteFile.getPath());
		System.out.println("delete --" + dele);
		System.out.println(ftpClient.getReplyCode());
		// 这里不能用该方法的原因是，该方法用于多个FTP操作命令组成一个事务
		// 该方法用于返回该事务的处理结果
		// return ftpClient.completePendingCommand();

		// 而删除操作，则是发送一条删除指令，不是由多个FTP操作命令组成的，直接就返回了结果码
		// 因此对此结果码进行判断即可
		// 表示是否是一个积极的处理结果
		return FTPReply.isPositiveCompletion(dele);
	}

	// 删除要使用递归删除
	/*
	 * public boolean removeAll(String pathname) { try { FTPFile[] files =
	 * this.listFiles(pathname); for (FTPFile f : files) { if (f.isDirectory())
	 * { this.removeAll(pathname + "/" + f.getName());
	 * this.removeDirectory(pathname); } if (f.isFile()) {
	 * this.deleteFile(pathname + "/" + f.getName()); } } } catch (IOException
	 * e) { e.printStackTrace(); return false; } return true; }
	 */

	private static boolean download(File localFile, File remoteFile) throws IOException {
		// 如果本地文件夹不存在，则则创建目录
		if (!localFile.exists() && !localFile.getParentFile().exists()) {
			// 只有file能创建目录，那么如果file的目录不存在，则获取父路径的file对象创建一个
			localFile.getParentFile().mkdirs();
		}

		// 判断远程文件是否存在
		// 获取所有的远程文件,通过路径

		System.out.println("---" + remoteFile.getAbsolutePath());

		FTPFile[] listFiles = ftpClient.listFiles(remoteFile.getPath());
		if (listFiles.length == 0) {
			throw new RuntimeException("文件不存在!");
		}
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(localFile));
			return ftpClient.retrieveFile(remoteFile.getPath(), out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return false;
	}

	private static boolean download2(File localFile, File remoteFile) throws IOException {
		// 如果本地文件夹不存在，则则创建目录
		if (!localFile.exists()) {
			if (localFile.isFile() && !localFile.getParentFile().exists()) {
				// 只有file能创建目录，那么如果file的目录不存在，则获取父路径的file对象创建一个
				localFile.getParentFile().mkdirs();
			} else if (localFile.isDirectory()) {
				localFile.mkdirs();
			}
		}

		// 判断远程文件是否存在
		// 获取所有的远程文件,通过绝对路径
		FTPFile[] listFiles = ftpClient.listFiles(remoteFile.getPath());
		if (listFiles.length == 0) {
			throw new RuntimeException("文件不存在!");
		}
		BufferedOutputStream out = null;
		InputStream in = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(localFile));
			in = ftpClient.retrieveFileStream(remoteFile.getPath());
			long copyStream = Util.copyStream(in, out, 8 * 1024);
			System.out.println("len:" + copyStream);
			return ftpClient.completePendingCommand();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return false;
	}

	// 上传测试
	public static void upload(FTPClient ftpClient) throws IOException {

		// 上传文件操作
		String localPath = "D:" + fileSeparator;
		String localFileName = "ee.ini";
		String remotePath = "/test";
		String remoteFileName = "test" + System.currentTimeMillis() + ".zip";
		// 远端的文件目录是否存在，不存在则创建
		// 如果创建失效则抛出异常
		// 先判断是否远端存在该路径
		boolean isExist = ftpClient.changeWorkingDirectory(remotePath);
		if (!isExist) {
			// 不存在则创建
			String[] pathNames = remotePath.split(splitStr);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < pathNames.length; i++) {
				sb.append(pathNames[i]).append(fileSeparator);
				if (!ftpClient.changeWorkingDirectory(sb.toString())) {
					// 如果创建成功
					if (ftpClient.makeDirectory(sb.toString())) {
						// 在这里切换目录的目的在于，最后一次，ftp的当前路径是传入的路径
						ftpClient.changeWorkingDirectory(sb.toString());
					} else {
						throw new RuntimeException("创建目录失败--目录：" + sb.toString());
					}
				}
			}
		}
		// 上传文件 方法一
		// upload01(localPath + localFileName, remotePath + remoteFileName);
		// 上传文件 方法二

		File localFile = new File(localPath + localFileName);
		File remoteFile = new File(remotePath + remoteFileName);

		if (upload02(localFile, remoteFile)) {
			System.out.println("上传完成");
		}
	}

	public static void upload01(String localName, String remoteName) throws IOException {
		// 上传文件前判断文件是否存在，存在则不上传？暂定

		File localfile = new File(localName);
		BufferedInputStream is = null;
		is = new BufferedInputStream(new FileInputStream(localfile));

		// 使用storeFile方法，无法做上传进度显示，如果要做大文件上传的话
		if (ftpClient.storeFile(remoteName, is)) {
			System.out.println("上传成功");
		}
		;

		is.close();
		System.out.println(ftpClient.completePendingCommand());

	}

	// 通过自定义缓冲大小，上传数据
	public static boolean upload02(File localFile, File remoteFile) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {

			input = new FileInputStream(localFile);

			// 注意这里是文件名，不是文件路径名
			output = ftpClient.storeFileStream(remoteFile.getName());
			// 判断状态：[300,400)

			System.out.println("---" + ftpClient.getReplyCode());// 150

			// 使用自带工具类进行流的拷贝工作,可以添加一次拷贝的大小，默认是1024
			// 还可以使用重载的方法，获取拷贝进度的Listener
			Util.copyStream(input, output, 8 * 1024);

			// 返回是否完成了待定的命令
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e2) {
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (Exception e2) {
				}
			}
		}
		return ftpClient.completePendingCommand();
	}

}
