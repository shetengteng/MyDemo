package com.stt.ThreadPatternDemo.part09.test02;

import java.io.DataInputStream;
import java.net.URL;

public class SynContentImpl implements Content{

	private byte[] contentBytes;
	public SynContentImpl(String urlStr) {
		
		System.out.println(Thread.currentThread().getName()+" Url:"+urlStr);
		try {
			//访问网页，读取网页的数据，将网页变成byte数组
			URL url = new URL(urlStr);
			DataInputStream in = new DataInputStream(url.openStream());
			byte[] buffer = new byte[1];
			int index = 0;
			try {
				while(true){
					//一个字符一个字符的读取，超过最大值，扩容一倍
					int c = in.readUnsignedByte();
					if(buffer.length <= index){
						byte[] largerBuffer = new byte[buffer.length*2];
						System.arraycopy(buffer, 0, largerBuffer, 0, index);
						buffer = largerBuffer;
					}
					buffer[index++] = (byte) c;
				}
			} catch (Exception e) {
			}finally{
				in.close();
			}
			//数据处理完毕后，可以用于返回
			contentBytes = new byte[index];
			System.arraycopy(buffer, 0, contentBytes, 0, index);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] getBytes() {
		return contentBytes;
	}

}
