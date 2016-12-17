package com.stt.ThreadDemo.concurrentLibrary.Executor.ThreadPoolExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class MyRejected implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("自定义拒绝处理类");
		System.out.println(r.toString());
		// 一般对于自定义的拒绝器处理类，是做日志进行存储的
		System.out.println(executor.getQueue().size());
	}

}
