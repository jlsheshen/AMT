package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.entity.SubChaptersBean;

import java.util.List;

public class ClassChapterLvAdapter extends BaseAdapter {
	List<SubChaptersBean> datas;
	Context context;
	boolean isBook;

	public ClassChapterLvAdapter(Context context) {
		super();
		this.context = context;
	}

	public void setDatas(List<SubChaptersBean> datas,boolean isBook) {
		this.datas = datas;
		this.isBook = isBook;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return datas == null?0:datas.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return datas.get(position).getId();
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
		convertView = LayoutInflater.from(context).inflate(R.layout.item_main_classchapter_exlv, parent,false);}

		TextView titlyTv = (TextView) convertView.findViewById(R.id.item_classchapter_tv);
		titlyTv.setText(datas.get(position).getTitle());
		if (isBook){

		}else {
			TextView timeTv = (TextView) convertView.findViewById(R.id.item_classchapter_time_tv);
			timeTv.setVisibility(View.VISIBLE);

			try {
				String time = datas.get(position).getCreateTime().substring(5,17);
				timeTv.setText(time);
			}catch (Exception e){
				Toast.makeText(context, "e:" + e, Toast.LENGTH_SHORT).show();
			}

		}

		return convertView;
	}






}
