package com.edu.accountingteachingmaterial.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.edu.accountingteachingmaterial.constant.ClassContstant;

/**
 * 不能触摸滑动的ViewPager
 * 
 * @author lucher
 * 
 */
public class UnTouchableViewPager extends ViewPager {
	private int type ;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public UnTouchableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (type == ClassContstant.SUBJECT_BILL||type == ClassContstant.SUBJECT_GROUP_BILL||type == ClassContstant.SUBJECT_ENTRY) {
			return false;
		}
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (type == ClassContstant.SUBJECT_BILL||type == ClassContstant.SUBJECT_GROUP_BILL||type == ClassContstant.SUBJECT_ENTRY) {
			return false;
		}
		return super.onTouchEvent(ev);
	}
}
