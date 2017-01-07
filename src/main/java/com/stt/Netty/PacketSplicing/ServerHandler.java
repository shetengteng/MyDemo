package com.stt.Netty.PacketSplicing;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

	// 在通道刚启动的时候可以做一些处理
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(" server channel active... ");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String request = (String) msg;
		System.out.println("recevie:" + request);
		// 已$_结尾
		String response = "服务器响应：" + msg + "$_";
		// 注意：即使在pipeline中添加了StringDecoder，只是针对接收数据而言，而发送数据，依然只能发送ByteBuf类型，否则失败
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) throws Exception {
		ctx.close();
	}

}
