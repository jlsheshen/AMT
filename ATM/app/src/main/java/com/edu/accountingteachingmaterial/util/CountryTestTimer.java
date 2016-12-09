package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.widget.SeekBar;
import android.widget.TextView;

import com.edu.library.timer.EduBaseTimer;

/**
 * 乡试考试答题界面用的timer
 *
 * @author lucher
 */
public class CountryTestTimer extends EduBaseTimer {

    // 绑定用来显示时间的tv
    private TextView tvTimer;
    // 显示时间的进度条
    private SeekBar sbTimer;
    // 计时器监听
    private OnTimeOutListener mListener;

    //	private Context mContext;
    public CountryTestTimer(long countDownInterval) {
        super(countDownInterval);
    }

    public CountryTestTimer(TextView tvTimer, long countDownInterval, long totalTime, Context context) {
        super(countDownInterval, totalTime);
        this.tvTimer = tvTimer;
//		mContext=context;
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
        //tvTimer.setText((minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second);
        tvTimer.setText((hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second);
    }

    /**
     * 获取当前使用时间，单位：s
     *
     * @return
     */
    public int getUsedTime() {
        return (int) (mCurrentTotalTime / 1000);
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
     */
    public interface OnTimeOutListener {

        /**
         * 时间到
         */
        public void onTimeOut();
    }
}
