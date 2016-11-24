package com.edu.testbill.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.edu.R;
import com.edu.subject.util.BitmapParseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 单据题目图片资料查看对话框
 * 
 * @author lucher
 * 
 */
public class PictureBrowseDialog extends Dialog implements View.OnClickListener {

	private ScrollView scroll;
	private LinearLayout picContent;
	private Context mContext;
	private List<Bitmap> bitmaps;

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public PictureBrowseDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.dialog_picture_browse);
		mContext = context;

		picContent = (LinearLayout) findViewById(R.id.picContent);
		scroll = (ScrollView) findViewById(R.id.scrollView);
		picContent.setOnClickListener(this);
	}

	/**
	 * 设置图片
	 * 
	 * @param pics
	 *            图片uri数组
	 */
	public void setPics(String[] pics) {
		bitmaps = new ArrayList<Bitmap>(pics.length);
		picContent.removeAllViews();
		scroll.scrollTo(0, 0);
		for (String pic : pics) {
			View view = View.inflate(mContext, R.layout.item_picture, null);
			ImageView iv = (ImageView) view.findViewById(R.id.img_pic);
			picContent.addView(view);
			Bitmap bm = BitmapParseUtil.parse(pic, mContext, false);
			iv.setImageBitmap(bm);
			bitmaps.add(bm);
		}
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		for (Bitmap bm : bitmaps) {
			bm.recycle();
			bm = null;
		}
	}
}
