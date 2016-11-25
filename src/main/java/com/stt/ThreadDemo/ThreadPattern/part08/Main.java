package com.stt.ThreadDemo.ThreadPattern.part08;

public class Main {

	public static void main(String[] args) {
		Channel channel = new Channel(5);
		channel.startWorkers();
		ClientThread a = new ClientThread("A",channel);
		ClientThread b = new ClientThread("B",channel);
		ClientThread c = new ClientThread("C",channel);
		
		a.start();
		b.start();
		c.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		a.stopThread();
		b.stopThread();
		c.stopThread();
		channel.stopAllWorkerThread();
		
	}
	
}
