package com.stt.ThreadPatternDemo.part03.test01;

import java.util.LinkedList;

public class RequestQueue {

	private final LinkedList<Request> queue = new LinkedList<>();
	public synchronized Request getRequest(){
		//当队列里面还有值时返回一个值
		while(queue.size() <=0){
			//这里使用if进行判断，而是用while进行判断是有原因的
			//如果使用了if，那么多个线程运行，通过了if后停止，
			//但是其他的线程将queue中的值取完了，则当该线程再运行时，
			//会出现异常
			//注意：wait后会释放锁给其他线程
			try {
				//此处该线程进行等待
				this.wait();
			} catch (Exception e) {
			}
		}
		//从顶部拿出一个对象返回
		return queue.removeFirst();
	}
	
	public synchronized void putRequest(Request request){
		//放在链表的底部
		queue.addLast(request);
		//通知其他线程，该队列里面有值了
		this.notifyAll();
	}
}
