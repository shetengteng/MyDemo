package com.stt.ThreadDemo.ThreadPattern.part09.test01;

public class RealData implements Data{

	private final String content;
	
	public RealData(int count,char c) {
		System.out.println("make realData "+count+":"+c+" start");
		char[] buffer = new char[count];
		for(int i = 0;i<count;i++){
			buffer[i] = c;
			try {
				//模拟处理该字符串需要的时间
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("make realData "+count+":"+c+" end");
		this.content = new String(buffer);
	}
	
	@Override
	public String getContent() {
		return content;
	}

}
