package com.stt.NetWorkDemo01.part15_SSL.test01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class HelloWorld_httpsClient {

	public static void main(String[] args) {
		String host = "www.usps.com";
		int port = 443;
		try {
			// 获取SSLSocket工厂
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			// 获得Soket对象
			SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
			// 获取支持的加密套件
			String[] supportedCipherSuites = socket.getSupportedCipherSuites();
			for (String item : supportedCipherSuites) {
				/**
				 * TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256
				 * TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256
				 * TLS_RSA_WITH_AES_128_CBC_SHA256
				 * TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256 等
				 */
				System.out.println(item);
			}
			// 设置加密套件
			socket.setEnabledCipherSuites(supportedCipherSuites);
			// 获取输出流
			OutputStream out = socket.getOutputStream();
			// 编写头部
			StringBuilder sb = new StringBuilder();
			sb.append("GET http://" + host + "/HTTP/1.1\r\n");
			sb.append("Host: " + host + "\r\n");
			sb.append("Accept: */*\r\n");
			sb.append("\r\n");
			// 发送请求
			out.write(sb.toString().getBytes());
			// 接收返回
			InputStream in = socket.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = 1024;
			while ((len = in.read(buf)) != -1) {
				buffer.write(buf, 0, len);
			}
			// 输出返回的结果
			System.out.println(new String(buffer.toByteArray()));
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
