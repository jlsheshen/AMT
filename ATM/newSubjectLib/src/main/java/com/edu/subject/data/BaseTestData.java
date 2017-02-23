package com.edu.subject.data;

import com.edu.library.data.BaseData;
import com.edu.subject.SubjectState;
import com.edu.subject.TestMode;
import com.edu.subject.data.answer.CommonAnswerData;
import com.edu.subject.net.SubjectAnswerResult;

/**
 * 题目测试数据基类
 * 
 * @author lucher
 * 
 */
public abstract class BaseTestData extends BaseData {

	// 问题显示的index
	protected String subjectIndex;
	// 预置标识
	protected int flag;
	// 题目id
	protected int subjectId;
	// 用户答案数据封装，用于保持各种题型的答案保存到数据库以及从数据库读取的方式统一
	protected UserAnswerData userAnswer;
	// 各题型答案数据保存，用于各题型对答案数据的操作
	protected CommonAnswerData answerData;
	// 用户得分
	protected float uScore;
	/**
	 * 作答状态，与{@link SubjectState}对应
	 */
	protected int state;

	/**
	 * 测试模式，与{@link TestMode}对应
	 */
	protected int testMode;

	public abstract CommonSubjectData getSubjectData();

	/**
	 * 设置题目数据
	 * @param subjectData
	 */
	public abstract void setSubjectData(CommonSubjectData subjectData);

	/**
	 * 转换为结果对象
	 * @return
	 */
	public abstract SubjectAnswerResult toResult();
	
	/**
	 * 获取用户答案，然后保存到数据库
	 * @return
	 */
	public abstract String getUAnswer();
	
	/**
	 * 解析用户答案，用于从数据库恢复答案
	 * @param answerData
	 */
	public abstract void parseUAnswerData(UserAnswerData answerData);
	
	/**
	 * 设置用户答案，用于从各题型获取用户答案后保存到数据库
	 * @param uAnswer 答案数据字符串
	 */
	public void setUAnswer(String uAnswer) {
		userAnswer = new UserAnswerData();
		userAnswer.setUanswer(uAnswer);
		parseUAnswerData(userAnswer);
	}

	public void setUAnswerData(CommonAnswerData answerData) {
		this.answerData = answerData;
	}
	
	public CommonAnswerData getUAnswerData() {
		return answerData;
	}

	public int getTestMode() {
		return testMode;
	}

	public void setTestMode(int testMode) {
		this.testMode = testMode;
	}

	public void setSubjectIndex(String subjectIndex) {
		this.subjectIndex = subjectIndex;
	}

	public String getSubjectIndex() {
		return subjectIndex;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public float getuScore() {
		return uScore;
	}

	public void setuScore(float uScore) {
		this.uScore = uScore;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
