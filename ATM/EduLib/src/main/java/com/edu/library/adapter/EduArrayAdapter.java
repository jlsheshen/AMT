package com.edu.library.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.library.data.BaseListItemData;
import com.edu.library.util.ToastUtil;

/**
 * 定制的arrayadapter,使用该类可以简单的实现只有文字的列表界面
 * 
 * @author lucher
 * 
 */
public class EduArrayAdapter extends BaseAdapter {

	// 待显示数据
	protected List<BaseListItemData> mDatas;

	protected Context mContext;
	//item使用的布局文件资源
	protected int resource;

	/**
	 * @param context
	 */
	public EduArrayAdapter(Context context, int res) {
		mContext = context;
		resource = res;
	}

	/**
	 * 设置数据
	 * 
	 * @param items
	 */
	public void setDatas(List<BaseListItemData> items) {
		mDatas = items;
		notifyDataSetChanged();
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(mContext, resource, null);
		convertView.setTag(mDatas.get(position));
		if(convertView instanceof TextView) {
			((TextView) convertView).setText(mDatas.get(position).getText());
		} else {
			ToastUtil.showToast(mContext, "EduArrayAdapter的构造方法里的资源id只能是Textview");
		}

		return convertView;
	}

	@Override
	public int getCount() {
		if (mDatas == null)
			return 0;
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
