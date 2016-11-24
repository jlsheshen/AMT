package com.edu.subject;

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
}
