package com.edu.accountingteachingmaterial.oldsubject.subject.bill.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.edu.subject.bill.element.ElementLayoutParams;
import com.edu.subject.bill.element.info.FlashInfo;
import com.edu.subject.bill.scale.IScaleable;
import com.edu.subject.bill.scale.ScaleUtil;

/**
 * 闪电符视图
 * 
 * @author lucher
 * 
 */
public class FlashView extends View implements IScaleable {

	// 对应闪电符的信息
	private FlashInfo mData;
	// 画笔
	private Paint mPaint;
	// 划线的粗细
	private float mStrokeWidth = 1;
	// 动画方式显示的时候变换的颜色
	private int[] colors = { Color.RED, Color.BLUE, Color.YELLOW};
	// 用于变换颜色计数
	private int count = 0;

	public FlashView(Context context) {
		super(context);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
	}

	/**
	 * 显示
	 * 
	 * @param anim
	 *            是否用动画方式显示
	 */
	public void show(boolean anim) {
		if (anim) {
			// 动画方式显示
			AnimatorSet set = new AnimatorSet();
			ObjectAnimator animator = ObjectAnimator.ofFloat(this, View.ALPHA, 0, 1f, 0f, 1f);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					count++;
					mPaint.setColor(colors[count % colors.length]);
					invalidate();
				}
			});
			animator.addListener(new AnimatorListener() {

				@Override
				public void onAnimationStart(Animator animation) {

				}

				@Override
				public void onAnimationRepeat(Animator animation) {

				}

				@Override
				public void onAnimationEnd(Animator animation) {
					mPaint.setColor(Color.BLACK);
					invalidate();
				}

				@Override
				public void onAnimationCancel(Animator animation) {
					mPaint.setColor(Color.BLACK);
					invalidate();
				}
			});
			set.play(animator).with(ObjectAnimator.ofFloat(this, View.SCALE_X, 0f, 1f)).with(ObjectAnimator.ofFloat(this, View.SCALE_Y, 0f, 1f));
			set.setInterpolator(new DecelerateInterpolator());
			set.setDuration(500).start();
		} else {
			setAlpha(255);
		}

	}

	/**
	 * 设置闪电符对应数据，并把数据应用到该闪电符上
	 * 
	 * @param data
	 *            数据
	 * @param scale
	 *            缩放比例
	 */
	public void apply(FlashInfo data, float scale) {
		mData = data;
		postScale(scale, 0);
	}

	/**
	 * 设置颜色
	 * 
	 * @param color
	 */
	public void setColor(int color) {
		mPaint.setColor(color);
	}

	@Override
	public void postScale(float scale, int scaleTimes) {
		// 布局参数缩放
		int scaledX = ScaleUtil.getScaledValue(mData.getX(), scale);
		int scaledY = ScaleUtil.getScaledValue(mData.getY(), scale);
		int scaledWidth = ScaleUtil.getScaledValue(mData.getWidth(), scale);
		int scaledHeight = ScaleUtil.getScaledValue(mData.getHeight(), scale);
		setLayoutParams(new ElementLayoutParams(mData.getType(), scaledX, scaledY, scaledWidth, scaledHeight));
		// 划线粗细缩放
		mPaint.setStrokeWidth(mStrokeWidth * scale);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawLine(0, getHeight(), getWidth(), 0, mPaint);
	}

}
