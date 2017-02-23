package com.edu.subject.data;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.edu.subject.data.body.SelectBodyData;

/**
 * 选择类题目数据
 * 
 * @author lucher
 * 
 */
public class SubjectSelectData extends CommonSubjectData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 123432L;
	//选择类题目body内容
	private List<SelectBodyData> options;

	public List<SelectBodyData> getOptions() {
		return options;
	}

	public void setOptions(List<SelectBodyData> options) {
		this.options = options;
	}

	/**
	 * 制作body
	 */
	public void makeBody() {
		setOptions(JSON.parseArray(body, SelectBodyData.class));
	}
}
