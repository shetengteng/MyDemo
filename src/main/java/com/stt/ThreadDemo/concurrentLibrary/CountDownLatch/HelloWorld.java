package com.stt.ThreadDemo.concurrentLibrary.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * 示例：countDownLatch
 * @author Administrator
 *
 */
public class HelloWorld {

	public static void main(String[] args) {

		final CountDownLatch countDown = new CountDownLatch(2);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + "等待其他线程启动完毕后启动...");
					countDown.await();
					System.out.println(Thread.currentThread().getName() + "其他线程启动完毕，继续运行...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}, "t1").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + "线程启动");
					Thread.sleep(500);
					System.out.println(Thread.currentThread().getName() + "线程启动完毕");
					countDown.countDown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "t2").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + "线程启动");
					Thread.sleep(500);
					System.out.println(Thread.currentThread().getName() + "线程启动完毕");
					countDown.countDown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "t3").start();
	}

}
