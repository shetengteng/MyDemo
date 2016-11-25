package com.stt.ThreadDemo.ThreadPattern.part12;

public class ActivationQueue {
	private static final int MAX_METHOD_REQUEST = 100;
	private final MethodRequest[] requestQueue;
	private int tail;//next putRequest的index
	private int head;//next takeRequest的index
	private int count;//MethodRequest的数量
	
	public ActivationQueue() {
		this.requestQueue = new MethodRequest[MAX_METHOD_REQUEST];
		this.tail = 0;
		this.head = 0;
		this.count = 0;
	}

	public synchronized void putRequest(MethodRequest request){
		while(count >= requestQueue.length){
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		requestQueue[tail] = request;
		tail = (tail+1) % requestQueue.length;
		count ++;
		notifyAll();
	}
	
	public synchronized MethodRequest takeRequest(){
		
		while(count<=0){
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		MethodRequest result = requestQueue[head];
		head = (head + 1) % requestQueue.length;
		count --;
		notifyAll();
		return result;
	}
	
}
