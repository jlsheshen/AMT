package com.edu.subject.data;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.edu.subject.SubjectType;
import com.edu.subject.data.answer.ComprehensiveAnswerData;
import com.edu.subject.net.SubjectAnswerResult;

/**
 * 综合题测试数据
 * 
 * @author lucher
 * 
 */
public class TestComprehensiveData extends BaseTestData {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7866847549496754459L;
	// 题目数据
	private SubjectComprehensiveData subjectData;
	// 子题测试数据
	private List<BaseTestData> testDatas;

	@Override
	public SubjectComprehensiveData getSubjectData() {
		return subjectData;
	}

	@Override
	public void setSubjectData(CommonSubjectData subjectData) {
		this.subjectData = (SubjectComprehensiveData) subjectData;
	}

	public List<BaseTestData> getTestDatas() {
		return testDatas;
	}

	public void setTestDatas(List<BaseTestData> testDatas) {
		this.testDatas = testDatas;
	}

	@Override
	public SubjectAnswerResult toResult() {
		SubjectAnswerResult result = new SubjectAnswerResult();
		result.setFlag(getSubjectData().getFlag());
		result.setType(SubjectType.SUBJECT_COMPREHENSIVE);
		if (getUAnswer() == null) {
			result.setAnswer("null");
		} else {
			result.setAnswer(getUAnswer().toString());
		}
		result.setScore(uScore);
		return result;
	}

	@Override
	public ComprehensiveAnswerData getUAnswerData() {
		return (ComprehensiveAnswerData) answerData;
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
			answerData = JSON.parseObject(answer.getUanswer(), ComprehensiveAnswerData.class);
		}
	}
}
