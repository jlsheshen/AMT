package com.edu.subject.data.answer;

import java.util.List;

import com.edu.subject.data.UserAnswerData;

/**
 * 综合题答案数据
 * @author lucher
 *
 */
public class ComprehensiveAnswerData extends CommonAnswerData {

	// 所有子题答案数据
	private List<UserAnswerData> answerDatas;

	public List<UserAnswerData> getAnswerDatas() {
		return answerDatas;
	}

	public void setAnswerDatas(List<UserAnswerData> answerDatas) {
		this.answerDatas = answerDatas;
	}
}
