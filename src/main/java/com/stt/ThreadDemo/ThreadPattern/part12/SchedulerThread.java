package com.stt.ThreadDemo.ThreadPattern.part12;

public class SchedulerThread extends Thread{

	private final ActivationQueue queue;
	public SchedulerThread(ActivationQueue queue) {
		this.queue = queue;
	}
	
	//放入消息对象
	public void invoke(MethodRequest request){
		queue.putRequest(request);
	}
	
	//执行消息对象
	@Override
	public void run() {
		while(true){
			MethodRequest request = queue.takeRequest();
			request.execute();
		}
	}
	
	
}
