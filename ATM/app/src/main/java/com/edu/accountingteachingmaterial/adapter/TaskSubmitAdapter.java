package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.TaskDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 */

public class TaskSubmitAdapter extends RecyclerView.Adapter<TaskSubmitAdapter.SubmitViewHolder> {

    List<TaskDetailBean.HistoryBean> datas;
    Context context;

    public TaskSubmitAdapter(Context context) {
        this.context = context;
    }

    public TaskSubmitAdapter setDatas(List<TaskDetailBean.HistoryBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
        return this;
    }

    @Override
    public SubmitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SubmitViewHolder(LayoutInflater.from(context).inflate(R.layout.item_dialog_task_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(SubmitViewHolder holder, int position) {

        holder.nameTv.setText(datas.get(position).getName());
        holder.dateTv.setText(datas.get(position).getCreate_date());
        ColorDrawable drawable = new ColorDrawable();

        if (position % 2 == 0) {
            drawable.setColor(context.getResources().getColor(R.color.colorBlueContent));
            holder.bgView.setBackground(drawable);
        } else {
            drawable.setColor(context.getResources().getColor(R.color.white));
            holder.bgView.setBackground(drawable);

        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class SubmitViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, dateTv;
        View bgView;

        public SubmitViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_task_submit_name_tv);
            dateTv = (TextView) itemView.findViewById(R.id.item_task_submit_date_tv);
            bgView = itemView;

        }
    }
}
