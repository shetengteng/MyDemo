package com.stt.ThreadDemo.ThreadPattern.part12;

public class Servant implements ActiveObject{
	
	@Override
	public Result makeString(int count,char c){
		char[] buffer = new char[count];
		for(int i = 0; i < count; i++){
			buffer[i] = c;
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
		return new RealResult(new String(buffer));
	}

	@Override
	public void displayString(String str) {
		try {
		System.out.println("displaying:"+str);
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}
	
	
}
