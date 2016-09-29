package com.stt.ThreadPatternDemo.part02.test02;

public final class ImmutablePerson {

	private final String name;
	private final String address;
	
	public ImmutablePerson(String name,String address){
		this.name = name;
		this.address = address;
	}

	public ImmutablePerson(MutablePerson mutablePerson){
		synchronized (mutablePerson) {
			this.name = mutablePerson.getName();
			//如果没有添加锁，那么有线程在外部在调用MutablePerson的setPerson方法结束后
			//返回此处时address有变化
			this.address = mutablePerson.getAddress();
		}
	}

	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}

	@Override
	public String toString() {
		return "ImmutablePerson [name=" + name + ", address=" + address + "]";
	}
	
}
