package com.stt.ThreadPatternDemo.part00;

public class Test01_02 {

	public static void main(String[] args) {
		class MyThread extends Thread {
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println("Good");
				}
			}
		}
		Thread t = new MyThread();
		t.start();
		for (int i = 0; i < 100; i++) {
			System.out.println("hello thread!");
		}
	}
}
