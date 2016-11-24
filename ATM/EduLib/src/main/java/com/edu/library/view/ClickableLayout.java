package com.edu.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.library.R;

/**
 * 可点击布局封装
 * 
 * @author lucher
 * 
 */
public class ClickableLayout extends RelativeLayout {

	// 初级，次级标题
	private String mPrimaryTitle;
	private String mSecondaryTitle;

	public ClickableLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ClickableLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		View.inflate(context, R.layout.layout_item_clickable, this);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClickableLayout, defStyle, 0);
		mPrimaryTitle = a.getString(R.styleable.ClickableLayout_primary_text);
		mSecondaryTitle = a.getString(R.styleable.ClickableLayout_secondary_text);
		a.recycle();

		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		TextView tvPrimary = (TextView) findViewById(R.id.tv_primary_title);
		TextView tvSecondary = (TextView) findViewById(R.id.tv_secondary_title);
		tvPrimary.setText(mPrimaryTitle);
		tvSecondary.setText(mSecondaryTitle);
	}
}
