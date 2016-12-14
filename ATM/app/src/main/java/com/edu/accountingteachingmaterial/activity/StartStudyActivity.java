package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.entity.HomepageInformationData;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.COURSE_ID;

/**
 * Created by Administrator on 2016/11/11.
 */
public class StartStudyActivity extends BaseActivity {
    ImageView imageView;
    HomepageInformationData data;
    @Override
    public int setLayout() {
        return R.layout.activity_startstudy;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);




        imageView = bindView(R.id.activity_startstudy_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceHelper.getInstance(StartStudyActivity.this).setIntValue(COURSE_ID,data.getCourse_id());
                startActivity(MainActivity.class);
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        data = (HomepageInformationData) bundle.getSerializable("HomepageInformationData");
        if (data != null){
            findViewById(R.id.startstudy_aty_pb).setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
        findViewById(R.id.jump_up_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
                finish();

            }
        });
    }
    //线程类型
    @Subscribe(threadMode= ThreadMode.MAIN)
    public void getData(HomepageInformationData date){
        findViewById(R.id.startstudy_aty_pb).setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        data = date;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }
}
