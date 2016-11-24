/**
 * ImageGroupAdapter.java
 * ImageChooser
 * 
 * Created by likebamboo on 2014-4-22
 * Copyright (c) 1998-2014 http://likebamboo.github.io/ All rights reserved.
 */

package com.edu.library.picselect;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.edu.library.R;
import com.edu.library.util.ToastUtil;

/**
 * 某个图片组中图片列表适配器 支持单选和多选
 * 
 * @author lucher
 */
/**
 * @author lucher
 * 
 */
public class ImageListAdapter extends BaseAdapter {
	/**
	 * 上下文对象
	 */
	private Context mContext = null;

	/**
	 * 图片列表
	 */
	private ArrayList<String> mDataList = new ArrayList<String>();

	/**
	 * 选中的图片列表
	 */
	private ArrayList<String> mSelectedList = new ArrayList<String>();

	// 是否为多选模式
	private boolean mMultiSelect;
	// 多选模式下最多可选择的图片数量
	private int mMaxCount;

	/**
	 * 支持单选和多选模式，默认单选
	 * 
	 * @param context
	 * @param list
	 */
	public ImageListAdapter(Context context, ArrayList<String> list) {
		mDataList = list;
		mContext = context;
		mMultiSelect = false;
		// 之前逻辑是把选择的文件保存到文件里的，修改为临时的
		// mSelectedList = Util.getSeletedImages(context);
	}

	/**
	 * 设置是否为多选模式
	 * 
	 * @param multiSelect
	 *            是否多选
	 * @param maxCount
	 *            多选下可选择图片的最大值
	 */
	public void setMultiSelect(boolean multiSelect, int maxCount) {
		mMultiSelect = multiSelect;
		mMaxCount = maxCount;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public String getItem(int position) {
		if (position < 0 || position > mDataList.size()) {
			return null;
		}
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.image_list_item, null);
			holder.mImageIv = (MyImageView) view.findViewById(R.id.list_item_iv);
			holder.mClickArea = view.findViewById(R.id.list_item_cb_click_area);
			holder.mSelectedCb = (CheckBox) view.findViewById(R.id.list_item_cb);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		final String path = getItem(position);
		// 加载图片
		holder.mImageIv.setTag(path);
		// 加载图片
		// 利用NativeImageLoader类加载本地图片
		Bitmap bitmap = LocalImageLoader.getInstance(mContext).loadImage(path, holder.mImageIv.getPoint(),
				LocalImageLoader.getImageListener(holder.mImageIv, path, R.drawable.pic_thumb, R.drawable.pic_thumb));
		if (bitmap != null) {
			holder.mImageIv.setImageBitmap(bitmap);
		} else {
			holder.mImageIv.setImageResource(R.drawable.pic_thumb);
		}

		if (mMultiSelect) {
			holder.mSelectedCb.setVisibility(View.VISIBLE);
			holder.mSelectedCb.setChecked(false);
			// 该图片是否选中
			for (String selected : mSelectedList) {
				if (selected.equals(path)) {
					holder.mSelectedCb.setChecked(true);
				}
			}
		} else {
			holder.mSelectedCb.setVisibility(View.GONE);
		}

		// 可点区域单击事件
		holder.mClickArea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mMultiSelect) {
					boolean checked = holder.mSelectedCb.isChecked();
					if (!checked) {
						if (mSelectedList.size() >= mMaxCount) {
							ToastUtil.showToast(mContext, "最多只能选择" + mMaxCount + "张");
							return;
						}
						holder.mSelectedCb.setChecked(!checked);
						addImage(path);
					} else {
						holder.mSelectedCb.setChecked(!checked);
						deleteImage(path);
					}
				}
			}
		});

		return view;
	}

	/**
	 * 将图片地址添加到已选择列表中
	 * 
	 * @param path
	 */
	private void addImage(String path) {
		if (mSelectedList.contains(path)) {
			return;
		}
		mSelectedList.add(path);
	}

	/**
	 * 将图片地址从已选择列表中删除
	 * 
	 * @param path
	 */
	private void deleteImage(String path) {
		mSelectedList.remove(path);
	}

	/**
	 * 获取已选中的图片列表
	 * 
	 * @return
	 */
	public ArrayList<String> getSelectedImgs() {
		return mSelectedList;
	}

	static class ViewHolder {
		public MyImageView mImageIv;

		public View mClickArea;

		public CheckBox mSelectedCb;

	}

}
