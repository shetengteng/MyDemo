package com.stt.Netty.HeartBeat;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHeartBeatHandler extends ChannelHandlerAdapter {

	// 定义存储用户ip和key，用于认证使用，在实际项目中，存储在缓存或者数据库中
	private static HashMap<String, String> AUTH_IP_MAP = new HashMap<>();
	private static String SUCCESS_KEY = "auth_success_key";
	private static String FAILED_KEY = "auth_failed_key";
	private static String REQUESTINFO_RECEIVED = "requestInfo_received";
	private static String CONNECT_FAILURE = "connect_failure";

	static {
		// 从缓存中加载
		AUTH_IP_MAP.put("192.168.14.61", "111");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(msg);
		if (msg instanceof String) {
			auth(ctx, msg);
		} else if (msg instanceof RequestInfo) {
			// 通过验证，则检验状态信息
			RequestInfo info = (RequestInfo) msg;
			System.out.println("-----------" + info.getIp() + "----------");
			System.out.println("当前主机IP为：" + info.getIp());
			System.out.println("当前主机CPU的情况----");
			Map<String, Object> cpu = info.getCpuPerMap();
			System.out.println("总使用率：" + cpu.get("combined"));
			System.out.println("用户使用率：" + cpu.get("user"));
			System.out.println("系统使用率：" + cpu.get("sys"));
			System.out.println("等待率：" + cpu.get("wait"));
			System.out.println("空闲率：" + cpu.get("idle"));
			System.out.println("当前主机Memory情况----");
			Map<String, Object> memory = info.getMemoryMap();
			System.out.println("内存总量：" + memory.get("total"));
			System.out.println("当前内存使用量：" + memory.get("used"));
			System.out.println("当前内存剩余量：" + memory.get("free"));
			System.out.println("-----------------------------------");
			ctx.writeAndFlush(REQUESTINFO_RECEIVED);
		} else {
			ctx.writeAndFlush(CONNECT_FAILURE).addListener(ChannelFutureListener.CLOSE);
		}
	}

	// 进行认证操作
	private boolean auth(ChannelHandlerContext ctx, Object msg) {
		String[] ret = ((String) msg).split(",");
		String key = AUTH_IP_MAP.get(ret[0]);
		if (key != null && key.equals(ret[1])) {
			ctx.writeAndFlush(SUCCESS_KEY);
			return true;
		} else {
			ChannelFuture future = ctx.writeAndFlush(FAILED_KEY);
			// 添加关闭的监听器
			future.addListener(ChannelFutureListener.CLOSE);
			return false;
		}
	}

}
