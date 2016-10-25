package com.stt.NetWorkDemo01.part15_SSL.test02_SingleSSL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLEchoClient2 {

	public static void main(String[] args) {
		try {
			// System.setProperty("javax.net.debug", "all");
			System.setProperty("javax.net.ssl.trustStore", "d:\\client_ks");
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket socket = (SSLSocket) factory.createSocket("127.0.0.1", 8443);
			String[] supportedCipherSuites = socket.getSupportedCipherSuites();
			socket.setEnabledCipherSuites(supportedCipherSuites);
			System.out.println(socket.getUseClientMode() ? "客户模式" : "服务器模式");
			BufferedReader localIn = null;
			BufferedWriter out = null;
			BufferedReader in = null;
			try {
				// 本地输入流
				localIn = new BufferedReader(new InputStreamReader(System.in));
				out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String msg = null;
				while ((msg = localIn.readLine()) != null) {
					System.out.println("send:" + msg);
					out.write(msg);
					out.newLine();
					out.flush();
					String returnMsg = in.readLine();
					System.out.println(returnMsg);
					if ("exit".equals(msg)) {
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (localIn != null) {
					try {
						localIn.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
