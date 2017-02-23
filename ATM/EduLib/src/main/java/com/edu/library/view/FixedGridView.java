package com.edu.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义的GridView，ScrollView，GridView都是可滑动的控件，
 * 当GridView嵌套在ScrollView下时需要重写onMeasure方法，否则会出现显示不全的情况
 * 
 * @author lucher
 * 
 */
public class FixedGridView extends GridView {

	public FixedGridView(Context context) {
		super(context);
	}

	public FixedGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
