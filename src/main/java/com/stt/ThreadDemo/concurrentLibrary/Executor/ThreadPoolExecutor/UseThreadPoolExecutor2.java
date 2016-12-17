package com.stt.ThreadDemo.concurrentLibrary.Executor.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 对于有界序列，使用自定义的拒绝器示例
 * 有new DiscardOldestPolicy()这类拒绝器：拒绝最旧的任务
 */
public class UseThreadPoolExecutor2 {

	public static void main(String[] args) {

		ThreadPoolExecutor pool = new ThreadPoolExecutor(
				// 初始化参数
				1, // corePoolSize 初始化线程个数
				2, // maxPoolSize 最大线程个数
				60, // 线程空闲时间，若线程的空闲时间超过该时间，同时当前线程个数>corePoolSize，则销毁该线程
				TimeUnit.MINUTES, // 空闲时间的单位
				new ArrayBlockingQueue<Runnable>(3), // 指定一种队列 （有界队列）
				new MyRejected());

		MyTask mt1 = new MyTask(1, "任务1");
		MyTask mt2 = new MyTask(2, "任务2");
		MyTask mt3 = new MyTask(3, "任务3");
		MyTask mt4 = new MyTask(4, "任务4");
		MyTask mt5 = new MyTask(5, "任务5");
		MyTask mt6 = new MyTask(6, "任务6");

		pool.execute(mt1);// 执行第一个，直接放入线程中
		pool.execute(mt2);// 由于执行的过程中延时5s，因此，mt2放入队列中，当mt1执行完毕后再执行
		pool.execute(mt3);// 放入队列中
		pool.execute(mt4);// 放入队列中
		pool.execute(mt5);// 开启新的线程
		pool.execute(mt6);// 线程的maxPoolSize和 队列的个数已经满了，此时再放入则抛出异常，但之前的任务依旧会执行

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 查看队列的元素个数
		System.out.println(pool.getQueue().size());
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(pool.getQueue().size());

		pool.shutdown();
	}

}
