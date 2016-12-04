package com.stt.ThreadDemo.concurrentLibrary.Atomic.Long;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 有问题
 * 
 * @author Administrator
 *
 */
public class AtomicLongDemo {

	public static void main(String[] args) {
		AtomicLongForTest test = new AtomicLongForTest();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					test.set0();
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					test.set1();
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					test.check();
				}
			}
		}).start();
	}
}

/**
 * 用于测试long的原子性的类 long类型的数组在0与1之间切换
 * 
 * @author Administrator
 */
class AtomicLongForTest {

	private static AtomicLong value = new AtomicLong(0);

	public void set0() {
		value.compareAndSet(-1, 0);
	}

	public void set1() {
		value.compareAndSet(0, -1);
	}

	public void check() {
		// 这里失败的原因是由于并发，导致判断失效的
		// if (0 != value.get() && -1 != value.get()) {
		// System.out.println(value.get());
		// System.err.println("ERROR");
		// }

		long temValue = value.get();
		if (0 != temValue && -1 != temValue) {
			System.out.println(temValue);
			System.err.println("ERROR");
		}
	}
}
