package com.stt.ThreadDemo.ThreadPattern.part09.test01;

import java.lang.reflect.InvocationTargetException;

public class FutureData implements Data{

	private RealData realdata = null;
	//警戒条件
	private boolean ready = false;
	//用于抛出的包裹的异常
	private InvocationTargetException exception = null;
	
	public synchronized void setReaLData(RealData realdata){
		if(ready){
			//如果已经设置过了，则balk
			return;
		}
		this.realdata = realdata;
		this.ready = true;
		this.notifyAll();
	}
	
	//这里设置抛出的异常，那么setRealData就不再执行了
	public synchronized void setException(Throwable throwable){
		if(ready){
			return;
		}
		this.exception = new InvocationTargetException(throwable);
		this.ready = true;
		this.notifyAll();
	}
	
	@Override
	public synchronized String getContent() throws InvocationTargetException  {
		//使用边界条件，Guard suspension
		while(!ready){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//如果有异常的产生，则抛出异常
		if(exception != null){
			throw exception;
		}
		return realdata.getContent();
	}
}
