package com.edu.subject.data;


import com.edu.subject.net.AnswerResult;

import java.util.List;

/**
 * 分组单据测试数据
 * 
 * @author lucher
 * 
 */
public class TestGroupBillData extends BaseTestData {

	// 对应单据的数据
	private List<TestBillData> testDatas;

	//该组单据的总分
	private float score;

	public List<TestBillData> getTestDatas() {
		return testDatas;
	}

	public void setTestDatas(List<TestBillData> testData) {
		this.testDatas = testData;
	}

	@Override
	public BaseSubjectData getSubjectData() {
		return testDatas.get(0).getSubjectData();
	}



	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public void setSubjectData(BaseSubjectData subjectData) {

	}
	@Override
	public AnswerResult toResult() {
		return null;
	}
}
