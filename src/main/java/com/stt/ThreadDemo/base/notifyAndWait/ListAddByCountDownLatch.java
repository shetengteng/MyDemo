package com.stt.ThreadDemo.base.notifyAndWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 使用countDownLatch的方式比wait和notify要高效
 * 
 * @author Administrator
 *
 */
public class ListAddByCountDownLatch {

	public static void main(String[] args) {

		final List<String> list = new ArrayList<>();
		// 这里的1表示发送countDown的次数，只有满足了发送次数，await才能解除
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					list.add("add : " + i);
					System.out.println(list.get(i));
					if (i == 4) {
						// 满足5的时候，线程t2可以运行了
						countDownLatch.countDown();
					}
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (list.size() != 5) {
						// 不满足5的时候，t2线程在运行到此处
						countDownLatch.await();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("t2 print...");
			}
		});
		// 注意是t2先启动
		t2.start();
		t1.start();
	}
}
