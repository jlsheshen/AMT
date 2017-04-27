package com.edu.subject.net;

import com.edu.library.data.BaseData;

/**
 * 答题结果-用于上传服务器
 * 
 * @author lucher
 * 
 */
public class SubjectAnswerResult extends BaseData {

	// 服务器端题目id
	private int flag;
	// 题目类型
	private int type;
	// 用户答案
	private String answer;
	// 用户得分
	private float score;
	// 是否答对
	private boolean right;

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

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return String.format("flag:%s,answer:%s", flag, answer);
	}
}