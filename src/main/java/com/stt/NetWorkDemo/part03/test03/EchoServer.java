package com.stt.NetWorkDemo.part03.test03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) throws IOException {

		// Runtime的availableProcessor()方法返回当前系统的CPU的数目
		// CPU的数目越多，则工作在线程池中的线程也就是越多
		int poolSize = 4 * Runtime.getRuntime().availableProcessors();
		ThreadPool threadPool = new ThreadPool(poolSize);
		ServerSocket server = new ServerSocket(8000);
		System.out.println("server start...");
		while (true) {
			Socket client = null;
			try {
				client = server.accept();
				// 将通信任务交给线程池
				threadPool.execute(new Handler(client));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

class Handler implements Runnable {
	private final Socket client;

	public Handler(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		// client的操作
	}
}
