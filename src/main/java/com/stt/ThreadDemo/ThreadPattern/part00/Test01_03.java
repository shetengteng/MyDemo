package com.stt.ThreadDemo.ThreadPattern.part00;

public class Test01_03 {

	public static void main(String[] args) {
		class PrintThread extends Thread {
			private String message;
			public PrintThread(String msg){
				this.message = msg;
			}
			
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println(message);
				}
			}
		}
		new PrintThread("Hello").start();
		new PrintThread("Thread").start();
	}
}
