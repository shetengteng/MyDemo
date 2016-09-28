package com.stt.JAXBDemo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class JAXBTest02 {

	@Test
	public void toXml() {

		City city01 = new City();
		city01.setName("合肥");
		City city02 = new City();
		city02.setName("芜湖");
		Set<City> citySet01 = new HashSet<>();
		citySet01.add(city01);
		citySet01.add(city02);

		Province province01 = new Province();
		province01.setName("安徽省");
		province01.setCitiySet(citySet01);

		List<Province> provinceList = new ArrayList<>();
		provinceList.add(province01);

		Country country = new Country();
		country.setName("中国");
		country.setProvinceList(provinceList);

		String xml = JaxbUtil.convertToXml(country);
		System.out.println(xml);
	}

	@Test
	public void toBean() {
		String xml = "<country><COUNTRY_NAME>中国</COUNTRY_NAME> " + "<provinceList><province><PROV_NAME>安徽省</PROV_NAME>"
				+ " <citiySet> <city> <CITY_NAME>芜湖</CITY_NAME> </city>" + "<city><CITY_NAME>合肥</CITY_NAME> </city> </citiySet> "
				+ "</province> </provinceList></country>";

		Country country = JaxbUtil.convertToBean(xml, Country.class);
		System.out.println(country.toString());

	}
}
