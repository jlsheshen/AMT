package com.edu.subject.bill.template;

import com.edu.library.data.BaseData;
import com.edu.subject.bill.element.info.BaseElementInfo;

import java.util.List;

/**
 * 单据模板,注：模板中使用的图片不能存放在drawable下，否则可能会引起界面混乱
 * 
 * @author lucher
 * 
 */
public class BillTemplate extends BaseData {

	// 模板id
	private int id;
	// 底图图片
	private String bitmap;
	// 模板名称
	private String name;
	// 预置标识，-1：预置，其他：非预置
	private int flag;
	// 对应的所有空
	private List<BaseElementInfo> elementDatas;

	public BillTemplate() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBitmap() {
		return bitmap;
	}

	public void setBitmap(String bitmap) {
		this.bitmap = bitmap;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<BaseElementInfo> getElementDatas() {
		return elementDatas;
	}

	public void setElementDatas(List<BaseElementInfo> elementDatas) {
		this.elementDatas = elementDatas;
	}
}
