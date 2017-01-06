package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseCheckAdapter;
import com.edu.accountingteachingmaterial.bean.ReviewHisListBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ReviewHisAdapter extends BaseCheckAdapter {
    List<ReviewHisListBean> datas;
//    HashMap<Integer ,Boolean> isCheck;

    public ReviewHisAdapter(Context context) {
        super(context);
    }


    public void setChecked(OnCheckedListener checked) {
        this.checkedListener = checked;
    }




    @Override
    public int getCount() {
        return datas==null?0:datas.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        viewHolder.timeTv.setText(reviewHisListBean.getDate());
        if (reviewHisListBean.getState() == ClassContstant.EXAM_UNDONE){
            viewHolder.stateIv.setImageResource(R.mipmap.weiwancheng);
            viewHolder.scoreTv.setVisibility(View.GONE);
        }else {
            viewHolder.stateIv.setImageResource(R.mipmap.wancheng);
            viewHolder.scoreTv.setVisibility(View.VISIBLE);
            viewHolder.scoreTv.setText(reviewHisListBean.getScore());
        }
        if (checkIsShow){
            viewHolder.check.setVisibility(View.VISIBLE);

        }else {
            viewHolder.check.setVisibility(View.INVISIBLE);
        }
        if (checkList != null&&checkList.size()>0){
        viewHolder.check.setChecked(checkList.get(position));
        viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkList.set(position,isChecked);
                checkedListener.onCheckeBoxChecked();

            }
        });}else {

        }

        return convertView;
    }
    public void setDatas(List<ReviewHisListBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    public class ViewHolder{
       public ImageView stateIv;
        TextView titleTv,numberTv,timeTv,scoreTv;
        public CheckBox check;
        public ViewHolder(View view) {
            stateIv = (ImageView) view.findViewById(R.id.item_review_his_state_iv);
            titleTv = (TextView) view.findViewById(R.id.item_review_his_title);
            numberTv = (TextView) view.findViewById(R.id.item_review_his_number_tv);
            timeTv = (TextView) view.findViewById(R.id.item_review_his_data_tv);
            scoreTv = (TextView) view.findViewById(R.id.item_review_his_score_tv);
            check = (CheckBox) view.findViewById(R.id.item_review_his_cb);
        }
    }
    public interface OnCheckedListener {
        void onCheckeBoxChecked();
    }
}
