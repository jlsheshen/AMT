package com.edu.accountingteachingmaterial.oldsubject.subject.data;

import com.edu.library.data.BaseData;
import com.edu.subject.SubjectType;

/**
 * 题目数据，与服务器交互使用
 * 
 * @author lucher
 * 
 */
public class SubjectData extends BaseData {
	// 预置标识
	private int flag;
	// 章节id
	private int chapterId;
	// 题目内容
	private String question;
	// 图片
	private String pic;
	// 该题分数
	private float score;// 分数
	// 正确答案
	private String answer;
	// 解析
	private String analysis;
	/**
	 * 题目类别,与{@link SubjectType}对应
	 */
	private int subjectType;
	// 选项
	private String option;
	
	//模板id
	private String templateId;
	//标签
	private String label;

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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
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

	public int getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(int subjectType) {
		this.subjectType = subjectType;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return String.format("id:%s,question:%s", flag, question);
	}

}
