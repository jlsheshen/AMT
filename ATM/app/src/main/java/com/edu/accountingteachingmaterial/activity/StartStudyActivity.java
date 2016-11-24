package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;

/**
 * Created by Administrator on 2016/11/11.
 */
public class StartStudyActivity extends BaseActivity {
    ImageView imageView;
    @Override
    public int setLayout() {
        return R.layout.activity_startstudy;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
         imageView = bindView(R.id.activity_startstudy_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
            }
        });

    }

    @Override
    public void initData() {

    }
}
