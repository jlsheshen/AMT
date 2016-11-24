package com.edu.subject.bill.element;

/**
 * 单据中元素的类型定义
 * 
 * @author lucher
 * 
 */
public class ElementType {
	/**
	 * 背景图
	 */
	public static final int TYPE_BG = 1;
	/**
	 * 普通空
	 */
	public static final int TYPE_NORMAL = 11;
	/**
	 * 大写日期空
	 */
	public static final int TYPE_DATE_UPPER = 12;
	/**
	 * 小写日期空
	 */
	public static final int TYPE_DATE_LOWER = 13;
	/**
	 * 大写金额空
	 */
	public static final int TYPE_AMOUNT_UPPER = 14;
	/**
	 * 小写金额空-不加逗号
	 */
	public static final int TYPE_AMOUNT_LOWER = 15;
	/**
	 * 小写金额空-加逗号
	 */
	public static final int TYPE_AMOUNT_LOWER_COMMA = 16;
	/**
	 * 小写金额独立空-推荐使用小写金额组合空：TYPE_AMOUNT_LOWER_GROUP
	 */
	public static final int TYPE_AMOUNT_LOWER_SEP = 17;
	/**
	 * 竖直空
	 */
	public static final int TYPE_VERTICAL = 18;
	/**
	 * 多个答案空
	 */
	public static final int TYPE_MULTI_ANSWER = 19;
	/**
	 * 关键字空
	 */
	public static final int TYPE_KEY_WORD = 20;
	/**
	 * 普通空-内容-居中
	 */
	public static final int TYPE_NORMAL_CENTER = 21;
	/**
	 * 小写金额空-不加逗号-居中
	 */
	public static final int TYPE_AMOUNT_LOWER_CENTER = 22;
	/**
	 * 小写金额空-加逗号-居中
	 */
	public static final int TYPE_AMOUNT_LOWER_COMMA_CENTER = 23;
	/**
	 * 小写金额组合空
	 */
	public static final int TYPE_AMOUNT_LOWER_GROUP = 24;
	
	/**
	 * 印章
	 */
	public static final int TYPE_SIGN = 30;
	/**
	 * 闪电符
	 */
	public static final int TYPE_FLASH = 40;
}
