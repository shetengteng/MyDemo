package com.stt.MyDemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseTest {

	public static void main(String[] args) {
		try {

			Calendar c = Calendar.getInstance();
			// 1天前
			c.add(Calendar.DATE, -1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			System.out.println(c.getTime());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			// 需要添加8小时
			String startDate = sdf.format(new Date()) + "080000";
			String endDate = sdf.format(new Date()) + "315959";
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");

			System.out.println(sdf2.parse(startDate));
			System.out.println(sdf2.parse(endDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static class MyBean {
		private String name;

		public MyBean() {

		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public MyBean getBean() {
		MyBean bean = new MyBean();
		bean.setName("stt");
		return bean;
	}

}
