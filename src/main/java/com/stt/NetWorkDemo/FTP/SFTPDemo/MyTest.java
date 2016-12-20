package com.stt.NetWorkDemo.FTP.SFTPDemo;

import com.stt.NetWorkDemo.FTP.SFTPDemo.FTPUtilFactory.FTPUtil;

public class MyTest {

	public static void main(String[] args) {
		try {
			FTPUtil ftpUtil = FTPUtilFactory.getSFTPUtilInstance("127.0.0.1", 22, "shetengteng", "123", 10000);
			System.out.println(ftpUtil.download("d:\\s.txt", "/test/002.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
