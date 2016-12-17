package com.stt.ThreadDemo.concurrentLibrary.Semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 模拟java内部的限流，同时只能有5个线程访问一段代码
 * @author Administrator
 *
 */
public class HelloWorld {

	public static void main(String[] args) {
		// 线程池
		ExecutorService threadPool = Executors.newCachedThreadPool();
		// 同时只能有5个线程访问，以后在实际生产中，应该依据经验，设置阈值
		final Semaphore semp = new Semaphore(5);
		// 模拟20个线程同时访问
		for (int i = 0; i < 20; i++) {
			final int no = i;
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						// 获取许可
						semp.acquire();
						System.out.println("thread " + no + " accessing");
						// 模拟实际业务处理时间
						Thread.sleep(new Random().nextInt(10000));
						// 释放许可，注意，其他线程也可以释放许可
						semp.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		// 退出线程池
		threadPool.shutdown();
	}

}
