package com.edu.subject.data;

import com.alibaba.fastjson.JSON;
import com.edu.subject.SubjectType;
import com.edu.subject.data.answer.BillAnswerData;
import com.edu.subject.net.SubjectAnswerResult;

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
	public CommonSubjectData getSubjectData() {
		return testDatas.get(0).getSubjectData();
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public void setSubjectData(CommonSubjectData subjectData) {
	}

	@Override
	public SubjectAnswerResult toResult() {
		SubjectAnswerResult result = new SubjectAnswerResult();

		List<BillAnswerData> billAnswers = new ArrayList<BillAnswerData>(testDatas.size());
		for (int i = 0; i < testDatas.size(); i++) {
			TestBillData testBill = testDatas.get(i);
			BillAnswerData answer = testBill.getUAnswerData();
			if (answer != null) {
				answer.setLabel(testBill.getSubjectData().getBills().get(i).getLabel());
			}
			billAnswers.add(answer);
		}
		String uAnswer = JSON.toJSONString(billAnswers);
		result.setAnswer(uAnswer);
		result.setFlag(testDatas.get(0).getFlag());
		result.setType(SubjectType.SUBJECT_BILL);
		if (uAnswer == null) {
			result.setAnswer("");
		} else {
			result.setAnswer(uAnswer);
		}
		result.setScore(uScore);
		result.setRight(uScore == getSubjectData().getScore());
		
		return result;
	}

	/**
	 * 获取用户答案，然后保存到数据库
	 * @return
	 */
	public String getUAnswer() {
		return userAnswer==null?"" : userAnswer.toString();
	}

	@Override
	public void parseUAnswerData(UserAnswerData answer) {

	}
}
