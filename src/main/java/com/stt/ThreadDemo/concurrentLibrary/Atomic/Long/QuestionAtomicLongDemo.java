package com.stt.ThreadDemo.concurrentLibrary.Atomic.Long;

public class QuestionAtomicLongDemo {

	public static void main(String[] args) {
		ForTest test = new ForTest();
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
class ForTest {

	private long value = 0;

	public void set0() {
		value = 0;
	}

	public void set1() {
		value = -1;
	}

	public void check() {
		if (0 != value && -1 != value) {
			System.out.println(value);
			System.err.println("ERROR");
		}
	}
}
