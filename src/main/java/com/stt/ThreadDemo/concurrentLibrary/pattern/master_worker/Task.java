package com.stt.ThreadDemo.concurrentLibrary.pattern.master_worker;

/**
 * 用于执行任务的Task类
 * 这里的任务示例，直接返回结果
 * @author Administrator
 *
 */
public class Task {

	private String id;
	private String name;
	private int result;

	public Task(String id, String name, int result) {
		this.id = id;
		this.name = name;
		this.result = result;
	}

	public Task() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}
