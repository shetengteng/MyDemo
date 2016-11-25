package com.stt.ThreadDemo.ThreadPattern.part01.test01;

public class Test01 {

	public static void main(String[] args) {
		Gate gate = new Gate();
		new UserTask(gate,"Alice","Alaska").start();
		new UserTask(gate,"Bob","Brazil").start();
		new UserTask(gate,"Chris","Canada").start();
	}
	
}
