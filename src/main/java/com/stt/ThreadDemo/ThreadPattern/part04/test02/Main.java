package com.stt.ThreadDemo.ThreadPattern.part04.test02;

public class Main {

	public static void main(String[] args) {
		
		Host host = new Host(10000);
		try {
			System.out.println("begin");
			host.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
