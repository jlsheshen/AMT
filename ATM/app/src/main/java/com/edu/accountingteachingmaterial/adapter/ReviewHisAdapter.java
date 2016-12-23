package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.ReviewHisListBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ReviewHisAdapter extends BaseAdapter {
    Context context;
    List<ReviewHisListBean> datas;
    boolean checkIsShow = false;
//    HashMap<Integer ,Boolean> isCheck;
    List<Boolean> checkList;
    OnCheckedListener checkedListener;

    public void setChecked(OnCheckedListener checked) {
        this.checkedListener = checked;
    }

    public ReviewHisAdapter(Context context) {
        this.context = context;
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
        viewHolder.scoreTv.setText(reviewHisListBean.getScore());
        viewHolder.timeTv.setText(reviewHisListBean.getDate());
        if (reviewHisListBean.getState() == ClassContstant.EXAM_UNDONE){
            viewHolder.stateIv.setImageResource(R.mipmap.weiwancheng);
        }else {
            viewHolder.stateIv.setImageResource(R.mipmap.wancheng);
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
    // 初始化isSelected的数据
    private void checkRest(){
        for(int i=0; i<datas.size();i++) {
            getIsChecked().add(i,false);
        }
    }

    public void setClickShow() {
        if (checkList==null){
            checkList  = new ArrayList<>();
            checkRest();
        }
            checkIsShow = true;

        notifyDataSetChanged();
    }
    public void setClickConceal() {
        checkIsShow = false;
        checkRest();
        notifyDataSetChanged();
    }
    public  List<Boolean> getIsChecked() {
        return checkList;
    }
//    public  HashMap<Integer,Boolean> getIsChecked() {
//        return isCheck;
//    }

    public  void setIsChecked(int i,boolean b) {
        this.checkList.set(i,b);
    }

    public void setAllchecked() {
        for(int i=0; i<datas.size();i++) {
            getIsChecked().set(i,true);

        }
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
