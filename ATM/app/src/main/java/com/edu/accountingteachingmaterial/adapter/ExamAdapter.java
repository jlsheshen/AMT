package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.entity.OnLineExamListData;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ExamAdapter extends BaseAdapter {
    private Context context;
    private List<OnLineExamListData> datas;
    private boolean isExercise;

    public ExamAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<OnLineExamListData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public ExamAdapter setExercise(boolean exercise) {
        isExercise = exercise;
        return this;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_exam_lv, viewGroup, false);
            AutoUtils.autoSize(view);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        OnLineExamListData examBean = datas.get(i);
        setContent(viewHolder.titleTv, examBean.getExam_name());
        setContent(viewHolder.timeTv, String.valueOf(examBean.getLast_time()));
        setContent(viewHolder.publisherTv, examBean.getCreator_name());
        setContent(viewHolder.itemNumTv, examBean.getTopic_count() + "道");
        if (isExercise){
            viewHolder.startTimeTv.setVisibility(View.GONE);
            viewHolder.durationTv.setVisibility(View.GONE);
        }else {
            setContent(viewHolder.startTimeTv, String.valueOf(examBean.getStart_time()));
            setContent(viewHolder.durationTv, String.valueOf(examBean.getLast_time()) + "分钟");
        }

        switch (examBean.getState()) {
            case ClassContstant.EXAM_COMMIT:
                viewHolder.imageView.setImageResource(R.mipmap.btn_yituijiao_n);
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.imageView.setVisibility(View.VISIBLE);

                break;
            case ClassContstant.EXAM_DOWNLOADING:
                viewHolder.imageView.setVisibility(View.GONE);
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                break;
            case ClassContstant.EXAM_NOT:
                viewHolder.imageView.setImageResource(R.drawable.selector_exam_download_type);
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.imageView.setVisibility(View.VISIBLE);
                break;
            case ClassContstant.EXAM_READ:
                viewHolder.imageView.setImageResource(R.mipmap.btn_yipiyue_n);
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.imageView.setVisibility(View.VISIBLE);

                break;
            case ClassContstant.EXAM_UNDONE:
                viewHolder.imageView.setImageResource(R.mipmap.btn_weitijiao_n);
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.imageView.setVisibility(View.VISIBLE);

                break;
        }
        return view;
    }

    void setContent(TextView view, String s) {
        view.setText(s);
    }

    class ViewHolder {
        TextView titleTv, timeTv, publisherTv, itemNumTv, startTimeTv, durationTv;
        ImageView imageView;
        ProgressBar progressBar;

        public ViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_exam_title_tv);
            timeTv = (TextView) view.findViewById(R.id.item_exam_time_tv);
            publisherTv = (TextView) view.findViewById(R.id.item_exam_publisher_tv);
            itemNumTv = (TextView) view.findViewById(R.id.item_exam_itemumber_tv);
            startTimeTv = (TextView) view.findViewById(R.id.item_exam_starttime_tv);
            durationTv = (TextView) view.findViewById(R.id.item_exam_duration_tv);
            imageView = (ImageView) view.findViewById(R.id.item_exam_type_iv);
            progressBar = (ProgressBar) view.findViewById(R.id.item_exam_type_pb);
        }
    }
}
