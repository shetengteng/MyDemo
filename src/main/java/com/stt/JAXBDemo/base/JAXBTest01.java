package com.stt.JAXBDemo.base;

import org.junit.Test;

public class JAXBTest01 {

	@Test
	public void toXml() {

		Role role = new Role();
		role.setDesc("master");
		role.setName("master");

		Student student = new Student();
		student.setId(1);
		student.setName("stt");
		student.setRole(role);

		String xml = JaxbUtil.convertToXml(student);
		System.out.println(xml);
	}

	@Test
	public void toBean() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<STUDENT id=\"1\">" + "<name>stt</name>" + "<ROLE>" + "<name>master</name>"
				+ "<desc>master</desc>" + "</ROLE>" + "</STUDENT>";

		Student student = JaxbUtil.convertToBean(xml, Student.class);
		System.out.println(student.toString());

	}
}
