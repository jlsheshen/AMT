package com.edu.library.timer;

import android.util.Log;

/**
 * 累计使用时间计时器
 * 
 * @author lucher
 * 
 */
public class TotalTimeTimer extends EduBaseTimer {

	private static final String TAG = "TotalTimeTimer";

	// 自身实例
	private static TotalTimeTimer mSingleton;

	private TotalTimeTimer(long countDownInterval) {
		super(countDownInterval);
	}

	/**
	 * 以单例模式获取timer
	 * 
	 * @return
	 */
	public static synchronized TotalTimeTimer getSingleton() {
		if (mSingleton == null) {
			mSingleton = new TotalTimeTimer(1000);
		}

		return mSingleton;
	}

	/**
	 * 获取当前计时总时间，单位：分
	 * 
	 * @return
	 */
	public int getTotalMinutes() {
		int total = (int) ((mCurrentTotalTime / 1000) / 60);

		Log.d(TAG, "getTotalMinutes:" + total + ",s:" + mCurrentTotalTime / 1000);

		return total;
	}

	/**
	 * 获取当前计时总时间，单位：秒
	 * 
	 * @return
	 */
	public int getTotalSeconds() {
		int total = (int) (mCurrentTotalTime / 1000);

		Log.d(TAG, "getTotalSeconds:" + total + ",s:" + mCurrentTotalTime / 1000);

		return total;
	}

	@Override
	public void onTick() {
		Log.d(TAG, "mCurrentTotalTime:" + mCurrentTotalTime / 1000);
	}

	@Override
	public void onFinish() {
	}

}
