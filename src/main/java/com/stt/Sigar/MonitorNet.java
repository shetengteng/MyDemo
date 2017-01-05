package com.stt.Sigar;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * 监测网络
 * @author Administrator
 *
 */
public class MonitorNet {
	public static void main(String[] args) {
		try {
			Sigar sigar = new Sigar();
			String ifNames[];
			ifNames = sigar.getNetInterfaceList();

			for (int i = 0; i < ifNames.length; i++) {
				String name = ifNames[i];
				NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
				System.out.println("网络设备名:    " + name);// 网络设备名
				System.out.println("IP地址:    " + ifconfig.getAddress());// IP地址
				System.out.println("子网掩码:    " + ifconfig.getNetmask());// 子网掩码
				if ((ifconfig.getFlags() & 1L) <= 0L) {
					System.out.println("!IFF_UP...skipping getNetInterfaceStat");
					continue;
				}
				NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
				System.out.println(name + "接收的总包裹数:" + ifstat.getRxPackets());// 接收的总包裹数
				System.out.println(name + "发送的总包裹数:" + ifstat.getTxPackets());// 发送的总包裹数
				System.out.println(name + "接收到的总字节数:" + ifstat.getRxBytes());// 接收到的总字节数
				System.out.println(name + "发送的总字节数:" + ifstat.getTxBytes());// 发送的总字节数
				System.out.println(name + "接收到的错误包数:" + ifstat.getRxErrors());// 接收到的错误包数
				System.out.println(name + "发送数据包时的错误数:" + ifstat.getTxErrors());// 发送数据包时的错误数
				System.out.println(name + "接收时丢弃的包数:" + ifstat.getRxDropped());// 接收时丢弃的包数
				System.out.println(name + "发送时丢弃的包数:" + ifstat.getTxDropped());// 发送时丢弃的包数
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}
}
