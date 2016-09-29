package com.stt.ThreadPatternDemo.part06.test02;

public class Data {
	private final ReadWriteLock lock;
	private final ReadWriteStrategy readWriteStrategy;
	
	public Data() {
		this.lock = new ReadWriteLock(new DefaultGuardStrategy());
		this.readWriteStrategy = new DefaultReadWriteStrategy();
	}
	public Data(GuardStrategy gs){
		this.lock = new ReadWriteLock(gs);
		this.readWriteStrategy = new DefaultReadWriteStrategy();
	}
	public Data(ReadWriteStrategy rws){
		this.lock = new ReadWriteLock(new DefaultGuardStrategy());
		this.readWriteStrategy = rws;
	}
	public Data(GuardStrategy gs,ReadWriteStrategy rws){
		this.lock = new ReadWriteLock(gs);
		this.readWriteStrategy = rws;
	}
	
	public Object read() throws InterruptedException {
		lock.readLock();
		try {
			return readWriteStrategy.doRead();
		}finally{
			lock.readUnlock();
		}
	}
	
	public void write(Object arg) throws InterruptedException{
		lock.writeLock();
		try {
			readWriteStrategy.doWrite(arg);
		}finally{
			lock.writeUnlock();
		}
	}
	
	//inner class
	private class ReadWriteLock{
		//配置各种锁的策略
		private final GuardStrategy guardStrategy;
		public ReadWriteLock(GuardStrategy guardStrategy) {
			this.guardStrategy = guardStrategy;
		}
		
		//开启读锁
		public synchronized void readLock() throws InterruptedException{
			guardStrategy.beforeReadWait();
			try {
				while(!guardStrategy.readGuard()){
					this.wait();
				}
			}finally{
				guardStrategy.afterReadWait();
			}
			guardStrategy.beforeDoRead();
		}
		//解除读的锁
		public synchronized void readUnlock(){
			guardStrategy.afterDoRead();
			this.notifyAll();
		}
		//开始写锁
		public synchronized void writeLock() throws InterruptedException{
			guardStrategy.beforeWriteWait();
			try {
				while(!guardStrategy.writeGuard()){
					this.wait();
				}
			} finally{
				guardStrategy.afterWriteWait();
			}
			guardStrategy.beforeDoWrite();
		}
		//解除写锁
		public synchronized void writeUnlock(){
			guardStrategy.afterDoWrite();
			this.notifyAll();
		}
	}
	
	//inner class 默认锁的策略
	private class DefaultGuardStrategy implements GuardStrategy{
		
		private int readingReaders = 0;
		private int waitingWriters = 0;
		private int writingWriters = 0;
		private boolean preferWriter = true;
		
		@Override
		public void beforeReadWait() { }

		@Override
		public boolean readGuard() {
			/*if(writingWriters >0 ||(preferWriter && waitingWriters >0)){
				return false;
			}
			return true;
			*/
			return !(writingWriters >0 || (preferWriter && waitingWriters >0));
		}

		@Override
		public void afterReadWait() {}

		@Override
		public void beforeDoRead() {
			readingReaders ++;
		}
		@Override
		public void afterDoRead() {
			readingReaders --;
			preferWriter = true;
		}
		@Override
		public void beforeWriteWait() {
			waitingWriters ++;
		}
		@Override
		public boolean writeGuard() {
			return !(writingWriters >0 || readingReaders >0);
		}

		@Override
		public void afterWriteWait() {
			waitingWriters --;
		}

		@Override
		public void beforeDoWrite() {
			writingWriters ++;
		}

		@Override
		public void afterDoWrite() {
			writingWriters --;
			preferWriter = false;
		}
	}
	
	//inner class 
	private class DefaultReadWriteStrategy implements ReadWriteStrategy{
		private final char[] buffer;
		public DefaultReadWriteStrategy() {
			this(10);
		}
		public DefaultReadWriteStrategy(int size){
			buffer = new char[size];
			for(int i = 0;i<size;i++){
				buffer[i] = '*';
			}
		}
		
		@Override
		public Object doRead() throws InterruptedException {
			//读取时，复制一份
			char[] newbuf = new char[buffer.length];
			for(int i = 0;i<newbuf.length;i++){
				newbuf[i] = buffer[i];
			}
			slowly();
			return newbuf;
		}

		@Override
		public void doWrite(Object arg) throws InterruptedException {
			char c = ((Character)arg).charValue();
			for(int i = 0;i<buffer.length;i++){
				buffer[i] = c;
				slowly();
			}
		}

		private void slowly() throws InterruptedException {
			Thread.sleep(50);
		}
	}
	
}
