package com.edu.library.timer;

import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 带进度条和文本显示的timer,有时间限制
 * 
 * @author lucher
 * 
 */
public class DoubleTimer extends EduBaseTimer {

	// 绑定用来显示时间的tv
	private TextView tvTimer;
	// 显示时间的进度条
	private ProgressBar pbTimer;
	// 计时器监听
	private OnTimeOutListener mListener;

	public DoubleTimer(long countDownInterval) {
		super(countDownInterval);
	}

	/**
	 * @param tvTimer 时间显示
	 * @param sbTimer 时间进度条
	 * @param countDownInterval 计时间隔
	 * @param totalTime 定时器总定时时间
	 */
	public DoubleTimer(TextView tvTimer, ProgressBar sbTimer, long countDownInterval, long totalTime) {
		super(countDownInterval, totalTime);
		this.tvTimer = tvTimer;
		this.pbTimer = sbTimer;
		updataTime();
	}

	@Override
	public void onTick() {
		if (mCurrentTotalTime >= mTotalTime) {
			mCurrentTotalTime = mTotalTime;
		}
		updataTime();
	}

	/**
	 * 更新时间
	 */
	private void updataTime() {
		long totalTimeLeft = (mTotalTime - mCurrentTotalTime);
		long hour = (totalTimeLeft / 1000) / 60 / 60;
		long minute = (totalTimeLeft / 1000) / 60 - hour * 60;
		long second = totalTimeLeft / 1000 - minute * 60 - hour * 60 * 60;

		tvTimer.setText((hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second);
		pbTimer.setProgress((int) ((mTotalTime - mCurrentTotalTime) * 100 / mTotalTime));
	}

	@Override
	public void onFinish() {
		if (mListener != null) {
			mListener.onTimeOut();
		}
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
