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

public class AddGroupAdapter extends BaseAdapter implements RvAddGroupAdapter.FootViewClickListener {

    private List<GroupsListBean> datas;
    private Context context;
    ItemFootViewClickListener itemFootViewClickListener;
    int ItemPosition;


    public AddGroupAdapter(Context context) {
        this.context = context;
    }
    public  GroupsListBean getData(int pos){
        return datas.get(pos);
    }

    public AddGroupAdapter setDatas(List<GroupsListBean> datas) {
        this.datas = datas;
        return this;
    }

    public AddGroupAdapter setItemFootViewClickListener(ItemFootViewClickListener itemFootViewClickListener) {
        this.itemFootViewClickListener = itemFootViewClickListener;
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
        return datas.get(position).getId();
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

        if (data.getGroup_sum() > data.getStu_count()){
            GroupsListBean.StudentlistBean  footData = new GroupsListBean.StudentlistBean();
            footData.setFootView(true);
            data.getStudentlist().add(footData);
        }
        RvAddGroupAdapter addGroupAdapter = new RvAddGroupAdapter(context);
        addGroupAdapter.setDatas(data.getStudentlist());
        viewHolder.groupPeopleRv.setAdapter(addGroupAdapter);
        addGroupAdapter.setFootViewClickListener(this,position);
        viewHolder.groupPeopleRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        return convertView;
    }

    @Override
    public void onFootClick(int partentPos) {
        itemFootViewClickListener.onItemFootClick(partentPos);
    }
    public interface ItemFootViewClickListener{
        void onItemFootClick(int pos);
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
