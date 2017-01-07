package com.stt.Netty.part04_TcpPackage.LineBaseFrameDecoder;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

	private int count;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String separator = System.getProperty("line.separator");
		String ret = (String) msg;
		System.out.println("The time server receive order:" + ret + " --count:" + (++count));

		// 如果是Query Time ，那么就返回当前时间，否则返回Bad Query
		String currentTime = "BAD_QUERY";
		if ("QUERY_TIME".equalsIgnoreCase(ret)) {
			currentTime = new Date().toString() + separator;
		}
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 从性能的角度出发，为了防止频繁的唤醒Selector进行消息的发送
		// Netty的Write方法不直接将消息写入SocketChannel中，而是将发送的消息发送到缓冲数据组中
		// 最后调用flush方法，将缓冲区的数据全部写到SocketChannel中
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 发生异常时，关闭ChannelHandlerContext，释放相关资源
		ctx.close();
	}
}
