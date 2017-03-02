package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.edu.accountingteachingmaterial.R;
import com.edu.library.data.BaseData;

import java.util.List;

/**
 * 分组列表adapter
 * Created by Administrator on 2017/2/28.
 */

public class GroupTaskAdapter extends BaseAdapter {

    private Context context;

    private List<BaseData> datas;

    public GroupTaskAdapter(Context context) {
        this.context = context;
    }
    public  BaseData getItemData(int pos){

        return datas.get(pos);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grouptask_lv,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.authorTv.setText("赵四");
        viewHolder.groupTv.setText("3/2组");
        viewHolder.timeTv.setText("2024");
        viewHolder.titleTv.setText("探索");

        return convertView;
    }
    class ViewHolder {
        TextView titleTv, timeTv, authorTv, groupTv;//标题,时间,发布任务人,小组
        ImageView imageView;

        public ViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_task_title_tv);
            timeTv = (TextView) view.findViewById(R.id.item_task_time_tv);
            authorTv = (TextView) view.findViewById(R.id.item_task_author_tv);
            groupTv = (TextView) view.findViewById(R.id.item_task_group_tv);
            imageView = (ImageView) view.findViewById(R.id.item_task_title_iv);
        }
    }

}
