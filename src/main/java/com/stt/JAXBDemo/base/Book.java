package com.stt.JAXBDemo.base;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// 定义访问的xml类型
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "book", propOrder = { "author", "calendar", "id", "price" })
public class Book {

	@XmlAttribute(name = "ID")
	private Integer id;

	@XmlElement(required = true)
	private String author;

	@XmlElement(name = "PRICE", required = true, defaultValue = "0.0")
	private Float price;

	@XmlElement
	private Date calendar;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getCalendar() {
		return calendar;
	}

	public void setCalendar(Date calendar) {
		this.calendar = calendar;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", author=" + author + ", price=" + price + ", calendar=" + calendar + "]";
	}

}