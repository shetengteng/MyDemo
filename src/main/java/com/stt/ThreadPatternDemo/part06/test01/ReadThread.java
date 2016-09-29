package com.stt.ThreadPatternDemo.part06.test01;

public class ReadThread extends Thread{
	private final Data data;
	public ReadThread(Data data) {
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			while(true){
				char[] readBuf = data.read();
				System.out.println(Thread.currentThread().getName()+"::"+String.valueOf(readBuf));
			}
		} catch (Exception e) {
		}
	}
	
}
