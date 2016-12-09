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
import com.edu.accountingteachingmaterial.bean.ExamBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ExamAdapter extends BaseAdapter {
    private Context context;
    private List<ExamBean> datas;

    public ExamAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<ExamBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null?0:datas.size();
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
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_exam_lv,viewGroup,false);
            AutoUtils.autoSize(view);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ExamBean examBean = datas.get(i);
       setContent(viewHolder.titleTv,examBean.getTitle());
        setContent(viewHolder.timeTv,examBean.getTime());
        setContent(viewHolder.publisherTv,examBean.getPublisher());
        setContent(viewHolder.itemNumTv, examBean.getItemNumber() + "道");
        setContent(viewHolder.startTimeTv,examBean.getStartTime());
        setContent(viewHolder.durationTv, examBean.getDuration() + "分钟");
        switch (examBean.getExmaStatus()){
            case ClassContstant.EXAM_COMMIT:
                viewHolder.imageView.setImageResource(R.mipmap.btn_yituijiao_n);

            break;
            case ClassContstant.EXAM_DOWNLOADING:
                viewHolder.imageView.setVisibility(View.GONE);
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                break;
            case ClassContstant.EXAM_NOT:
                viewHolder.imageView.setImageResource(R.drawable.selector_exam_download_type);
                break;
            case ClassContstant.EXAM_READ:
                viewHolder.imageView.setImageResource(R.mipmap.btn_yipiyue_n);

                break;
            case ClassContstant.EXAM_UNDONE:
                viewHolder.imageView.setImageResource(R.mipmap.btn_weitijiao_n);

                break;
        }
        return view;
    }
    void setContent(TextView view,String s){
        view.setText(s);
    }

    class ViewHolder{
        TextView titleTv,timeTv,publisherTv,itemNumTv,startTimeTv,durationTv;
        ImageView imageView;
        ProgressBar progressBar;
        public ViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_exam_title_tv);
            timeTv = (TextView) view.findViewById(R.id.item_exam_time_tv);
            publisherTv = (TextView) view.findViewById(R.id.item_exam_publisher_tv);
            itemNumTv = (TextView) view.findViewById(R.id.item_exam_itemumber_tv);
            startTimeTv = (TextView) view.findViewById(R.id.item_exam_starttime_tv);
            durationTv = (TextView) view.findViewById(R.id.item_exam_duration_tv);
            imageView  = (ImageView) view.findViewById(R.id.item_exam_type_iv);
            progressBar  = (ProgressBar) view.findViewById(R.id.item_exam_type_pb);
        }
    }
}
