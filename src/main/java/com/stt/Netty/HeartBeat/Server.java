package com.stt.Netty.HeartBeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {

	public static void main(String[] args) {

		EventLoopGroup bossGroup = new NioEventLoopGroup();// 接收连接
		EventLoopGroup workGroup = new NioEventLoopGroup();// 处理数据
		ServerBootstrap bootstrap = new ServerBootstrap();// NIO启动辅助类
		bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)// 设置存放3次握手的TCP连接的队列长度
				.handler(new LoggingHandler(LogLevel.INFO))// 设置日志等级
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						// 添加方法，使用职责链模式
						// 添加解码编码对象，该对象是Netty下的handler对象，那么肯定是实现了ChannelHandler接口
						ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
						ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
						ch.pipeline().addLast(new ServerHeartBeatHandler());
					}
				});
		try {
			// 绑定端口
			ChannelFuture cf = bootstrap.bind(8888).sync();
			// 等待关闭，类似与ThreadSleep
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

}
