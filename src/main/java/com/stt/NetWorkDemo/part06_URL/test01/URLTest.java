package com.stt.NetWorkDemo.part06_URL.test01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class URLTest {

	@Test
	public void test01() {
		InputStream in = null;
		try {
			URL url = new URL("http://www.baidu.com");
			in = url.openStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

			System.out.println(out.toString("UTF-8"));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void test02() {
		InputStream in = null;
		try {
			URL url = new URL("https://www.baidu.com");
			URLConnection urlConnection = url.openConnection();

			// 接收的响应结果

			System.out.println("返回的正文类型:" + urlConnection.getContentType());
			System.out.println("返回的正文编码:" + urlConnection.getContentEncoding());
			System.out.println("返回的正文长度:" + urlConnection.getContentLength());

			// 遍历头部
			Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
			headerFields.forEach((p, l) -> {
				System.out.println(p + ":");
				l.forEach((s) -> System.out.println("---" + s));
			});

			// 打印正文
			in = url.openStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

			System.out.println(out.toString("UTF-8"));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
