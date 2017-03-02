package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.view.CircleImageView;

/**
 * 小组成员rv的adapter
 * Created by Administrator on 2017/2/28.
 */

public class RvAddGroupAdapter extends RecyclerView.Adapter<RvAddGroupAdapter.GroupViewHolder> {
    private Context context;

    public RvAddGroupAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.item_addgroup_rv,parent,false));

    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.headIv.setImageResource(R.mipmap.ic_launcher);
        holder.nameTv.setText("asd");
    }

    @Override
    public int getItemCount() {
        return 3;
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
}
