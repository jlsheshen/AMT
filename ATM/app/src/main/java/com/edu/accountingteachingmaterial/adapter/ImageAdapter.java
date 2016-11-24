package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ImageAdapter extends RecyclerView.Adapter{
    Context context;




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        viewHolder = new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_rv,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_image_name_tv);
            imageView = (ImageView) imageView.findViewById(R.id.item_image_iv);
        }
    }
}
