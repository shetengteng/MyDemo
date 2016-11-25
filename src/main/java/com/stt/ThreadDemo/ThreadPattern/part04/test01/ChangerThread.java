package com.stt.ThreadDemo.ThreadPattern.part04.test01;

import java.util.Random;

public class ChangerThread extends Thread{

	private Data data;
	private Random random = new Random();
	public ChangerThread(String name,Data data) {
		super(name);
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			for(int i = 0;true;i++){
				//修改数据
				data.change("No."+i);
				//模拟时间间隔
				Thread.sleep(random.nextInt(1000));
				//明确要求保存
				data.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
