package com.stt.ThreadDemo.ThreadPattern.part00;

public class Test01_05 {

	public static void main(String[] args) {
		class PrintThread implements Runnable {
			private String message;
			public PrintThread(String msg){
				this.message = msg;
			}
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(message);
				}
			}
		}
		new Thread(new PrintThread("Hello")).start();
	}
}
