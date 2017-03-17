package com.edu.library.imageloader;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.edu.library.R;
import com.edu.library.loadingimage.BaseLoadingImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 图片下载显示类，基于universal-image-loader，为了更高效开发而封装
 * @author lucher
 * 
 */
public class EduImageLoader {

	// universal-image-loader提供的
	private ImageLoader mImageLoader;
	// 显示图片的配置
	private Builder mBuilder;

	/**
	 * 构造
	 */
	private EduImageLoader() {
		mImageLoader = ImageLoader.getInstance();
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static EduImageLoader getInstance() {

		return new EduImageLoader();
	}

	/**
	 * 获取默认的图片显示配置
	 * 
	 * @return
	 */
	public Builder getDefaultBuilder() {
		if (mBuilder == null) {
			mBuilder = new Builder().showImageOnLoading(R.drawable.ic_default).showImageForEmptyUri(R.drawable.ic_empty)
					.showImageOnFail(R.drawable.ic_error).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true);
		}
		return mBuilder;
	}

	/**
	 * 下载图片并显示,将使用默认的DisplayImageOptions
	 * 
	 * @param uri
	 *            图片地址
	 * @param loadingImageView
	 *            显示图片的加载视图
	 */
	public void displayImage(String uri, final BaseLoadingImageView loadingImageView) {
		displayImage(uri, loadingImageView, getDefaultBuilder().build());
	}

	/**
	 * 下载图片并显示
	 * 
	 * @param uri
	 *            图片地址
	 * @param loadingImageView
	 *            显示图片的加载视图
	 * @param options
	 *            显示图片的配置
	 */
	public void displayImage(String uri, final BaseLoadingImageView loadingImageView, DisplayImageOptions options) {
		if (options == null) {
			options = mBuilder.build();
		}
		mImageLoader.displayImage(uri, loadingImageView.getImageView(), options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				loadingImageView.onLoadingStarted(imageUri, loadingImageView);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String reason = "图片加载失败，未知错误";
				switch (failReason.getType()) {
				case IO_ERROR:
					reason = "图片输入输出错误";
					break;
				case DECODING_ERROR:
					reason = "图片解析出错";
					break;
				case NETWORK_DENIED:
					reason = "图片下载失败";
					break;
				case OUT_OF_MEMORY:
					reason = "图片内存溢出";
					break;
				case UNKNOWN:
					reason = "图片加载失败，未知错误";
					break;
				}

				loadingImageView.onLoadingFailed(imageUri, loadingImageView, reason);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				loadingImageView.onLoadingComplete(imageUri, loadingImageView, loadedImage);
			}

		}, new ImageLoadingProgressListener() {
			@Override
			public void onProgressUpdate(String imageUri, View view, int current, int total) {
				loadingImageView.onLoadingProgress(imageUri, loadingImageView.getImageView(), current, total);
			}
		});
	}

	/**
	 * 下载图片并显示-直接使用universal-image-loader提供的方法
	 * 
	 * @param uri
	 * @param imageView
	 * @param options
	 * @param listener
	 * @param progressListener
	 */
	public void displayImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener,
			ImageLoadingProgressListener progressListener) {
		mImageLoader.displayImage(uri, imageView, options, listener, progressListener);
	}

	/**
	 * 下载图片并显示-直接使用universal-image-loader提供的方法
	 * 
	 * @param uri
	 * @param imageView
	 * @param options
	 * @param listener
	 * @param progressListener
	 */
	public void displayImage(String uri, ImageView imageView, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
		mImageLoader.displayImage(uri, imageView, getDefaultBuilder().build(), listener, progressListener);
	}
}
