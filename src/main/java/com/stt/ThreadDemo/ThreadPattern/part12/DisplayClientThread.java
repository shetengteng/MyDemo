package com.stt.ThreadDemo.ThreadPattern.part12;

public class DisplayClientThread extends Thread{

	private final ActiveObject activeObject;
	
	public DisplayClientThread(String name,ActiveObject activeObject) {
		super(name);
		this.activeObject = activeObject;
	}
	
	@Override
	public void run() {
		try {
			for(int i = 0 ;true;i++){
				String str = Thread.currentThread().getName()+""+i;
				activeObject.displayString(str);
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
		}
	}
	
}
