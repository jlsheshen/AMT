package com.edu.subject;

import java.util.List;

import com.edu.subject.data.BaseTestData;

/**
 * 题目视图监听
 * @author lucher
 *
 */
public interface SubjectListener {

	/**
	 * 答题完毕-主要用于自动跳转到下一题
	 */
	void onComplete();
	
	/**
	 * 保存答案-例如单选题在练习模式点击选项的时候需要保存答案，这个操作需要由具体的调用者处理
	 * @param testData
	 */
	void onSaveTestData(BaseTestData testData);
	
	/**
	 * 保存答案-例如单选题在练习模式点击选项的时候需要保存答案，这个操作需要由具体的调用者处理
	 * @param testDatas
	 */
	void onSaveTestDatas(List<BaseTestData> testDatas);
}
