package com.stt.DesignPatterns.Observer;

public class MySubject extends AbstractSubject {

	@Override
	public void operation() {
		System.out.println("mySubject operation...");
		// 执行完操作后通知所有的观察者
		notifyObservers();
	}

}
