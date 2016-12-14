package com.stt.DesignPatterns.Observer;

/**
 * 观察对象的接口
 * @author Administrator
 *
 */
public interface Subject {
	/**增加观察者*/
	public void add(Observer observer);

	/**删除观察者*/
	public void del(Observer observer);

	/**通知所有观察者*/
	public void notifyObservers();

	/**自有操作*/
	public void operation();

}
