package com.stt.ThreadDemo.ThreadPattern.part11.test02;

public class Main {
	public static void main(String[] args) {
		new ClientThread("c1").start();
		new ClientThread("c2").start();
		new ClientThread("c3").start();
	}
	
}
