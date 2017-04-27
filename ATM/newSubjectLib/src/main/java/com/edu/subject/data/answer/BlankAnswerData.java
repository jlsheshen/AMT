package com.edu.subject.data.answer;

import java.util.List;

/**
 * 填空题答案数据
 * @author lucher
 *
 */
public class BlankAnswerData extends CommonAnswerData {

	//填空答案
	private List<BlankAnswer> answers;

	public List<BlankAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<BlankAnswer> answers) {
		this.answers = answers;
	}




}
