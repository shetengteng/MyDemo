package com.stt.ThreadDemo.ThreadPattern.part06.test02;

public interface ReadWriteStrategy {

	public abstract Object doRead() throws InterruptedException;
	public abstract void doWrite(Object arg) throws InterruptedException;
}
