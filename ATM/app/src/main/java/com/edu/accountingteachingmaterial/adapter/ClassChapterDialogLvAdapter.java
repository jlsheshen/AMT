package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.entity.SubChaptersBean;

import java.util.List;

public class ClassChapterDialogLvAdapter extends BaseAdapter {
    List<SubChaptersBean> datas;
    Context context;

    public ClassChapterDialogLvAdapter(Context context) {
        super();
        this.context = context;
    }

    public void setDatas(List<SubChaptersBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
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
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_classchapter_exlv, parent, false);
        TextView titlyTv = (TextView) convertView.findViewById(R.id.item_classchapter_tv);
        titlyTv.setText(datas.get(position).getTitle());
        return convertView;
    }

//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//
//        convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_classchapter_exlv, parent, false);
//        TextView titlyTv = (TextView) convertView.findViewById(R.id.item_classchapter_tv);
//        titlyTv.setText(datas.get(groupPosition).getTitle());
//       TextView titleNum = (TextView) convertView.findViewById(R.id.item_classchapter_num_tv);
//       titleNum.setText("第" + (groupPosition + 1) + "章");
//        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_classchapter_iv);
//        //箭头随着子列的展开而变化
//        if (isExpanded) {
//            imageView.setSelected(true);
//        } else {
//            imageView.setSelected(false);
//        }
//        return convertView;
//    }

//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_classnode_exlv, parent, false);
//        TextView textView = (TextView) convertView.findViewById(R.id.item_classnode_tv);
//        textView.setText(datas.get(groupPosition).getSubChapters().get(childPosition).getTitle());
//        TextView num = (TextView) convertView.findViewById(R.id.item_classnode_num_tv);
//        num.setText("第" + (childPosition + 1) + "节");
//        return convertView;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        // TODO Auto-generated method stub
//        return true;
//    }


}
