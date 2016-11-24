package com.edu.library.data;

import java.io.Serializable;

/**
 * 数据封装基类
 * 
 * @author lucher
 * 
 */
public abstract class BaseData implements Serializable {

	/**
	 * 数据索引
	 */
	protected int id;

	/**
	 * 备注
	 */
	protected String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
