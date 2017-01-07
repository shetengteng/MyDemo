package com.stt.Netty.HelloWorld;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MultiClient {

	public static void main(String[] args) throws Exception {

		EventLoopGroup workgroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workgroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast(new ClientHandler());
			}
		});

		ChannelFuture cf1 = b.connect("127.0.0.1", 8888).sync();
		ChannelFuture cf2 = b.connect("127.0.0.1", 8889).sync();

		// buf
		// 在进行写数据的时候，不需要release操作，因为netty框架帮助做了，但是read操作依然要
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("777".getBytes()));
		cf2.channel().writeAndFlush(Unpooled.copiedBuffer("888".getBytes()));

		cf1.channel().closeFuture().sync();
		cf2.channel().closeFuture().sync();
		workgroup.shutdownGracefully();

	}
}
