package com.stt.Netty.part03_TimeServerDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

	public void bind(int port) {
		// 创建bossGroup线程组,用于服务端接收客户端连接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 创建workGroup线程组,用于SocketChannel的网络读写
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			// 启动辅助类
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					// 绑定IO时间处理类，如日志和编码等
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new TimeServerHandler());
						}
					});
			// 绑定端口，同步等待成功，同步阻塞
			ChannelFuture future = bootstrap.bind(port).sync();
			// 等待服务器监听端口关闭，进行阻塞
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 优雅的退出，释放资源
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		int port = 9999;
		new TimeServer().bind(port);
	}
}
