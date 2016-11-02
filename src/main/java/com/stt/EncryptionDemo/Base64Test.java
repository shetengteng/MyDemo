package com.stt.EncryptionDemo;

import org.junit.Test;

public class Base64Test {

	@Test
	public void testBase64() throws Exception {
		String str = "base64测试";
		System.out.println(Base64Util.encode(str, "GBK"));
	}

	@Test
	public void testBase64_2() throws Exception {
		String str = "YmFzZTY0suLK1A==";
		System.out.println(Base64Util.decode(str, "GBK"));
	}

}
