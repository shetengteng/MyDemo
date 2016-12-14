package com.stt.ThreadDemo.base.notifyAndWait;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程1往里面放元素 线程2判断是五个的时候打印消息 缺点是：不实时，因为t1没有释放锁
 * ，可以使用concurrent下的CountDownLatch进行拦截
 * 
 * @author Administrator
 *
 */
public class ListAdd {

	public static void main(String[] args) {

		final List<String> list = new ArrayList<>();
		final Object lock = new Object();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					for (int i = 0; i < 10; i++) {
						list.add("add : " + i);
						System.out.println(list.get(i));
						if (i == 4) {
							// 注意：notify是不会释放锁的
							lock.notify();
						}
					}
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					try {
						// wait操作会释放锁
						// 这里先wait，满足条件后直接从此处执行下面的语句
						if (list.size() != 5)
							lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("t2 print...");
				}
			}
		});
		// 注意是t2先启动
		t2.start();
		t1.start();
	}
}
