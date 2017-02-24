package com.edu.accountingteachingmaterial.oldsubject.subject;

import com.edu.subject.SubjectListener;
import com.edu.subject.data.BaseTestData;

/**
 * 题目视图对象接口
 * 
 * @author lucher
 * 
 */
public interface ISubject {

	/**
	 * 从object恢复视图状态
	 * 
	 * @param data
	 */
	void applyData(BaseTestData data);

	/**
	 * 保存答案
	 */
	void saveAnswer();

	/**
	 * 提交
	 * 
	 * @return 得分
	 */
	float submit();

	/**
	 * 显示详情
	 */
	void showDetails();

	/**
	 * 重置该题
	 */
	void reset();
	
	/**
	 * 设置题目视图监听
	 * @param listener
	 */
	void setSubjectListener(SubjectListener listener);
}
