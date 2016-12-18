package com.stt.ThreadDemo.concurrentLibrary.Lock_Condition.ReadWriteLock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HelloWorld {
	public static void main(String[] args) {
		Data data = new Data();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					data.put(new Random().nextInt(20));
				}
			}).start();
		}
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					data.get();
				}
			}).start();
		}
	}
}

class Data {

	private Object value = null;

	// 设置读写锁
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	// 读操作
	public void get() {
		// 获取读锁
		lock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "ready to read:" + value);
			Thread.sleep(new Random().nextInt(2000));
			System.out.println(Thread.currentThread().getName() + "haved to read:" + value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放读锁
			lock.readLock().unlock();
		}
	}

	// 写操作
	public void put(Object value) {
		lock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "ready to write:" + value);
			Thread.sleep(new Random().nextInt(2000));
			this.value = value;
			System.out.println(Thread.currentThread().getName() + "have to write:" + value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}
}
