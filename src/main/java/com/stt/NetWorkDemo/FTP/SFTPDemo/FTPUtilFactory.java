package com.stt.NetWorkDemo.FTP.SFTPDemo;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.stt.NetWorkDemo.FTP.SFTPDemo.FTPUtilHandler.ConnectionMode;

public class FTPUtilFactory {

	private static final FTPUtilFactory fTPUtilFactory = new FTPUtilFactory();

	private FTPUtilFactory() {
	}

	/**
	 * @param hostname
	 * @param port
	 * @param username
	 * @param password
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public static FTPUtil getSFTPUtilInstance(String hostname, int port, String username, String password, int timeOut)
			throws Exception {
		try {
			FTPUtil target = new SFTPUtilHandler(hostname, port, username, password, timeOut);
			FTPInvocationHandler handler = fTPUtilFactory.new FTPInvocationHandler(target);
			return (FTPUtil) handler.getProxy();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public static FTPUtil getSFTPUtilInstance(String hostname, int port, String username, String password)
			throws Exception {
		try {
			FTPUtil target = new SFTPUtilHandler(hostname, port, username, password);
			FTPInvocationHandler handler = fTPUtilFactory.new FTPInvocationHandler(target);
			return (FTPUtil) handler.getProxy();
		} catch (Exception e) {
			throw new Exception(e);
		}
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
	public static FTPUtil getFTPUtilInstance(String hostname, int port, String username, String password,
			ConnectionMode connMode, String character, int bufferSize, int dataTimeOut, int dataPort) throws Exception {
		try {
			FTPUtil target = new FTPUtilHandler(hostname, port, username, password, connMode, character, bufferSize,
					dataTimeOut, dataPort);
			FTPInvocationHandler handler = fTPUtilFactory.new FTPInvocationHandler(target);
			return (FTPUtil) handler.getProxy();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public static FTPUtil getFTPUtilInstance(String hostname, int port, String username, String password,
			ConnectionMode connMode, int dataPort) throws Exception {
		try {
			FTPUtil target = new FTPUtilHandler(hostname, port, username, password, connMode, dataPort);
			FTPInvocationHandler handler = fTPUtilFactory.new FTPInvocationHandler(target);
			return (FTPUtil) handler.getProxy();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public static FTPUtil getFTPUtilInstance(String hostname, int port, String username, String password,
			ConnectionMode connMode, int dataPort, String character) throws Exception {
		try {
			FTPUtil target = new FTPUtilHandler(hostname, port, username, password, connMode, character, dataPort);
			FTPInvocationHandler handler = fTPUtilFactory.new FTPInvocationHandler(target);
			return (FTPUtil) handler.getProxy();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public static FTPUtil getFTPUtilInstance(String hostname, int port, String username, String password,
			ConnectionMode connMode, int dataPort, String character, int bufferSize) throws Exception {
		try {
			FTPUtil target = new FTPUtilHandler(hostname, port, username, password, connMode, character, bufferSize,
					dataPort);
			FTPInvocationHandler handler = fTPUtilFactory.new FTPInvocationHandler(target);
			return (FTPUtil) handler.getProxy();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * FTPClient代理处理器类
	 */
	private class FTPInvocationHandler implements InvocationHandler {

		PretreatmentHandler pretreatmentHandler;
		Object target;

		public FTPInvocationHandler(Object target) {
			this.pretreatmentHandler = (PretreatmentHandler) target;
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// TODO 处理器类进行invoke方法调用
			try {
				if (pretreatmentHandler.connect()) {
					return method.invoke(target, args);
				} else {
					throw new RuntimeException("FTP链接失败");
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				pretreatmentHandler.disconnect();
			}
		}

		public Object getProxy() {
			return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
		}
	}

	/**
	 * 预处理
	 */
	public static abstract class PretreatmentHandler {

		public abstract boolean connect();

		public abstract void disconnect();

		protected String getFileSeparator(String original) {
			String fileSeqarator = "/";
			if (original.contains("\\")) {
				fileSeqarator = "\\";
			}
			return fileSeqarator;
		}

		protected String getSplitSeparator(String original) {
			String splitSeparator = "/";
			if (original.contains("\\")) {
				splitSeparator = "\\\\";
			}
			return splitSeparator;
		}

		protected String addFileSeparator(String original) {
			// 判断original中的文件分隔符，并在最后添加一个，如果没有的话
			String fileSeqarator = getFileSeparator(original);
			if (original.contains(fileSeqarator)) {
				if (original.lastIndexOf(fileSeqarator) != original.length() - 1) {
					return original + fileSeqarator;
				}
			}
			return original;
		}
	}

	public interface FTPUtil {

		public boolean download(File localFile, File remoteFile) throws Exception;

		public boolean download(String localPathAndFileName, String remotePathAndFileName) throws Exception;

		public boolean download(String localPath, String localFileName, String remotePath, String remoteFileName)
				throws Exception;

		public byte[] downloadBytes(File remoteFile) throws Exception;

		public byte[] downloadBytes(String remotePathAndFileName) throws Exception;

		public byte[] downloadBytes(String remotePath, String remoteFileName) throws Exception;

		public boolean upload(File localFile, File remoteFile) throws Exception;

		public boolean upload(String localPathAndFileName, String remotePathAndFileName) throws Exception;

		public boolean upload(String localPath, String localFileName, String remotePath, String remoteFileName)
				throws Exception;

		public boolean upload(byte[] localByte, File remoteFile) throws Exception;

		public boolean upload(byte[] localByte, String remotePathAndFileName) throws Exception;

		public boolean upload(byte[] localByte, String remotePath, String remoteFileName) throws Exception;

		public boolean createDirecroty(File remoteFile) throws Exception;

		/** 删除文件，以及文件夹 ，递归删除 */
		public boolean remove(File remoteFile) throws Exception;

		public boolean remove(String remotePathAndFileName) throws Exception;

		public boolean remove(String remotePath, String remoteFileName) throws Exception;

	}
}
