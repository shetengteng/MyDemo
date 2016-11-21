package com.stt.JAXBDemo.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

	@XmlElement(name = "Name")
	private String name;
	@XmlElement(name = "B")
	private B a;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParentA getA() {
		return a;
	}

	public void setA(B a) {
		this.a = a;
	}

}

class ParentA {

	private String a = "A";

	@XmlElement(name = "A")
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

}

@XmlAccessorType(XmlAccessType.FIELD)
class B extends ParentA {

	@XmlTransient
	private String b = "B";

	@XmlElement(name = "C")
	public String getA() {
		return super.getA();
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

}