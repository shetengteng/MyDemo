package com.stt.NetWorkDemo01.part11_RMI.test04_ProducerAndConsumer.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 用于存放商品，放置于服务端，供给客户端调用的远程对象
 * 
 * @author Administrator
 *
 */
public class StackImpl extends UnicastRemoteObject implements Stack {
	private static final long serialVersionUID = 7096242097698069752L;
	private String name;
	private String[] buffer = new String[100];
	private int point = -1;

	public StackImpl(String name) throws RemoteException {
		this.name = name;
	}

	public String getName() throws RemoteException {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 这里需要添加锁， point可能修改
	 */
	@Override
	public synchronized int getPoint() throws RemoteException {
		return point;
	}

	@Override
	public synchronized String pop() throws RemoteException {
		// 唤醒所有线程，特别是生产者可以开始工作了
		this.notifyAll();
		// 如果point为-1 表示buffer中为空
		while (point == -1) {
			System.out.println(Thread.currentThread().getName() + ":wait");
			try {
				// 这里调用该应用的线程进入waitset中，等待被唤醒进行消费
				this.wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		String goods = buffer[point];
		buffer[point] = null;
		point -= 1;
		// 退让，将当前的cpu执行权交给其他线程
		// 在这里使用的意义不明白？
		Thread.yield();
		return goods;
	}

	@Override
	public synchronized void push(String goods) throws RemoteException {
		this.notifyAll();

		while (point == buffer.length - 1) {
			// 说明buffer已经满了，不需要进行填充了，调用此方法的线程需要wait
			try {
				this.wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		point++;
		Thread.yield();
		buffer[point] = goods;
	}
}
