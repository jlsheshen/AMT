package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.GroupTaskListBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;

import java.util.List;

/**
 * 分组列表adapter
 * Created by Administrator on 2017/2/28.
 */

public class GroupTaskAdapter extends BaseAdapter {

    private Context context;

    private List<GroupTaskListBean.ListBean> datas;

    public GroupTaskAdapter(Context context) {
        this.context = context;
    }

    public GroupTaskAdapter setDatas(List<GroupTaskListBean.ListBean> datas) {
        this.datas = datas;
        return this;
    }

    public GroupTaskListBean.ListBean getItemData(int pos) {

        return datas.get(pos);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grouptask_lv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GroupTaskListBean.ListBean data = datas.get(position);
        viewHolder.authorTv.setText(data.getName());
        viewHolder.groupTv.setText(data.getStudent_attend() + "/" + data.getGroup_sum() + "组");
        viewHolder.timeTv.setText(data.getCreate_date());
        viewHolder.titleTv.setText(data.getTask_name());
        switch (data.getTask_status()) {
            case ClassContstant.STATE_RUNING:
                viewHolder.stateIv.setImageResource(R.mipmap.task_tast_runing);
                break;
            case ClassContstant.STATE_FINSH:
                viewHolder.stateIv.setImageResource(R.mipmap.task_tast_finsh);
                break;
            case ClassContstant.STATE_AFTER:
                viewHolder.stateIv.setImageResource(R.mipmap.task_tast_after);
                break;

        }

        return convertView;
    }

    class ViewHolder {
        TextView titleTv, timeTv, authorTv, groupTv;//标题,时间,发布任务人,小组
        ImageView imageView, stateIv;

        public ViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_task_title_tv);
            timeTv = (TextView) view.findViewById(R.id.item_task_time_tv);
            authorTv = (TextView) view.findViewById(R.id.item_task_author_tv);
            groupTv = (TextView) view.findViewById(R.id.item_task_group_tv);
            imageView = (ImageView) view.findViewById(R.id.item_task_title_iv);
            stateIv = (ImageView) view.findViewById(R.id.item_groups_state);

        }
    }

}
