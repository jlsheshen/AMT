package com.edu.accountingteachingmaterial.presenterview;

import android.os.Bundle;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.MainActivity;
import com.edu.accountingteachingmaterial.activity.StartStudyActivity;
import com.edu.accountingteachingmaterial.base.BaseMvpActivity;
import com.edu.library.util.DBCopyUtil;
import com.edu.subject.util.SoundPoolUtil;
import com.edu.testbill.Constant;

/**
 * Created by Administrator on 2016/12/28.
 */

public class LaunchActivity extends BaseMvpActivity<LaunchView,LaunchPresenter> implements LaunchView {
    @Override
    public LaunchPresenter initPresenter() {
        return new LaunchPresenter();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_launch;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        presenter.loadData(this);



    }

    @Override
    public void initData() {


    }

    @Override
    public void launchLogin() {

    }

    @Override
    public void loadData() {
        // 检测数据库是否已拷贝
        DBCopyUtil fileCopyUtil = new DBCopyUtil(this);
        fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);
        // 初始化声音播放工具，如果不初始化，盖章没声
        SoundPoolUtil.getInstance().init(this);
    }

    @Override
    public void startActivity() {
        startActivity(StartStudyActivity.class);
        finish();

    }

    @Override
    public void jumpMain() {
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void jumpLogin() {
        startActivity(StartStudyActivity.class);
        finish();
    }
}
