package com.edu.subject.data;


import com.edu.subject.SubjectType;
import com.edu.subject.net.AnswerResult;
import com.edu.subject.net.BlankResult;

import java.util.ArrayList;
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
		AnswerResult result = new AnswerResult();
		ArrayList<BlankResult> blankResults = new ArrayList<>();
		for (TestBillData testBill : testDatas) {
			AnswerResult result1 = new AnswerResult();
			testBill.judgeAnswer(result1);
			blankResults.addAll(result1.getBlankResult());
		}

		result.setBlankResult(blankResults);
		result.setAnswer(uAnswer);
		result.setFlag(getSubjectData().getFlag());
		result.setType(SubjectType.SUBJECT_BILL);
		if (uAnswer == null) {
			result.setAnswer("null");
		} else {
			result.setAnswer(uAnswer);
		}
		result.setScore(uScore);
		return result;
	}
}
