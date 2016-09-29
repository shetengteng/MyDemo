package com.stt.ThreadPatternDemo.part06.test01;

import java.util.Random;

public class WriteThread extends Thread{

	private static final Random random = new Random();
	private final Data data;
	private final String filler;
	private int index =0;
	public WriteThread(Data data,String filler) {
		this.data = data;
		this.filler = filler;
	}

	public void run(){
		try {
			while(true){
				//从输入的字符串中依次获取一个字符输入
				char c = nextChar();
				data.write(c);
				Thread.sleep(random.nextInt(3000));
			}
		} catch (Exception e) {
		}
	}

	private char nextChar() {
		char c = filler.charAt(index);
		index = (index + 1) % filler.length();
		return c;
	}
}
