package com.stt.ThreadDemo.ThreadPattern.part03.test01;

public class Main {

	public static void main(String[] args) {
		
		RequestQueue queue = new RequestQueue();
		new ClientThread("client", queue, 22212L).start();
		new ServerThread("server",queue,45454L).start();
	
	}
	
}
