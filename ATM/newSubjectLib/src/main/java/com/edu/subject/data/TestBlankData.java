package com.edu.subject.data;

import com.alibaba.fastjson.JSON;
import com.edu.subject.data.answer.BlankAnswerData;
import com.edu.subject.net.SubjectAnswerResult;

/**
 * 填空题测试数据
 * 
 * @author lucher
 * 
 */
public class TestBlankData extends BaseTestData {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7866847549496754459L;

	// 题目数据
	private SubjectBlankData subjectData;

	@Override
	public SubjectBlankData getSubjectData() {
		return subjectData;
	}

	@Override
	public void setSubjectData(CommonSubjectData subjectData) {
		this.subjectData = (SubjectBlankData) subjectData;
	}

	@Override
	public SubjectAnswerResult toResult() {
		SubjectAnswerResult result = new SubjectAnswerResult();
		result.setFlag(getSubjectData().getFlag());
		result.setType(getSubjectData().getSubjectType());
		if (answerData == null) {
			result.setAnswer("null");
		} else {
			result.setAnswer(JSON.toJSONString(answerData));
		}
		result.setScore(uScore);

		return result;
	}

	public BlankAnswerData getUAnswerData() {
		return (BlankAnswerData) answerData;
	}

	/**
	 * 获取用户答案，然后保存到数据库
	 * @return
	 */
	public String getUAnswer() {
		userAnswer = new UserAnswerData();
		userAnswer.setUanswer(JSON.toJSONString(answerData));
		return userAnswer.toString();
	}

	@Override
	public void parseUAnswerData(UserAnswerData answer) {
		if (answer != null) {
			answerData = JSON.parseObject(answer.getUanswer(), BlankAnswerData.class);
		}
	}

	@Override
	public String toString() {
		return String.format("subjectData:%s,uAnswer:%s,score:%s", subjectData, userAnswer, uScore);
	}
}
