package com.stt.NetWorkDemo01.part03.test02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public void service() throws IOException {
		// 定义服务器端
		ServerSocket serverSocket = new ServerSocket(8000);
		System.out.println("server start...");
		while (true) {
			Socket socket = null;
			try {
				// 通过ServerSocket的监听获取客户端的服务
				socket = serverSocket.accept();
				// 开启一个线程
				Thread workThread = new Thread(new Handler(socket));
				workThread.start();
			} catch (Exception e) {
			}
		}
	}

	class Handler implements Runnable {
		private Socket socket;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			System.out.println("connection accepted:" + socket.getInetAddress() + " port:" + socket.getPort());
			try {
				// 从客户端输入
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// 输出给客户端
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				String msg = null;
				while ((msg = br.readLine()) != null) {
					System.out.println(msg);
					pw.write("echo:" + msg);
					if ("exit".equals(msg)) {
						break;
					}
				}
			} catch (IOException e) {
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
}
