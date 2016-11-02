package com.stt.EncryptionDemo;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

public class RSACoderTest {

	private byte[] privateKey;
	private byte[] publicKey;

	@Before
	public void initKey() throws Exception {
		Map<String, Object> initKey = RSACoder.initKey();
		privateKey = RSACoder.getPrivateKey(initKey);
		publicKey = RSACoder.getPublicKey(initKey);
		System.out.println("privateKey:" + Base64.encodeBase64String(privateKey));
		System.out.println("publicKey:" + Base64.encodeBase64String(publicKey));
	}

	@Test
	public void test01() throws Exception {
		System.out.println("-----start-----");
		String dataStr = "RSA加密解密测试";
		System.out.println("测试明文:" + dataStr);
		byte[] encryptData = RSACoder.encryptByPrivateKey(dataStr.getBytes(), privateKey);
		System.out.println("----加密后----");
		System.out.println("密文:" + Base64.encodeBase64String(encryptData));

		byte[] decryptData = RSACoder.decryptByPublicKey(encryptData, publicKey);
		System.out.println("----解密后-----");
		System.out.println("明文：" + new String(decryptData));

	}

}
