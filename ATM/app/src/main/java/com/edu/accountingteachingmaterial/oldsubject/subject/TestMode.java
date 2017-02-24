package com.edu.accountingteachingmaterial.oldsubject.subject;

/**
 * 测试模式
 * 
 * @author lucher
 * 
 */
public class TestMode {
	/**
	 * 练习模式，该模式答完直接显示答案，且判断答案的正确性
	 */
	public static final int MODE_PRACTICE = 3;
	/**
	 * 测试模式，该模式答完不显示答案，也不判断答案的正确性，提交后才会判断
	 */
	public static final int MODE_EXAM =1;
	/**
	 * 显示详情模式，该模式会显示出正确答案，用户答案，解析等信息
	 */
	public static final int MODE_SHOW_DETAILS = 2;
	/**
	 * 查看我的作答模式
	 */
	public static final int MODE_LOOK = 4;
}
