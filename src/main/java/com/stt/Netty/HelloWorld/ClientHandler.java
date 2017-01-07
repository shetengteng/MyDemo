package com.stt.Netty.HelloWorld;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// do something msg
			ByteBuf buf = (ByteBuf) msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String request = new String(data, "utf-8");
			System.out.println("Client: " + request);

			// 进行写操作的时候，可以不需要release操作
			ctx.write(msg);
			ctx.flush();

		} finally {
			// 在进行写数据的时候，不需要release操作，因为netty框架帮助做了，但是read操作依然要
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
