package com.stt.ThreadPatternDemo.part08.test01;

public class Channel {

	private static final int MAX_REQUEST = 100;
	private final Request[] requestQueue;
	private int tail;//下一个putRequest的索引位置
	private int head;//下一个takeRequest的索引位置
	private int count;//request的数目
	
	private final WorkerThread[] threadPool;
	
	public Channel(int threadNum) {
		this.requestQueue = new Request[MAX_REQUEST];
		this.tail = 0;
		this.head = 0;
		this.count = 0;
		
		threadPool = new WorkerThread[threadNum];
		//初始化线程池，线程池中都是WorkerThread线程
		for(int i = 0;i<threadPool.length;i++){
			threadPool[i] = new WorkerThread("worker"+i,this);
		}
	}
	public void startWorkers(){
		for(int i = 0;i<threadPool.length;i++){
			threadPool[i].start();
		}
	}
	public synchronized void putRequest(Request request){
		//警戒条件，如果requestQueue中的数目已经到达了
		//所设定的最大值，那么，进行再次放入Request操作的线程进入waitset操作
		while(count >= requestQueue.length){
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		//将Request放入Queue中
		requestQueue[tail] = request;
		tail = (tail + 1) % requestQueue.length;
		count ++;
		//通知其他线程进行take操作
		notifyAll();
	}
	public synchronized Request takeRequest(){
		while(count <= 0){
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		Request request = requestQueue[head]; 
		head = (head + 1)%requestQueue.length;
		count --;
		notifyAll();
		return request;
	}
	public void stopAllWorkerThread(){
		for(int i = 0; i< threadPool.length; i++){
			threadPool[i].stopThread();
		}
	}
}
