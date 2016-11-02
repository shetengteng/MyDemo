package com.stt.EncryptionDemo;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSACoder {

	// 非对称加密算法
	public static final String KEY_ALGORITHM = "RSA";

	// 公钥
	private static final String PUBLIC_KEY = "RSAPublicKey";
	// 私钥
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 加解密算法/ 工作模式 / 填充方式
	 */
	public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

	/**
	 * RSA秘钥长度
	 * 默认1024
	 * 秘钥长度必须是64的倍数
	 */
	private static final int KEY_SIZE = 1024;

	/**
	 * 产生一对秘钥
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, Object> initKey() throws Exception {
		// 实例化秘钥对生成器
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// 初始化生成器
		keyPairGen.initialize(KEY_SIZE);
		// 生成秘钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 获得公钥
		RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
		// 获得私钥
		RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
		// 返回秘钥对
		Map<String, Object> keyMap = new HashMap<>(2);
		keyMap.put(PUBLIC_KEY, pubKey);
		keyMap.put(PRIVATE_KEY, priKey);
		return keyMap;
	}

	/**
	 * 获得公钥
	 * @param keyMap
	 * @return
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	/**
	 * 获得私钥
	 * @param keyMap
	 * @return
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}

	/**
	 * 私钥加密
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception 
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {
		// 获取私钥材料
		PKCS8EncodedKeySpec pkcs8EncodeKeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodeKeySpec);
		// 对数据进行加密
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 公钥解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
		// 获取公钥材料
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 公钥加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
		// 获取公钥材料
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 私钥解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
		// 获取私钥材料
		PKCS8EncodedKeySpec pkcs8EncodeKeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodeKeySpec);
		// 对数据进行加密
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

}
