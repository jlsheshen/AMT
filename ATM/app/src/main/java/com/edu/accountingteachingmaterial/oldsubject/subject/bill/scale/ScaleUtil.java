package com.edu.accountingteachingmaterial.oldsubject.subject.bill.scale;


/**
 * 缩放工具类
 * @author lucher
 *
 */
public class ScaleUtil {
	private static final String TAG = "ScaleUtil";
	
	/**
	 * 根据缩放比例对给定值进行缩放
	 * @param value 待缩放的值
	 * @param scale 缩放比例
	 * @return 缩放后的值
	 */
	public static int getScaledValue(float value, float scale) {
		return Math.round(value * scale);
	}
}
