package com.stt.ThreadPatternDemo.part03.test01;

import java.util.Random;

public class ServerThread  extends Thread{

	private Random random;
	private RequestQueue requestQueue;
	
	public ServerThread(String name,RequestQueue requestQueue,long seed){
		super(name);
		this.random = new Random(seed);
		this.requestQueue = requestQueue;
	}
	
	@Override
	public void run() {
		for(int i = 0;i<10000;i++){
			Request request = requestQueue.getRequest();
			System.out.println(Thread.currentThread().getName()+"--"+request);
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (Exception e) {
			}
		}
	}
	
}
