package com.edu.accountingteachingmaterial.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.HistoryAdapter;
import com.edu.accountingteachingmaterial.entity.HistoryData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */

public class HistoryListView {
    private List<HistoryData> historyData;
    private Context mContext;
    private View mView;
    private List<HistoryAdapter> historyAdapters;
    private String[] strStem;

    public HistoryListView(Context context, List<HistoryData> historyData) {
        this.mContext = context;
        this.historyData = historyData;
        historyAdapters = new ArrayList<HistoryAdapter>();
        strStem = mContext.getResources().getStringArray(R.array.data);
    }

    public View init() {
        mView = View.inflate(mContext, R.layout.view_history_list, null);
        LinearLayout llyout = (LinearLayout) mView.findViewById(R.id.llyout_card);
        List<HistoryData> dataClass = new ArrayList<HistoryData>();
        for (int i = 1; i < strStem.length; i++) {// 1到5为题目类型
            View view = View.inflate(mContext, R.layout.llyout_history_item, null);
            llyout.addView(view);
            TextView tvTilte = (TextView) view.findViewById(R.id.tv_itcard_child_title);
            if (i == 1) {
                for (int j = 0; j < historyData.size(); j++) {
                    if (isToday(historyData.get(j).getData())) {
                        dataClass.add(historyData.get(j));
                        tvTilte.setText(strStem[i]);
                    }

                }
            } else if (i == 2) {
                for (int j = 0; j < historyData.size(); j++) {
                    if (!isToday(historyData.get(j).getData())) {
                        dataClass.add(historyData.get(j));
                        tvTilte.setText(strStem[i]);
                    }

                }
                if (dataClass.size() == 0) {
                    // view = null;
                    llyout.removeView(view);// 移除对应的view，否则布局混乱
                    continue;
                }
            }


            ListView listView = (ListView) view.findViewById(R.id.list_child_choice);
            HistoryAdapter historyAdapter = new HistoryAdapter(mContext, dataClass);
            listView.setAdapter(historyAdapter);
            historyAdapters.add(historyAdapter);
        }
        return mView;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */

    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater.get().format(today);
            String timeDate = dateFormater.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}