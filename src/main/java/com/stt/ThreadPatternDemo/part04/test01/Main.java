package com.stt.ThreadPatternDemo.part04.test01;

public class Main {

	public static void main(String[] args) {
		Data data = new Data("data.txt","empty");
		new ChangerThread("change", data).start();
		new SaveThread(data).start();
	}
	
}
