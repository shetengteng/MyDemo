package com.stt.ThreadDemo.ThreadPattern.part12;

public class Main {
	public static void main(String[] args) {
		ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
		new MakerClientThread(activeObject,"AAA").start();
		new MakerClientThread(activeObject,"BBB").start();
		new DisplayClientThread("CCC", activeObject).start();
	}	
}
