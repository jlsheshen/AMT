package com.edu.library.util;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.TypedValue;

/**
 * px和dp之间转换
 * 
 * @author lucher
 */
public class DensityUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 使用TypedValue的方法转换
	 * 
	 * @param context
	 * @param unit
	 *            待转换数的单位，使用TypedValue的类型
	 * @param value
	 *            待转换的值
	 * @return eg: what2px(context, TypedValue.COMPLEX_UNIT_DIP, 20)
	 *         表示把20dip转为px值
	 */
	public static int what2px(Context context, int unit, int value) {
		return (int) TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics());
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}
}