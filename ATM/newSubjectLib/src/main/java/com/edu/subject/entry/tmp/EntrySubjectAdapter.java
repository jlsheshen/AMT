package com.edu.subject.entry.tmp;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.subject.R;

/**
 * 分录科目使用的adapter
 * 
 * @author lucher
 * 
 */
public class EntrySubjectAdapter extends BaseAdapter {

	protected static final String TAG = "EntrySubjectAdapter";

	private Context mContext;
	private List<EntrySubjectData> datas;

	public EntrySubjectAdapter(Context context, List<EntrySubjectData> datas) {
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
		return (long) position;
	}

	/**
	 * 设置数据
	 * 
	 * @param datas
	 */
	public void setDatas(List<EntrySubjectData> datas) {
		this.datas = datas;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(mContext, R.layout.item_entry_subject, null);
		((TextView) convertView).setText(datas.get(position).getName());
		convertView.setId(datas.get(position).getId());

		return convertView;
	}
}
