package com.edu.library.util;

import android.app.Activity;
import android.widget.Toast;

/**
 * 
 * @author lucher
 */
public class DoubleClickExitUtil {

	// 临时保存按下时间
	static long temptime;

	/**
	 * 按两次返回键退出
	 */
	public static void doubleClickExit(Activity activity) {
		if (System.currentTimeMillis() - temptime > 2000) {// 2s内再次选择back键有效

			ToastUtil.showToast(activity, "再按一次退出", Toast.LENGTH_SHORT);
			temptime = System.currentTimeMillis();
		} else {
			activity.finish();
		}
	}
}
