package com.stt.NetWorkDemo.HTTP.HttpURLConnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

/**
 * 配置文件：
 	testFlag=isTest
	sslTrustStorePath=H:\\cert\\cacerts
	sslKeyStorePath=H:/cert/test_client_16.pfx
	sslKeyStorePassword=Abcd1234
	sslTrustStorePassword=changeit
	host=111.205.51.153
	port=7765
	busiNum=7996
	keyPath=H:/cert/
	含有验证的httpsUtil
  @author Administrator
 *
 */
public class HttpsUtil {

	// 客户端密钥库
	private String sslKeyStorePath;
	private String sslKeyStorePassword;

	// 客户端信任证书
	private String sslTrustStorePath;
	private String sslTrustStorePassword;
	// 代理
	private Proxy proxy;

	public HttpsUtil(String sslKeyStorePath, String sslKeyStorePassword, String sslTrustStore,
			String sslTrustStorePassword) {
		this.sslKeyStorePath = sslKeyStorePath;
		this.sslKeyStorePassword = sslKeyStorePassword;
		this.sslTrustStorePath = sslTrustStore;
		this.sslTrustStorePassword = sslTrustStorePassword;
		this.proxy = null;
	}

	public HttpsUtil(String sslKeyStorePath, String sslKeyStorePassword, String sslTrustStore,
			String sslTrustStorePassword, Proxy proxy) {
		this.sslKeyStorePath = sslKeyStorePath;
		this.sslKeyStorePassword = sslKeyStorePassword;
		this.sslTrustStorePath = sslTrustStore;
		this.sslTrustStorePassword = sslTrustStorePassword;
		this.proxy = proxy;
	}

	// 发送并接收信息
	public String sengMessage(String message, String host, int port, int timeout) throws Exception {
		URL targetUrl = new URL("https://" + host + ":" + port);
		HttpsURLConnection conn = this.getHttps(targetUrl, timeout);
		OutputStream output = null;
		InputStream input = null;
		try {
			conn.setRequestMethod("POST");
			output = conn.getOutputStream();
			// 发送报文
			output.write(message.getBytes("gbk"));
			input = conn.getInputStream();
			if (HttpURLConnection.HTTP_OK != conn.getResponseCode()) {
				throw new RuntimeException("request url failed!");
			}
			byte[] buf = new byte[1024];
			int len = -1;
			ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
			while ((len = input.read(buf)) != -1) {
				byteBuffer.write(buf, 0, len);
			}
			return new String(byteBuffer.toByteArray(), "gb18030");
		} catch (Exception e) {
			throw new RuntimeException("HttpUtil sendData " + e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					throw new RuntimeException("HttpUtil sendData close os IOException:" + e);
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new RuntimeException("HttpUtil sendData close br IOException:" + e);
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	// 获取连接
	private HttpsURLConnection getHttps(URL targetUrl, int timeout) throws Exception {
		// 密钥管理器
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		// 信任管理器
		KeyStore trustStore = KeyStore.getInstance("jks");
		InputStream ksIn = new FileInputStream(sslKeyStorePath);
		InputStream tsIn = new FileInputStream(new File(sslTrustStorePath));
		// 加载客户端密钥库
		keyStore.load(ksIn, sslKeyStorePassword.toCharArray());
		// 加载信任密钥库
		trustStore.load(tsIn, sslTrustStorePassword.toCharArray());
		// 关闭加载的流
		ksIn.close();
		tsIn.close();

		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(keyStore, sslKeyStorePassword.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		tmf.init(trustStore);

		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		HostnameVerifier hostnameVerifier = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection conn = null;
		if (proxy != null) {
			conn = (HttpsURLConnection) targetUrl.openConnection(proxy);
		} else {
			conn = (HttpsURLConnection) targetUrl.openConnection();
		}
		conn.setHostnameVerifier(hostnameVerifier);
		conn.setSSLSocketFactory(sslContext.getSocketFactory());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-type", "text/html");
		conn.setRequestProperty("Accept-Charset", "gbk");
		conn.setRequestProperty("contentType", "gbk");
		conn.setConnectTimeout(timeout);
		return conn;
	}

}
