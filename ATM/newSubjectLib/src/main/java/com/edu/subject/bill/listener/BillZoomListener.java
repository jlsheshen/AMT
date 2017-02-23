package com.edu.subject.bill.listener;

/**
 * 单据缩放监听类
 * 
 * @author lucher
 * 
 */
public interface BillZoomListener {

	/**
	 * 缩放初始化
	 * 
	 * @param zoomInEnable
	 *            是否可放大
	 * @param zoomOutEnable
	 *            是否可缩小
	 */
	void onZoomInit(boolean zoomInEnable, boolean zoomOutEnable);

	/**
	 * 放大开始
	 * 
	 * @param currentScaleTimes
	 *            当前缩放倍数
	 */
	void onZoomInStart(int currentScaleTimes);

	/**
	 * 放大结束
	 * 
	 * @param currentScaleTimes
	 *            当前缩放倍数
	 * @param zoomInEnable
	 *            是否可放大
	 * @param zoomOutEnable
	 *            是否可缩小
	 */
	void onZoomInEnd(int currentScaleTimes, boolean zoomInEnable, boolean zoomOutEnable);

	/**
	 * 缩小开始
	 * 
	 * @param currentScaleTimes
	 *            当前缩放倍数
	 */
	void onZoomOutStart(int currentScaleTimes);

	/**
	 * 缩小结束
	 * 
	 * @param currentScaleTimes
	 *            当前缩放倍数
	 * @param zoomInEnable
	 *            是否可放大
	 * @param zoomOutEnable
	 *            是否可缩小
	 */
	void onZoomOutEnd(int currentScaleTimes, boolean zoomInEnable, boolean zoomOutEnable);
}
