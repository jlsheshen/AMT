package com.edu.subject.data;

import com.edu.library.data.BaseData;
import com.edu.subject.SubjectType;
import com.edu.subject.common.rich.RichTextData;

/**
 * 题目数据类
 * 
 * @author lucher
 * 
 */
public class CommonSubjectData extends BaseData {

	//父id，用于综合题
	protected int parentId;
	// 预置标识
	protected int flag;
	/**
	 * 题目类别,与{@link SubjectType}对应
	 */
	protected int subjectType;
	// 题目富文本内容
	protected RichTextData question;
	// 正确答案RichTextData
	protected RichTextData answer;
	// 解析
	protected RichTextData analysis;
	// 该题分数
	protected float score;

	// 该题对应body，某些题型不需要
	protected String body;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(int subjectType) {
		this.subjectType = subjectType;
	}

	public RichTextData getQuestion() {
		return question;
	}

	public void setQuestion(RichTextData question) {
		this.question = question;
	}

	public RichTextData getAnswer() {
		return answer;
	}

	public void setAnswer(RichTextData answer) {
		this.answer = answer;
	}

	public RichTextData getAnalysis() {
		return analysis;
	}

	public void setAnalysis(RichTextData analysis) {
		this.analysis = analysis;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return String.format("id:%s,question:%s", id, question.getText().substring(0, Math.min(question.getText().length(), 50)));
	}
}
