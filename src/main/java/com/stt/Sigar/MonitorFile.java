package com.stt.Sigar;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * 监测文件系统状态
 * @author Administrator
 */
public class MonitorFile {

	public static void main(String[] args) {
		try {
			Sigar sigar = new Sigar();
			FileSystem[] fileSystems = sigar.getFileSystemList();
			for (int i = 0; i < fileSystems.length; i++) {
				System.out.println("分区盘符编号：" + (i + 1) + " ------------");
				System.out.println("分区的盘符名称：" + fileSystems[i].getDevName());
				System.out.println("分区的盘符路径：" + fileSystems[i].getDirName());
				System.out.println("分区的盘符标志：" + fileSystems[i].getFlags());
				System.out.println("分区的系统盘符类型：" + fileSystems[i].getSysTypeName());// FAT32.NTFS
				System.out.println("分区的盘符类型：" + fileSystems[i].getTypeName());// 本地磁盘、光驱、网络文件系统
				System.out.println("分区的盘符的文件系统类型：" + fileSystems[i].getType());

				switch (fileSystems[i].getType()) {
				case 0:// TYPE_UNKNOWN
				case 1:// TYPE_NONE
					break;
				case 2:// TYPE_LOCAL_DISK
					FileSystemUsage usage = sigar.getFileSystemUsage(fileSystems[i].getDirName());
					System.out.println(fileSystems[i].getDevName() + "总大小：" + usage.getTotal() + "KB");// 该盘符的总大小
					System.out.println(fileSystems[i].getDevName() + "剩余大小：" + usage.getFree() + "KB");
					System.out.println(fileSystems[i].getDevName() + "可用大小：" + usage.getAvail() + "KB");
					System.out.println(fileSystems[i].getDevName() + "已经使用量：" + usage.getUsed() + "KB");
					System.out.println(fileSystems[i].getDevName() + "资源利用率：" + usage.getUsePercent() * 100 + "%");
					System.out.println(fileSystems[i].getDevName() + "读出：" + usage.getDiskReads());
					System.out.println(fileSystems[i].getDevName() + "写入：" + usage.getDiskWrites());
					break;
				case 3:// TYPE_NETWORK
				case 4:// TYPE_RAM_DISK 闪存
				case 5:// TYPE_CDROM
				case 6:// TYPE_SWAP 页面交换
					break;
				}
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}
}
