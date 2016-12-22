package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.ReviewHisAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ReviewHistoryActivity extends BaseActivity {
    ListView listView;
    ReviewHisAdapter reviewHisAdapter;
    TextView cancelTv;
    ImageView backIv;

    @Override
    public int setLayout() {
        return R.layout.activity_review_history;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        listView = bindView(R.id.review_his_lv);
        cancelTv = bindView(R.id.review_his_cancel_tv);
        backIv = bindView(R.id.review_his_back_iv);
    }

    @Override
    public void initData() {

        
    }
}
