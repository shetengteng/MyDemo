package com.stt.Netty.part05_TcpPackage.DelimiterBasedFrameDecoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter {

	private int count;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client channelActive");
		for (int i = 0; i < 10; i++)
			ctx.writeAndFlush(Unpooled.copiedBuffer("hello$_".getBytes()));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("client received :" + msg + "---count:" + (++count));
		ReferenceCountUtil.release(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}
