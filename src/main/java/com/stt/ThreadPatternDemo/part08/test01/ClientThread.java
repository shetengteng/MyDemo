package com.stt.ThreadPatternDemo.part08.test01;

import java.util.Random;

public class ClientThread extends Thread{

	private final Channel channel;
	private static final Random random = new Random();
	private volatile boolean terminated = false;
	
	public ClientThread(String name,Channel channel) {
		super(name);
		this.channel = channel;
	}
	@Override
	public void run() {
		try{
			for(int i =0;!terminated;i++){
				try {
						Request request = new Request(getName(), i);
						channel.putRequest(request);
						Thread.sleep(random.nextInt(1000));
				} catch (Exception e) {
					terminated = true;
				}
			}
		}finally{
			System.out.println(Thread.currentThread().getName()+" end.");
		}
	}
	
	public void stopThread(){
		terminated = true;
		interrupt();
	}
	
}
