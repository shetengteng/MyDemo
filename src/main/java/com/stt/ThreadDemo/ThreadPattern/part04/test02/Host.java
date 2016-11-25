package com.stt.ThreadDemo.ThreadPattern.part04.test02;

public class Host {

	private final long timeout;
	private boolean ready = false;
	
	public Host(long timeout){
		this.timeout = timeout;
	}
	
	//更改状态
	public synchronized void setExecutable(boolean on){
		ready = on;
		this.notifyAll();
	}
	
	public synchronized void execute() throws InterruptedException{
		long start = System.currentTimeMillis();//开始时间
		while(!ready){
			long now = System.currentTimeMillis();//现在的时间
			long rest = timeout-(now - start);//剩下的时间
			if(rest <= 0){
				
				throw new TimeoutException("timeout:"+rest);
			}
			wait(rest);
		}
		doExecute();
	}
	
	private void doExecute(){
		System.out.println(Thread.currentThread().getName()+"：doExecute");
	}
	
}
