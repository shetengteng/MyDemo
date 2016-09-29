package com.stt.ThreadPatternDemo.part09.test01;

import java.lang.reflect.InvocationTargetException;

public class Main {

	public static void main(String[] args) {
		Host host = new Host();
		Data data1 = host.request(10, 'S');
		Data data2 = host.request(8, 'T');
		Data data3 = host.request(13, 't');
		
		System.out.println("--begin--");
		try {
			System.out.println(data1.getContent());
			System.out.println(data2.getContent());
			System.out.println(data3.getContent());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("---end---");
		
		
	}
	
}
