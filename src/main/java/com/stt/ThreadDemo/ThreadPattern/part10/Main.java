package com.stt.ThreadDemo.ThreadPattern.part10;

public class Main {
	public static void main(String[] args) {
		System.out.println("begin");
		
		CountThread t = new CountThread();
		t.start();
		try {
			Thread.sleep(10000);
			System.out.println("main:shutdown");
			t.shutdownRequest();
			System.out.println("main:join");
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
