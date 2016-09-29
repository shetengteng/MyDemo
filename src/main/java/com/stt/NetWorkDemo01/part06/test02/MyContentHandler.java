package com.stt.NetWorkDemo01.part06.test02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ContentHandler;
import java.net.URLConnection;

public class MyContentHandler extends ContentHandler {

	@Override
	public Object getContent(URLConnection connection) throws IOException {
		/* 读取字符串，并返回 */
		InputStream inputStream = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		return br.readLine();
	}

	/**
	 * 依据类型返回相应的对象
	 */
	public Object getContent(URLConnection connection, Class[] classes) throws IOException {
		InputStream in = connection.getInputStream();
		for (Class clazz : classes) {
			if (clazz == String.class) {
				return this.getContent(connection);
			} else if (clazz == InputStream.class) {
				return in;
			}
		}
		return null;
	}
}
