package com.stt.ThreadDemo.ThreadPattern.part01.test01;

public class Gate {
	private int counter = 0;
	private String name = "nobody";
	private String address = "nowhere";
	public synchronized void  pass(String name,String address){
		this.counter ++;
		this.name = name;
		this.address = address;
		check();
	}
	public synchronized String toString(){
		return "NO."+counter+" ,name:"+name+" ,address:"+address;
	}
	public void check(){
		if(name.charAt(0) != address.charAt(0)){
			System.out.println("*****BROKEN******"+toString());
		}
	}
}
