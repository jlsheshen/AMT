package com.edu.accountingteachingmaterial.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.HomepageInformationData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.LoginNetMananger;
import com.edu.accountingteachingmaterial.util.net.SendJsonNetReqManager;
import com.edu.library.util.DoubleClickExitUtil;
import com.lucher.net.req.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.STUDNET_NUMBER;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.STUDNET_PASSWORD;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.URL_NAME;
import static com.edu.subject.BASE_URL.BASE_URL;
import static com.edu.subject.BASE_URL.TEMP_URL;

/**
 * Created by Administrator on 2016/11/11.
 */
public class StartStudyActivity extends BaseActivity {
    ImageView imageView;
    HomepageInformationData data;
    EditText numEt, passwerEt;
    EditText editText;//ip地址的et
    ImageView bgIv;
    TextView settingIpTv;
    AlertDialog alertDialog;
    boolean inpassword = false;
    private Context mContext;
    // 需要上传答题结果的所有数据
    private static LoginNetMananger mSingleton;
    Context context = this;

    @Override
    public int setLayout() {
        return R.layout.activity_startstudy;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        numEt = bindView(R.id.startstudy_num_et);
        numEt.setText(PreferenceHelper.getInstance(this).getStringValue(STUDNET_NUMBER));
        bgIv = bindView(R.id.startstudy_bg_iv);
        passwerEt = bindView(R.id.startstudy_pw_et);
        passwerEt.setText(PreferenceHelper.getInstance(this).getStringValue(STUDNET_PASSWORD));
        passwerEt.setText("123456");

        passwerEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.d("StartStudyActivity", "b:" + b);
                if (b) {
                    bgIv.setImageResource(R.mipmap.login_nolook_gb);
                } else {
                    bgIv.setImageResource(R.mipmap.login_look_bg);
                }
            }
        });

        imageView = bindView(R.id.activity_startstudy_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    login();
                } catch (Exception e) {
                    Toast.makeText(mContext, "登录失败:" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        settingIpTv = bindView(R.id.startstudy_setting_ip_iv);
        settingIpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIpDialog();
            }
        });

        findViewById(R.id.jump_up_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainChoseActivity.class);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DoubleClickExitUtil.doubleClickExit(this);
    }

    /**
     * 登陆操作
     */
    private void login() {
        String num = numEt.getText().toString();
        String pw = passwerEt.getText().toString();
        LoginNetMananger.getSingleton(context).login(num, pw, new LoginNetMananger.loginListener() {
            @Override
            public void onSuccess() {
                Log.d("StartStudyActivity", "登陆操作成功");
                startActivity(MainChoseActivity.class);
            }

            @Override
            public void onFailure(String message) {
                Log.d("StartStudyActivity", "登陆操作失败" + message);
                Toast.makeText(StartStudyActivity.this, "登陆操作失败" + message, Toast.LENGTH_SHORT).show();

            }

        });
    }

    /**
     * 弹出设置ipdialog
     */
    private void showIpDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertDialog = builder.create();
        alertDialog.show();
        final Window window = alertDialog.getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);

        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.dialog_changeip);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        editText = (EditText) window.findViewById(R.id.ip_content_et);

        String oldBaseUrl[] = PreferenceHelper.getInstance(StartStudyActivity.this).getStringValue(PreferenceHelper.URL_NAME).split("http://");
        editText.setText(oldBaseUrl[1]);
        window.findViewById(R.id.ip_save_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = editText.getText().toString();
                if (s.length() < 4) {
                    Toast.makeText(StartStudyActivity.this, "请输入正确IP地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                //  Toast.makeText(MainActivity.this, s + "链接失败", Toast.LENGTH_SHORT).show();
                showIp(s);
            }
        });
        window.findViewById(R.id.ip_close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    /**
     * 测试ip
     *
     * @param s
     */
    private void showIp(String s) {
        TEMP_URL = "http://" + s;
        Log.d("StartStudyActivity", "---------" + NetUrlContstant.getSettingIpUrl());
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getSettingIpUrl());
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    BASE_URL = TEMP_URL;
                    PreferenceHelper.getInstance(StartStudyActivity.this).setStringValue(URL_NAME, BASE_URL);
                    Toast.makeText(StartStudyActivity.this, "Ip设置成功", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("LaunchActivity", "Ip设置失败" + NetUrlContstant.getSettingIpUrl());
                Toast.makeText(StartStudyActivity.this, "Ip设置失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //线程类型
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String date) {
        Log.d("StartStudyActivity", "收到了发来的bus");
        if ("1".equals(date)) {
            Log.d("StartStudyActivity", "走过activity");
            startActivity(MainChoseActivity.class);
        }

    }


    @Override
    public void initData() {

    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        finish();
        super.onDestroy();
    }
}
