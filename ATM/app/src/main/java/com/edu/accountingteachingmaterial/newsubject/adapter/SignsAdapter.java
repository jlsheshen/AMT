package com.edu.accountingteachingmaterial.newsubject.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.subject.bill.SignData;
import com.edu.subject.common.ProgressImageView;
import com.edu.testsubject.R;

/**
 * 印章选择表格的adapter
 * 
 * @author lucher
 * 
 */
public class SignsAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<SignData> mList = new ArrayList<SignData>();

	public SignsAdapter(Context context, List<SignData> list) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.item_sign, null);
		} else {
			view = convertView;
		}

		//加载印章图片
		ProgressImageView sign = (ProgressImageView) view.findViewById(R.id.ivSign);
		sign.loadImage(mList.get(position).getPic());
		
		TextView signContent = (TextView) view.findViewById(R.id.tvName);
		signContent.setText(mList.get(position).getName());
		view.setTag(mList.get(position));

		return view;
	}
}
