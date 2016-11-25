package com.stt.ThreadDemo.ThreadPattern.part02.test01;

public final class Person {

	private final String name;
	private final String address;
	public Person(String name,String address){
		this.name = name;
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", address=" + address + "]";
	}
	
}
