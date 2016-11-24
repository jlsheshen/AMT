package com.edu.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * gif显示控件，该类是从github上找到的开源项目里提取出来,经过一定的修改而来，加入了几个自定义方法
 * 
 * @author lucher
 * 
 */

public class GifMovieView extends View {

	private static final int DEFAULT_MOVIEW_DURATION = 1000;

	private int mMovieResourceId;
	private Movie mMovie;

	private int mCurrentAnimationTime = 0;

	// 记录开始时间
	private long mMovieStart;
	// 是否循环
	private boolean mCirculation;
	// gif相关监听
	private GifViewListener mListener;
	/**
	 * Position for drawing animation frames in the center of the view.
	 */
	private float mLeft;
	private float mTop;

	/**
	 * Scaling factor to fit the animation within view bounds.
	 */
	private float mScale;

	/**
	 * Scaled movie frames width and height.
	 */
	private int mMeasuredMovieWidth;
	private int mMeasuredMovieHeight;

	private volatile boolean mPaused = false;
	private boolean mVisible = true;

	public GifMovieView(Context context) {
		this(context, null);
	}

	public GifMovieView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GifMovieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setViewAttributes(context, attrs, defStyle);
	}

	@SuppressLint("NewApi")
	private void setViewAttributes(Context context, AttributeSet attrs, int defStyle) {

		/**
		 * Starting from HONEYCOMB have to turn off HW acceleration to draw
		 * Movie on Canvas.
		 */
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}

	/**
	 * 设置资源id，是否循环播放
	 * 
	 * @param gifId
	 *            gif图片res id
	 * @param circulation
	 *            是否循环播放
	 */
	public void setMovieResource(int movieResId, boolean circulation) {
		mMovieStart = 0;
		this.mMovieResourceId = movieResId;
		this.mCirculation = circulation;
		mMovie = Movie.decodeStream(getResources().openRawResource(mMovieResourceId));
		requestLayout();
	}

	public void setMovie(Movie movie) {
		this.mMovie = movie;
		requestLayout();
	}

	public Movie getMovie() {
		return mMovie;
	}

	public void setMovieTime(int time) {
		mCurrentAnimationTime = time;
		invalidate();
	}

	public void setPaused(boolean paused) {
		this.mPaused = paused;

		/**
		 * Calculate new movie start time, so that it resumes from the same
		 * frame.
		 */
		if (!paused) {
			mMovieStart = android.os.SystemClock.uptimeMillis() - mCurrentAnimationTime;
		}

		invalidate();
	}

	public boolean isPaused() {
		return this.mPaused;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (mMovie != null) {
			int movieWidth = mMovie.width();
			int movieHeight = mMovie.height();

			/*
			 * Calculate horizontal scaling
			 */
			float scaleH = 1f;
			int measureModeWidth = MeasureSpec.getMode(widthMeasureSpec);

			if (measureModeWidth != MeasureSpec.UNSPECIFIED) {
				int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
				if (movieWidth > maximumWidth) {
					scaleH = (float) movieWidth / (float) maximumWidth;
				}
			}

			/*
			 * calculate vertical scaling
			 */
			float scaleW = 1f;
			int measureModeHeight = MeasureSpec.getMode(heightMeasureSpec);

			if (measureModeHeight != MeasureSpec.UNSPECIFIED) {
				int maximumHeight = MeasureSpec.getSize(heightMeasureSpec);
				if (movieHeight > maximumHeight) {
					scaleW = (float) movieHeight / (float) maximumHeight;
				}
			}

			/*
			 * calculate overall scale
			 */
			mScale = 1f / Math.max(scaleH, scaleW);

			mMeasuredMovieWidth = (int) (movieWidth * mScale);
			mMeasuredMovieHeight = (int) (movieHeight * mScale);

			setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight);

		} else {
			/*
			 * No movie set, just set minimum available size.
			 */
			setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		/*
		 * Calculate left / top for drawing in center
		 */
		mLeft = (getWidth() - mMeasuredMovieWidth) / 2f;
		mTop = (getHeight() - mMeasuredMovieHeight) / 2f;

		mVisible = getVisibility() == View.VISIBLE;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mMovie != null) {
			if (!mPaused) {
				// updateAnimationTime();
				// drawMovieFrame(canvas);
				// invalidateView();

				long now = android.os.SystemClock.uptimeMillis();

				if (mMovieStart == 0) { // 第一次初始化mMovieStart
					mMovieStart = now;
				}

				if (mMovie != null) {
					int dur = mMovie.duration();// gif播放一次的时间
					if (dur == 0) {
						dur = 1000;
					}

					if (mCirculation) {// 循环模式
						int relTime = (int) ((now - mMovieStart) % dur);
						mMovie.setTime(relTime);
						mMovie.draw(canvas, 0, 0);
						invalidate();
					} else {// 非循环模式
						int relTime = (int) (now - mMovieStart);
						if (relTime >= dur) {// 播放完毕
							// drawMovieFrame(canvas);
							mMovie.setTime(dur);
							mMovie.draw(canvas, 0, 0);
							if (mListener != null)
								mListener.onGifOver(this.getId());
							return;
						} else {
							mMovie.setTime(relTime);
							mMovie.draw(canvas, 0, 0);
							invalidate();
						}
					}
				}
			} else {
				drawMovieFrame(canvas);
			}
		}
	}

	public void restart() {
		mMovieStart = android.os.SystemClock.uptimeMillis();
		invalidate();
	}

	/**
	 * Invalidates view only if it is visible. <br>
	 * {@link #postInvalidateOnAnimation()} is used for Jelly Bean and higher.
	 * 
	 */
	@SuppressLint("NewApi")
	private void invalidateView() {
		if (mVisible) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				postInvalidateOnAnimation();
			} else {
				invalidate();
			}
		}
	}

	/**
	 * Calculate current animation time
	 */
	private void updateAnimationTime() {
		long now = android.os.SystemClock.uptimeMillis();

		if (mMovieStart == 0) {
			mMovieStart = now;
		}

		int dur = mMovie.duration();

		if (dur == 0) {
			dur = DEFAULT_MOVIEW_DURATION;
		}

		mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);
	}

	/**
	 * Draw current GIF frame
	 */
	private void drawMovieFrame(Canvas canvas) {

		mMovie.setTime(mCurrentAnimationTime);

		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.scale(mScale, mScale);
		mMovie.draw(canvas, mLeft / mScale, mTop / mScale);
		canvas.restore();
	}

	@SuppressLint("NewApi")
	@Override
	public void onScreenStateChanged(int screenState) {
		super.onScreenStateChanged(screenState);
		mVisible = screenState == SCREEN_STATE_ON;
		invalidateView();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		mVisible = visibility == View.VISIBLE;
		invalidateView();
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		mVisible = visibility == View.VISIBLE;
		invalidateView();
	}

	/**
	 * 设置gif监听
	 * 
	 * @param listener
	 */
	public void setGifViewListener(GifViewListener listener) {
		this.mListener = listener;
	}

	/**
	 * gif视图监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface GifViewListener {
		/**
		 * gif播放完毕（只限于不循环模式下）
		 * 
		 * @param 注册者的id
		 */
		public void onGifOver(int id);
	}
}
