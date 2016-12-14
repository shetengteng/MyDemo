package com.stt.DesignPatterns.Observer;

import java.util.Enumeration;
import java.util.Vector;

public abstract class AbstractSubject implements Subject {

	private Vector<Observer> observers = new Vector<>();

	@Override
	public void add(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void del(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		Enumeration<Observer> elements = observers.elements();
		while (elements.hasMoreElements()) {
			elements.nextElement().update();
		}
	}
}
