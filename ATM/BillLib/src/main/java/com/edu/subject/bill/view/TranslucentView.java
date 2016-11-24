package com.edu.subject.bill.view;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.edu.subject.bill.element.ElementLayoutParams;
import com.edu.subject.bill.listener.SignViewListener;
import com.edu.subject.bill.scale.IScaleable;

/**
 * 半透明视图,支持缩放
 * 
 * @author lucher
 * 
 */
public class TranslucentView extends View implements IScaleable {

	// 盖章监听
	private SignViewListener mSignListener;
	private SignView mSignView;

	public TranslucentView(Context context, int width, int height) {
		super(context);
		init(width, height);
	}

	/**
	 * 初始化
	 * 
	 * @param width
	 * @param height
	 */
	private void init(int width, int height) {
		setLayoutParams(new ElementLayoutParams(0, 0, 0, width, height));
		setMinimumWidth(width);
		setMinimumHeight(height);
		setBackgroundColor(Color.CYAN);
		getBackground().setAlpha(150);
	}

	@Override
	public void postScale(float scale, int scaleTimes) {
	}

	/**
	 * 设置盖章监听
	 * 
	 * @param listener
	 */
	public void setSignListener(SignViewListener listener) {
		mSignListener = listener;
	}

	/**
	 * 设置印章实例
	 * 
	 * @param signView
	 */
	public void setSignView(SignView signView) {
		mSignView = signView;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			setBackgroundColor(Color.RED);
			getBackground().setAlpha(100);
			if (mSignListener != null) {
				mSignListener.onDragHint(mSignView, "先把当前印章盖完才能做别的操作");
			}

			break;

		case MotionEvent.ACTION_MOVE:
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			setBackgroundColor(Color.CYAN);
			getBackground().setAlpha(150);
			break;
		}

		return true;
	}
}
