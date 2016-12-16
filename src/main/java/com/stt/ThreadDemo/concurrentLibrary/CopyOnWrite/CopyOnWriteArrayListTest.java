package com.stt.ThreadDemo.concurrentLibrary.CopyOnWrite;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {

	/**
	 * CopyOnWriteArrayList
	 * 可能会出现的问题：在修改过程中有读取，但读取仍旧没有结束，而修改（删除）已经结束
	 */
	public static void main(String[] args) {

		// 测试 CopyOnWriteArrayList，测试一个线程读，一个线程写，读线程执行中嵌套着写线程，最后读的结果是否是最新的
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add("list_" + i);
		}
		// 这种情况下会抛出异常，边界异常
		// 是个坑，需要注意
		// 如果只是修改，而非删除操作的话，应该是没有问题的

		int sum = list.size();

		for (int i = 0; i < sum; i++) {
			System.out.println("---" + list.size());
			if (i == 4) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						list.remove(7);
					}
				}).start();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(list.get(i));
		}

	}

}
