package com.stt.ThreadDemo.concurrentLibrary.Executor.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用有界队列时，作为线程池任务存储
 * 若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程，
 * 若大于corePoolSize，则会将任务加入队列，
 * 若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程，
 * 若线程数大于maximumPoolSize，则执行拒绝策略。或其他自定义方式。
 * 
 */
public class UseThreadPoolExecutor1 {

	public static void main(String[] args) {

		ThreadPoolExecutor pool = new ThreadPoolExecutor(
				// 初始化参数
				1, // corePoolSize 初始化线程个数
				2, // maxPoolSize 最大线程个数
				60, // 线程空闲时间，若线程的空闲时间超过该时间，同时当前线程个数>corePoolSize，则销毁该线程
				TimeUnit.MINUTES, // 空闲时间的单位
				new ArrayBlockingQueue<Runnable>(3) // 指定一种队列 （有界队列）
		);

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
		// pool.execute(mt6);// 线程的maxPoolSize和 队列的个数已经满了，此时再放入则抛出异常，但之前的任务依旧会执行

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

		/**结果：
		 * run task id:1
		run task id:5
		3
		--run task id:1
		--run task id:5
		run task id:2
		run task id:3
		1
		--run task id:2
		--run task id:3
		run task id:4
		--run task id:4
		 */
	}

}
