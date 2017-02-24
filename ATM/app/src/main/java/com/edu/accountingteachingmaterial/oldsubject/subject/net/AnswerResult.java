package com.edu.accountingteachingmaterial.oldsubject.subject.net;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * 答题结果-用于上传服务器
 * 
 * @author lucher
 * 
 */
public class AnswerResult extends BaseData {

	// 服务器端题目id
	private int flag;
	// 题目类型
	private int type;
	// 用户答案
	private String answer;
	// 用户得分
	private float score;

	/******** 下面为单据专用 **********/
	// 用户印章
	private List<SignResult> sign;
	// 用户答案
	private List<BlankResult> blankResult;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public List<SignResult> getSign() {
		return sign;
	}

	public void setSign(List<SignResult> sign) {
		this.sign = sign;
	}

	public List<BlankResult> getBlankResult() {
		return blankResult;
	}

	public void setBlankResult(List<BlankResult> blankResult) {
		this.blankResult = blankResult;
	}


	/**
	 * 单据题印章的答题结果
	 * 
	 * @author lucher
	 * 
	 */
	public class SignResult {
		// 待实现
	}
}