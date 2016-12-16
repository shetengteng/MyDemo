package com.stt.ThreadDemo.ThreadPattern.part00.single;

public class SingleData {

	private static SingleData data;

	private SingleData() {
	}

	public static SingleData getInstance() {
		if (data == null) {
			System.out.println(Thread.currentThread().getName() + "--1");
			synchronized (SingleData.class) {
				if (data == null) {
					System.out.println(Thread.currentThread().getName() + "--2");
					data = new SingleData();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "--3");
				}
			}
		}
		return data;
	}
}
