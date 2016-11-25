package com.stt.ThreadDemo.ThreadPattern.part01.test01;

public class UserTask  extends Thread{

	private final  Gate gate;
	private final String myName;
	private final String myAddress;
	
	public UserTask(Gate gate,String name,String address) {
		this.gate = gate;
		this.myName = name;
		this.myAddress = address;
	}
	
	@Override
	public void run() {
		System.out.println("BEGIN:"+myName);
		while(true){
			gate.pass(myName, myAddress);
		}
	}
	
}
