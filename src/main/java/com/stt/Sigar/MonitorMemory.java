package com.stt.Sigar;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

/**
 * 监测memory
 * @author Administrator
 *
 */
public class MonitorMemory {

	public static void main(String[] args) {

		try {
			Sigar sigar = new Sigar();
			Mem mem = sigar.getMem();
			System.out.println("内存总量：" + mem.getTotal() / 1024L + "K av");
			System.out.println("当前内存使用量：" + mem.getUsed() / 1024L + "K used");
			System.out.println("当前内存剩余量：" + mem.getFree() / 1024L + "K free");

			Swap swap = sigar.getSwap();
			System.out.println("交换区总量：" + swap.getTotal() / 1024L + "K av");
			System.out.println("当前交换区使用量：" + swap.getUsed() / 1024L + "K used");
			System.out.println("当前交换区剩余量：" + swap.getFree() / 1024L + "K free");

		} catch (SigarException e) {
			e.printStackTrace();
		}
	}
}
