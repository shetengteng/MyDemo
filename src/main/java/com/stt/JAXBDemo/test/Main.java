package com.stt.JAXBDemo.test;

import com.stt.JAXBDemo.base.JaxbUtil;

public class Main {

	public static void main(String[] args) {

		Person p = new Person();
		p.setA(new B());

		String convertToXml = JaxbUtil.convertToXml(p);
		System.out.println(convertToXml);

	}

}
