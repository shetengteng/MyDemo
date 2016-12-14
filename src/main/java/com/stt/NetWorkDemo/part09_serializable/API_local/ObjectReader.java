package com.stt.NetWorkDemo.part09_serializable.API_local;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;

public class ObjectReader {
	public static void main(String[] args) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\myObj.obj"));
			// 每执行一次，获取一个对象
			String str = (String) in.readObject();
			System.out.println(str);
			Date date = (Date) in.readObject();
			System.out.println(date);
			Customer customer = (Customer) in.readObject();
			System.out.println(customer);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
