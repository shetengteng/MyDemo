package com.stt.ThreadDemo.ThreadPattern.part05.test01;

import java.util.Random;

public class EaterThread extends Thread{

	private final Random random;
	private final Table table;
	
	public EaterThread(String name,Table table,int seed){
		this.random = new Random(seed);
		this.table = table;
	}
	
	@Override
	public void run() {
		try {
			while(true){
				System.out.println("cake No. "+ table.take()+ "take by "+this.getName());
				Thread.sleep(random.nextInt(1000));
			}
		} catch (Exception e) {
		}
	}
	
}
