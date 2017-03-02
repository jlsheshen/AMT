package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.TaskDetailActivity;

/**
 * Created by Administrator on 2017/2/28.
 */

public class AddGroupAdapter extends BaseAdapter{


    private Context context;

    public AddGroupAdapter(Context context) {
        this.context = context;
    }

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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_addgroup_lv,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.groupNumbereTv.setText("2/2");
        viewHolder.groupTitleTv.setText("学习精英");
        viewHolder.groupPeopleRv.setAdapter(new RvAddGroupAdapter(context));
        viewHolder.groupPeopleRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        viewHolder.stateIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TaskDetailActivity.class));
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView groupTitleTv, groupNumbereTv;//小组名
        ImageView stateIv;//状态
        RecyclerView groupPeopleRv;
        public ViewHolder(View view) {
            groupTitleTv = (TextView) view.findViewById(R.id.item_addgroup_title_tv);
            groupNumbereTv = (TextView) view.findViewById(R.id.item_addgroup_number_tv);
            stateIv = (ImageView) view.findViewById(R.id.item_addgroup_addone_iv);
            groupPeopleRv = (RecyclerView) view.findViewById(R.id.item_addgroup_rv);
        }
    }
}
