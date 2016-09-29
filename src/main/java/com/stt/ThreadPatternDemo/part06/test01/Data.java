package com.stt.ThreadPatternDemo.part06.test01;

public class Data {

	private final char[] buffer; //定义数据实体
	private final ReadWriteLock lock = new ReadWriteLock();
	public Data(int size){
		//进行初始化操作
		this.buffer = new char[size];
		for(int i = 0;i< buffer.length;i++){
			buffer[i] = '*';
		}
	}
	
	//读操作，返回数据
	public char[] read() throws InterruptedException{
		//获取读锁，并锁定
		lock.readLock();
		try {
			return doRead();
		} catch (Exception e) {
		}finally{
			//最终释放锁
			lock.readUnlock();
		}
		return null;
	}

	//写操作
	public void write(char c) throws InterruptedException{
		//获取写锁
		lock.writeLock();
		try {
			doWrite(c);
		} catch (Exception e) {
		}finally{
			//释放写锁
			lock.writeUnlock();
		}
	}
	
	private char[] doRead() {
		char[] result = new char[buffer.length];
		for(int i=0;i<buffer.length;i++){
			result[i] = buffer[i];
		}
		slowly();//模拟读取延时
		return result;
	}

	private void slowly() {
		try {
			Thread.sleep(50);
		} catch (Exception e) {
		}
	}
	
	private void doWrite(char c){
		for(int i =0;i<buffer.length;i++){
			buffer[i] = c;
			slowly();//模拟写延时
		}
	}
}
