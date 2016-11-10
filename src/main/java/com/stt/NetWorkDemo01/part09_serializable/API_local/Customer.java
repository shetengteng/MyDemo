package com.stt.NetWorkDemo01.part09_serializable.API_local;

import java.io.Serializable;

public class Customer implements Serializable {
	private static final long serialVersionUID = 13686245876597955L;

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", age=" + age + "]";
	}

}
