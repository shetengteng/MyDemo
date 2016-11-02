package com.stt.EncryptionDemo;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author Administrator
 *
 */
public final class Base64Util {

	private static final String ENCOD_DEFALT = "UTF-8";

	private Base64Util() {
	}

	/**
	 * @param 加密操作
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String encode(String plainText, String charset) throws Exception {
		byte[] input = plainText.getBytes(charset);
		byte[] encodeBase64 = Base64.encodeBase64(input);
		return new String(encodeBase64, charset);
	}

	public static String encode(String plainText) throws Exception {
		return encode(plainText, ENCOD_DEFALT);
	}

	/**
	 * @param 解密操作
	 * @return
	 * @throws Exception 
	 */
	public static String decode(String encryptText, String charset) throws Exception {
		byte[] input = encryptText.getBytes(charset);
		byte[] decodeBase64 = Base64.decodeBase64(input);
		return new String(decodeBase64, charset);
	}

	public static String decode(String encrytText) throws Exception {
		return decode(encrytText, ENCOD_DEFALT);
	}

}
