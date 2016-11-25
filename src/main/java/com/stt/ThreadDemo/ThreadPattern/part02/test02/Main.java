package com.stt.ThreadDemo.ThreadPattern.part02.test02;

public class Main {

	public static void main(String[] args) {
		MutablePerson mp = new MutablePerson("stt","stt");
		new CheckThread(mp).start();
		new CheckThread(mp).start();
		new CheckThread(mp).start();
		
		for(int i =0;true;i++){
			mp.setPerson(""+i, ""+i);
		}
	}
}


class CheckThread extends Thread{
	private final MutablePerson p;
	public CheckThread(MutablePerson p) {
		this.p = p;
	}
	@Override
	public void run() {
		while(true){
			ImmutablePerson ip = new ImmutablePerson(p);
			//如果外部的P发生了改变，则这里会出出现错误的情况
			if(!ip.getName().equals(ip.getAddress())){
				System.out.println("BROKEN***"+ip.toString());
			}
			
		}
	
	}
	
}
