package com.stt.NetWorkDemo.Netty.HeartBeat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestInfo implements Serializable {
	private static final long serialVersionUID = 7134724328767721991L;
	private String ip;
	private Map<String, Object> cpuPerMap = new HashMap<>();
	private Map<String, Object> memoryMap = new HashMap<>();

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Map<String, Object> getCpuPerMap() {
		return cpuPerMap;
	}

	public void setCpuPerMap(Map<String, Object> cpuPerMap) {
		this.cpuPerMap = cpuPerMap;
	}

	public Map<String, Object> getMemoryMap() {
		return memoryMap;
	}

	public void setMemoryMap(Map<String, Object> memoryMap) {
		this.memoryMap = memoryMap;
	}

	@Override
	public String toString() {
		return "RequestInfo [ip=" + ip + ", cpuPerMap=" + cpuPerMap + ", memoryMap=" + memoryMap + "]";
	}

}
