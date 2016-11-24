package com.edu.library.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;

/**
 * 音频播放类
 * 
 * @author lucher
 * 
 */
public class SoundPlayer implements MediaPlayer.OnCompletionListener {

	private final String TAG = SoundPlayer.class.getName();
	private MediaPlayer mPlayer;

	// 自身唯一实例
	private static SoundPlayer mSingleton;

	private List<OnCompletionListener> mListeners = new ArrayList<OnCompletionListener>(1);

	private SoundPlayer() {
		mPlayer = new MediaPlayer();
		mPlayer.setOnCompletionListener(this);
	}

	/**
	 * 单例模式获取实例
	 * 
	 * @return
	 */
	public static SoundPlayer getSingleton() {
		if (mSingleton == null) {
			mSingleton = new SoundPlayer();
		}

		return mSingleton;
	}

	/**
	 * 开始播放
	 * 
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public void startPlay(String path) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		mPlayer.reset();
		// 设置要播放的文件
		mPlayer.setDataSource(path);
		mPlayer.prepare();
		// 播放
		mPlayer.start();
	}

	/**
	 * 停止播放
	 * 
	 * @return
	 */
	public void stopPlay() {
		mPlayer.stop();
	}

	/**
	 * 释放
	 */
	public void release() {
		mPlayer.release();
		mPlayer = null;
	}

	/**
	 * 加入监听者
	 * 
	 * @param listener
	 */
	public void addOnCompletionListener(OnCompletionListener listener) {
		if (!mListeners.contains(listener)) {
			mListeners.add(listener);
		}
	}

	/**
	 * 移除监听者
	 * 
	 * @param listener
	 */
	public void removeOnCompletionListener(OnCompletionListener listener) {
		if (mListeners.contains(listener)) {
			mListeners.remove(listener);
		}
	}

	/**
	 * 播放状态监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface OnCompletionListener {
		void onCompletion(MediaPlayer mp);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		for (OnCompletionListener listener : mListeners) {
			listener.onCompletion(mp);
		}
	}
}
