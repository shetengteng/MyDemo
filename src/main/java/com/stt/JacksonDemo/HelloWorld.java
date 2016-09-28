package com.stt.JacksonDemo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HelloWorld {

	// jackson 与 Map 互转

	@Test
	public void test01() {

		Map<String, Object> map = new HashMap<>();

		map.put("key1", "value1");
		map.put("key2", "value2");

		ObjectMapper mapper = new ObjectMapper();
		try {

			String str = mapper.writeValueAsString(map);

			System.out.println(str);

			@SuppressWarnings("unchecked")
			Map<String, Object> readValue = mapper.readValue(str, Map.class);
			System.out.println(readValue);
			System.out.println(readValue.get("key1"));

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void test02() {

		Map<String, Object> map = new HashMap<>();

		ObjectMapper mapper = new ObjectMapper();
		try {

			String str = "ff";

			@SuppressWarnings("unchecked")
			Map<String, Object> readValue = mapper.readValue(new HashMap<>().toString(), Map.class);

			readValue.put("key1", "value1");
			System.out.println(readValue);
			System.out.println(readValue.get("key1"));

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
