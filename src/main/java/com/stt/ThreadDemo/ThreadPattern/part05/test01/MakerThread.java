package com.stt.ThreadDemo.ThreadPattern.part05.test01;

import java.util.Random;

public class MakerThread extends Thread{

	private final Random random;
	private final Table table;
	private static int id = 0;
	public MakerThread(String name,Table table,int seed){
		super(name);
		this.table = table;
		random = new Random(seed);
	}

	public void run(){
		try {
			while(true){
				Thread.sleep(random.nextInt(1000));
				String cake = "Cake No."+nextId()+"by"+this.getName();
				table.put(cake);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static synchronized int nextId(){
		return id++;
	}
	
}
