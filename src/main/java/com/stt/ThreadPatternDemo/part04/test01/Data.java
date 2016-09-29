package com.stt.ThreadPatternDemo.part04.test01;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Data {

	//保存的文件名
	private final String fileName;
	//保存的文件内容
	private String content;
	//修改后的内容没有保存时，是true
	private boolean changed;
	
	public Data(String fileName,String content){
		this.fileName = fileName;
		this.content = content;
		this.changed = true;
	}

	//修改内容
	public synchronized void change(String newContent){
		this.content = newContent;
		//表示需要保存
		changed = true;
	}
	
	//此处如果抛出了异常，说明写入有问题，没有进行更改，changed依然是true
	public synchronized void save() throws IOException{
		if(changed){
			doSave();
			changed = false;
		}
	}
	
	private void doSave() throws IOException {
		
		System.out.println(Thread.currentThread().getName()+"  doSave:"+content);
		Writer writer = new FileWriter(fileName);
		writer.write(content);
		writer.close();
	}
	
	
}
