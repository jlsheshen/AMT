package com.edu.library.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * toast工具，更方便的使用toast，主要解决多次触发toast后，toast显示时间太长的问题
 * 
 * @author lucher
 * 
 */
public class ToastUtil {

	private static final String TAG = "ToastUtil";
	private static Toast toast;
	static boolean inTest = false;

	/**
	 * 显示toast，若多次触发，按最后一次的显示时间显示
	 * 
	 * @param context
	 * @param msg
	 *            显示的信息
	 * @param duration
	 *            显示的时长
	 */
	public static void showToast(Context context, String msg, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, duration);
		} else {
			toast.cancel();
			toast = Toast.makeText(context, msg, duration);
		}
		toast.show();
	}

	/**
	 * 功能暂时不可用提示
	 */
	public static void showFunctionDisable(Context context) {
		showToast(context, "抱歉，此功能暂时没有实现！", Toast.LENGTH_SHORT);
	}

	/**
	 * 显示toast，若多次触发，按最后一次的显示时间显示
	 * 
	 * @param context
	 * @param resId
	 *            字符串资源id
	 * @param lengthShort
	 */
	public static void showToast(Context context, int resId, int lengthShort) {
		String msg = context.getResources().getString(resId);
		showToast(context, msg, lengthShort);
	}

	/**
	 * 显示toast，若多次触发，按最后一次的显示时间显示,显示时间为1s
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToast(Context context, String msg) {

		if (inTest){
		showToast(context, msg, 1000);}
	}

	/**
	 * 显示toast，若多次触发，按最后一次的显示时间显示,显示时间为1s,且打印日志
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToastWithLog(Context context, String msg) {
		showToast(context, msg, 1000);
		Log.d(TAG, msg);
	}
}
