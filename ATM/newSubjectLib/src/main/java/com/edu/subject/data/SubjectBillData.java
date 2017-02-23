package com.edu.subject.data;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.edu.subject.data.body.BillBodyData;

/**
 * 单据题目数据
 * 
 * @author lucher
 * 
 */
public class SubjectBillData extends CommonSubjectData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 123432L;
	//标签
	private String label;
	//单据题目body内容
	private List<BillBodyData> bills;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<BillBodyData> getBills() {
		return bills;
	}

	public void setBills(List<BillBodyData> bills) {
		this.bills = bills;
	}

	/**
	 * 制作body
	 */
	public void makeBody() {
		setBills(JSON.parseArray(body, BillBodyData.class));
	}
}
