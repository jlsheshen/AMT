package com.edu.library.picgrid;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.edu.library.R;
import com.edu.library.picselect.activity.ImageListActivity;
import com.edu.library.util.SdcardPathUtil;

/**
 * 图片选择弹出框
 * 
 * @author lucher
 * 
 */
public class PicSelectPopupWindow extends PopupWindow implements OnClickListener {

	/**
	 * 调用相机
	 */
	public static final int CAMERA = 1;
	/**
	 * 调用图片库
	 */
	public static final int PICTURE = 2;

	// 分别对应三个按钮
	private Button btnTakePhoto, btnPickPhoto, btnCancel;
	private View mMenuView;

	private Context mContext;

	// 是否为多选模式
	private boolean mMultiSelect;
	// 多选模式下最多可选择的图片数量
	private int mMaxCount;

	/**
	 * @param context
	 * 
	 * 
	 */
	public PicSelectPopupWindow(Activity context) {
		super(context);
		mContext = context;

		init();
	}

	/**
	 * @param multiSelct
	 * 
	 *            是否多选
	 * @param maxCount
	 *            多选下可选择图片的最大值
	 */
	public void setMultiSelect(boolean multiSelect, int maxCount) {
		mMultiSelect = multiSelect;
		mMaxCount = maxCount;
	}

	/**
	 * 初始化
	 */
	private void init() {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popup_pic_select, null);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);

		btnTakePhoto = (Button) mMenuView.findViewById(R.id.btn_take_photo);
		btnPickPhoto = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
		btnCancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
		// 设置按钮监听
		btnTakePhoto.setOnClickListener(this);
		btnPickPhoto.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xa0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
	}

	@Override
	public void onClick(View v) {
		dismiss();
		if (v.getId() == R.id.btn_take_photo) {
			setPhotoByCamera();
		} else if (v.getId() == R.id.btn_pick_photo) {
			setPhotoByPicture();
		}
	}

	/**
	 * 调用相机拍照
	 */
	private void setPhotoByCamera() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(SdcardPathUtil.getExternalSdCardPath(), "image.png"));
		// 指定照片保存路径（SD卡），image.png为一个临时文件，每次拍照后这个图片都会被替换
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		((Activity) mContext).startActivityForResult(openCameraIntent, CAMERA);
	}

	/**
	 * 访问本地文件夹图片
	 */
	private void setPhotoByPicture() {
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//		intent.addCategory(Intent.CATEGORY_OPENABLE);
//		intent.setType("image/*");
//		((Activity) mContext).startActivityForResult(Intent.createChooser(intent, "选择图片"), PICTURE);
		Intent intent = new Intent(mContext, ImageListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putBoolean("multiSelect", mMultiSelect);
		bundle.putInt("maxCount", mMaxCount);
		intent.putExtras(bundle);
		((Activity) mContext).startActivityForResult(intent, PICTURE);
	}

	/**
	 * 显示窗口
	 * 
	 * @param view
	 */
	public void show(View view) {
		// 设置layout在PopupWindow中显示的位置
		showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}
}
