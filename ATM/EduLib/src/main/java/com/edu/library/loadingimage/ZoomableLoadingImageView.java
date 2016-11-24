package com.edu.library.loadingimage;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.edu.library.R;

/**
 * 自定义带加载进度条的imageview，图片下载之前显示进度条，加载后隐藏进度条,支持缩放
 * 
 * @author lucher
 * 
 */
public class ZoomableLoadingImageView extends BaseLoadingImageView {

	//PhotoView开源库提供支持缩放的类
	protected PhotoViewAttacher mAttacher;
	// imageview的scaletype
	protected ScaleType mScaleType;
	private PhotoViewAttacherListener mListener;

	public ZoomableLoadingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.layout_zoomable_loading_imageview, this);

		init(view);
	}

	/**
	 * 初始化
	 */
	private void init(View view) {
		imageView = (ImageView) view.findViewById(R.id.iv_img);
		loadingView = view.findViewById(R.id.loading_view);
		tvProgress = (TextView) view.findViewById(R.id.tv_progress);
		pbProgress = view.findViewById(R.id.pb_loading);
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		super.setScaleType(scaleType);
		mScaleType = scaleType;
	}

	/**
	 * 获取PhotoViewAttacher
	 */
	public void setPhotoViewAttacherListener(PhotoViewAttacherListener listener) {
		mListener = listener;
	}

	@Override
	public void onLoadingProgress(String imageUri, View view, int current, int total) {
		if (tvProgress.getVisibility() == View.VISIBLE)
			tvProgress.setText(Math.round(100.0f * current / total) + "%");
	}

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		super.onLoadingComplete(imageUri, view, loadedImage);
		mAttacher = new PhotoViewAttacher(imageView);
		mAttacher.setScaleType(mScaleType);
		if(mListener != null) {
			mListener.onPhotoViewAttacherCreated(mAttacher);
		}
	}

	@Override
	public ImageView getImageView() {
		return imageView;
	}

	@Override
	public View getLoadingView() {
		return loadingView;
	}

	@Override
	public void setProgressValueVisiblility(int visibility) {
		tvProgress.setVisibility(visibility);
	}

	@Override
	public void setProgressVisibility(int visibility) {
		pbProgress.setVisibility(visibility);
	}

	/**
	 * PhotoViewAttacher初始化监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface PhotoViewAttacherListener {
		/**
		 * PhotoViewAttacher初始化完成
		 * 
		 * @param attacher
		 */
		void onPhotoViewAttacherCreated(PhotoViewAttacher attacher);
	}
}
