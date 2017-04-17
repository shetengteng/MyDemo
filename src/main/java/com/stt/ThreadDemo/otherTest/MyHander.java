package com.stt.ThreadDemo.otherTest;

public class MyHander implements Handler {

	private static int no = 0;
	private static Boolean ok = true;

	// 在此处加锁是有效果的
	@Override
	public synchronized void test() {

		if (ok) {
			no = no + 1;
			ok = false;
		} else {
			no = no - 1;
			ok = true;
		}
		System.out.println(no);
	}

}
