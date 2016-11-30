package com.edu.subject.data;

/**
 * 单据题目数据
 * 
 * @author lucher
 * 
 */
public class SubjectBillData extends BaseSubjectData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 123432L;

	// 多组单据标签
	private String label;
	//错误次数
	private int errorCount;

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {

		return String.format("templateId:%s,question:%s", templateId, question);
	}
}
