package com.edu.library.usercenter;

import com.edu.library.data.BaseData;

/**
 * 服务配置信息数据封装
 * 
 * @author lucher
 * 
 */
public class ServiceInfoData extends BaseData {
	private static final long serialVersionUID = 21L;

	// ip地址
	private String ip;
	// 端口号
	private int port;
	// 命名空间
	private String nameSpace;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

}
