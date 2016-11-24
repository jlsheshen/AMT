package com.edu.library.player;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * 控制mediaplyer从而控制视频的播放
 * 
 * @author lucher
 */
public class PlayerController implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, OnErrorListener, OnVideoSizeChangedListener, OnCompletionListener {

	private static final String TAG = "PlayerController";

	// 视频播放类
	public MediaPlayer mediaPlayer;
	// 视频显示
	private MediaView mMediaView;
	private SurfaceHolder surfaceHolder;
	// 播放器就绪监听
	private MediaPlayer.OnPreparedListener mOnPreparedListener;
	// 播放完成监听
	private OnCompletionListener mOnCompletionListener;
	// 播放器错误监听
	private OnErrorListener mOnErrorListener;
	// 播放器尺寸改变监听
	private OnSizeChangeLinstener mSizeChangeLinstener;

	private Context mContext;
	// 存放要播放视频的uri
	private Uri mUri;
	// 就绪后的播放进度
	private int mSeekWhenPrepared;
	// 视频总长
	private int mDuration;

	// 视频尺寸
	private int mSurfaceWidth;
	private int mSurfaceHeight;

	// mediaView是否准备好
	private boolean mIsPrepared;
	// 播放器就绪时是否开始
	private boolean mStartWhenPrepared;

	// 当前音量,默认为最大音量
	private float currentVolume = 1f;
	// 是否静音
	private boolean isMute;

	public PlayerController(Context context, MediaView mediaView) {
		mMediaView = mediaView;
		mContext = context;

		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		// 初始化播放器
		mMediaView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mMediaView.getHolder().addCallback(this);
		initMediaPlayer();
		addMPListeners();

		// 停止正在播放的music
		Intent intent = new Intent("com.android.music.musicservicecommand");
		intent.putExtra("command", "pause");
		mContext.sendBroadcast(intent);
	}

	/**
	 * 初始化media player
	 */
	private void initMediaPlayer() {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		// 保持屏幕在播放的时候一直亮着
		mediaPlayer.setScreenOnWhilePlaying(true);
	}

	/**
	 * 为media player添加监听
	 */
	private void addMPListeners() {
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnVideoSizeChangedListener(this);
		mediaPlayer.setOnCompletionListener(this);
	}

	/**
	 * 获取视频控件宽度
	 * 
	 * @return
	 */
	public int getVideoWidth() {
		return this.mMediaView.mVideoWidth;
	}

	/**
	 * 是否让播放器静音
	 * 
	 * @param mute
	 */
	public void muteVolume(boolean mute) {
		if (mediaPlayer != null && mIsPrepared) {
			isMute = mute;
			if (mute) {
				mediaPlayer.setVolume(0, 0);
			} else {
				mediaPlayer.setVolume(currentVolume, currentVolume);
			}
		}
	}

	/**
	 * 设置音量,范围0-1
	 * 
	 * @param volume
	 */
	public void setVolume(float volume) {
		if (mediaPlayer != null && mIsPrepared) {
			currentVolume = volume;
			mediaPlayer.setVolume(currentVolume, currentVolume);
		}
	}

	/**
	 * 当前是否静音
	 * 
	 * @return
	 */
	public boolean isMute() {
		return isMute;
	}

	/**
	 * 获取视频控件高度
	 * 
	 * @return
	 */
	public int getVideoHeight() {
		return this.mMediaView.mVideoHeight;
	}

	/**
	 * 重载 设置视频的资源路径
	 * 
	 * @param path
	 */
	public void setDataUri(String path) {
		this.setDataUri(Uri.parse(path));
	}

