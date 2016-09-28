package com.stt.JAXBDemo;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class HelloWorld2 {

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<book ID=\"3\">" + "<author>stt</author>"
				+ "<calendar>2016-09-25T17:56:13.005+08:00</calendar>" + "<PRICE>33.3</PRICE>" + "</book>";
		// 解析成Bean
		try {
			JAXBContext context = JAXBContext.newInstance(Book.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			// 将xml放入内存中
			StringReader sr = new StringReader(xml);
			// 进行解析
			Book book = (Book) unmarshaller.unmarshal(sr);
			System.out.println(book.toString());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
