package com.stt.NetWorkDemo01.part03.test01;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static int port = 8000;

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(port, 3);
		System.out.println("start....");
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("new connection" + socket.getInetAddress() + ":" + socket.getPort());
			} catch (Exception e) {
			} finally {
				try {
					if (socket != null) {
						socket.close();
					}
				} catch (Exception e2) {
				}
			}
		}
	}

}
