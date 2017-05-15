package com.edu.accountingteachingmaterial.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.ClassBean;
import com.edu.library.imageloader.EduImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ClassGvAdapter extends BaseAdapter{
    //数据集合
    List<ClassBean> datas;
    TextBookGvAdapter.ViewHolder viewHolder = null;

    public ClassGvAdapter setDatas(List<ClassBean> datas) {
        this.datas = datas;
        return this;
    }
    public ClassBean etDtaa(int pos){
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_gv, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ClassBean data = datas.get(position);
        viewHolder.nameTv.setText(data.getTitle());
        Log.d("ClassGvAdapter", data.getPicture() + "反斜杠是" + "\\");
        String s= data.getPicture().replace("\\","/");
//        data.setPicture(data.getPicture().replace("\\","/"));
        Log.d("ClassGvAdapter", "替换后 "+s);
//        data.getPicture().replaceAll("\\","  ");
        ImageLoader.getInstance().displayImage(s, viewHolder.bgIv, EduImageLoader.getInstance().getDefaultBuilder().build());
        viewHolder.authorTv.setText(data.getName());
        return convertView;
    }

    public ClassBean getData(int position) {
        return datas.get(position);
    }

    class ViewHolder {
        TextView nameTv,authorTv;
        ImageView  bgIv;

        public ViewHolder(View view) {
            super();
            nameTv = (TextView) view.findViewById(R.id.item_class_name_iv);
            authorTv = (TextView) view.findViewById(R.id.item_class_author_tv);
            bgIv = (ImageView) view.findViewById(R.id.item_class_bg_iv);
        }
    }
}
