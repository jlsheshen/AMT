/**
 * ImageListActivity.java
 * ImageChooser
 * 
 * Created by likebamboo on 2014-4-23
 * Copyright (c) 1998-2014 http://likebamboo.github.io/ All rights reserved.
 */

package com.edu.library.picselect.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.edu.library.R;
import com.edu.library.common.ShowPicDialog;
import com.edu.library.picselect.ImageListAdapter;
import com.edu.library.picselect.ImageLoadTask;
import com.edu.library.picselect.LoadingLayout;
import com.edu.library.picselect.OnTaskResultListener;
import com.edu.library.picselect.TaskUtil;
import com.edu.library.util.SdcardPathUtil;
import com.edu.library.util.ToastUtil;

/**
 * 手机里的所有图片列表
 * 
 * @author lucher
 */
public class ImageListActivity extends Activity implements OnItemClickListener, OnClickListener {

	/**
	 * title
	 */
	public static final String EXTRA_TITLE = "extra_title";

	/**
	 * 图片列表extra
	 */
	public static final String EXTRA_IMAGES_DATAS = "extra_images";

	/**
	 * 图片列表GridView
	 */
	private GridView mImagesGv = null;

	/**
	 * 图片地址数据源
	 */
	private ArrayList<String> mImages = new ArrayList<String>();

	/**
	 * 适配器
	 */
	private ImageListAdapter mImageAdapter = null;

	// 显示大图窗口
	private ShowPicDialog dilog;
	// loading布局
	private LoadingLayout mLoadingLayout = null;
	// 图片扫描一般任务
	private ImageLoadTask mLoadTask = null;

	// 是否为多选模式
	private boolean mMultiSelect;
	// 多选模式下最多可选择的图片数量
	private int mMaxCount = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mMultiSelect = bundle.getBoolean("multiSelect");
			mMaxCount = bundle.getInt("maxCount");
		}

		initView();
		loadImages();
	}

	/**
	 * 初始化界面元素
	 */
	@SuppressLint("NewApi")
	private void initView() {
		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_done).setOnClickListener(this);
		if (!mMultiSelect) {
			findViewById(R.id.btn_done).setVisibility(View.GONE);
		}

		dilog = new ShowPicDialog(this);
		mImagesGv = (GridView) findViewById(R.id.images_gv);
		mLoadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
	}

	/**
	 * 加载图片
	 */
	private void loadImages() {
		mLoadingLayout.showLoading(true);
		if (SdcardPathUtil.getExternalSdCardPath() == null) {
			mLoadingLayout.showEmpty("无SD卡");
			return;
		}

		// 线程正在执行
		if (mLoadTask != null && mLoadTask.getStatus() == Status.RUNNING) {
			return;
		}

		mLoadTask = new ImageLoadTask(this, new OnTaskResultListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onResult(boolean success, String error, Object result) {
				mLoadingLayout.showLoading(false);
				// 如果加载成功
				if (success && result != null && result instanceof ArrayList) {
					mImages = (ArrayList<String>) result;
					setAdapter(mImages);
				} else {
					// 加载失败，显示错误提示
					mLoadingLayout.showFailed("加载出错");
				}
			}
		});
		TaskUtil.execute(mLoadTask);
	}

	/**
	 * 构建并初始化适配器
	 * 
	 * @param datas
	 */
	private void setAdapter(ArrayList<String> datas) {
		mImageAdapter = new ImageListAdapter(this, datas);
		mImageAdapter.setMultiSelect(mMultiSelect, mMaxCount);
		mImagesGv.setAdapter(mImageAdapter);
		mImagesGv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (mMultiSelect) {// 多选下点击查看大图
			dilog.setBitmap(BitmapFactory.decodeFile(mImages.get(arg2)));
			dilog.show();
		} else {// 单选下直接选中图片后返回
			Intent intent = new Intent();
			ArrayList<String> selectedImgs = mImageAdapter.getSelectedImgs();
			selectedImgs.add(mImages.get(arg2));
			intent.putStringArrayListExtra("selectedImgs", selectedImgs);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_back) {
			finish();
		} else if (v.getId() == R.id.btn_done) {
			if (mImageAdapter.getSelectedImgs().size() != 0) {
				Intent intent = new Intent();
				intent.putStringArrayListExtra("selectedImgs", mImageAdapter.getSelectedImgs());
				setResult(Activity.RESULT_OK, intent);
				finish();
			} else {
				ToastUtil.showToast(this, "请先选择文件");
			}
		}
	}

}
