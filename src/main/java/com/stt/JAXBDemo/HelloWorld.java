package com.stt.JAXBDemo;

import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class HelloWorld {

	public static void main(String[] args) {

		Book book = new Book();
		book.setAuthor("stt");
		book.setCalendar(new Date());
		book.setId(3);
		book.setPrice(33.3F);

		// 解析成xml
		try {
			JAXBContext context = JAXBContext.newInstance(book.getClass());
			Marshaller marshaller = context.createMarshaller();
			// 设定编码格式
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			// 输出格式：true表示非紧凑
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 去除xml文档的首行<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
			StringWriter sw = new StringWriter();
			marshaller.marshal(book, sw);
			String result = sw.toString();
			System.out.println(result);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
