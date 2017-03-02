package com.edu.accountingteachingmaterial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.edu.accountingteachingmaterial.R;

/**
 * Created by Administrator on 2017/3/1.
 */

public class TaskContentAdapter extends BaseAdapter {
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
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taskcontent_gv,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bgIv.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
    class ViewHolder{
        ImageView bgIv;//状态
        public ViewHolder(View view) {
            bgIv = (ImageView) view.findViewById(R.id.item_task_content_iv);
        }
    }

}
