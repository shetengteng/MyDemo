package com.stt.NetWorkDemo01.part10_reflection.test01_remoteCall.service.impl;

import java.util.Date;

import com.stt.NetWorkDemo01.part10_reflection.test01_remoteCall.service.HelloService;

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
