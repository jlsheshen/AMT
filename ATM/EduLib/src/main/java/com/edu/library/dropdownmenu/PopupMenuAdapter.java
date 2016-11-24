package com.edu.library.dropdownmenu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.library.R;

/**
 * 下拉菜单窗体里listview对应的adapter
 * 
 * @author lucher
 * @param <T>
 */
public class PopupMenuAdapter<T> extends BaseAdapter {

	private Context mContext;
	// 对应数据
	private List<T> mObjects = new ArrayList<T>();
	private int mSelectItem = 0;

	public PopupMenuAdapter(Context context) {
		init(context);
	}

	/**
	 * 刷新数据
	 * 
	 * @param objects
	 * @param selIndex
	 */
	public void refreshData(List<T> objects, int selIndex) {
		mObjects = objects;
		if (selIndex < 0) {
			selIndex = 0;
		}
		if (selIndex >= mObjects.size()) {
			selIndex = mObjects.size() - 1;
		}

		mSelectItem = selIndex;
	}

	private void init(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {

		return mObjects.size();
	}

	@Override
	public Object getItem(int pos) {
		return mObjects.get(pos).toString();
	}
	
	public Object getData(int pos) {
		return mObjects.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		convertView = View.inflate(mContext, R.layout.popup_menu_item, null);

		TextView mTextView = (TextView) convertView.findViewById(R.id.textView);
		// 设置item的文本内容
		String item = (String) getItem(pos);
		mTextView.setText(item);
		// 设置tag
		convertView.setTag(mObjects.get(pos));
		if(pos == mObjects.size()-1) {
			convertView.findViewById(R.id.dash_line).setVisibility(View.GONE);
		}

		return convertView;
	}

	public int getSelectItem() {
		return mSelectItem;
	}

	public void setSelectItem(int mSelectItem) {
		this.mSelectItem = mSelectItem;
	}

	/**
	 * 获取item的高度
	 * 
	 * @return
	 */
	public int getItemHeight() {
		// 获取item的高度，listView的item
		// 由于是适配器动态添加的，所以只有当加载完成后才取得到高度，所以必须要手动调用一次measure方法
		View item = View.inflate(mContext, R.layout.popup_menu_item, null);
		item.measure(0, 0);
		return item.getMeasuredHeight();
	}
}
