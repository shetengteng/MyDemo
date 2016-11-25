package com.stt.ThreadDemo.ThreadPattern.part05.test01;

public class Table {

	private final String[] buffer;
	private int tail;//将消息的编号放在buffer的末尾索引tail处
	private int head;
	private int count;
	
	Table(int count){
		this.buffer = new String[count];
		this.head = 0;
		this.tail = 0;
		this.count =0;
	}
	
	//放置消息
	public synchronized void put(String cake) throws InterruptedException {
		
		System.out.println(Thread.currentThread().getName()+" put "+cake);
		while(count>=buffer.length){
			//让所有的生产者休息
			wait();
		}
		buffer[tail] = cake;
		//每次放置一个后添加一
		tail = ( tail + 1 ) % buffer.length;
		count++;
		//通知所有在waitset中的消费者
		notifyAll();
	}
	
	//提取消息
	public synchronized String take() throws InterruptedException {
		
		//当消息已经没有了
		while(count <= 0){
			//让所有的消费者休息
			wait();
		}
		//获取顶部的消息
		String cake = buffer[head];
		head = (head + 1) % buffer.length;
		count --;
		
		//通知生产者可以进行生产了，如果生产者在waitset中的话
		notifyAll();
		
		System.out.println(Thread.currentThread().getName()+ " take "+ cake);
		return cake;
	}
	
}
