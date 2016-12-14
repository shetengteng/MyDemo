package com.stt.NetWorkDemo.part03.test03;

public class ThreadPoolTest {
	public static void main(String[] args) {
		ThreadPool pool = new ThreadPool(4);
		for (int i = 0; i < 10; i++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("Task start");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
					System.out.println("Task end");
				}
			});
		}

		// 执行完毕后关闭线程池
		// pool.join();
		// 直接关闭线程池
		pool.close();
	}

}
