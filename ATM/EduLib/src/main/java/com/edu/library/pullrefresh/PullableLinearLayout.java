package com.edu.library.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 支持pullable的LinearLayout
 * 
 * @author lucher
 * 
 */
public class PullableLinearLayout extends LinearLayout implements Pullable {

	public PullableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean canPullDown() {
		return true;
	}

	@Override
	public boolean canPullUp() {
		return false;
	}

}
