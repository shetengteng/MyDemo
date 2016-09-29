package com.stt.NetWorkDemo01.part04.test02;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {

	public void service() {
		// 定义一个选择器，用于存放selectionKey
		Selector selector = null;
		ServerSocketChannel server = null;
		try {
			selector = Selector.open();
			server = ServerSocketChannel.open();
			// 设置该端口可以继续访问，用于重启可以使用该端口
			server.socket().setReuseAddress(true);
			// 配置为非阻塞模式
			server.configureBlocking(false);
			// 服务器进程与本地端口绑定
			server.socket().bind(new InetSocketAddress(8000));

			// 注册服务,可以返回一个SelectionKey，该key表示server的服务key
			server.register(selector, SelectionKey.OP_ACCEPT);

			while (selector.select() > 0) {// 该方法为阻塞方法
				// 获取selected-Keys集合
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectedKeys.iterator();
				// 使用while循环处理key
				// 依次询问已发生的事件，然后处理该事件
				while (iter.hasNext()) {
					SelectionKey key = null;
					try {
						key = iter.next();
						iter.remove();// 由于获取后要删除该key，因此不使用增强for

						if (key.isAcceptable()) {
							// 如果isAcceptable返回为true，表示已经有连接就绪事件的发生
							// 处理接收连接就绪事件
							// 获得与SelectionKey关联的ServerSocketChannel
							ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
							// 此时获得一个客户端Channel
							// 在非阻塞的模式下，如果没有连接则返回null
							SocketChannel socketChannel = ssc.accept();
							if (socketChannel != null) {
								System.out.println("socketChannel client:" + socketChannel.socket().getInetAddress()
										+ ":" + socketChannel.socket().getPort());
								// 将client设置为非阻塞
								socketChannel.configureBlocking(false);
								// 创建缓冲区
								ByteBuffer buffer = ByteBuffer.allocate(1024);
								// SocketChannel向Selector注册读就绪事件和写就绪事件
								// 这里关联了一个buffer的附件
								socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, buffer);
							}
						}

						if (key.isReadable()) {
							// 处理读就绪事件,返回true，则表示该事件发生
							// 获得客户端
							SocketChannel socketChannel = (SocketChannel) key.channel();
							ByteBuffer buffer = (ByteBuffer) key.attachment();
							ByteBuffer readBuffer = ByteBuffer.allocate(32);
							socketChannel.read(readBuffer);
							readBuffer.flip();// 此时limit = position
							// 将buffer的limit设置为容量
							buffer.limit(buffer.capacity());
							// 假设buffer容量很大，可以使用可变的缓冲区ChannelIO类
							// 在非阻塞模式下，读取的字符的个数不确定，需要拼装到buffer中
							buffer.put(readBuffer);
						}
						if (key.isWritable()) {
							// 处理写就绪事件,有一行数据就输出一行数据，没有则不执行，做其他的条件
							SocketChannel socketChannel = (SocketChannel) key.channel();
							ByteBuffer buffer = (ByteBuffer) key.attachment();
							buffer.flip();// 将limit=position-->position=0
							// 进行编码
							String data = Charset.forName("GBK").decode(buffer).toString();
							// 如果不包含回行，则返回
							if (data.indexOf("\r\n") == -1)
								return;
							// 截取一行数据
							String outMsg = data.substring(0, data.indexOf("\n") + 1);
							System.out.println(outMsg);
							// 将字符串进行GBK编码为byteBuffer类型
							ByteBuffer outputBuffer = Charset.forName("GBK").encode("echo:>" + outMsg);

							// 输出outputBuffer中的字节
							// 判断是否还有字节，remaining=(limit-position)
							while (outputBuffer.hasRemaining()) {
								socketChannel.write(outputBuffer);
							}
							// 去除buffer中已经发送的数据
							ByteBuffer tmp = Charset.forName("GBK").encode(outMsg);
							buffer.position(tmp.limit());
							// 删除操作
							buffer.compact();
							// 如果已经输出了"exit\r\n"则退出
							if (outMsg.equals("exit\r\n")) {
								key.cancel();
								socketChannel.close();
								System.out.println("close connection...");
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
						try {
							if (key != null) {
								// 使该key失效
								// 使Selector不再监控该key
								key.cancel();
								// 关闭该key关联的SocketChannel
								key.channel().close();
							}
						} catch (Exception e2) {
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
