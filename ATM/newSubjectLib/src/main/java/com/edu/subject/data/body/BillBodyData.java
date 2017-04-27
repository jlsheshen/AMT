package com.edu.subject.data.body;

import com.edu.library.data.BaseData;

/**
 * 单据题型body对象
 * @author lucher
 *
 */
public class BillBodyData extends BaseData {

	//模板id
	private int templateId;
	//标签-多组单据使用
	private String label;
	//对应的空，具体规则见数据库说明文档
	private String blanks;
	//对应的印章，具体规则见数据库说明文档
	private String signs;
	//对应的闪电符，具体规则见数据库说明文档
	private String flashs;

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getBlanks() {
		return blanks;
	}

	public void setBlanks(String blanks) {
		this.blanks = blanks;
	}

	public String getSigns() {
		return signs;
	}

	public void setSigns(String signs) {
		this.signs = signs;
	}

	public String getFlashs() {
		return flashs;
	}

	public void setFlashs(String flashs) {
		this.flashs = flashs;
	}

}
