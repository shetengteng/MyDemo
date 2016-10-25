package com.stt.NetWorkDemo01.part15_SSL.test02_SingleSSL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SSLEchoServer {

	public static void main(String[] args) {
		String keyStoreFileName = "D:\\server_ks";
		String password = "123123";
		int port = 8443;
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(keyStoreFileName), password.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, password.toCharArray());
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(kmf.getKeyManagers(), null, null);
			SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
			// 创建了server
			SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(port);
			// 打印默认信息
			System.out.println("服务启动：");
			// 客户模式：服务端不验证客户的身份 默认false
			System.out.println(sslServerSocket.getUseClientMode() ? "客户模式" : "服务器模式");
			System.out.println(sslServerSocket.getNeedClientAuth() ? "需要验证对方身份" : "不需要验证对方身份");
			String[] supportedCipherSuites = sslServerSocket.getSupportedCipherSuites();
			sslServerSocket.setEnabledCipherSuites(supportedCipherSuites);
			while (true) {
				SSLSocket socket = null;
				try {
					// 监听获取
					socket = (SSLSocket) sslServerSocket.accept();
					System.out.println("connecting: IP" + socket.getInetAddress() + " port:" + socket.getPort());
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					String msg = null;
					while ((msg = br.readLine()) != null) {
						System.out.println("recieve:" + msg);
						bw.write("echo:" + msg);
						bw.newLine();
						bw.flush();
						if ("exit".equals(msg)) {
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (socket != null) {
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
