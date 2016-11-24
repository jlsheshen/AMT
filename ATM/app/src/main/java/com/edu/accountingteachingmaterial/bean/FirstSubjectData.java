package com.edu.accountingteachingmaterial.bean;


import com.edu.library.data.BaseData;

/**
 * 一级科目表数据封装
 * 
 * @author edu_lx
 * 
 */
public class FirstSubjectData extends BaseData {

	// 科目类型 1or2,标记属于一级主科目还是一级从科目
	private int subtype;
	// 科目名称
	private String name;
	// 从属ID  与ID字段相对应
	private int parentid;

	public int getSubtype() {
		return subtype;
	}

	public void setSubtype(int subtype) {
		this.subtype = subtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	@Override
	public String toString() {
		return String.format("subtype:%s,name:%s,parentid:%s", subtype, name, parentid);
	}

}
