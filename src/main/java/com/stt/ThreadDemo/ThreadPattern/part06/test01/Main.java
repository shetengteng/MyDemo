package com.stt.ThreadDemo.ThreadPattern.part06.test01;

import com.stt.ThreadDemo.ThreadPattern.part06.test01.Data;
import com.stt.ThreadDemo.ThreadPattern.part06.test01.ReadThread;
import com.stt.ThreadDemo.ThreadPattern.part06.test01.WriteThread;

public class Main {

	public static void main(String[] args) {
		Data d = new Data(10);
		new ReadThread(d).start();
		new ReadThread(d).start();
		new ReadThread(d).start();
		new ReadThread(d).start();
		new WriteThread(d,"abcdefghijklmnopqrstuvwxyz").start();
		new WriteThread(d,"ABCDEFGHIJKLMNOPQRSTUVWXYZ").start();
	}
	
}
