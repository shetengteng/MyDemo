package com.stt.ThreadDemo.ThreadPattern.part02.test02;

public  final class MutablePerson {

	private String name;
	private String address;

	//初始化，调用构造函数不是原子操作
	public MutablePerson(String name,String address){
		//指定引用和给引用赋值是原子操作
		this.name = name;
		this.address = address;
	}
	
	public MutablePerson(ImmutablePerson immutablePerson){
		this.name = immutablePerson.getName();
		this.address = immutablePerson.getAddress();
	}
	
	//这里上锁，要将2个字段都进行赋值，才有锁的效果
	public synchronized void setPerson(String name,String address){
		this.name = name;
		this.address = address;
	}
	
	public synchronized ImmutablePerson getImmutablePerson(){
		return new ImmutablePerson(this);
	}
	
	//返回数值不需要上锁，此时是默认修饰，只能在同一个包下进行访问
	//又是给Immutable进行访问
	String getName(){
		return this.name;
	}
	
	String getAddress(){
		return this.address;
	}
	
	public synchronized String toString(){
		return "MutablePerson name:"+name+" address:"+address;
	}
	
}
