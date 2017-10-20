package com.edu.subject.util;

import android.content.Context;

/**
 * 根据分辨率对长度值进行缩放
 * 
 * @author xd
 * 
 */
public class MyScaleUtil {

	// private static final String TAG = "ScaleUtil";
	// 设备的屏幕密度
	public float mDensity_x;
	public float mDensity_y;
	public float mDensity;
	private float mm;
	/**
	 * 自身引用
	 */
	private static MyScaleUtil instance = null;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static MyScaleUtil getInstance(Context context) {

		if (instance == null)
			instance = new MyScaleUtil(context);
		return instance;
	}

	/**
	 * 构造
	 * 
	 * @param context
	 */
	public MyScaleUtil(Context context) {
		mDensity_x = (float) (context.getResources().getDisplayMetrics().widthPixels / 1024.0);
		mDensity_y = (float) (context.getResources().getDisplayMetrics().heightPixels / 552.0);
		mDensity = context.getResources().getDisplayMetrics().density;
		// context.getResources().getDisplayMetrics().scaledDensity;
		// Log.d(TAG, "mDensity:" + mDensity);
	}

	public float getM() {
		return mm;
	}

	/**
	 * 根据屏幕密度对value进行缩放
	 * 
	 * @param value
	 *            待缩放值
	 * @return 缩放后的值
	 */
	public float getScaledValue(int value, int type) {
		if (type == 1) {// X轴的坐标
			return value * mDensity_x;
		} else if (type == 2) {// Y轴的
			return value * mDensity_y;
		} else {// X轴的宽度
			return value * mDensity_x;
		}
	}

}
