package com.stt.ThreadPatternDemo.part03.test01;

import java.util.Random;

public class ClientThread extends Thread{
	//定义一个随机数，用来模拟发送时间的延时
	private Random random;
	private RequestQueue requestQueue; 
	
	public ClientThread(String name,RequestQueue queue,long seed) {
		super(name);
		this.requestQueue = queue;
		this.random = new Random(seed);
	}	
	
	@Override
	public void run() {
		
		for(int i=0;i<10000;i++){
			Request request = new Request("No."+i);
			System.out.println(Thread.currentThread().getName()+"::"+request);
			requestQueue.putRequest(request);

			try {
				//随机
				Thread.sleep(random.nextInt(1000));
			} catch (Exception e) {
			}
		}

	}
	
}
