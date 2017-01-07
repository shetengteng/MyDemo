package com.stt.Netty.HelloWorld;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler2 extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			ByteBuf buf = (ByteBuf) msg;
			byte[] data = new byte[buf.readableBytes()];
			// 将数据读取到data中
			buf.readBytes(data);
			String request = new String(data, "utf-8");
			System.out.println(request);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 这里是链接失败或者异常情况，这里用于捕获处理
		cause.printStackTrace();
		// 关闭上下文处理
		ctx.close();
	}
}
