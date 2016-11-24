package com.edu.library.common;

import java.util.List;

import com.edu.library.util.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

/**
 * 输入法帮助类，目前主要用于输入法切换
 * 
 * @author lucher
 * 
 */
public class EduImeHelper {

	// 预置输入法包名：爱丁五笔
	public static final String IME_EDU_PAD = "com.edu.android.softkeyboard";
	// 预置输入法包名：搜狗输入法
	public static final String IME_SOUGOU = "com.sohu.inputmethod.sogou";
	// 预置输入法包名：谷歌输入法
	public static final String IME_GOOGLE = "com.google.android.inputmethod.pinyin";

	// 自身实例
	private static EduImeHelper mSingleton;
	private Context mContext;

	/**
	 * 构造
	 * 
	 * @param context
	 */
	private EduImeHelper(Context context) {
		mContext = context;
	}

	/**
	 * 获取单例
	 * 
	 * @param context
	 * @return
	 */
	public static EduImeHelper getSingleton(Context context) {
		if (mSingleton == null) {
			mSingleton = new EduImeHelper(context);
		}
		return mSingleton;
	}

	/**
	 * 设置输入法，发送广播给用户中心
	 * 
	 * @param imeName
	 *            输入法包名
	 * @return
	 */
	public boolean setIme(String imeName) {
		boolean result = existIme(imeName);
		if (result) {
			Intent intent = new Intent("com.edu.changeIME.applyIme");
			intent.putExtra("com.edu.changeIME", imeName);
			mContext.sendBroadcast(intent);
		}
		return result;
	}

	/**
	 * 还原输入法
	 * 
	 * @param context
	 */
	public void restoreIme(Context context) {
		Intent intent = new Intent("com.edu.changeIME.recoveryIme");
		context.sendBroadcast(intent);
	}

	/**
	 * 当前系统是否存在指定输入法
	 * 
	 * @param name
	 * @return
	 */
	private boolean existIme(String name) {
		List<InputMethodInfo> inputMethods = ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).getInputMethodList();
		for (int i = 0; i < inputMethods.size(); i++) {
			if (inputMethods.get(i).getPackageName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}
