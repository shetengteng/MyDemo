package com.stt.NetWorkDemo.part06_URL.test02;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author Administrator
 *
 */
public class MyURLConnection extends URLConnection {

	private Socket connection = null;
	public final static int DEFAULT_PORT = 8000;

	protected MyURLConnection(URL url) {
		super(url);
	}

	// 此处为固定值，如果是常规做法，应该依据报文来判断
	public String getContentType() {
		return "text/plain";
	}

	// 这里添加锁的目的，是让connected做边界条件，用于判断是否要重新链接
	// 可能会有多个线程使用这一个URLConnection
	@Override
	public synchronized void connect() throws IOException {
		if (!connected) {
			int port = url.getPort();
			String host = url.getHost();
			if (port < 0 || port > 65535) {
				port = DEFAULT_PORT;
			}
			connection = new Socket(host, port);
			connected = true;
		}
	}

	public synchronized InputStream getInputStream() throws IOException {
		if (!connected) {
			this.connect();
		}
		return connection.getInputStream();
	}

	public synchronized OutputStream getOutputStream() throws IOException {
		if (!connected) {
			this.connect();
		}
		return connection.getOutputStream();
	}

	public synchronized void disconnect() throws IOException {
		if (connected) {
			this.connection.close();
			this.connected = false;
		}
	}

}
