package com.stt.EncryptionDemo;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public abstract class DESedeCoder {

	/**
	 * 密钥算法:3DES
	 * 默认168位
	 */
	public static final String KEY_ALGORITHM = "DESede";

	/**
	 * 加解密算法/ 工作模式 / 填充方式
	 */
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

	/**
	 * 加密操作
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {

		// 还原密钥
		Key k = getKey(key);
		// 加密实体实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 还原秘钥
		Key k = getKey(key);
		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 转换密钥
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	private static Key getKey(byte[] key) throws Exception {
		// 实例化DES秘钥材料
		DESedeKeySpec dks = new DESedeKeySpec(key);
		// 实例化秘钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		// 获得秘钥
		return keyFactory.generateSecret(dks);
	}

	/**
	 * 生成一个秘钥
	 * @return
	 * @throws Exception 
	 */
	public static byte[] initKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 秘钥长度112和168
		kg.init(168);
		SecretKey key = kg.generateKey();
		// 返回二进制编码
		return key.getEncoded();
	}

}
