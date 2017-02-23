package com.edu.subject.entry.tmp;

import com.edu.library.data.BaseData;

/**
 * 分录科目数据封装
 * @author lucher
 *
 */
public class EntrySubjectData extends BaseData {

	// 科目类型,属于一级主科目还是一级从科目或者二级科目
	private int subType;
	// 预置标识
	protected int flag;
	// 科目名称
	private String name;
	// 父id
	private int parentId;

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return String.format("subtype:%s,name:%s,parentid:%s", subType, name, parentId);
	}

}
