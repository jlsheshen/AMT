package com.edu.subject.data;

import com.alibaba.fastjson.JSON;
import com.edu.subject.data.answer.EntryAnswerData;
import com.edu.subject.net.SubjectAnswerResult;

/**
 * 分录测试数据
 * 
 * @author lucher
 * 
 */
public class TestEntryData extends BaseTestData {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7866847549496754459L;

	// 题目数据
	private SubjectEntryData subjectData;

	@Override
	public SubjectEntryData getSubjectData() {
		return subjectData;
	}

	@Override
	public void setSubjectData(CommonSubjectData subjectData) {
		this.subjectData = (SubjectEntryData) subjectData;
	}

	@Override
	public SubjectAnswerResult toResult() {
		return null;
	}

	public EntryAnswerData getUAnswerData() {
		return (EntryAnswerData) answerData;
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
			answerData = JSON.parseObject(answer.getUanswer(), EntryAnswerData.class);
		}
	}

	@Override
	public String toString() {
		return String.format("subjectData:%s,uAnswer:%s,score:%s", subjectData, userAnswer, uScore);
	}
}
