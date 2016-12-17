package com.stt.ThreadDemo.concurrentLibrary.pattern.master_worker;

import java.util.Random;

public class Client {

	public static void main(String[] args) {

		// 创建20个并行计算的线程
		Master master = new Master(new Worker(), 20);
		for (int i = 0; i < 100; i++) {
			// 创建100个任务
			Task task = new Task(i + "", "task-" + i, new Random().nextInt(500));
			master.submit(task);
		}
		// 执行
		master.execute();
		// 获取结果
		long start = System.currentTimeMillis();
		for (;;) {
			try {
				if (master.isComplete()) {
					System.out.println("final Result :" + master.getResult());
					long end = System.currentTimeMillis();
					System.out.println("执行时间：" + (end - start));
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
