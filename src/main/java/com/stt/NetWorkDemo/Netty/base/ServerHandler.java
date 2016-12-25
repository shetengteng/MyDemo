package com.stt.NetWorkDemo.Netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 将读取到的数据释放,注意这里的msg是ByteBuf类型，是Netty的类
		// 这里的功能是将读取到的数据不做任何的处理
		((ByteBuf) msg).release();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 这里是链接失败或者异常情况，这里用于捕获处理
		cause.printStackTrace();
		// 关闭上下文处理
		ctx.close();
	}
}
