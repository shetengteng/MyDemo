package com.stt.Sigar;

import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class MonitorEthernet {
	public static void main(String[] args) {
		try {
			Sigar sigar = new Sigar();
			String[] ifaces = sigar.getNetInterfaceList();
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}
				System.out.println(cfg.getName() + "IP地址:" + cfg.getAddress());// IP地址
				System.out.println(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());// 网关广播地址
				System.out.println(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());// 网卡MAC地址
				System.out.println(cfg.getName() + "子网掩码:" + cfg.getNetmask());// 子网掩码
				System.out.println(cfg.getName() + "网卡描述信息:" + cfg.getDescription());// 网卡描述信息
				System.out.println(cfg.getName() + "网卡类型" + cfg.getType());//
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}
}
