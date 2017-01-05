package com.stt.Sigar;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Who;

public class MonitorWho {
	public static void main(String[] args) {
		try {
			Sigar sigar = new Sigar();
			Who[] whoList = sigar.getWhoList();
			// 取当前系统进程表中的用户信息
			if (whoList != null && whoList.length != 0) {
				for (int i = 0; i < whoList.length; i++) {
					System.out.println("用户" + (i + 1));
					System.out.println("用户设备：" + whoList[i].getDevice());
					System.out.println("用户名：" + whoList[i].getHost());
					System.out.println("当前系统进程表中的用户名：" + whoList[i].getUser());
					System.out.println(whoList[i].getTime());
				}
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}

	}
}
