package com.edu.library.common;

import android.content.Context;
import android.content.Intent;

/**
 * 系统状态栏和导航栏显示与隐藏的帮助类
 * 
 * @author lucher
 * 
 */
public class SystemUIHelper {

	// 显示状态栏和导航栏的广播
	private static final String ACTION_SHOW_SYSTEM_UI = "com.edu.show.systemui";
	// 隐藏状态栏和导航栏的广播
	private static final String ACTION_HIDE_SYSTEM_UI = "com.edu.hide.systemui";

	/**
	 * 显示状态栏和导航栏
	 * @param context
	 */
	public static void showSystemUI(Context context) {
		context.sendBroadcast(new Intent(ACTION_SHOW_SYSTEM_UI));
	}

	/**
	 * 隐藏状态栏和导航栏
	 * @param context
	 */
	public static void hideSystemUI(Context context) {
		context.sendBroadcast(new Intent(ACTION_HIDE_SYSTEM_UI));
	}
}
