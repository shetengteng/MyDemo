package com.stt.ThreadDemo.ThreadPattern.part04.test01;

public class SaveThread extends Thread {

	private Data data;
	public SaveThread(Data data){
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			while(true){
				data.save();
				//间隔是1s保存一下
				Thread.sleep(1000L);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
