package com.edu.accountingteachingmaterial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ClassGvAdapter extends BaseAdapter{
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_example_gv, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTv.setText("会计基础");
        viewHolder.bgIv.setImageResource(R.mipmap.ic_launcher);
        viewHolder.authorTv.setText("taiyoucai");
        return convertView;
    }

    class ViewHolder {
        TextView nameTv,authorTv;
        ImageView  bgIv;

        public ViewHolder(View view) {
            super();
            nameTv = (TextView) view.findViewById(R.id.item_class_name_iv);
            authorTv = (TextView) view.findViewById(R.id.item_class_author_tv);
            bgIv = (ImageView) view.findViewById(R.id.item_class_bg_iv);
        }
    }
}
