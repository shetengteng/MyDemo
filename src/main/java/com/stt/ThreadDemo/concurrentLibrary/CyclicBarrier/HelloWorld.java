package com.stt.ThreadDemo.concurrentLibrary.CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟运动员同时起跑
 * @author Administrator
 *
 */
public class HelloWorld {

	public static void main(String[] args) {

		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		CyclicBarrier barrier = new CyclicBarrier(3);
		threadPool.execute(new HelloWorld().new Runner(barrier, "zhangsan"));
		threadPool.execute(new HelloWorld().new Runner(barrier, "lisi"));
		threadPool.execute(new HelloWorld().new Runner(barrier, "wangwu"));

		threadPool.shutdown();
	}

	public class Runner implements Runnable {

		private CyclicBarrier barrier;
		private String name;

		public Runner(CyclicBarrier barrier, String name) {
			this.barrier = barrier;
			this.name = name;
		}

		@Override
		public void run() {
			try {
				System.out.println(name + " wait...");
				Thread.sleep(new Random().nextInt(5000));
				barrier.await();
				System.out.println(name + "go!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

	}
}
