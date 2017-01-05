package com.stt.Sigar;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * 监测CPU状态
 * @author Administrator
 */
public class MonitorCPU {

	public static void main(String[] args) {
		try {
			Sigar sigar = new Sigar();
			System.out.println("CPU的总量参数：" + sigar.getCpu());
			System.out.println("CPU的总百分比：" + sigar.getCpuPerc());

			CpuInfo[] infos = sigar.getCpuInfoList();
			// 对多块cpu进行监测
			for (int i = 0; i < infos.length; i++) {
				System.out.println("第" + (i + 1) + "块CPU参数信息-------");
				System.out.println("CPU的总量MHZ：" + infos[i].getMhz());
				System.out.println("CPU的生产商：" + infos[i].getVendor());
				System.out.println("CPU的类别：" + infos[i].getModel());
				System.out.println("CPU的缓存数量：" + infos[i].getCacheSize());
			}

			// 对cpu当前的运行状况的监测
			CpuPerc[] cpuPercs = sigar.getCpuPercList();
			for (int i = 0; i < cpuPercs.length; i++) {
				System.out.println("第" + (i + 1) + "块CPU的使用状况--------");
				System.out.println("CPU的用户使用率：" + CpuPerc.format(cpuPercs[i].getUser()));
				System.out.println("CPU的系统使用率：" + CpuPerc.format(cpuPercs[i].getSys()));
				System.out.println("CPU的当前等待率：" + CpuPerc.format(cpuPercs[i].getWait()));
				System.out.println("CPU的当前错误率：" + CpuPerc.format(cpuPercs[i].getNice()));
				System.out.println("CPU的当前空闲率：" + CpuPerc.format(cpuPercs[i].getIdle()));
				System.out.println("CPU的当前使用率：" + CpuPerc.format(cpuPercs[i].getCombined()));
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}
}
