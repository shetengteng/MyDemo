package com.stt.ThreadDemo.ThreadPattern.part02.test01;

public class Main {

	public static void main(String[] args) {
		Person p = new Person("stt","hefei");
		PersonThread pt01 = new PersonThread(p);
		PersonThread pt02 = new PersonThread(p);
		PersonThread pt03 = new PersonThread(p);
		pt01.start();
		pt02.start();
		pt03.start();
	}
	
}
