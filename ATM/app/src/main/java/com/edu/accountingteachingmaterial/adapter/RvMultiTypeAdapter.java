package com.edu.accountingteachingmaterial.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.library.data.BaseData;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class RvMultiTypeAdapter extends RecyclerView.Adapter<RvMultiTypeAdapter.MultiViewHolder> {
    List<BaseData> datas;
    int rvModel;
    final static int LAST_ITEM = 1;


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return LAST_ITEM;
        return super.getItemViewType(position);
    }

    public RvMultiTypeAdapter setRvModel(int rvModel) {
        this.rvModel = rvModel;
        return this;
    }

    @Override
    public MultiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accessory_rv, parent, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MultiViewHolder holder, int position) {
        switch (rvModel) {
            case ClassContstant.STATE_RUNING:
                int type = getItemViewType(position);
                if (type == LAST_ITEM) {
                    holder.bgIv.setImageResource(R.mipmap.ic_launcher);
                } else {
                    holder.bgIv.setImageResource(R.mipmap.ic_launcher);
                    holder.cancelIv.setVisibility(View.VISIBLE);
                    holder.nameTv.setVisibility(View.VISIBLE);
                    holder.nameTv.setText("图片一号");
                }
                break;
            case ClassContstant.STATE_AFTER:
                holder.bgIv.setImageResource(R.mipmap.ic_launcher);
                break;
            case ClassContstant.STATE_FINSH:
                holder.bgIv.setImageResource(R.mipmap.ic_launcher);
                break;

        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        ImageView bgIv, cancelIv;

        public MultiViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_accessory_name_tv);
            bgIv = (ImageView) itemView.findViewById(R.id.item_accessory_iv);
            cancelIv = (ImageView) itemView.findViewById(R.id.item_accessory_cancel_iv);


        }
    }
}
