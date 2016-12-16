package com.stt.ThreadDemo.ThreadPattern.part00.single;

/**
 * 用于测试当一个线程进入初始化后，其他线程是否可以获取更改后的对象
 * @author Administrator
 *
 */
public class SingleTest {

	public static void main(String[] args) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("t1" + SingleData.getInstance());
			}
		}, "t1").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("t2" + SingleData.getInstance());
			}
		}, "t2").start();
	}

	/**
	 * 结果：
	 * 
	t1--1
	t1--2
	// 这里t1线程还没有从锁中释放，但是t2可以获取static的引用变量，而static引用变量的获取是静态的
	// 获取引用操作是原子性的
	t2com.stt.ThreadDemo.ThreadPattern.part00.single.SingleData@784ba3e6
	t1--3
	t1com.stt.ThreadDemo.ThreadPattern.part00.single.SingleData@784ba3e6
	 * */
}
