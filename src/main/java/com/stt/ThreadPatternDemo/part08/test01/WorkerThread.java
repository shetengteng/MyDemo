package com.stt.ThreadPatternDemo.part08.test01;

public class WorkerThread extends Thread{

	private final Channel channel;
	//用于判断是否执行关闭操作.使用volatile也不是絕對的安全
	private volatile boolean terminated = false;
	
	public WorkerThread(String name,Channel channel) {
		super(name);
		this.channel = channel;
	}
	
	@Override
	public void run() {
		try{
			while(!terminated){
				try {
					Request request = channel.takeRequest();
					request.execute();
				} catch (Exception e) {
					terminated = true;
				}
			}
		}finally{
			System.out.println(Thread.currentThread().getName()+" end");
		}
	}
	
	public void stopThread(){
		terminated = true;
		interrupt();
	}
	
}
