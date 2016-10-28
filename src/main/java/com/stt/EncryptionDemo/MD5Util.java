package com.stt.EncryptionDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Util {
	private MD5Util() {
	}

	/**
	 * @Description 获得字串的摘要信息
	 * @param plainText 原串
	 * @return 摘要串
	 * @throws Exception
	 * @see 需要参考的类或方法
	 */
	public static String getMD5Encoding(String plainText) {
		byte[] input = plainText.getBytes();// 声明16进制字母
		char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hex = new char[32];
		;
		try {
			// 获得一个MD5摘要算法的对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input);
			/*
			 * MD5算法的结果是128位一个整数，在这里javaAPI已经把结果转换成字节数组了
			 */
			byte[] digest = md.digest();// 获得MD5的摘要结果
			byte b = 0;
			for (int i = 0; i < 16; i++) {
				b = digest[i];
				hex[2 * i] = hexChar[b >>> 4 & 0xf];// 取每一个字节的低四位换成16进制字母
				hex[2 * i + 1] = hexChar[b & 0xf];// 取每一个字节的高四位换成16进制字母
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
		return new String(hex);
	}

	/**
	 * @Description 根据指定的字符集获得字串的摘要信息
	 * @param plainText 原串
	 * @param charset 字符集
	 * @return 摘要串
	 * @throws Exception
	 * @see 需要参考的类或方法
	 */
	public static String getMD5Encoding(String plainText, String charset) {
		char[] hex = new char[32];
		try {
			byte[] input = plainText.getBytes(charset);
			// 声明16进制字母
			char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
			// 获得一个MD5摘要算法的对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input);
			/*
			 * MD5算法的结果是128位一个整数，在这里javaAPI已经把结果转换成字节数组了
			 */
			byte[] digest = md.digest();// 获得MD5的摘要结果
			byte b = 0;
			for (int i = 0; i < 16; i++) {
				b = digest[i];
				hex[2 * i] = hexChar[b >>> 4 & 0xf];// 取每一个字节的低四位换成16进制字母
				hex[2 * i + 1] = hexChar[b & 0xf];// 取每一个字节的高四位换成16进制字母
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return new String(hex);
	}

	/**
	 * @Description 对文件内容算摘要串信息
	 * @param file 文件
	 * @return 摘要串
	 * @throws Exception
	 * @see 需要参考的类或方法
	 */
	public static String getMD5Encoding(File file) {
		String value = "";
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (null != in)
					in.close();
			} catch (IOException e) {
			}
		}
		return value;
	}
}
