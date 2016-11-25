package com.stt.ThreadDemo.ThreadPattern.part04.test02;

public class TimeoutException extends InterruptedException{
	public TimeoutException(String msg){
		super(msg);
	}
}
