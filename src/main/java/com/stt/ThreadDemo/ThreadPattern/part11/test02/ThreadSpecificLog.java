package com.stt.ThreadDemo.ThreadPattern.part11.test02;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ThreadSpecificLog {
	private PrintWriter writer = null;
	public ThreadSpecificLog(String fileName) {
		try {
			writer = new PrintWriter(new FileWriter(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//添加一条日志记录
	public void println(String logStr){
		writer.println(logStr);
	}
	//关闭日志
	public void close(){
		writer.println("end of log");
		writer.close();
	}
	
}
