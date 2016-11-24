package com.edu.library.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * 定制的SurfaceView，用来显示视频
 * @author lucher 
 *
 */
public class MediaView extends SurfaceView {

	//宽和高
	public int mVideoWidth;
	public int mVideoHeight;
	
	public MediaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	
	/**
	 * 初始化视图
	 */
	private void initView() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
		int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
		setMeasuredDimension(width, height);
	}
	
	/**
	 * 设置视频的尺寸
	 * @param width
	 * @param height
	 */
	public void setVideoScale(int width, int height) {
		LayoutParams lp = (LayoutParams) getLayoutParams();
		lp.gravity = Gravity.CENTER;
		lp.height = height;
		lp.width = width;
		setLayoutParams(lp);
	}
}
