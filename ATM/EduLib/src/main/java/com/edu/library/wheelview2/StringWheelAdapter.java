package com.edu.library.wheelview2;

import java.util.List;

import android.content.Context;

/**
 * 显示String的wheelview-adapter
 * 
 * @author lucher
 * 
 */
public class StringWheelAdapter implements WheelAdapter {

	// 待显示数据
	private List<String> mDatas;

	/**
	 * 构造
	 * 
	 * @param datas
	 */
	public StringWheelAdapter(List<String> datas) {
		mDatas = datas;
	}

	@Override
	public int getItemsCount() {
		if (mDatas == null)
			return 0;
		return mDatas.size();
	}

	/**
	 * 获取对应索引的item数据
	 * 
	 * @param index
	 * @return
	 */
	public String getItem(int index) {
		if (mDatas == null || mDatas.size() == 0)
			return null;
		index = Math.min(index, mDatas.size() - 1);

		return mDatas.get(index);
	}

	@Override
	public int getMaximumLength() {
		if (mDatas != null) {
			return mDatas.size();
		}
		return 0;
	}
}