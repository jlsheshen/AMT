package com.edu.accountingteachingmaterial.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.TextbookBean;
import com.edu.library.imageloader.EduImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class TextBookGvAdapter extends BaseAdapter {

    //数据集合
    List<TextbookBean> datas;
    ViewHolder viewHolder = null;

    public void setData(List<TextbookBean> datas) {
        this.datas = datas;
    }
    public TextbookBean getData(int pos){
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

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textbook_gv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TextbookBean data = datas.get(position);
        viewHolder.introTv.setText(data.getSummary());
        viewHolder.publisherTv.setText(data.getName());
        viewHolder.organizationTv.setText(data.getSchool_name());
        viewHolder.nameTv.setText(data.getTitle());

        ImageLoader.getInstance().displayImage(data.getPicture(), viewHolder.bgIv,
                EduImageLoader.getInstance().getDefaultBuilder().build(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        viewHolder.bgIv.setImageResource(R.mipmap.ic_launcher);
                    }
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    }
                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                    }
                });
        return convertView;
    }

    class ViewHolder {
        ImageView bgIv;
        TextView nameTv, organizationTv, publisherTv, introTv;

        public ViewHolder(View view) {
            bgIv = (ImageView) view.findViewById(R.id.item_textbook_bg_iv);
            nameTv = (TextView) view.findViewById(R.id.item_textbook_name_tv);
            organizationTv = (TextView) view.findViewById(R.id.item_textbook_organization_tv);
            publisherTv = (TextView) view.findViewById(R.id.item_textbook_publisher_tv);
            introTv = (TextView) view.findViewById(R.id.item_textbook_intro_tv);


        }
    }
}
