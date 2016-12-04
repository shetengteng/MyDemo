package com.stt.ThreadDemo.concurrentLibrary.LockAndCondition.Condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 3个线程，分别依次运行，每个线程输出10次，然后整个依次循环10次
 * 
 * @author Administrator
 *
 */
public class HelloWorld {

	public static void main(String[] args) {
		MyTask task = new HelloWorld().new MyTask();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					task.task1(i + 1);
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					task.task2(i + 1);
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					task.task3(i + 1);
				}
			}
		}).start();
	}

	class MyTask {
		private Lock lock = new ReentrantLock();
		private Condition task1 = lock.newCondition();
		private Condition task2 = lock.newCondition();
		private Condition task3 = lock.newCondition();
		/** 边界条件 */
		private int orderId = 1;

		/** 第一个线程执行的任务 */
		public void task1(int no) {
			lock.lock();
			try {
				while (orderId != 1) {
					// 哪个线程不满足条件，就进行wait操作
					task1.await();
				}
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " NO:" + no + " task1:" + (i + 1));
				}
				orderId = 2;
				task2.signalAll();
			} catch (Exception e) {
			} finally {
				lock.unlock();
			}
		}

		public void task2(int no) {
			lock.lock();
			try {
				while (orderId != 2) {
					task2.await();
				}
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " NO:" + no + " task2:" + (i + 1));
				}
				orderId = 3;
				task3.signalAll();
			} catch (Exception e) {
			} finally {
				lock.unlock();
			}
		}

		public void task3(int no) {
			lock.lock();
			try {
				while (orderId != 3) {
					task3.await();
				}
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " NO:" + no + " task3:" + (i + 1));
				}
				orderId = 1;
				task1.signalAll();
			} catch (Exception e) {
			} finally {
				lock.unlock();
			}
		}
	}
}
