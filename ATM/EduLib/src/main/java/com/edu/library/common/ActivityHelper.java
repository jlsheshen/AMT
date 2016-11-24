package com.edu.library.common;

import com.edu.library.util.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Activity帮助类
 * 
 * @author Lucher
 */
public class ActivityHelper {
	private static ActivityHelper instance;

	private ActivityHelper() {
	}

	public synchronized static ActivityHelper getInstance() {
		if (null == instance) {
			instance = new ActivityHelper();
		}
		return instance;
	}

	/**
	 * 按两次返回键退出
	 * 
	 * @return 执行结果
	 */
	static long temptime; // 判断是否在2s内按两次返回键

	public boolean doubleClickExit(Context context) {

		if (System.currentTimeMillis() - temptime > 2000) { // 2s内再次选择back键有效

			ToastUtil.showToast(context, "再按一次返回退出！", Toast.LENGTH_SHORT);
			temptime = System.currentTimeMillis();
		} else {
			((Activity) context).finish();
			System.exit(0);
		}
		return true;
	}
}
