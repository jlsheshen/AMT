package com.edu.library.loadingimage;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 图片加载监听
 * 
 * @author lucher
 * 
 */
public interface LoadingListener {

	/**
	 * 加载开始
	 * 
	 * @param imageUri
	 *            地址
	 * @param view
	 *            对应视图
	 */
	void onLoadingStarted(String imageUri, View view);

	/**
	 * 加载失败
	 * 
	 * @param imageUri
	 *            地址
	 * @param view
	 *            对应视图
	 * @param reason
	 *            失败原因
	 */
	void onLoadingFailed(String imageUri, View view, String reason);

	/**
	 * 加载完成
	 * 
	 * @param imageUri
	 *            地址
	 * @param view
	 *            对应视图
	 * @param loadedImage
	 *            对应bitmap
	 */
	void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

	/**
	 * 加载进度更新
	 * 
	 * @param imageUri
	 *            地址
	 * @param view
	 *            对应视图
	 * @param current
	 *            当前完成的size
	 * @param total
	 *            总size
	 */
	void onLoadingProgress(String imageUri, View view, int current, int total);
}
