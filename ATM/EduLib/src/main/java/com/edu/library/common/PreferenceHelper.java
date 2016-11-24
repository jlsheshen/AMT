package com.edu.library.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 保存preference的帮助类
 * 
 * @author lucher
 * 
 */
public class PreferenceHelper {
	/**
	 * 保存记录是否第一次进入某个页面的key
	 */
	private static final String PREFERENCE_ISFIRSTTIME_KEY = "is_first_time";
	/**
	 * 保存value的key
	 */
	private static final String PREFERENCE_VALUE_KEY = "value";

	private static Context mContext;

	/**
	 * 引导页preference的key
	 */
	public static final String KEY_GUIDANCE_PAGE = "guidance_page";

	/**
	 * 自身实例
	 */
	private static PreferenceHelper instance;

	/**
	 * 单例模式获取引用
	 * 
	 * @param context
	 * @return
	 */
	public static PreferenceHelper getInstance(Context context) {
		mContext = context;
		if (instance == null) {
			instance = new PreferenceHelper();
		}
		return instance;
	}

	private PreferenceHelper() {
	}

	/**
	 * 是否第一次进入某页面
	 * 
	 * @param key
	 * @return
	 */
	public boolean isFirstTime(String key) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_ISFIRSTTIME_KEY, Context.MODE_PRIVATE);
		boolean isFirstTime = sp.getBoolean(key, true);

		return isFirstTime;

	}

	/**
	 * 设置不是第一次进入某页面
	 * 
	 * @param key
	 */
	public void setFirstTimeFalse(String key) {
		Editor editor = mContext.getSharedPreferences(PREFERENCE_ISFIRSTTIME_KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, false);
		editor.commit();
	}

	/**
	 * 设置是第一次进入某页面
	 * 
	 * @param key
	 */
	public void setFirstTimeTrue(String key) {
		Editor editor = mContext.getSharedPreferences(PREFERENCE_ISFIRSTTIME_KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, true);
		editor.commit();
	}

	/**
	 * 获取指定key对应的value
	 * 
	 * @param key
	 * @return
	 */
	public String getStringValue(String key) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
		String value = sp.getString(key, "");

		return value;
	}

	/**
	 * 设置指定key对应的value到preference
	 * 
	 * @param key
	 * @param value
	 */
	public void setStringValue(String key, String value) {
		Editor editor = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 获取指定key对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBooleanValue(String key) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
		boolean value = sp.getBoolean(key, false);

		return value;
	}

	/**
	 * 设置指定key对应的value到preference
	 * 
	 * @param key
	 * @param value
	 */
	public void setBooleanValue(String key, boolean value) {
		Editor editor = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 获取指定key对应的value
	 * 
	 * @param key
	 * @return
	 */
	public long getLongValue(String key) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
		long value = sp.getLong(key, 0);

		return value;
	}

	/**
	 * 设置指定key对应的value到preference
	 * 
	 * @param key
	 * @param value
	 */
	public void setLongValue(String key, long value) {
		Editor editor = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE).edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	/**
	 * 获取指定key对应的value
	 * 
	 * @param key
	 * @return
	 */
	public int getIntValue(String key) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
		int value = sp.getInt(key, 0);

		return value;
	}

	/**
	 * 设置指定key对应的value到preference
	 * 
	 * @param key
	 * @param value
	 */
	public void setIntValue(String key, int value) {
		Editor editor = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}
}
