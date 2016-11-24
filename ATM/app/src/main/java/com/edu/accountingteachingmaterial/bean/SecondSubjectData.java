package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

/**
 * 二级科目表SECOND_SUBJECT数据封装
 * 
 * @author edu_lx
 * 
 */
public class SecondSubjectData extends BaseData {
	// 二级科目名称
	private String name;

	// 二级科目依赖ID 与First_Subject表中的ID相对应
	private int dependentid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDependentid() {
		return dependentid;
	}

	public void setDependentid(int dependentid) {
		this.dependentid = dependentid;
	}

	@Override
	public String toString(){
		return String.format("name:%s,dependentid:%d", name, dependentid);
	}
}
