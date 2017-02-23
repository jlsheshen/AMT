package com.edu.subject.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;

import com.edu.subject.R;

/**
 * 可拖动滑块自由调节高度的容器类
 * @author lucher
 *
 */
public class SlideableContainer extends FrameLayout implements OnTouchListener, OnGlobalLayoutListener {

	private static final String TAG = SlideableContainer.class.getSimpleName();
	/****************可在具体项目里设置这三个布局里的内容***************/
	//可滑动的布局
	private FrameLayout slideableLayout;
	//不可滑动的布局
	private FrameLayout unSlideableLayout;
	//滑块视图布局
	private FrameLayout sliderLayout;
	//滑块视图高度
	private int sliderHeight;

	//可滑动部分容器，包括了可滑动布局和滑块布局
	private View mainContainer;

	private Context mContext;

	// 拖拽起点坐标
	private float mOldY = 0;
	// 滑块重力，支持top，center，bottom
	private int mGravity = Gravity.CENTER;

	public SlideableContainer(Context context) {
		super(context);
		mContext = getContext();
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		//先加入不可滑动的容器
		unSlideableLayout = new FrameLayout(mContext);
		unSlideableLayout.setLayoutParams(new SliderLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(unSlideableLayout);

		//加入可滑动容器
		mainContainer = View.inflate(mContext, R.layout.layout_slidable_main_container, null);
		mainContainer.setLayoutParams(new SliderLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(mainContainer);
		//滑动容器里分为滑块和滑动布局
		slideableLayout = (FrameLayout) findViewById(R.id.slideableLayout);
		sliderLayout = (FrameLayout) findViewById(R.id.sliderLayout);
		sliderLayout.setOnTouchListener(this);

		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	/**
	 * 设置可滑动容器的内容,该内容将铺满容器
	 * @param view
	 */
	public void setSlideableContent(View view) {
		slideableLayout.removeAllViews();
		slideableLayout.addView(view);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * 设置不可滑动容器的内容,该内容将铺满容器
	 * @param view
	 */
	public void setUnSlideableContent(View view) {
		unSlideableLayout.removeAllViews();
		unSlideableLayout.addView(view);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * 设置滑块容器内容,该内容将在横向上铺满容器
	 * @param view
	 */
	public void setSliderContent(View view) {
		sliderLayout.removeAllViews();
		sliderLayout.addView(view);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	/**
	 * 设置滑块重力，支持Gravity.top，center，bottom
	 * @param gravity
	 */
	public void setSliderGravity(int gravity) {
		mGravity = gravity;
		requestGravity();
	}

	/**
	 * 使重力生效
	 */
	private void requestGravity() {
		Log.i(TAG, "requestGravity-----");
		int top = 0;
		switch (mGravity) {
		case Gravity.TOP:
			top = 0;

			break;
		case Gravity.CENTER:
			top = (getHeight() - sliderLayout.getHeight()) / 2;

			break;
		case Gravity.BOTTOM:
			top = slideableLayout.getHeight();

			break;
		default:
			break;
		}
		setSliderPosition(top);
	}

	/* 
	 * 处理滑块拖拽
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mOldY = event.getRawY();

			break;

		case MotionEvent.ACTION_MOVE:
			handleDrage(event);
			break;
		default:
			break;
		}

		return true;
	}

	/**
	 * 处理拖动事件
	 * @param event
	 */
	private void handleDrage(MotionEvent event) {
		float dy = mOldY - event.getRawY();// y方向手指移动的距离

		int top = Math.round((mainContainer.getTop() - dy));
		top = checkBorder(top);
		setSliderPosition(top);

		mOldY = event.getRawY();
	}

	/**
	 * 设置滑块的位置
	 * @param top
	 */
	private void setSliderPosition(int top) {
		Log.d(TAG, "setSliderPosition---------------------");
		SliderLayoutParams lp = (SliderLayoutParams) mainContainer.getLayoutParams();
		lp.setTop(top);
		requestLayout();
	}

	/**
	 * 检测边界是否出界,主要是通过判断top值是否出界，如果出界则返回临界值的top
	 * @param top
	 */
	private int checkBorder(int top) {
		int maxDistance = getHeight()-sliderHeight;//滑块滑动的最大范围
		if (top <= 0) {//上边界判断
			top = 0;
		} else if (top >= maxDistance) {
			top = maxDistance;
		}
		return top;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d(TAG, "onMeasure---");
		if (sliderHeight <= 0) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} else {
			int width = MeasureSpec.getSize(widthMeasureSpec);
			int height = MeasureSpec.getSize(heightMeasureSpec);
			//可滑动和不可滑动布局高度计算
			SliderLayoutParams lp = (SliderLayoutParams) mainContainer.getLayoutParams();
			//不可滑动布局
			int unslideableHeight = lp.getTop();
			int unslideableHeightMeasureSpec = MeasureSpec.makeMeasureSpec(unslideableHeight, MeasureSpec.EXACTLY);
			unSlideableLayout.measure(widthMeasureSpec, unslideableHeightMeasureSpec);
			//可滑动布局
			int slideableHeight = height - unslideableHeight;
			int slideableHeightMeasureSpec = MeasureSpec.makeMeasureSpec(slideableHeight, MeasureSpec.EXACTLY);
			mainContainer.measure(widthMeasureSpec, slideableHeightMeasureSpec);
			Log.i(TAG,"h1:"+unslideableHeight+",h2:"+slideableHeight);

			setMeasuredDimension(width, height);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Log.d(TAG, "onLayout---");
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			SliderLayoutParams lp = (SliderLayoutParams) child.getLayoutParams();

			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();
			//计算滑动容器的位置并重新布局
			top = lp.getTop();
			left = 0;
			right = width;
			bottom = top + height;
			Log.d(TAG, String.format("layout : %s,%s,%s,%s", left, top, right, bottom));
			child.layout(left, top, right, bottom);
		}
	}

	@Override
	public void onGlobalLayout() {
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
		sliderHeight = sliderLayout.getHeight();
		Log.d(TAG, "sliderHeight:" + sliderHeight);
		requestGravity();
	}

	/**
	 * 滑动布局参数
	 * @author lucher
	 *
	 */
	public class SliderLayoutParams extends LayoutParams {
		//top间距
		private int top;

		public SliderLayoutParams(int arg0, int arg1) {
			super(arg0, arg1);
		}

		public int getTop() {
			return top;
		}

		public void setTop(int top) {
			this.top = top;
		}

	}
}
