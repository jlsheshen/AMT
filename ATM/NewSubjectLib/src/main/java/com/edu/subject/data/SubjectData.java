package com.edu.subject.data;

import com.edu.library.data.BaseData;
import com.edu.subject.SubjectType;

/**
 * 题目数据类
 * 
 * @author lucher
 * 
 */
public class SubjectData extends BaseData {

	//父id，用于综合题
	protected int parentId;
	// 预置标识
	protected int flag;
	// 章节id
	protected int chapterId;
	/**
	 * 题目类别,与{@link SubjectType}对应
	 */
	protected int subjectType;
	// 题目富文本内容
	protected String question;
	// 正确答案RichTextData
	protected String answer;
	// 解析
	protected String analysis;
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

	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	public int getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(int subjectType) {
		this.subjectType = subjectType;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
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
		return String.format("id:%s,question:%s", id, question.substring(0, Math.min(question.length(), 50)));
	}
}
