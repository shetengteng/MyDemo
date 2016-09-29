package com.stt.NetWorkDemo01.part04.test01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable {

	private SocketChannel client;

	public Handler(SocketChannel client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			// 通过SocketChannel获取Socket对象
			Socket socket = client.socket();
			System.out.println("receive client ..." + socket.getInetAddress() + ":" + socket.getPort());

			// 获取输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// 获取输出流
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			String msg = null;
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
				pw.println("echo:" + msg);
				if (msg.equals("exit")) {
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (client != null) {
					client.close();
				}
			} catch (Exception e2) {
			}
		}
	}
}
