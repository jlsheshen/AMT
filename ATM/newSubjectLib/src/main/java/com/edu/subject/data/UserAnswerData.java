package com.edu.subject.data;

import com.alibaba.fastjson.JSON;

/**
 * 通用题型用户答案数据封装，用于保持各种题型的答案保存到数据库以及从数据库读取的方式统一
 * @author lucher
 *
 */
public class UserAnswerData {

	//用户答案
	private String uanswer;

	public String getUanswer() {
		return uanswer;
	}

	public void setUanswer(String uanswer) {
		this.uanswer = uanswer;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
