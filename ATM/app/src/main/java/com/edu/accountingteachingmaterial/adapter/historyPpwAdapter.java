package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.entity.HistoryListData;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class HistoryPpwAdapter extends BaseAdapter {
    Context context;
    List<HistoryListData> datas;
    private String lesson_title;

    public HistoryPpwAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<HistoryListData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
    public HistoryListData getData(int i) {
        return datas.get(i);
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
        HisViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_history_text, null);
            viewHolder = new HisViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (HisViewHolder) view.getTag();
        }
        if (null == datas.get(i)) {
//            viewHolder.chapterTv.setVisibility(View.GONE);
            viewHolder.NodeTv.setVisibility(View.GONE);
            viewHolder.contentTv.setVisibility(View.GONE);
            viewHolder.dayTv.setVisibility(View.VISIBLE);


            switch (datas.get(i + 1).getDate_diff()) {
                case ClassContstant.HISTORY_TODAY:
                    viewHolder.dayTv.setText("今天");
                    break;
                case ClassContstant.HISTORY_YESTODAY:
                    viewHolder.dayTv.setText("昨天");
                    break;
                default:
                    viewHolder.dayTv.setText("更早");
                    break;
            }
        } else {
//            viewHolder.chapterTv.setVisibility(View.VISIBLE);
            viewHolder.NodeTv.setVisibility(View.VISIBLE);
            viewHolder.contentTv.setVisibility(View.VISIBLE);
            viewHolder.dayTv.setVisibility(View.GONE);
//            viewHolder.chapterTv.setText(datas.get(i).getChapter_title());
            viewHolder.NodeTv.setText(datas.get(i).getSection_title());
            viewHolder.contentTv.setText(datas.get(i).getLesson_title());
        }
        return view;
    }

    public class HisViewHolder {
        TextView  NodeTv, contentTv, dayTv;

        public HisViewHolder(View view) {
//            chapterTv = (TextView) view.findViewById(R.id.item_history_chapter_tv);
            NodeTv = (TextView) view.findViewById(R.id.item_history_node_tv);
            contentTv = (TextView) view.findViewById(R.id.item_history_content_tv);
            dayTv = (TextView) view.findViewById(R.id.item_history_day_tv);
        }
    }

}
