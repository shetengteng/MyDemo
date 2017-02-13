package com.stt.BankDemo.ceb;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpsClient {
	// 客户端密钥库
	private String sslKeyStorePath;
	private String sslKeyStorePassword;
	private String sslKeyStoreType;

	// 客户端信任证书
	private String sslTrustStore;
	private String sslTrustStorePassword;
	private String sslTruststoreType;

	// 地址
	private String host;
	private int port;

	// 设置密钥库相关参数
	public void setKeyStoreParameters(String sslKeyStorePath, String sslKeyStorePassword, String sslKeyStoreType) {
		this.sslKeyStorePath = sslKeyStorePath;
		this.sslKeyStorePassword = sslKeyStorePassword;
		this.sslKeyStoreType = sslKeyStoreType;
	}

	// 设置客户端信任证书相关参数
	public void setTrustStoreParameters(String sslTrustStore, String sslTrustStorePassword, String sslTruststoreType) {
		this.sslTrustStore = sslTrustStore;
		this.sslTrustStorePassword = sslTrustStorePassword;
		this.sslTruststoreType = sslTruststoreType;
	}

	// 设置访问地址
	public void setHost(String host) {
		this.host = host;
	}

	// 设置访问地址
	public void setPort(int port) {
		this.port = port;
	}

	// 发送并接收信息
	@SuppressWarnings("deprecation")
	public String sengMessage(String message) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		// 密钥管理器
		KeyStore keyStore = KeyStore.getInstance(sslKeyStoreType);
		// 信任管理器
		KeyStore trustStore = KeyStore.getInstance(sslTruststoreType);
		InputStream ksIn = new FileInputStream(sslKeyStorePath);
		InputStream tsIn = new FileInputStream(new File(sslTrustStore));
		// 加载客户端密钥库
		keyStore.load(ksIn, sslKeyStorePassword.toCharArray());
		// 加载信任密钥库
		trustStore.load(tsIn, sslTrustStorePassword.toCharArray());

		// 关闭加载
		ksIn.close();
		tsIn.close();

		SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore, sslKeyStorePassword, trustStore);
		// 设置不验证服务端密钥中的地址
		socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme sch = new Scheme("https", port, socketFactory);
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);

		// 获取post对象
		HttpPost httpPost = new HttpPost("https://" + host + ":" + port);
		// 处理要发送的报文
		message = frontCompWithZore(message.getBytes().length, 6) + message;
		httpPost.setHeader("Content-Type", "text/html");
		// 转换为字节流
		ByteArrayInputStream byteMessage = new ByteArrayInputStream(message.getBytes(), 0, message.getBytes().length);
		// 获取请求体对象
		HttpEntity httpEntity = new InputStreamEntity(byteMessage, message.getBytes().length);
		// 设置请求体
		httpPost.setEntity(httpEntity);

		// 打印返回的状态
		System.out.println("executing request" + httpPost.getRequestLine());

		// 发送并返回相应对象
		HttpResponse response = httpClient.execute(httpPost);
		// 返回内容
		HttpEntity entity = response.getEntity();

		StringBuffer returnMessage = new StringBuffer();

		System.out.println(response.getStatusLine());
		// 取出返回内容
		if (entity != null) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String lineMessage;
			while ((lineMessage = bufferedReader.readLine()) != null) {
				returnMessage.append(lineMessage);
			}
			bufferedReader.close();
		}
		EntityUtils.consume(entity);

		return returnMessage.toString();
	}

	public static String frontCompWithZore(int sourceData, int formatLength) {
		/*
		 * 0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
		 */
		String newString = String.format("%0" + formatLength + "d", sourceData);
		return newString;
	}
}
