package com.edu.accountingteachingmaterial.bean;

import com.edu.subject.data.BaseSubjectData;

/**
 * 分录数据封装
 * 
 * @author edu_lx
 * 
 */
public class SubjectEntryData extends BaseSubjectData {

	private int serverId;
	// 问题描述
	private String question;
	// 图片名称
	private String picName;
	// 借款正确答案 1-1-200>>>2-4-100
	private String borrowAns;
	// 贷款正确答案
	private String loanAns;
	// 借款用户答案
	private String borrowUser;
	// 贷款用户答案
	private String loanUser;
	private boolean isCorrect;
	// 章节id
	private int chapter_id;
	private int type;
	private String children;

	private float uScore;

	public float getuScore() {
		return uScore;
	}

	public void setuScore(float uScore) {
		this.uScore = uScore;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getBorrowAns() {
		return borrowAns;
	}

	public void setBorrowAns(String borrowAns) {
		this.borrowAns = borrowAns;
	}

	public String getLoanAns() {
		return loanAns;
	}

	public void setLoanAns(String loanAns) {
		this.loanAns = loanAns;
	}

	public String getBorrowUser() {
		return borrowUser;
	}

	public void setBorrowUser(String borrowUser) {
		this.borrowUser = borrowUser;
	}

	public String getLoanUser() {
		return loanUser;
	}

	public void setLoanUser(String loanUser) {
		this.loanUser = loanUser;
	}

	public int getChapter_id() {
		return chapter_id;
	}

	public void setChapter_id(int chapter_id) {
		this.chapter_id = chapter_id;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return String.format("4,question:%s,picname:%s,borrowans:%s,loanans:%s,borrowuser:%s,loanuser:%s", question, picName, borrowAns, loanAns, borrowUser, loanUser);
	}
}
