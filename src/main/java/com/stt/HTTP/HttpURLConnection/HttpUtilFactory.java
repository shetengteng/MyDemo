package com.stt.HTTP.HttpURLConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtilFactory {
	private HttpUtilFactory() {
	}

	/**
	 * @param url 访问的地址
	 * @param timeout 链接超时间 单位ms
	 * @param proxy 中间代理
	 * @return
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public static HttpUtil getHttpClient(String url, int timeout, Proxy proxy) throws Exception {
		URL targetUrl = new URL(url);
		HttpURLConnection conn = null;
		if (proxy != null) {
			conn = (HttpURLConnection) targetUrl.openConnection(proxy);
		} else {
			conn = (HttpURLConnection) targetUrl.openConnection();
		}
		if ("https".equalsIgnoreCase(targetUrl.getProtocol())) {
			conn = setSSLConfig(conn);
		}
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setConnectTimeout(timeout);
		HttpUtil client = new HttpUtilImpl(conn);
		return client;
	}

	public static HttpUtil getHttpClient(String url, Proxy proxy) throws Exception {
		return getHttpClient(url, 5000, proxy);
	}

	public static HttpUtil getHttpClient(String url) throws Exception {
		return getHttpClient(url, 5000, null);
	}

	private static byte[] sendData(HttpURLConnection conn, byte[] data, String method) {
		OutputStream output = null;
		InputStream input = null;
		try {
			conn.setRequestMethod(method);
			output = conn.getOutputStream();
			// 发送报文
			output.write(data);
			// 接收结果,防止乱码，使用byte
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
			return byteBuffer.toByteArray();
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

	/**
	 * 设置HTTPS的验证配置，默认忽略
	 * @param conn
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	private static HttpsURLConnection setSSLConfig(HttpURLConnection conn)
			throws NoSuchAlgorithmException, KeyManagementException {
		X509TrustManager x509TrustManager = new X509TrustManager() {

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}
		};

		HostnameVerifier hostnameVerifier = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, new TrustManager[] { x509TrustManager }, new SecureRandom());
		HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
		httpsConn.setHostnameVerifier(hostnameVerifier);
		httpsConn.setSSLSocketFactory(sslContext.getSocketFactory());
		return httpsConn;
	}

	public static interface HttpUtil {

		public byte[] sendPostData(byte[] data);

		public String sendPostData(String data) throws UnsupportedEncodingException;

		public String sendPostData(String data, String charset) throws UnsupportedEncodingException;

		public byte[] sendGetData(byte[] data);

		public String sendGetData(String data) throws UnsupportedEncodingException;

		public String sendGetData(String data, String charset) throws UnsupportedEncodingException;

	}

	public static class HttpUtilImpl implements HttpUtil {
		private HttpURLConnection conn;

		public HttpUtilImpl(HttpURLConnection conn) {
			this.conn = conn;
		}

		@Override
		public byte[] sendPostData(byte[] data) {
			return sendData(conn, data, "POST");
		}

		@Override
		public String sendPostData(String data) throws UnsupportedEncodingException {
			return sendPostData(data, "UTF-8");
		}

		@Override
		public String sendPostData(String data, String charset) throws UnsupportedEncodingException {
			byte[] inputData = data.getBytes(charset);
			return new String(sendPostData(inputData), charset);
		}

		@Override
		public byte[] sendGetData(byte[] data) {
			return sendData(conn, data, "GET");
		}

		@Override
		public String sendGetData(String data) throws UnsupportedEncodingException {
			return sendGetData(data, "UTF-8");
		}

		@Override
		public String sendGetData(String data, String charset) throws UnsupportedEncodingException {
			byte[] inputData = data.getBytes(charset);
			return new String(sendGetData(inputData), charset);
		}
	}
}
