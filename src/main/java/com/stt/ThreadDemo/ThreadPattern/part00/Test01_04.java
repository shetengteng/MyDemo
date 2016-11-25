package com.stt.ThreadDemo.ThreadPattern.part00;

public class Test01_04 {

	public static void main(String[] args) {
		class PrintThread implements Runnable {
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
		new Thread(new PrintThread("Hello")).start();
		new Thread(new PrintThread("Thread")).start();
	}
}
