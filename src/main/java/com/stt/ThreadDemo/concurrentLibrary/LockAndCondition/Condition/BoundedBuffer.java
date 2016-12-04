package com.stt.ThreadDemo.concurrentLibrary.LockAndCondition.Condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列的实现 注意：先入先出
 * 
 * @author Administrator
 *
 */
public class BoundedBuffer {

	private final Lock lock = new ReentrantLock();
	/** 设置条件 */
	private final Condition put = lock.newCondition();
	private final Condition take = lock.newCondition();
	/** 定义队列中的存储单元 */
	private final Object[] items;
	/** 将要放入的下标 */
	private int putPtr;
	/** 将要取走的下标 */
	private int takePtr;
	private int count;

	public BoundedBuffer(int length) {
		items = new Object[length];
	}

	/**
	 * 放入条件是必须是items没有满
	 * 
	 * @param item
	 */
	public void put(Object item) {
		lock.lock();
		try {
			// 循环判断，防止虚假唤醒
			while (count == items.length) {
				put.await();
			}
			items[putPtr++] = item;
			if (putPtr == items.length) {
				putPtr = 0;
			}
			count++;
			take.signalAll();
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}

	public Object take() {
		lock.lock();
		try {
			while (count == 0) {
				take.await();
			}
			Object value = items[takePtr++];
			if (takePtr == items.length) {
				takePtr = 0;
			}
			count--;
			put.signalAll();
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return null;
	}

}
