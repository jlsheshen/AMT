package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.TaskDetailBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.library.imageloader.EduImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 横向recycleView
 * Created by Administrator on 2017/3/1.
 */

public class RvMultiTypeAdapter extends RecyclerView.Adapter<RvMultiTypeAdapter.MultiViewHolder> {
    List<TaskDetailBean.FileListBean> datas;
    Context context;
    int rvModel;
    AccessoryListener accessoryListener;
    int itemPos;

    public RvMultiTypeAdapter(Context context) {
        this.context = context;
    }

    public RvMultiTypeAdapter setAccessoryListener(AccessoryListener accessoryListener) {
        this.accessoryListener = accessoryListener;
        return this;
    }

    public RvMultiTypeAdapter setDatas(List<TaskDetailBean.FileListBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
        return this;
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
    public void onBindViewHolder(MultiViewHolder holder, final int position) {
         final TaskDetailBean.FileListBean data = datas.get(position);

        if (rvModel == ClassContstant.STATE_AFTER){
            ImageLoader.getInstance().displayImage(data.getPic(), holder.bgIv, EduImageLoader.getInstance().getDefaultBuilder().build());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accessoryListener.showAccessoryImage(datas.get(position).getPic());
                }
            });
            holder.cancelIv.setVisibility(View.GONE);
        }else if (rvModel == ClassContstant.STATE_FINSH){
            holder.cancelIv.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(data.getPic(), holder.bgIv, EduImageLoader.getInstance().getDefaultBuilder().build());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accessoryListener.showAccessoryImage(datas.get(position).getPic());
                }
            });

        }else {
        if (data.isFoot()) {
            holder.bgIv.setImageResource(R.mipmap.add_accessory);
            holder.bgIv.setClickable(true);
            holder.bgIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accessoryListener.addAccessoryListener();
                }
            });
            holder.cancelIv.setVisibility(View.GONE);
        } else {
            ImageLoader.getInstance().displayImage(data.getPic(), holder.bgIv, EduImageLoader.getInstance().getDefaultBuilder().build());

            holder.bgIv.setClickable(false);
            holder.cancelIv.setVisibility(View.VISIBLE);
            holder.cancelIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accessoryListener.deteleAccessoryListener(datas.get(position).getId());
                }
            });
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accessoryListener.showAccessoryImage(datas.get(position).getPic());
                }
            });
        }
//                    holder.nameTv.setText();
        }


    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }




    public interface AccessoryListener {
        void addAccessoryListener();
        void deteleAccessoryListener(int fileId);
        void showAccessoryImage(String url);


    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        ImageView bgIv, cancelIv;
        View view;

        public MultiViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameTv = (TextView) itemView.findViewById(R.id.item_accessory_name_tv);
            bgIv = (ImageView) itemView.findViewById(R.id.item_accessory_iv);
            cancelIv = (ImageView) itemView.findViewById(R.id.item_accessory_cancel_iv);


        }
    }
}
