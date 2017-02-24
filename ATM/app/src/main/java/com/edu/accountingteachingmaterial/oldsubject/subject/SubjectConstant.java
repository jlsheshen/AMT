package com.edu.accountingteachingmaterial.oldsubject.subject;

/**
 * 题目相关常量定义
 * 
 * @author lucher
 * 
 */
public class SubjectConstant {
	/**
	 * 多条数据分割符
	 */
	public static final String SEPARATOR_ITEM = "<<<";
	/**
	 * 多组数据分割符
	 */
	public static final String SEPARATOR_GROUP = ">>>";
	/**
	 * 多个答案分割符-目前用于单据题中空可以有多个答案的情况
	 */
	public static final String SEPARATOR_MULTI_ANSWER = "&";

	/**
	 * 空字符串标识符-目前主要用于单据题中空答案为空的情况
	 */
	public static final String FLAG_NULL_STRING = "null";
	/**
	 * 不可编辑前缀标识-目前主要用于单据题中空不需要用户填写，而是直接显示正确答案的情况
	 */
	public static final String FLAG_PREFIX_DISABLED = "***";
	/**
	 * 印章数据分割符
	 */
	public static final String SEPARATOR_SIGN_INFO = "-";

	/**
	 * 单据元素类别为空的范围起始值
	 */
	public static final int ELEMENT_TYPE_BLANK_START = 10;
	/**
	 * 单据元素类别为空的范围截止值
	 */
	public static final int ELEMENT_TYPE_BLANK_END = 30;

	/**
	 * 数据库名字，需要在具体项目中重新赋值
	 */
	public static String DATABASE_NAME = "AccountingTeaching.db";
}
