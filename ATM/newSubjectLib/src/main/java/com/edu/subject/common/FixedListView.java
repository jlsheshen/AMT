package com.edu.subject.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义的ListView，ScrollView，ListView都是可滑动的控件，
 * 当ListView嵌套在ScrollView下时需要重写onMeasure方法，否则会出现显示不全的情况
 * 
 * @author lucher
 * 
 */
public class FixedListView extends ListView {

	public FixedListView(Context context) {
		super(context);
	}

	public FixedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 设置为Integer.MAX_VALUE>>2 是listview全部展开
		int measureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		// // 设置为400是设置listview的高度只能有400 不全部展开 实现可以滑动的效果
		// int measureSpec1 = MeasureSpec.makeMeasureSpec(400,
		// MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, measureSpec);
	}
}
