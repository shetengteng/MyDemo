package com.stt.NetWorkDemo.part10_reflection.test04_remoteCallByProxy.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 负责建立与远端服务器的连接，接收发送socket对象
 * 
 * @author Administrator
 *
 */
public class Connector {

	private String host;
	private int port;

	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;

	public Connector(String host, int port) throws Exception {
		this.host = host;
		this.port = port;
		// 在初始化的时候，建立连接
		connect(host, port);
	}

	public void connect(String host, int port) throws Exception {
		socket = new Socket(host, port);
		out = socket.getOutputStream();
		in = socket.getInputStream();
		objOut = new ObjectOutputStream(out);
		objIn = new ObjectInputStream(in);
	}

	public void send(Object obj) throws IOException {
		objOut.writeObject(obj);
	}

	public Object receive() throws Exception {
		return objIn.readObject();
	}

	public void close() {
		if (socket != null) {
			try {
				objIn.close();
				objOut.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
