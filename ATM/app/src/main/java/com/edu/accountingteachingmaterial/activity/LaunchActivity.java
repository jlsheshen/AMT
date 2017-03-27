package com.edu.accountingteachingmaterial.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseMvpActivity;
import com.edu.accountingteachingmaterial.presenterview.LaunchPresenter;
import com.edu.accountingteachingmaterial.presenterview.LaunchView;
import com.edu.library.util.DBCopyUtil;
import com.edu.library.util.DoubleClickExitUtil;
import com.edu.subject.util.SoundPoolUtil;
import com.edu.testbill.Constant;


/**
 * Created by Administrator on 2016/12/28.
 */

public class LaunchActivity extends BaseMvpActivity<LaunchView, LaunchPresenter> implements LaunchView {
    boolean isSuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

    }

    @Override
    public LaunchPresenter initPresenter() {
        return new LaunchPresenter();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void initData() {
        TextView textView = (TextView) findViewById(R.id.version_name_tv);
        try {
            textView.setText(getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        presenter.loadData(this);
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
        if (!isSuccess){
        startActivity(StartStudyActivity.class);
        }else {
            startActivity(MainChoseActivity.class);
        }
        finish();
    }

    @Override
    public void jumpMain() {
        isSuccess = true;
    }

    @Override
    public void jumpLogin() {
        isSuccess = false;

    }
    @Override
    public void onBackPressed() {
        DoubleClickExitUtil.doubleClickExit(this);
    }

    @Override
    public String getVersionName() throws Exception {
            // 获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
            String versionName = packInfo.versionName;
            return versionName;
        }
    }



