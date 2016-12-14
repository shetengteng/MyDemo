package com.stt.NetWorkDemo.part09_serializable.API_local;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 测试序列化到本地
 * 
 * @author Administrator
 *
 */
public class ObjectSaver {

	public static void main(String[] args) {
		try {
			// 将文件流进行包装，成为序列化输出流
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\myObj.obj"));
			String str = "测试序列化";
			Date date = new Date();
			Customer customer = new Customer();
			customer.setAge(22);
			customer.setName("stt");
			out.writeObject(str);
			out.writeObject(date);
			out.writeObject(customer);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
