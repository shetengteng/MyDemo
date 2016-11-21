package com.stt.JAXBDemo.base;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbUtil {

	private JaxbUtil() {}

	public static <T> String convertToXml(T t) {
		return convertToXml(t, "UTF-8");
	}

	/**
	 * 将bean转换为xml
	 * @param t
	 * @param encoding
	 * @return
	 */
	public static <T> String convertToXml(T t, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(t.getClass());
			Marshaller marshaller = context.createMarshaller();
			// 设定编码
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			// 是否美化xml文档，false--紧凑
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter sw = new StringWriter();
			marshaller.marshal(t, sw);
			result = sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将xml转换为bean
	 * @param xml
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToBean(String xml, Class<T> clazz) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(xml);
			t = (T) unmarshaller.unmarshal(sr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
}
