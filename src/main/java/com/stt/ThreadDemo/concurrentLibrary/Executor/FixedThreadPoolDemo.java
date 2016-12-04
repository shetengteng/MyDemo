package com.stt.ThreadDemo.concurrentLibrary.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolDemo {

	public static void main(String[] args) {
		// 定义常规线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("hello thread pool");
			}
		});
	}
}
