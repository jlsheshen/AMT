package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.GroupsListBean;
import com.edu.accountingteachingmaterial.view.CircleImageView;
import com.edu.library.imageloader.EduImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 小组成员rv的adapter
 * Created by Administrator on 2017/2/28.
 */

public class RvAddGroupAdapter extends RecyclerView.Adapter<RvAddGroupAdapter.GroupViewHolder> implements View.OnClickListener {
    private Context context;
    private List<GroupsListBean.StudentlistBean> datas;
    FootViewClickListener footViewClickListener;
    int partentPos;
    public RvAddGroupAdapter(Context context) {
        this.context = context;
    }

    public RvAddGroupAdapter setDatas(List<GroupsListBean.StudentlistBean> datas) {
        this.datas = datas;
        return this;
    }

    public RvAddGroupAdapter setFootViewClickListener(FootViewClickListener footViewClickListener,int partentPos) {
        this.footViewClickListener = footViewClickListener;
        this.partentPos = partentPos;
        return this;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.item_addgroup_rv,parent,false));

    }
    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        GroupsListBean.StudentlistBean  data = datas.get(position);
        if (data.isFootView() == true){
            holder.headIv.setImageResource(R.mipmap.add_one);
            holder.headIv.setOnClickListener(this);
        }else {
            ImageLoader.getInstance().displayImage(data.getPicture(), holder.headIv, EduImageLoader.getInstance().getDefaultBuilder().build()) ;
            holder.nameTv.setText(data.getName());
        }

    }

    @Override
    public int getItemCount() {
        return datas == null?0:datas.size();
    }

    @Override
    public void onClick(View v) {
            footViewClickListener.onFootClick(partentPos);
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        CircleImageView headIv;
        public GroupViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_groups_name_tv);
            headIv = (CircleImageView) itemView.findViewById(R.id.item_groups_head_iv);
        }
    }
    public interface FootViewClickListener{
        void onFootClick(int partentPos);
    }
}
