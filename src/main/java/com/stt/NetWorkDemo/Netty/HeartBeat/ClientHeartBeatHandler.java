package com.stt.NetWorkDemo.Netty.HeartBeat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHeartBeatHandler extends ChannelHandlerAdapter {

	private static String SUCCESS_KEY = "auth_success_key";
	private static String FAILED_KEY = "auth_failed_key";
	private static String REQUESTINFO_RECEIVED = "requestInfo_received";
	private static String CONNECT_FAILURE = "connect_failure";

	// 创建定时运行的线程池
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> heartBeat;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String ip = InetAddress.getLocalHost().getHostAddress();
		// 通道建立时，发送认证信息
		ctx.writeAndFlush(ip + ",111");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			if (msg instanceof String) {
				if (SUCCESS_KEY.equals((String) msg)) {
					// 验证通过，发送心跳信息
					heartBeat = scheduler.scheduleWithFixedDelay(new HeartBeatTask(ctx), 0, 2, TimeUnit.SECONDS);
				}
				System.out.println(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 读取后要释放资源
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if (heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}

	private class HeartBeatTask implements Runnable {
		private final ChannelHandlerContext ctx;

		public HeartBeatTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			try {
				RequestInfo info = new RequestInfo();
				info.setIp(InetAddress.getLocalHost().getHostAddress());
				Sigar sigar = new Sigar();
				CpuPerc cpuPerc = sigar.getCpuPerc();
				HashMap<String, Object> cpuPercMap = new HashMap<String, Object>();
				cpuPercMap.put("combined", cpuPerc.getCombined());
				cpuPercMap.put("user", cpuPerc.getUser());
				cpuPercMap.put("sys", cpuPerc.getSys());
				cpuPercMap.put("wait", cpuPerc.getWait());
				cpuPercMap.put("idle", cpuPerc.getIdle());
				Mem mem = sigar.getMem();
				HashMap<String, Object> memoryMap = new HashMap<String, Object>();
				memoryMap.put("total", mem.getTotal() / 1024L);
				memoryMap.put("used", mem.getUsed() / 1024L);
				memoryMap.put("free", mem.getFree() / 1024L);
				info.setCpuPerMap(cpuPercMap);
				info.setMemoryMap(memoryMap);
				System.out.println("---send---" + info);
				ctx.writeAndFlush(info);
			} catch (UnknownHostException | SigarException e) {
				e.printStackTrace();
			}
		}
	}
}
