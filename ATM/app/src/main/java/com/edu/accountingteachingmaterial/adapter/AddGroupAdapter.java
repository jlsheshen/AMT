package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.GroupsListBean;

import java.util.List;

/**
 * 添加小组界面Adapter
 * Created by Administrator on 2017/2/28.
 */

public class AddGroupAdapter extends BaseAdapter{

    private List<GroupsListBean> datas;
    private Context context;


    public AddGroupAdapter(Context context) {
        this.context = context;
    }

    public AddGroupAdapter setDatas(List<GroupsListBean> datas) {
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_addgroup_lv,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GroupsListBean data = datas.get(position);
        viewHolder.groupNumbereTv.setText(data.getStu_count() + "/" + data.getGroup_sum());
        viewHolder.groupTitleTv.setText(data.getTeam_name());

        viewHolder.groupPeopleRv.setAdapter(new RvAddGroupAdapter(context));
        viewHolder.groupPeopleRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        return convertView;
    }
    class ViewHolder{
        TextView groupTitleTv, groupNumbereTv;//小组名
        RecyclerView groupPeopleRv;
        public ViewHolder(View view) {
            groupTitleTv = (TextView) view.findViewById(R.id.item_addgroup_title_tv);
            groupNumbereTv = (TextView) view.findViewById(R.id.item_addgroup_number_tv);
            groupPeopleRv = (RecyclerView) view.findViewById(R.id.item_addgroup_rv);
        }
    }
}
