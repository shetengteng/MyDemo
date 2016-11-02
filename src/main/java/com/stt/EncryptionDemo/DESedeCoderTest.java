package com.stt.EncryptionDemo;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class DESedeCoderTest {

	@Test
	public void test01() throws Exception {

		byte[] initKey = DESedeCoder.initKey();
		// 使用BASE64 进行转义
		String key = Base64.encodeBase64String(initKey);
		System.out.println(key);
	}

	// key：EBzsB3Ua9zd8nrBYm5RnpzKr3ClzTDuk

	@Test
	public void test02() throws Exception {

		String input = "Hello 3DES";
		System.out.println("明文：" + input);
		String keyStr = "EBzsB3Ua9zd8nrBYm5RnpzKr3ClzTDuk";
		byte[] key = Base64.decodeBase64(keyStr);
		byte[] out = DESedeCoder.encrypt(input.getBytes(), key);
		// 加密后
		String encryptText = Base64.encodeBase64String(out);
		//
		System.out.println("密文：" + encryptText);

	}

	// 明文：Hello 3DES
	// 密文：ImUSgRxnEaO0Ha++CYMAiA==
	@Test
	public void test03() throws Exception {
		String inputStr = "ImUSgRxnEaO0Ha++CYMAiA==";
		String keyStr = "EBzsB3Ua9zd8nrBYm5RnpzKr3ClzTDuk";
		byte[] key = Base64.decodeBase64(keyStr);
		byte[] input = Base64.decodeBase64(inputStr);
		byte[] out = DESedeCoder.decrypt(input, key);
		System.out.println("解密后：" + new String(out));
		// 解密后：Hello 3DES
	}

}
