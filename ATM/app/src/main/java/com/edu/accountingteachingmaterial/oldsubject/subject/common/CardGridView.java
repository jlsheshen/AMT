package com.edu.accountingteachingmaterial.oldsubject.subject.common;

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
public class CardGridView extends GridView {

	public CardGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
