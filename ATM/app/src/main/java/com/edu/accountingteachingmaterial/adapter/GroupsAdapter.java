package com.edu.accountingteachingmaterial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.TaskDetailBean;
import com.edu.accountingteachingmaterial.view.CircleImageView;

import java.util.List;

/**
 *
 *  显示学生列表适配器
 * Created by Administrator on 2017/3/1.
 */

public class GroupsAdapter extends BaseAdapter {

    List<TaskDetailBean.StudentlistBean> datas;

    public GroupsAdapter setDatas(List<TaskDetailBean.StudentlistBean> datas) {
        this.datas = datas;
        return this;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addgroup_rv,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        TaskDetailBean.StudentlistBean data = datas.get(position);
        viewHolder.headIv.setImageResource(R.mipmap.student_head);
        viewHolder.nameTv.setText(data.getName());
        return convertView;
    }

    class ViewHolder{
        TextView nameTv;
        CircleImageView headIv;
        public ViewHolder(View itemView) {
            nameTv = (TextView) itemView.findViewById(R.id.item_groups_name_tv);
            headIv = (CircleImageView) itemView.findViewById(R.id.item_groups_head_iv);
        }
    }
}
