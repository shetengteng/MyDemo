package com.stt.MyDemo;

import org.junit.Test;

public class ExceptionTest {

	@Test
	public void test01() {

		try {
			System.out.println("start");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}

	}

}
