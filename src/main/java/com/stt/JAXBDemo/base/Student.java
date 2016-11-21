package com.stt.JAXBDemo.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "STUDENT")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Student {

	@XmlAttribute
	private Integer id;

	@XmlElement
	private String name;

	@XmlElement(name = "ROLE")
	private Role role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", role=" + role + "]";
	}

}
