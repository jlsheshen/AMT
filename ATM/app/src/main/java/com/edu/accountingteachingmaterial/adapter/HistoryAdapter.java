package com.edu.accountingteachingmaterial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.entity.HistoryData;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */

public class HistoryAdapter extends BaseAdapter {
    // * context
    private Context context;
    // * 数据
    private List<HistoryData> data;


    public HistoryAdapter(Context context, List<HistoryData> datas) {
        this.context = context;
        this.data = datas;
    }

    public void setData(List<HistoryData> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    @SuppressLint("ViewHolder")
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_history_text, parent, false);
        TextView textTitle = (TextView) convertView;
        textTitle.setText(data.get(position).getChapterName());
        return convertView;
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
