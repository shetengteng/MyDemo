package com.stt.ThreadPatternDemo.part10.test01;

public class GracefulThread extends Thread{

	private volatile boolean shutdownRequested = false;
	
	//终止请求
	public final void shutdownRequest(){
		shutdownRequested = true;
		this.interrupt();
	}
	
	// 判断终止请求是否已经送出
	public final boolean isShutdownRequested(){
		return shutdownRequested;
	}
	
	//操作
	public final void run(){
		try {
			while(!shutdownRequested){
				doWork();
			}			
		} catch (InterruptedException e) {
		}finally{
			doShutdown();
		}
	}
	
	protected void doWork() throws InterruptedException{
		
	}
	//终止处理
	protected void doShutdown(){
		
	}
	
}
