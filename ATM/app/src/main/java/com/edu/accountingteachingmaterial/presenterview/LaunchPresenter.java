package com.edu.accountingteachingmaterial.presenterview;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.edu.accountingteachingmaterial.base.BasePresenter;
import com.edu.accountingteachingmaterial.util.LoginNetMananger;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.library.util.ToastUtil;
import com.edu.subject.BASE_URL;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.KEY_LOGIN_STATE;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.STUDNET_NUMBER;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.STUDNET_PASSWORD;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.URL_NAME;

public class LaunchPresenter extends BasePresenter<LaunchView> {
    CountDownTimer timer;

    public void loadData(final Context context) {
        String s = PreferenceHelper.getInstance(context).getStringValue(URL_NAME);
        Log.d("LaunchActivity", "--------------" + s);
        mView.launchLogin();
        if ("".equals(s)) {
        } else {
            BASE_URL.BASE_URL = s;
        }
        if (PreferenceHelper.getInstance(context).getBooleanValue(KEY_LOGIN_STATE) == true) {
            String num = PreferenceHelper.getInstance(context).getStringValue(STUDNET_NUMBER);
            String pw = PreferenceHelper.getInstance(context).getStringValue(STUDNET_PASSWORD);
            LoginNetMananger.getSingleton(context).login(num, pw, new LoginNetMananger.loginListener() {
                @Override
                public void onSuccess() {
                    mView.jumpMain();
                }
                @Override
                public void onFailure(String message) {
                    ToastUtil.showToast(context,"登录出错" + message);
                    if (mView != null){
                    mView.jumpLogin();}else {


                    }
                }
            });
//            login();
        }
        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mView.loadData();
            }

            @Override
            public void onFinish() {
                if (mView !=null){
                mView.startActivity();
                }
            }
        }.start();

    }


}
