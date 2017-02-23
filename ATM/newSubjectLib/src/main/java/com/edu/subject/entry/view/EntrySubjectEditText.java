package com.edu.subject.entry.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * 一级科目以及二级科目使用的EditText，点击右边图标可进行选择
 * @author lucher
 *
 */
public class EntrySubjectEditText extends EntryEditText {
	/** 
	* 下拉图片的引用 
	*/
	private Drawable mDropdownIcon;
	//下拉箭头点击监听
	private ArrowClickListener mListener;

	public EntrySubjectEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		setGravity(Gravity.CENTER_VERTICAL);
		setSingleLine();
		setHintTextColor(Color.GRAY);

		//暂时取消分录题一级科目二级科目下拉选择功能
//		//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片  
//		mDropdownIcon = getCompoundDrawables()[2];
//		if (mDropdownIcon == null) {
//			mDropdownIcon = getResources().getDrawable(R.drawable.arrow_down);
//		}
//		mDropdownIcon.setBounds(0, 0, mDropdownIcon.getIntrinsicWidth(), mDropdownIcon.getIntrinsicHeight());
//		//		默认设置显示图标  
//		setIconVisible(true);
	}

	/** 
	* 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 
	* 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和 
	* EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑 
	*/
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP && isEnabled()) {
			if (getCompoundDrawables()[2] != null) {
				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
				if (touchable) {
					//里面写上自己想做的事情，也就是DrawableRight的触发事件  
					if(mListener != null) {
						mListener.onClick(this);
					}
					return true;
				}
			}
		}
		return super.onTouchEvent(event);
	}

	/** 
	* 设置图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去 
	* 
	* @param visible 
	*/
	protected void setIconVisible(boolean visible) {
		//如果你想让它一直显示DrawableRight的图标，并且还需要让它触发事件，直接把null改成mDropdownIcon即可  
		Drawable right = visible ? mDropdownIcon : null;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	/** 
	* 设置晃动动画 
	*/
	public void setShakeAnimation() {
		this.setAnimation(shakeAnimation(5));
	}

	/** 
	* 晃动动画 
	* 
	* @param counts 1秒钟晃动多少下 
	* @return 
	*/
	public Animation shakeAnimation(int counts) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}
	
	public void setOnArrowClickListener(ArrowClickListener listener) {
		mListener =  listener;
	}
	
	/**
	 * 下拉箭头点击监听
	 * @author lucher
	 *
	 */
	public interface ArrowClickListener {
		/**
		 * 下拉箭头点击
		 * @param view
		 */
		void onClick(View view);
	}
}
