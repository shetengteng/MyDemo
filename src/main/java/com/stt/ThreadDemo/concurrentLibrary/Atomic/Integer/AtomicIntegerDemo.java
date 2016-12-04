package com.stt.ThreadDemo.concurrentLibrary.Atomic.Integer;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(100);
						if (Counter.addOne() == 100) {
							System.out.println("counter = 100");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
		}
	}
}

class Counter {
	private static AtomicInteger counter = new AtomicInteger(0);

	public static long addOne() {
		return counter.incrementAndGet();
	}
}
