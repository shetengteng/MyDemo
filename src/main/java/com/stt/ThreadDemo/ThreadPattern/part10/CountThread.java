package com.stt.ThreadDemo.ThreadPattern.part10;

public class CountThread extends Thread{

	private long counter = 0;

	// 是否已发送终止请求，发送过为true
	private volatile boolean shoutdownRequested = false;
	// 发出终止操作
	public void shutdownRequest(){
		shoutdownRequested = true;
		this.interrupt();
	}
	public boolean isShutdownRequested(){
		return shoutdownRequested;
	}
	
	@Override
	public void run() {
		try {
			while(!shoutdownRequested){
				doWork();
			}
		} catch (InterruptedException e) {
		}finally{
			doShutDown();
		}
	}
	
	private void doWork() throws InterruptedException{
		counter++;
		System.out.println("count:"+counter);
		Thread.sleep(500);
	}
	
	private void doShutDown(){
		System.out.println("shutDown--count:"+counter);
	}
	
}
