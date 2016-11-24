package com.edu.library.popupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.edu.library.R;
import com.edu.library.view.ZoomableImageView;
import com.edu.library.view.ZoomableImageView.OnImageTouchedListener;

/**
 * 显示图片的dialog
 * 
 * @author lucher
 * 
 */
public class ShowPicWindow extends PopupWindow implements OnImageTouchedListener {

	//图片缩放控件
	private ZoomableImageView zoomableImage;
	protected Context mContext;

	private Bitmap mBitmap;

	private View mView;

	public ShowPicWindow(Context context) {
		super(context);
		mContext = context;

		init();
	}
	
	/**
	 * 设置图片
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
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.dialog_show_pic, null);
		this.setContentView(mView);

		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.popupWindowAnim);

		zoomableImage = (ZoomableImageView) mView.findViewById(R.id.image);
		zoomableImage.setOnImageTouchedListener(this);
		
	}

	@Override
	public void onImageTouched() {
		mBitmap = null;
		dismiss();
	}

}
