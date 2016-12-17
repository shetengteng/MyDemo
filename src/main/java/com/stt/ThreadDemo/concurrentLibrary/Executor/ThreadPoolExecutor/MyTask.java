package com.stt.ThreadDemo.concurrentLibrary.Executor.ThreadPoolExecutor;

public class MyTask implements Runnable {

	private int id;
	private String name;

	public MyTask(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		try {
			System.out.println("run task id:" + id);
			Thread.sleep(5 * 1000);
			System.out.println("--run task id:" + id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "MyTask [id=" + id + ", name=" + name + "]";
	}

}
