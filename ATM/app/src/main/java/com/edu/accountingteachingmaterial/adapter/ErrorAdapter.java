package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseCheckAdapter;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.subject.data.BaseTestData;

import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ErrorAdapter extends BaseCheckAdapter {

    List<BaseTestData> datas;
    ReviewHisAdapter.OnCheckedListener checkedListener;

    public ErrorAdapter(Context context) {
        super(context);
    }
    public void setDatas(List<BaseTestData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
    public void setChecked(ReviewHisAdapter.OnCheckedListener checked) {
        this.checkedListener = checked;
    }
    @Override
    public int getCount() {
        return datas == null?0:datas.size();
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
        ErrorViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_error_lv,parent,false);
            holder = new ErrorViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ErrorViewHolder) convertView.getTag();
        }
        String s = null;
        String content = datas.get(position).getSubjectData().getQuestion().getText();

        switch (datas.get(position).getSubjectData().getSubjectType()){
            case ClassContstant.SUBJECT_SINGLE_CHOSE:
                s = ClassContstant.SUBJECT_SINGLE_CHOSE_STRING;
                break;
            case ClassContstant.SUBJECT_MULITI_CHOSE:
                s = ClassContstant.SUBJECT_MULITI_CHOSE_STRING;
                break;
            case ClassContstant.SUBJECT_JUDGE:
                s = ClassContstant.SUBJECT_JUDGE_STRING;
                break;
            case ClassContstant.SUBJECT_BLANK:
                content = content.replace("[blank]","___");
                s = ClassContstant.SUBJECT_BLANK_STRING;
                break;
            case ClassContstant.SUBJECT_ENTRY:
                s = ClassContstant.SUBJECT_ENTRY_STRING;
                break;
            case ClassContstant.SUBJECT_BILL:
                s = ClassContstant.SUBJECT_BILL_STRING;
                break;
            case ClassContstant.SUBJECT_SIMPLE_ANSWER:
                s = ClassContstant.SUBJECT_SIMPLE_ANSWER_STRING;
                break;
            case ClassContstant.SUBJECT_COMPREHENSIVE:
                s = ClassContstant.SUBJECT_COMPREHENSIVE_STRING;
                break;
            case ClassContstant.SUBJECT_GROUP_BILL:
                s = ClassContstant.SUBJECT_GROUP_BILL_STRING;
                break;
        }
        holder.typeTv.setText(s);
        holder.contentTv.setText(content);

        if (checkIsShow){
            holder.checkBox.setVisibility(View.VISIBLE);

        }else {
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        if (checkList != null&&checkList.size()>0){
            holder.checkBox.setChecked((Boolean) checkList.get(position));
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkList.set(position,isChecked);
                    checkedListener.onCheckeBoxChecked();
                }
            }
            );}else {

        }
        return convertView;
    }

    class ErrorViewHolder{
    CheckBox checkBox;
        TextView typeTv,contentTv;
        public ErrorViewHolder(View view) {
            checkBox = (CheckBox) view.findViewById(R.id.item_my_error_cb);
            typeTv = (TextView) view.findViewById(R.id.item_my_error_type_tv);
            contentTv = (TextView) view.findViewById(R.id.item_my_error_content_tv);

        }
    }
}
