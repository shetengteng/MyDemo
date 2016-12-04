package com.stt.ThreadDemo.concurrentLibrary.Executor;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolDemo {

	public static void main(String[] args) {
		// 定义定时器线程池
		// 只在10s后执行一次
		Executors.newScheduledThreadPool(3).schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("hello timer pool");
			}
		}, 10, TimeUnit.SECONDS);
		// 以固定的频率执行
		// 初始延时10s，后每隔2秒执行一次
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("hello timer pool2");
			}
		}, 10, 2, TimeUnit.SECONDS);
	}

}
