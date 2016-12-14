package com.stt.NetWorkDemo.part03.test01;

import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception {

		final int length = 100;
		String host = "localhost";
		int port = 8000;
		Socket[] sockets = new Socket[length];
		for (int i = 0; i < length; i++) {
			sockets[i] = new Socket(host, port);
			System.out.println("success--" + i);
		}
		Thread.sleep(2000);
		for (int i = 0; i < length; i++) {
			sockets[i].close();
		}

	}

}
