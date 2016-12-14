package com.stt.NetWorkDemo.part04.test01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用阻塞的方式创建服务器
 * 
 * @author Administrator
 */
public class EchoServer {

	public void service() {
		// 定义线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(4 * Runtime.getRuntime().availableProcessors());
		// 创建一个ServerSocketChannel对象
		ServerSocketChannel server = null;
		try {
			// 通过静态方法获取ServerSocketChannel对象实例
			server = ServerSocketChannel.open();
			// 获取channel对应的ServerSocket对象
			// 设置使得在同一个主机上关闭服务器程序，接着再启动该服务器程序时，
			// 可以顺利绑定到相同的端口
			server.socket().setReuseAddress(true);
			// 将服务与本地的port绑定
			server.socket().bind(new InetSocketAddress(8000));
			System.out.println("server start....");

			// 开始监听客户端
			while (true) {
				SocketChannel client = null;

				client = server.accept();
				// 将监听的得到的客户端交给线程池中的任务进行处理
				threadPool.execute(new Handler(client));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
