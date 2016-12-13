package com.stt.DesignPatterns.Observer;

/**
 * 观察者模式用例
 * @author Administrator
 *
 */
public class ObserverTest {

	public static void main(String[] args) {
		Subject subject = new MySubject();
		subject.add(new Observer1());
		subject.add(new Observer2());
		subject.operation();
	}

}
