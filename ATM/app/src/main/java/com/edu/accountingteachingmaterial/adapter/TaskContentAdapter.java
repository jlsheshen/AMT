package com.edu.accountingteachingmaterial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.edu.accountingteachingmaterial.R;
import com.edu.library.imageloader.EduImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 任务内容adapter
 * Created by Administrator on 2017/3/1.
 */

public class TaskContentAdapter extends BaseAdapter {
    List<String> datas;

    public TaskContentAdapter setDatas(List<String> datas) {
        this.datas = datas;
        return this;
    }

    @Override
    public int getCount() {
        return datas == null?0:datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
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
        ImageLoader.getInstance().displayImage(datas.get(position),viewHolder.bgIv, EduImageLoader.getInstance().getDefaultBuilder().build());

        return convertView;
    }
    class ViewHolder{
        ImageView bgIv;//状态
        public ViewHolder(View view) {
            bgIv = (ImageView) view.findViewById(R.id.item_task_content_iv);
        }
    }

}
