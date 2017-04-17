package com.stt.ThreadDemo.otherTest;

public class ThreadTest {

	public static void main(String[] args) {

		Handler myHandler = new MyHander();

		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						myHandler.test();
					}
				}
			}).start();
		}

	}

}
