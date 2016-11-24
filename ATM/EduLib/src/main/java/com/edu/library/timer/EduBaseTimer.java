package com.edu.library.timer;

import android.os.Handler;
import android.os.Message;

/**
 * 顺时计时器基类
 * 
 * @author lucher
 * 
 */
public abstract class EduBaseTimer {

	// 刷新时间的消息
	private static final int MSG_RUN = 1;

	// 计时间隔
	protected long mCountdownInterval;
	// 当前计时总时间
	protected long mCurrentTotalTime;
	// 定时器总定时时间
	protected long mTotalTime;

	// 是否在运行
	private boolean isRunning;
	// 是否开始
	private boolean started;

	// 是否有总的运行时间，若没有会一直运行，直到手动停止
	private boolean hasTotalTime;

	/**
	 * 计时器构造方法
	 * 
	 * @param countDownInterval
	 *            计时间隔 ms
	 * @param totalTime
	 *            总时间 ms
	 */
	public EduBaseTimer(long countDownInterval, long totalTime) {
		this.mCountdownInterval = countDownInterval;
		this.mTotalTime = totalTime;
		hasTotalTime = true;
	}

	public EduBaseTimer(long countDownInterval) {
		this.mCountdownInterval = countDownInterval;
		hasTotalTime = false;
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			synchronized (EduBaseTimer.this) {
				if (msg.what == MSG_RUN) {
					mCurrentTotalTime += mCountdownInterval;
					if (mCurrentTotalTime >= mTotalTime && hasTotalTime) {
						onTick();
						isRunning = false;
						onFinish();
					} else {
						onTick();
						sendMessageDelayed(obtainMessage(MSG_RUN), mCountdownInterval);
					}

				}
			}
		}

	};

	/**
	 * 开始计时
	 */
	public final void start() {
		if (!isRunning) {
			started = true;
			mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_RUN), mCountdownInterval);
			isRunning = true;
		}
	}

	/**
	 * 重新开始计时
	 */
	public void restart() {
		mCurrentTotalTime = 0;
		pause();
		start();
	}

	/**
	 * 立即更新时间
	 */
	protected void refresh() {
		mHandler.removeMessages(MSG_RUN);
		mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_RUN));
	}

	/**
	 * 取消计时
	 */
	public void cancel() {
		mCurrentTotalTime = 0;
		mHandler.removeMessages(MSG_RUN);
		isRunning = false;
		started = false;
	}

	/**
	 * 恢复
	 */
	public final void resume() {
		if (!isRunning && started) {
			mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_RUN));
			isRunning = true;
		}
	}

	/**
	 * 暂停
	 */
	public final void pause() {
		mHandler.removeMessages(MSG_RUN);
		isRunning = false;
	}

	/**
	 * 更新时间显示
	 */
	public abstract void onTick();

	/**
	 * 计时结束
	 */
	public abstract void onFinish();

	/**
	 * 是否在运行
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return isRunning;
	}
}
