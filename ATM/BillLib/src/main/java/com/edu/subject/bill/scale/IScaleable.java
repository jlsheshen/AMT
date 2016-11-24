package com.edu.subject.bill.scale;

/**
 * 支持缩放控件接口
 * 
 * @author lucher
 * 
 */
public interface IScaleable {
	
	/**
	 * 按指定比例对控件进行缩放
	 * 
	 * @param scale
	 *            缩放比例
	 * @param scaleTimes
	 *            缩放倍数
	 */
	void postScale(float scale, int scaleTimes);
}
