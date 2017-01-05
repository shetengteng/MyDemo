package com.stt.Sigar;

import org.hyperic.sigar.OperatingSystem;

public class MonitorOS {

	public static void main(String[] args) {
		OperatingSystem os = OperatingSystem.getInstance();
		System.out.println("操作系统：" + os.getArch());
		System.out.println("操作系统CpuEndian：" + os.getCpuEndian());
		System.out.println("操作系统位：" + os.getDataModel());
		System.out.println("操作系统描述：" + os.getDescription());
		System.out.println("操作系统名称：" + os.getName());
		System.out.println("操作系统补丁等级：" + os.getPatchLevel());
		System.out.println("操作系统生产商：" + os.getVendor());
		System.out.println("操作系统生产商Code名称：" + os.getVendorCodeName());
		System.out.println("操作系统生产商名称：" + os.getVendorName());
		System.out.println("操作系统类型：" + os.getVendorVersion());
		System.out.println("操作系统版本号：" + os.getVersion());
	}
}
