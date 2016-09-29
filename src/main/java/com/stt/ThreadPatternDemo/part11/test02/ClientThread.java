package com.stt.ThreadPatternDemo.part11.test02;
import com.stt.ThreadPatternDemo.part11.test02.Log;

public class ClientThread extends Thread{

	public ClientThread(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		System.out.println(getName()+" begin");
		for(int i =0;i<10;i++){
			Log.println("i="+i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		//Log.close();
		System.out.println(getName()+" end");
	}
}
