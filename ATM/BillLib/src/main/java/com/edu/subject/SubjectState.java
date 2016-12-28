package com.edu.subject;

/**
 * 题目答题状态
 * @author lucher
 *
 */
public class SubjectState {
	/**
	 * 初始化状态:用于练习/测试模式
	 */
	public static final int STATE_INIT = 0;
	/**
	 * 未完成状态：用于练习/测试模式
	 */
	public static final int STATE_UNFINISH = 1;
	/**
	 * 正确状态-用于所有模式
	 */
	public static final int STATE_CORRECT = 2;
	/**
	 * 错误状态-用于所有模式
	 */
	public static final int STATE_WRONG = 3;
	/**
	 * 错误状态-在错题页面删除后的状态
	 */
	public static final int STATE_AFTER_WRONG = 4;


}
