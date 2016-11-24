package com.edu.library.loadingimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 带显示进度的ImageView基类-可用于上传和下载
 * 
 * @author lucher
 * 
 */
public abstract class BaseLoadingImageView extends RelativeLayout implements LoadingListener {
	
	/**
	 * 显示图片的imageview
	 */
	public ImageView imageView;
	/**
	 * 加载进度条
	 */
	public ProgressBar pbLoading;
	/**
	 * 加载进度视图
	 */
	public View loadingView;
	/**
	 * 加载进度值
	 */
	public TextView tvProgress;
	/**
	 * 进度条
	 */
	public View pbProgress;

	public BaseLoadingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 获取显示图片的ImageView控件
	 * 
	 * @return
	 */
	public abstract ImageView getImageView();

	/**
	 * 获取加载进度控件，开始加载的时候显示，加载完成后隐藏
	 * 
	 * @return
	 */
	public abstract View getLoadingView();

	/**
	 * 设置进度值是否可见
	 * 
	 * @param visibility
	 */
	public abstract void setProgressValueVisiblility(int visibility);
	
	/**
	 * 设置进度是否可见
	 * @param visibility
	 */
	public abstract void setProgressVisibility(int visibility);
	
	/**
	 * 设置scaleType
	 * @param scaleType
	 */
	public void setScaleType(ScaleType scaleType) {
		if(getImageView() != null) {
			getImageView().setScaleType(scaleType);
		}
	}

	@Override
	public void onLoadingStarted(String imageUri, View view) {
		getLoadingView().setVisibility(View.VISIBLE);
	}

	@Override
	public void onLoadingFailed(String imageUri, View view, String reason) {
		getLoadingView().setVisibility(View.GONE);
	}

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		getLoadingView().setVisibility(View.GONE);
	}
}
