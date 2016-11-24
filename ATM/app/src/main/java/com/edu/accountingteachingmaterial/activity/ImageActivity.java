package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ImageActivity extends BaseActivity {
    RecyclerView recyclerView;
    @Override
    public int setLayout() {
        return R.layout.activity_image;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        recyclerView = bindView(R.id.activity_image_rv);

    }

    @Override
    public void initData() {

    }

}
