package com.edu.subject;

/**
 * 测试模式
 * 
 * @author lucher
 * 
 */
public class TestMode {
	/**
	 * 练习模式，进入该模式后会加载之前的用户答案并显示对应的状态，作答后直接显示答案，且判断答案的正确性
	 */
	public static final int MODE_PRACTICE = 1;
	/**
	 * 测试模式，进入该模式后会加载之前的用户答案，该模式答完不显示答案，也不判断答案的正确性，提交后才会判断
	 */
	public static final int MODE_EXAM = 2;
	/**
	 * 测试清空模式，进入该模式后不会加载用户答案，该模式答完不显示答案，也不判断答案的正确性，提交后才会判断
	 */
	public static final int MODE_EXAM_EMPTY = 3;
	/**
	 * 显示详情模式，进入该模式后会显示出正确答案，用户答案，解析等信息，不能编辑
	 */
	public static final int MODE_SHOW_DETAILS = 4;
	/**
	 * 显示用户答案模式，进入该模式后只显示出用户答案，不能编辑
	 */
	public static final int MODE_SHOW_UANSWER = 5;
}
