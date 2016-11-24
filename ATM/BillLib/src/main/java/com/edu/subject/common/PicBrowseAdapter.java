package com.edu.subject.common;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 图片查看控件adapter
 * @author lucher
 *
 */
public class PicBrowseAdapter extends PagerAdapter {

	//存放需要显示的视图
	private List<View> mList;

	public PicBrowseAdapter(List<View> list) {
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mList.get(position));
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {
		container.removeView(mList.get(position));
		container.addView(mList.get(position));
		return mList.get(position);

	}
}
