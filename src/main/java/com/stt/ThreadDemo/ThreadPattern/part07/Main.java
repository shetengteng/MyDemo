package com.stt.ThreadDemo.ThreadPattern.part07;

public class Main {

	public static void main(String[] args) {
		System.out.println("start");
		Host host = new Host();
		host.request(10, 's');
		host.request(9, 't');
		host.request(8, 'n');
		System.out.println("end");
	}
	
}
