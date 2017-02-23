package com.edu.library.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可控制是否可通过触摸滑动的ViewPager
 * 
 * @author lucher
 * 
 */
public class ControllableViewPager extends ViewPager {

	// 是否可滚动
	private boolean scrollable = true;

	public ControllableViewPager(Context context) {
		super(context);
	}

	public ControllableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}

	@Override
	public void scrollTo(int x, int y) {
		if (scrollable) {
			super.scrollTo(x, y);
		}
	}
}
