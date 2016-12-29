package com.stt.NetWorkDemo.Netty.HelloWorld;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 将读取到的数据释放,注意这里的msg是ByteBuf类型，是Netty的类
		// 这里的功能是将读取到的数据不做任何的处理
		// ((ByteBuf) msg).release();

		// 打印接收的数据
		ByteBuf in = (ByteBuf) msg;
		try {
			while (in.isReadable()) {
				System.out.println((char) in.readByte());
				System.out.flush();
			}
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
