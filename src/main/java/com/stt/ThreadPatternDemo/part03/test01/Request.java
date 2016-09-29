package com.stt.ThreadPatternDemo.part03.test01;

public class Request {

	private final String name;
	public Request(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	@Override
	public String toString() {
		return "Request [name=" + name + "]";
	}
}
