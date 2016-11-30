package com.edu.accountingteachingmaterial.bean;


import com.edu.subject.data.BaseSubjectData;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.net.AnswerResult;

/**
 * 基础题型测试数据：：单多判
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
	private SubjectBasicData subjectData;

	@Override
	public BaseSubjectData getSubjectData() {
		return subjectData;
	}

	@Override
	public void setSubjectData(BaseSubjectData subjectData) {
		this.subjectData = (SubjectBasicData) subjectData;
	}

	@Override
	public String toString() {
		return String.format("subjectData:%s,uAnswer:%s", subjectData, uAnswer);
	}

	@Override
	public AnswerResult toResult() {
		AnswerResult result = new AnswerResult();
		result.setFlag(getSubjectData().getFlag());
		result.setType(subjectType);
		if (uAnswer == null) {
			result.setAnswer("");
		} else {
			result.setAnswer(uAnswer);
		}
		result.setScore(uScore);

		return result;
	}
}