	/**
	 * 重载 设置视频的资源路径
	 * 
	 * @param uri
	 */
	public void setDataUri(Uri uri) {
		try {
			this.mUri = uri;
			mIsPrepared = false;
			mediaPlayer.reset();
			mediaPlayer.setDataSource(mContext, mUri);
			mediaPlayer.prepareAsync();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重新开始
	 */
	public void restart() {
		mSeekWhenPrepared = 0;
		if (mUri == null)
			return;
		mediaPlayer.reset();
		setDataUri(mUri);
		play();
	}

	/**
	 * 播放视频
	 */
	public void play() {
		if (mediaPlayer != null && mIsPrepared) {
			mediaPlayer.start();
		} else {
			mStartWhenPrepared = true;
		}
	}

	/**
	 * 暂停视频
	 */
	public void pause() {
		if (mediaPlayer != null && mIsPrepared) {
			mediaPlayer.pause();
		}
	}

	/**
	 * 停止视频
	 */
	public void stopPlay() {
		if (mediaPlayer != null) {
			// mediaPlayer.stop();
			mediaPlayer.pause();
			// mediaPlayer.seekTo(0);
			// mediaPlayer.release();
			// mediaPlayer = null;
		}
	}

	/**
	 * 释放播放器
	 */
	public void release() {
		mediaPlayer.release();
		mediaPlayer = null;
	}

	/**
	 * @return 视频是否正在播放
	 */
	public boolean isPlaying() {
		if (mediaPlayer != null && mIsPrepared) {
			return mediaPlayer.isPlaying();
		}
		return false;
	}

	/**
	 * @return 是否准备好
	 */
	public boolean isPrepared() {
		return mIsPrepared;
	}

	/**
	 * 把视频调到指定进度
	 * 
	 * @param msec
	 *            表示时间的进度
	 */
	public void seekTo(int msec) {
		if (mediaPlayer != null && mIsPrepared) {
			mediaPlayer.seekTo(msec);
			mSeekWhenPrepared = msec;
		} else {
			mSeekWhenPrepared = msec;
		}
	}

	/**
	 * @return 当前播放时间
	 */
	public int getCurrentPosition() {
		if (mediaPlayer != null && mIsPrepared) {
			return mediaPlayer.getCurrentPosition();
		}
		return 0;
	}

	/**
	 * 获取视频总长
	 * 
	 * @return 总长
	 */
	public int getDuration() {
		if (mediaPlayer != null && mIsPrepared) {
			if (mDuration > 0) {
				return mDuration;
			}
			mDuration = mediaPlayer.getDuration();
			return mDuration;
		}
		mDuration = -1;
		return mDuration;
	}

	/**
	 * 设置播放器就绪监听
	 * 
	 * @param preparedListener
	 */
	public void setOnPreparedListener(MediaPlayer.OnPreparedListener preparedListener) {
		mOnPreparedListener = preparedListener;
	}

	/**
	 * 设置播放器错误监听
	 * 
	 * @param errorListener
	 */
	public void setOnErrorListener(OnErrorListener errorListener) {
		mOnErrorListener = errorListener;
	}

	/**
	 * 设置播放完成监听
	 * 
	 * @param listener
	 */
	public void setOnCompletion(OnCompletionListener listener) {
		mOnCompletionListener = listener;
	}

	/**
	 * 设置尺寸大小改变监听
	 * 
	 * @param sizeChangeListener
	 */
	public void setOnSizeChangedListener(OnSizeChangeLinstener sizeChangeListener) {
		mSizeChangeLinstener = sizeChangeListener;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Log.e(TAG, "surfaceChanged");
		mSurfaceWidth = w;
		mSurfaceHeight = h;
		if (mediaPlayer != null && mIsPrepared && mMediaView.mVideoWidth == w && mMediaView.mVideoHeight == h) {
			if (mSeekWhenPrepared != 0) {
				mediaPlayer.seekTo(mSeekWhenPrepared);
				mSeekWhenPrepared = 0;
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e(TAG, "surfaceCreated");
		surfaceHolder = holder;
		if (mediaPlayer == null)
			return;
		mediaPlayer.setDisplay(surfaceHolder);

		mediaPlayer.reset();
		setDataUri(mUri);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e(TAG, "surfaceDestroyed");
		stopPlay();
		// release();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		Log.e(TAG, "onPrepared");
		mIsPrepared = true;
		// 回调接口
		if (mOnPreparedListener != null) {
			mOnPreparedListener.onPrepared(mediaPlayer);
		}
		// 初始化尺寸
		mMediaView.mVideoWidth = mp.getVideoWidth();
		mMediaView.mVideoHeight = mp.getVideoHeight();
		if (mMediaView.mVideoWidth != 0 && mMediaView.mVideoHeight != 0) {
//			surfaceHolder.setFixedSize(mMediaView.mVideoWidth, mMediaView.mVideoHeight);
//			if (mSurfaceWidth == mMediaView.mVideoWidth && mSurfaceHeight == mMediaView.mVideoHeight) {
				// 恢復状态
				if (mSeekWhenPrepared != 0) {
					mediaPlayer.seekTo(mSeekWhenPrepared);
					mSeekWhenPrepared = 0;
				}
				if (mStartWhenPrepared) {
					mediaPlayer.start();
					mStartWhenPrepared = false;
				}
//			}
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.d(TAG, "Error: " + what + "," + extra);
		if (mOnErrorListener != null) {
			if (mOnErrorListener.onError(mediaPlayer, what, extra)) {
				return true;
			}
		}
		return true;
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.e(TAG, "onVideoSizeChanged");
		mMediaView.mVideoWidth = mp.getVideoWidth();
		mMediaView.mVideoHeight = mp.getVideoHeight();

		if (mSizeChangeLinstener != null) {
			mSizeChangeLinstener.onChangeSize();
		}

		try {
			if (mMediaView.mVideoWidth != 0 && mMediaView.mVideoHeight != 0) {
//				surfaceHolder.setFixedSize(mMediaView.mVideoWidth, mMediaView.mVideoHeight);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.e(TAG, "onCompletion");
		if (mOnCompletionListener != null) {
			mOnCompletionListener.onCompletion(mp);
		}
		stopPlay();
		// Toast.makeText(mContext, "视频播放完毕", 1000).show();
	}

	/**
	 * 播放器尺寸改变监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface OnSizeChangeLinstener {
		public void onChangeSize();
	}

}