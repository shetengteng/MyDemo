package com.stt.NetWorkDemo.Netty.HelloWorld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MulitServer {

	public static void main(String[] args) {
		// EventLoopGroup 用于处理IO操作的多线程循环器
		// bossGroup 用于接收进来的链接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// workerGroup 用于处理被接收的数据
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {

			// 启动NIO服务的辅助类
			ServerBootstrap bootStrap = new ServerBootstrap();
			bootStrap.group(bossGroup, workerGroup);
			// 指定使用NioServerSocketChannel类举例说明一个新的Channel如何接收进来的链接
			bootStrap.channel(NioServerSocketChannel.class);
			// 使用ChannelInitializer 配置一个新的Channel
			bootStrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sch) throws Exception {
					sch.pipeline().addLast(new ServerHandler());
				}
			});
			// option 提供给NioServerSocketChannel 用来接收进来的连接
			// 设置TCP的缓冲区
			bootStrap.option(ChannelOption.SO_BACKLOG, 128);
			// 使用childOption 提供给父管道ServerChannel接收到的连接
			// 保持连接
			bootStrap.childOption(ChannelOption.SO_KEEPALIVE, true);

			// 可以绑定多个端口
			ChannelFuture future = bootStrap.bind(8888).sync();
			ChannelFuture future2 = bootStrap.bind(8889).sync();

			// 在存储阻塞，等待关闭，类似与ThreadSleep
			future.channel().closeFuture().sync();
			future2.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
