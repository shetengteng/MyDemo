package com.stt.NetWorkDemo01.part15_SSL.test02_SingleSSL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLEchoClient {
	public static void main(String[] args) {
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} }, new SecureRandom());
			SSLSocketFactory factory = sslContext.getSocketFactory();
			SSLSocket socket = (SSLSocket) factory.createSocket("127.0.0.1", 8443);
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
