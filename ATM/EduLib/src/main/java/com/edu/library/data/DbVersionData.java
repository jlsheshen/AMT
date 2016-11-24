package com.edu.library.data;

/**
 * 数据库版本号数据封装
 * 
 * @author lucher
 * 
 */
public class DbVersionData extends BaseData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 数据库版本号
	private int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
