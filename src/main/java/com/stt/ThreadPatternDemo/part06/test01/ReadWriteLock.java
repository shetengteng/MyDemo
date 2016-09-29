package com.stt.ThreadPatternDemo.part06.test01;

public class ReadWriteLock {

	private int readingReaders = 0;//实际正在读取的线程数量
	private int waitingWriters = 0;//正在等待写入的线程数量
	private int writingWriters = 0;//实际正在写入的线程数量
	private boolean preferWriter = true;//写入优先
	
	public synchronized void readLock() throws InterruptedException{
		//当一个读线程运行到此处进行判断，
		while(writingWriters > 0 || ( waitingWriters >0)){
			//进入此处，说明有写线程在等待或者执行，
			//read-write conflict,此时该读线程进入waitset
			this.wait();
		}
		readingReaders ++;
		//运行到此处，读线程就将锁释放了
	}
	
	public synchronized void readUnlock(){
		//再次获得锁后，将读线程的个数进行减法操作
		readingReaders --;
		preferWriter = true;
		//唤醒在waitset中的线程
		this.notifyAll();
	}
	
	public synchronized void writeLock() throws InterruptedException{
		waitingWriters++;//进行写操作，可能需要等待，因为会有write-read conflict
		try {
			while(readingReaders >0 || writingWriters >0){
				//运行到此处，说明 是read-write 或者是 write-write的conflict
				//然后本线程进入waitset操作
				this.wait();
			}
		}finally{
			waitingWriters --;
			//运行到此处，说明该线程从waitset唤醒，并获得了锁
			//开始继续执行，不再等待
		}
		writingWriters ++;
	}

	public synchronized void writeUnlock(){
		writingWriters --;
		preferWriter = false;
		this.notifyAll();
	}
	
}
