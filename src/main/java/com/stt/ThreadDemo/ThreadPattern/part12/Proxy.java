package com.stt.ThreadDemo.ThreadPattern.part12;

public class Proxy implements ActiveObject{

	private final SchedulerThread scheduler;
	private final Servant servant;
	
	public Proxy(SchedulerThread scheduler,Servant servant) {
		this.scheduler = scheduler;
		this.servant = servant;
	}
	
	@Override
	public Result makeString(int count, char c) {
		FutureResult future = new FutureResult(); 
		scheduler.invoke(new MakeStringRequest(servant, future, count, c));
		return future;
	}

	@Override
	public void displayString(String str) {
		scheduler.invoke(new DisplayStringRequest(servant, str));
	}

}
