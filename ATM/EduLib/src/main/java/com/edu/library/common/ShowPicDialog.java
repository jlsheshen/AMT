package com.edu.library.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.edu.library.R;
import com.edu.library.view.ZoomableImageView;
import com.edu.library.view.ZoomableImageView.OnImageTouchedListener;

/**
 * 显示图片的dialog
 * 
 * @author lucher
 * 
 */
public class ShowPicDialog extends Dialog implements OnImageTouchedListener {

	// 图片缩放控件
	private ZoomableImageView zoomableImage;
	protected Context mContext;

	private Bitmap mBitmap;

	public ShowPicDialog(Context context) {
		super(context);
		mContext = context;

		init();
	}

	/**
	 * 设置图片
	 * 
	 * @param bitmap
	 */
	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		zoomableImage.setImageBitmap(mBitmap);
	}

	/**
	 * 初始化
	 */
	private void init() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.dialog_show_pic);
		// // 设置对话框的位置
		Window dialogWindow = getWindow();
		dialogWindow.setGravity(Gravity.CENTER_VERTICAL);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.width = mContext.getResources().getDisplayMetrics().widthPixels;
		dialogWindow.setAttributes(params);

		zoomableImage = (ZoomableImageView) findViewById(R.id.image);
		zoomableImage.setOnImageTouchedListener(this);

	}

	@Override
	public void onImageTouched() {
		mBitmap = null;
		dismiss();
	}

}
