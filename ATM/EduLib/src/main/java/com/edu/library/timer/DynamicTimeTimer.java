package com.edu.library.timer;

import android.widget.TextView;

/**
 * 可动态改变时间的计时器，适用场景：在指定时间范围内答题，打错时需要动态增加用时
 * 
 * @author lucher
 * 
 */
public class DynamicTimeTimer extends EduBaseTimer {

	// 绑定用来显示时间的tv
	private TextView tvTimer;
	// 计时器监听
	private OnTimeOutListener mListener;

	public DynamicTimeTimer(TextView timer, long countDownInterval, long totalTime) {
		super(countDownInterval, totalTime);
		this.tvTimer = timer;
	}

	@Override
	public void onTick() {
		if (mCurrentTotalTime >= mTotalTime) {
			mCurrentTotalTime = mTotalTime;
		}
		long minute = (mCurrentTotalTime / 1000) / 60;
		long second = mCurrentTotalTime / 1000 - minute * 60;
		tvTimer.setText((minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second);
	}

	/**
	 * 设置计时器监听
	 * 
	 * @param listener
	 */
	public void setOnTimeOutListener(OnTimeOutListener listener) {
		this.mListener = listener;
	}

	/**
	 * 计时器加时，单位ms
	 */
	public void addCurrentTotalTime(long time) {
		this.mCurrentTotalTime += (time - mCountdownInterval);
		this.refresh();
	}

	/**
	 * 获取当前计时总时间
	 * 
	 * @return
	 */
	public long getCurrentTotalTime() {
		return mCurrentTotalTime;
	}

	@Override
	public void restart() {
		super.restart();
		tvTimer.setText("00:00");
	}

	@Override
	public void cancel() {
		super.cancel();
	}

	@Override
	public void onFinish() {
		// tvTimer.setText("00:00");
		if (mListener != null) {
			mListener.onTimeOut();
		}
	}

	/**
	 * 计时器计时完成监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface OnTimeOutListener {

		/**
		 * 时间到
		 */
		public void onTimeOut();
	}
}
