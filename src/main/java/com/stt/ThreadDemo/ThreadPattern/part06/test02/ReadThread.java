package com.stt.ThreadDemo.ThreadPattern.part06.test02;

public class ReadThread extends Thread{
	private final Data data;
	public ReadThread(Data data) {
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			while(true){
				char[] readBuf = (char[]) data.read();
				System.out.println(Thread.currentThread().getName()+"::"+String.valueOf(readBuf));
			}
		} catch (Exception e) {
		}
	}
	
}
