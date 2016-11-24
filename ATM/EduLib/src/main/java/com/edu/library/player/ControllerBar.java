package com.edu.library.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.edu.library.R;

/**
 * 控制条
 * 
 * @author lucher
 * 
 */
public class ControllerBar extends RelativeLayout
		implements OnClickListener, OnSeekBarChangeListener, OnPreparedListener, OnCompletionListener {

	// 播放，收藏按钮
	private Button btnPlay, btnFavorite;
	// 使用时间，总时间文本
	private TextView tvTimePlayed, tvTimeDuration;
	// 进度条
	private SeekBar seekBar;

	private ControllerBarListener mListener;

	// 视频播放控制类
	private PlayerController controller;

	// 更新进度
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			setCurrentProgress(controller.getCurrentPosition());

			sendEmptyMessageDelayed(0, 500);
		}
	};

	public ControllerBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		View view = View.inflate(context, R.layout.controller_bar, this);
		init(view);
	}

	/**
	 * 初始化
	 * 
	 * @param view
	 */
	private void init(View view) {
		btnPlay = (Button) view.findViewById(R.id.btn_play);
		btnFavorite = (Button) view.findViewById(R.id.btn_favorite);
		tvTimePlayed = (TextView) view.findViewById(R.id.tv_time_played);
		tvTimeDuration = (TextView) view.findViewById(R.id.tv_duration);
		btnPlay.setOnClickListener(this);
		btnFavorite.setOnClickListener(this);
		seekBar = (SeekBar) findViewById(R.id.seekbar);
		seekBar.setOnSeekBarChangeListener(this);
	}

	/**
	 * 设置总时长
	 * 
	 * @param duration
	 *            单位ms
	 */
	public void setDuration(int duration) {
		seekBar.setMax(duration);
		tvTimeDuration.setText(getFormatedTime(duration));
	}

	/**
	 * 设置视频控制类
	 * 
	 * @param controller
	 */
	public void setController(PlayerController controller) {
		this.controller = controller;
		controller.setOnPreparedListener(this);
		controller.setOnCompletion(this);
		// 开始进度更新
		handler.sendEmptyMessage(0);
	}

	/**
	 * 设置当前进度
	 * 
	 * @param current
	 *            当前进度 单位ms
	 */
	public void setCurrentProgress(int current) {
		if (current > seekBar.getMax()) {
			if (handler.hasMessages(0)) {// 停止进度更新
				handler.removeMessages(0);
			}
			setCurrentProgress(0);
			btnPlay.setBackgroundResource(R.drawable.btn_edulib_start);
		} else {
			seekBar.setProgress(current);
			tvTimePlayed.setText(getFormatedTime(current));
		}
	}

	/**
	 * 设置收藏按钮的可见性
	 * 
	 * @param visibility
	 */
	public void setFavoriteVisibility(int visibility) {
		btnFavorite.setVisibility(visibility);
	}

	/**
	 * 获取格式化后的时间字符串
	 * 
	 * @param time
	 *            时间，单位ms
	 * @return
	 */
	private String getFormatedTime(int time) {
		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

	@Override
	public void onClick(View v) {
		if (mListener == null) return;
		if (v.getId() == R.id.btn_play) {
			handlePlayClick();
		} else if (v.getId() == R.id.btn_favorite) {
			mListener.onFavoriteClicked();
		}
	}

	/**
	 * 处理播放按钮点击
	 */
	private void handlePlayClick() {
		if (controller.isPlaying()) {// 播放中
			pause();
		} else {
			play();
		}
		if (mListener != null) {
			mListener.onPlayClicked();
		}
	}

	/**
	 * 播放
	 */
	public void play() {
		if (handler.hasMessages(0)) {// 停止进度更新
			handler.removeMessages(0);
		}
		handler.sendEmptyMessage(0);// 开始进度更新
		controller.play();
		btnPlay.setBackgroundResource(R.drawable.btn_edulib_pause);
	}

	/**
	 * 暂停
	 */
	public void pause() {
		if (handler.hasMessages(0)) {// 停止进度更新
			handler.removeMessages(0);
		}
		controller.pause();
		btnPlay.setBackgroundResource(R.drawable.btn_edulib_start);
	}

	/**
	 * 设置控制条监听
	 * 
	 * @param listener
	 */
	public void setControllerBarListener(ControllerBarListener listener) {
		mListener = listener;
	}

	/**
	 * 控制条监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface ControllerBarListener {
		/**
		 * 播放按钮点击
		 */
		void onPlayClicked();

		/**
		 * 收藏按钮点击
		 */
		void onFavoriteClicked();

		/**
		 * 进度改变
		 * 
		 * @param progress
		 *            进度值
		 */
		void onProgressChanged(int progress);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		setCurrentProgress(progress);
		if (fromUser) {
			// 调整视频进度
			controller.seekTo(progress);
			// 点击进度条后自动播放
			play();
			// 通知监听
			if (mListener != null) {
				mListener.onProgressChanged(progress);
			}
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		setDuration(mp.getDuration());
	}

	public void setFavoriteState(boolean isFavourite) {
		if (isFavourite) {
			btnFavorite.setBackgroundResource(R.drawable.btn_edulib_favourited);
		} else {
			btnFavorite.setBackgroundResource(R.drawable.btn_edulib_favourite);
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if (handler.hasMessages(0)) {// 停止进度更新
			handler.removeMessages(0);
		}
		setCurrentProgress(0);
		btnPlay.setBackgroundResource(R.drawable.btn_edulib_start);
	}
}
