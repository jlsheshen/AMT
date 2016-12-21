package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.ReviewHisListBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ReviewHisAdapter extends BaseAdapter {
    Context context;
    List<ReviewHisListBean> datas;

    public ReviewHisAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<ReviewHisListBean> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return 0;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_review_his,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ReviewHisListBean reviewHisListBean = datas.get(position);
        viewHolder.numberTv.setText(reviewHisListBean.getNumber());
        viewHolder.titleTv.setText(reviewHisListBean.getTitle());
        viewHolder.scoreTv.setText(reviewHisListBean.getScore());
        viewHolder.timeTv.setText(reviewHisListBean.getDate());
        if (reviewHisListBean.getState() == ClassContstant.EXAM_UNDONE){
            viewHolder.stateIv.setImageResource(R.mipmap.weiwancheng);
        }else {
            viewHolder.stateIv.setImageResource(R.mipmap.wancheng);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView stateIv;
        TextView titleTv,numberTv,timeTv,scoreTv;
        public ViewHolder(View view) {
            stateIv = (ImageView) view.findViewById(R.id.item_review_his_state_iv);
            titleTv = (TextView) view.findViewById(R.id.item_review_his_title);
            numberTv = (TextView) view.findViewById(R.id.item_review_his_number_tv);
            timeTv = (TextView) view.findViewById(R.id.item_review_his_data_tv);
            scoreTv = (TextView) view.findViewById(R.id.item_review_his_score_tv);
        }
    }
}
