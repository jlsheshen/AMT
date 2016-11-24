package com.edu.subject.bill.view;

import com.edu.subject.bill.element.ElementLayoutParams;
import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.scale.IScaleable;
import com.edu.subject.bill.scale.ScaleUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 单据背景图控件，支持缩放
 * 
 * @author lucher
 * 
 */
public class BackgroudView extends ImageView implements IScaleable {

	// 宽
	private int mWidth;
	// 高
	private int mHeight;

	public BackgroudView(Context context) {
		super(context);

		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		setScaleType(ScaleType.FIT_XY);
		setLayoutParams(new ElementLayoutParams(ElementType.TYPE_BG, 0, 0, mWidth, mHeight));
	}
	
	/**
	 * 设置图片
	 * @param bitmap
	 */
	public void setBitmap(Bitmap bitmap) {
		setImageBitmap(bitmap);
		mWidth = bitmap.getWidth();
		mHeight = bitmap.getHeight();
	}

	@Override
	public void postScale(float scale, int scaleTimes) {
		// 布局参数缩放
		int scaledWidth = ScaleUtil.getScaledValue(mWidth, scale);
		int scaledHeight = ScaleUtil.getScaledValue(mHeight, scale);
		ElementLayoutParams params = (ElementLayoutParams) getLayoutParams();
		params.setWidth(scaledWidth);
		params.setHeight(scaledHeight);
	}
}
