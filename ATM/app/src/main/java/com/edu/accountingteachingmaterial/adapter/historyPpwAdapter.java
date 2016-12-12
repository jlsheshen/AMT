package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.entity.HistoryListData;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class historyPpwAdapter extends BaseAdapter {
    Context context;
    List<HistoryListData> datas;

    public historyPpwAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<HistoryListData> datas) {
        this.datas = datas;
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
        HisViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_history_text,null);
            viewHolder = new HisViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (HisViewHolder) view.getTag();
        }


        return null;
    }
    public class HisViewHolder{
TextView chapterTv,NodeTv,contentTv;
        public HisViewHolder(View view) {
            chapterTv = (TextView) view.findViewById(R.id.item_history_chapter_tv);
            NodeTv = (TextView) view.findViewById(R.id.item_history_node_tv);
            contentTv = (TextView) view.findViewById(R.id.item_history_content_tv);
        }
    }

}
