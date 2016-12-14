package com.stt.NetWorkDemo.part10_reflection.test03_dynamicProxy.service.impl;

import java.util.Date;

import com.stt.NetWorkDemo.part10_reflection.test03_dynamicProxy.service.HelloService;

public class HelloServiceImpl implements HelloService {

	@Override
	public String echo(String msg) {
		return "echo:" + msg;
	}

	@Override
	public Date getTime() {
		return new Date();
	}
}
