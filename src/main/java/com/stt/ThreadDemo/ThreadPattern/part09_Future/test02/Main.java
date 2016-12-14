package com.stt.ThreadDemo.ThreadPattern.part09_Future.test02;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
	
		long start = System.currentTimeMillis();
		Content c1 = Retriever.retrieve("http://www.baidu.com/");
		Content c2 = Retriever.retrieve("http://www.sohu.com/");
		Content c3 = Retriever.retrieve("http://www.sina.com/");
		
		//将数据写入到文件中
		saveToFile("baidu.html",c1);
		saveToFile("sohu.html",c2);
		saveToFile("sina.html",c3);
		
		long end = System.currentTimeMillis();
		System.out.println("Elapsed time:"+(end - start)+"ms");
		
	}

	private static void saveToFile(String fileName, Content c) {
		byte[] bytes = c.getBytes();
		//将数据保存到文件中
		FileOutputStream os = null;
		try {
		
			os = new FileOutputStream(fileName);
			os.write(bytes);
			os.flush();
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
		
		
		
	}
	
}
