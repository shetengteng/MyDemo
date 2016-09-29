package com.stt.ThreadPatternDemo.part08.test01;

import java.util.Random;

public class Request {

	private final String name;
	private final int number;
	private static final Random random = new Random();
	
	public Request(String name,int n) {
		this.name = name;
		this.number = n;
	}
	public void execute(){
		
		System.out.println(Thread.currentThread().getName()+
					"execute:"+"name--"+name+"  number--"+number);
		try {
			Thread.sleep(random.nextInt(100));
		} catch (Exception e) {
		}
	}
}
