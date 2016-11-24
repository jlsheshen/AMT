package com.edu.library.loadingimage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.library.R;

/**
 * 自定义带加载进度条的imageview，图片下载之前显示进度条，加载后隐藏进度条
 * 
 * @author lucher
 * 
 */
public class SimpleLoadingImageView extends BaseLoadingImageView {

	public SimpleLoadingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.layout_loading_imageview, this);

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
	public void onLoadingProgress(String imageUri, View view, int current, int total) {
		if (tvProgress.getVisibility() == View.VISIBLE)
			tvProgress.setText(Math.round(100.0f * current / total) + "%");
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
}
