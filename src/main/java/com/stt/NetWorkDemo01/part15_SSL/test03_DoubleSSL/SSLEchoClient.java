package com.stt.NetWorkDemo01.part15_SSL.test03_DoubleSSL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * 双向SSL验证
 * @author Administrator
 *
 */
public class SSLEchoClient {
	public static void main(String[] args) {
		String password = "456456";
		String keyStorePath = "d:\\client_ks";
		String host = "localhost";
		int port = 8443;
		try {
			// 创建秘钥库实例，有PrivateKey、SecretKey、JKS、PKCS12、JCEKS等
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");
			// 通过路径进行加载，注意：可以有2个keyStore，另一个作为trustStore存在
			// 这里示例简单，使用一个
			ks.load(new FileInputStream(keyStorePath), password.toCharArray());
			tks.load(new FileInputStream(keyStorePath), password.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, password.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(tks);
			KeyManager[] km = kmf.getKeyManagers();
			// 双向验证需要使用trustManager
			TrustManager[] tm = tmf.getTrustManagers();
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(km, tm, new SecureRandom());
			SSLSocketFactory factory = sslContext.getSocketFactory();
			SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
			System.out.println("已经建立了连接...");
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
					// 注意：这里的换行很重要
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
