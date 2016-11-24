package com.edu.library.timer;

import android.widget.ProgressBar;

/**
 * 进度条计时器
 * 
 * @author lucher
 * 
 */
public class ProgressBarTimer extends EduBaseTimer {

	// 绑定用来显示时间的tv
	private ProgressBar pbTimer;
	// 计时器监听
	private OnTimeOutListener mListener;

	public ProgressBarTimer(ProgressBar timer, long countDownInterval, long totalTime) {
		super(countDownInterval, totalTime);
		this.pbTimer = timer;
	}

	@Override
	public void onTick() {
		if (mCurrentTotalTime >= mTotalTime) {
			mCurrentTotalTime = mTotalTime;
		}
		if (mTotalTime == 0) {
			return;
		} else if (mTotalTime - mCurrentTotalTime <= 10000) {// 时间只有10s的时候进度条变成红色
//			pbTimer.setProgressDrawable(pbTimer.getResources().getDrawable(R.drawable.progressbar_layer_time_red));
		}
		pbTimer.setProgress((int) (mCurrentTotalTime * 100 / mTotalTime));
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
		this.mCurrentTotalTime += (time - mCountdownInterval);;
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
		pbTimer.setProgress(0);
	}

	@Override
	public void cancel() {
		super.cancel();
	}

	@Override
	public void onFinish() {
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
