package com.stt.JAXBDemo.base;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "citiySet" })
public class Province {

	@XmlElement(name = "PROV_NAME")
	private String name;

	@XmlElementWrapper(name = "citiySet")
	@XmlElement(name = "city")
	private Set<City> citiySet;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<City> getCitiySet() {
		return citiySet;
	}

	public void setCitiySet(Set<City> citiySet) {
		this.citiySet = citiySet;
	}

	@Override
	public String toString() {
		return "Province [name=" + name + ", citiySet=" + citiySet + "]";
	}

}
