package com.edu.subject.data;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 填空题题目数据
 * 
 * @author lucher
 * 
 */
public class SubjectBlankData extends CommonSubjectData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 12343232L;
	//填空题对应的答案
	private List<String> answers;

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	/**
	 * 把answer转为list
	 */
	public void makeAnswers() {
		try {
			setAnswers(JSON.parseArray(answer.getText(), String.class));
		} catch (Exception e) {

		}
	}
}
