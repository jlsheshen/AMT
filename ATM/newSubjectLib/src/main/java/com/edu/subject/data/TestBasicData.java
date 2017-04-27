package com.edu.subject.data;

import com.edu.subject.data.answer.BasicAnswerData;
import com.edu.subject.net.SubjectAnswerResult;

/**
 * 基础类题型题目测试数据
 * 
 * @author lucher
 * 
 */
public class TestBasicData extends BaseTestData {
	/**
	 * 
	 */
	private static final long serialVersionUID = -78668476754459L;

	// 题目数据
	private CommonSubjectData subjectData;

	@Override
	public CommonSubjectData getSubjectData() {
		return subjectData;
	}

	@Override
	public void setSubjectData(CommonSubjectData subjectData) {
		this.subjectData = subjectData;
	}

	/**
	 * 获取用户答案，然后保存到数据库
	 * @return
	 */
	public String getUAnswer() {
		return userAnswer == null? "":userAnswer.toString();
	}

	@Override
	public BasicAnswerData getUAnswerData() {
		return (BasicAnswerData) answerData;
	}

	@Override
	public String toString() {
		return String.format("subjectData:%s,uAnswer:%s", subjectData, userAnswer);
	}

	@Override
	public SubjectAnswerResult toResult() {
		SubjectAnswerResult result = new SubjectAnswerResult();
		result.setFlag(getSubjectData().getFlag());
		result.setType(getSubjectData().getSubjectType());
		if (userAnswer == null && answerData == null) {
			result.setAnswer("");
		} else {
			if (answerData != null) {
				result.setAnswer(((BasicAnswerData) answerData).getUanswer());
			} else if (userAnswer != null) {
				result.setAnswer(userAnswer.toString());
			}
		}
		result.setScore(uScore);
		result.setRight(uScore == getSubjectData().getScore());

		return result;
	}

	@Override
	public void parseUAnswerData(UserAnswerData answer) {
		if (answer != null) {
			answerData = new BasicAnswerData();
			((BasicAnswerData) answerData).setUanswer(answer.getUanswer());
//			answerData.setuScore(answer.getuScore());
		}
	}
}
