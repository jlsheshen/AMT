package com.edu.subject.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.edu.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * 滑动菜单拖拽布局,管理菜单的打开关闭特效等操作
 * 
 * @author lucher
 * 
 */
public class SlidingDragLayout extends FrameLayout {

	// 是否显示阴影
	private boolean isShowShadow = false;

	private Context context;
	private ViewDragHelper dragHelper;
	// 菜单打开关闭监听
	private DragListener dragListener;
	// 菜单滑动范围
	private int range;
	// 控件宽高
	private int mWidth;
	private int mHeight;
	// 主界面阴影
	private ImageView ivShadow;
	// 菜单
	private ViewGroup menuView;
	// 主界面
	private ViewGroup mainLayout;
	// 主界面左边距离
	private int mainLeft;
	// 角标
	private View badgeView;

	// 菜单状态
	public enum Status {
		Drag, Open, Close
	}

	private Status status = Status.Close;
	// 单据是否收缩
	private boolean billContract;

	public SlidingDragLayout(Context context) {
		this(context, null);
	}

	public SlidingDragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public SlidingDragLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		dragHelper = ViewDragHelper.create(this, dragHelperCallback);
	}

	/**
	 * 设置角标
	 * 
	 * @param badgeView
	 */
	public void setBadgeView(View badgeView) {
		this.badgeView = badgeView;
	}

	private ViewDragHelper.Callback dragHelperCallback = new ViewDragHelper.Callback() {

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (mainLeft + dx < 0) {
				return 0;
			} else if (mainLeft + dx > range) {
				return range;
			} else {
				return left;
			}
		}

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return true;
		}

		@Override
		public int getViewHorizontalDragRange(View child) {
			return mWidth;
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			if (xvel > 0) {
				openMenu();
			} else if (xvel < 0) {
				closeMenu();
			} else if (releasedChild == mainLayout && mainLeft > range * 0.3) {
				openMenu();
			} else if (releasedChild == menuView && mainLeft > range * 0.7) {
				openMenu();
			} else {
				closeMenu();
			}
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			if (changedView == mainLayout) {
				mainLeft = left;
			} else {
				mainLeft = mainLeft + left;
			}
			if (mainLeft < 0) {
				mainLeft = 0;
			} else if (mainLeft > range) {
				mainLeft = range;
			}

			if (isShowShadow) {
				ivShadow.layout(mainLeft, 0, mainLeft + mWidth, mHeight);
			}
			if (changedView == menuView) {
				menuView.layout(0, 0, mWidth, mHeight);
				mainLayout.layout(mainLeft, 0, mainLeft + mWidth, mHeight);
			}

			dispatchDragEvent(mainLeft);
		}
	};

	public void setDragListener(DragListener dragListener) {
		this.dragListener = dragListener;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (isShowShadow) {
			ivShadow = new ImageView(context);
			ivShadow.setImageResource(R.drawable.shadow);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			addView(ivShadow, 1, lp);
		}
		menuView = (ViewGroup) getChildAt(0);
		mainLayout = (ViewGroup) getChildAt(isShowShadow ? 2 : 1);
		menuView.setClickable(true);
		mainLayout.setClickable(true);
	}

	public ViewGroup getVg_main() {
		return mainLayout;
	}

	public ViewGroup getVg_left() {
		return menuView;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);
		range = (int) (mWidth * 0.5f);
		// 计算子控件的尺寸
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			if (i == 0) {// 菜单栏，把宽度设为控件宽度的一半
				int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (mWidth / 2), MeasureSpec.EXACTLY);
				int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
				child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
			} else {
				int childWidthMeasureSpec;
				if (billContract) {// 单据，菜单打开状态，设置宽度为控件宽度的一半
					childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth / 2, MeasureSpec.EXACTLY);
				} else {// 单据，菜单关闭状态，设置宽度为控件宽度
					childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY);
				}
				int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
				child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
			}
		}
		setMeasuredDimension(mWidth, mHeight);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		menuView.layout(0, 0, mWidth, mHeight);
		mainLayout.layout(mainLeft, 0, mainLeft + mWidth, mHeight);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return false;
	}

	private void dispatchDragEvent(int mainLeft) {
		float percent = mainLeft / (float) range;
		animateView(percent);
		Status lastStatus = status;
		if (dragListener != null) {
			dragListener.onDrag(percent);
		}
		if (lastStatus != getStatus() && status == Status.Close) {
			if (dragListener != null) {
				dragListener.onClose();
			}
		} else if (lastStatus != getStatus() && status == Status.Open) {
			billContract = true;
			requestLayout();
			badgeView.setVisibility(View.GONE);
			if (dragListener != null) {
				dragListener.onOpen();
			}
		}
	}

	private void animateView(float percent) {
		float f1 = 1 - percent * 0.3f;
		// ViewHelper.setScaleX(mainLayout, f1);// 取消主界面的缩放 lucher
		// ViewHelper.setScaleY(mainLayout, f1);
		ViewHelper.setScaleX(badgeView, 1 - percent);// 取消主界面的缩放 lucher
		ViewHelper.setScaleY(badgeView, 1 - percent);
		ViewHelper.setAlpha(badgeView, 1 - percent);
		ViewHelper.setTranslationX(menuView, -menuView.getWidth() / 2.3f + menuView.getWidth() / 2.3f * percent);
		ViewHelper.setScaleX(menuView, 0.5f + 0.5f * percent);
		ViewHelper.setScaleY(menuView, 0.5f + 0.5f * percent);
		ViewHelper.setAlpha(menuView, percent);
		if (isShowShadow) {
			ViewHelper.setScaleX(ivShadow, f1 * 1.4f * (1 - percent * 0.12f));
			ViewHelper.setScaleY(ivShadow, f1 * 1.85f * (1 - percent * 0.12f));
		}
		if (getBackground() != null) {
			getBackground().setColorFilter(evaluate(percent, Color.BLACK, Color.TRANSPARENT), Mode.SRC_OVER);
		}

		if (dragListener != null) {
			dragListener.onClose();
		}
	}

	private Integer evaluate(float fraction, Object startValue, Integer endValue) {
		int startInt = (Integer) startValue;
		int startA = (startInt >> 24) & 0xff;
		int startR = (startInt >> 16) & 0xff;
		int startG = (startInt >> 8) & 0xff;
		int startB = startInt & 0xff;
		int endInt = (Integer) endValue;
		int endA = (endInt >> 24) & 0xff;
		int endR = (endInt >> 16) & 0xff;
		int endG = (endInt >> 8) & 0xff;
		int endB = endInt & 0xff;
		return (int) ((startA + (int) (fraction * (endA - startA))) << 24) | (int) ((startR + (int) (fraction * (endR - startR))) << 16) | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
				| (int) ((startB + (int) (fraction * (endB - startB))));
	}

	@Override
	public void computeScroll() {
		if (dragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	/**
	 * 获取菜单状态
	 * 
	 * @return
	 */
	public Status getStatus() {
		if (mainLeft == 0) {
			status = Status.Close;
		} else if (mainLeft == range) {
			status = Status.Open;
		} else {
			status = Status.Drag;
		}
		return status;
	}

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		open(true);
	}

	/**
	 * 打开菜单
	 * 
	 * @param animate
	 */
	public void open(boolean animate) {
		if (animate) {
			if (dragHelper.smoothSlideViewTo(mainLayout, range, 0)) {
				ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			mainLayout.layout(range, 0, range * 2, mHeight);
			dispatchDragEvent(range);
		}
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		billContract = false;
		requestLayout();
		badgeView.setVisibility(View.VISIBLE);
		close(true);
	}

	/**
	 * 关闭菜单
	 * 
	 * @param animate
	 */
	public void close(boolean animate) {
		if (animate) {
			if (dragHelper.smoothSlideViewTo(mainLayout, 0, 0)) {
				ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			mainLayout.layout(0, 0, mWidth, mHeight);
			dispatchDragEvent(0);
		}
	}

	/**
	 * 切换菜单状态
	 */
	public void toggleMenu() {
		if (getStatus() == Status.Open) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	/**
	 * 单据是否处于收缩状态
	 * 
	 * @return
	 */
	public boolean isBillContract() {
		return billContract;
	}

	public interface DragListener {
		public void onOpen();

		public void onClose();

		public void onDrag(float percent);
	}

}
