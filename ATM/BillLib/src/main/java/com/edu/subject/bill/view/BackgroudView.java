package com.edu.subject.bill.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.edu.subject.bill.element.ElementLayoutParams;
import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.scale.IScaleable;
import com.edu.subject.bill.scale.ScaleUtil;
import com.edu.subject.common.ProgressImageView;

/**
 * 单据背景图控件，支持缩放
 * 
 * @author lucher
 * 
 */
public class BackgroudView extends ProgressImageView implements IScaleable {

	// 宽
	private int mWidth;
	// 高
	private int mHeight;
	// 底图加载监听
	private ImageLoadListener mListener;

	public BackgroudView(Context context) {
		super(context);
	}

	@Override
	protected void init() {
		super.init();
		setScaleType(ScaleType.FIT_XY);
		setLayoutParams(new ElementLayoutParams(ElementType.TYPE_BG, 0, 0, mWidth, mHeight));
	}

	/**
	 * 获取底图宽度
	 * 
	 * @return
	 */
	public int getBmWidth() {
		return mWidth;
	}

	/**
	 * 获取底图高度
	 * 
	 * @return
	 */
	public int getBmHeight() {
		return mHeight;
	}

	@Override
	public void loadImage(String uri) {
		super.loadImage(uri);
		initSize();
	}

	/**
	 * 初始化控件大小
	 */
	private void initSize() {
		if (mBitmap != null) {
			mWidth = mBitmap.getWidth();
			mHeight = mBitmap.getHeight();
			setScaleType(ScaleType.FIT_XY);//显示底图
		} else {
			setScaleType(ScaleType.CENTER);//显示加载图片
		}
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

	@Override
	public void onLoadingStarted(String arg0, View arg1) {
		super.onLoadingStarted(arg0, arg1);
		if (mListener != null) {
			mListener.onLoadStart();
		}
	}

	@Override
	public void onLoadingComplete(String imageUri, View arg1, Bitmap loadedImage) {
		super.onLoadingComplete(imageUri, arg1, loadedImage);
		initSize();
		if (mListener != null) {
			mListener.onLoadComplete();
		}
		invalidate();
	}

	/**
	 * 设置底图加载监听
	 * 
	 * @param listener
	 */
	public void setImageLoadListener(ImageLoadListener listener) {
		mListener = listener;
	}

	/**
	 * 底图下载监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface ImageLoadListener {
		/**
		 * 下载开始
		 */
		void onLoadStart();

		/**
		 * 下载完成
		 */
		void onLoadComplete();
	}
}
