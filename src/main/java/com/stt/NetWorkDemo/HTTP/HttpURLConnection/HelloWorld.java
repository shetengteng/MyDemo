package com.stt.NetWorkDemo.HTTP.HttpURLConnection;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description: Http工具类
 * @version 2016年10月13日 下午8:51:11
 * @author tengteng.she
 */
public class HelloWorld {

	public static enum Method {
		POST, GET
	}

	public static String sendData(String uri, String data, Method method, String charset, int timeout) {
		OutputStream os = null;
		BufferedReader br = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(timeout);
			conn.setRequestMethod(method.name());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// 隐式调动了conn.connect()方法
			os = conn.getOutputStream();
			// 发送报文
			os.write(data.toString().getBytes(charset));
			// 接收结果,防止乱码，使用byte
			InputStream input = conn.getInputStream();
			byte[] buf = new byte[1024];
			int len = -1;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			while ((len = input.read(buf)) != -1) {
				output.write(buf, 0, len);
			}
			return output.toString(charset);

		} catch (Exception e) {
			throw new RuntimeException("HttpUtil sendData " + e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					throw new RuntimeException("HttpUtil sendData close os IOException:" + e);
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new RuntimeException("HttpUtil sendData close br IOException:" + e);
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static void main(String[] args) {

		HelloWorld helloWorld = new HelloWorld();
		// sun.misc.Launcher$AppClassLoader@73d16e93
		System.out.println(helloWorld.getClass().getClassLoader());
	}

}
