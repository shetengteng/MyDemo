package com.stt.Netty.part04_TcpPackage.LineBaseFrameDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class TimeClientHandler extends ChannelHandlerAdapter {

	private int count;
	private byte[] req;

	// 当客户端和服务端建立链接成功之后，Netty的NIO线程会调用channelActive方法
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 100; i++) {
			byte[] msg = ("QUERY_TIME" + System.getProperty("line.separator")).getBytes();
			// 初始化一个byteBuf类型
			ByteBuf sendMsg = Unpooled.buffer(msg.length);
			sendMsg.writeBytes(msg);
			ctx.writeAndFlush(sendMsg);
		}
	}

	// 服务端返回消息后，会滴啊用channelRead方法
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Now is:" + (String) msg + "--count:" + (++count));
		// 读取后需要释放资源
		ReferenceCountUtil.release(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
