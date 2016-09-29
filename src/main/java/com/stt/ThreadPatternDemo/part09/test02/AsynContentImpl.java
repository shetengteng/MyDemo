package com.stt.ThreadPatternDemo.part09.test02;

public class AsynContentImpl implements Content{

	private SynContentImpl realContent;
	private boolean ready = false;
		
	public synchronized void setSynContent(SynContentImpl realContent){
		//只有一个线程调用此函数，可以使用if判断
		if(ready){
			return;
		}
		this.realContent = realContent;
		this.ready = true;
		this.notifyAll();
	}
	
	@Override
	public synchronized byte[] getBytes() {
		while(!ready){
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		return realContent.getBytes();
	}

}
