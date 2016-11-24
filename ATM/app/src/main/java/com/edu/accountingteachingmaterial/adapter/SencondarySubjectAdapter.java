package com.edu.accountingteachingmaterial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.SecondSubjectData;

import java.util.List;

/**
 * 二级科目使用的adapter
 * 
 * @author lucher
 * 
 */
public class SencondarySubjectAdapter extends BaseAdapter {

	protected static final String TAG = "SencondarySubjectAdapter";

	private Context mContext;
	private List<SecondSubjectData> datas;

	public SencondarySubjectAdapter(Context context, List<SecondSubjectData> datas) {
		mContext = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		if (datas == null) {
			return 0;
		}
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 设置数据
	 * 
	 * @param datas
	 */
	public void setDatas(List<SecondSubjectData> datas) {
		this.datas = datas;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(mContext, R.layout.item_primary_subject, null);
		((TextView) convertView).setText(datas.get(position).getName());
		convertView.setId(datas.get(position).getId());

		return convertView;
	}
}
