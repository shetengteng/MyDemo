package com.stt.ThreadDemo.ThreadPattern.part07;

public class Helper {

	public void handle(int count ,char c){
		System.out.println("handle count"+count+"::"+c+" begin");
		for(int i = 0;i<count;i++){
			slowly();//模拟执行延时
			System.out.println(c);
		}
		System.out.println("handle count"+count+"::"+c+" end");
	}

	private void slowly()  {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}
	
}
