package com.edu.accountingteachingmaterial.oldsubject.subject.data;

import com.edu.library.data.BaseData;

/**
 * 印章数据封装
 * 
 * @author lucher
 * 
 */
public class SignData extends BaseData {

	private static final long serialVersionUID = 1917503070608258845L;
	// 预置标识，-1：预置，其他：非预置
	private int flag;
	// 印章的名称
	private String name;
	// 印章图片：1.预置印章：assets://sign.png,file://sign.png 2.下载印章：http://sign.png 3.人名印章：name
	private String pic;

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

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}
