package com.edu.accountingteachingmaterial.activity;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.entity.HomepageInformationData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.lucher.net.req.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.edu.NetUrlContstant.BASE_URL;

/**
 * Created by Administrator on 2016/11/11.
 */
public class StartStudyActivity extends BaseActivity {
    ImageView imageView;
    HomepageInformationData data;
    EditText numEt, passwerEt;
    ImageView bgIv;
    TextView settingIpTv;
    boolean inpassword = false;

    @Override
    public int setLayout() {
        return R.layout.activity_startstudy;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
        numEt = bindView(R.id.startstudy_num_et);
        bgIv = bindView(R.id.startstudy_bg_iv);
        passwerEt = bindView(R.id.startstudy_pw_et);
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
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PreferenceHelper.getInstance(StartStudyActivity.this).setIntValue(COURSE_ID, data.getCourse_id());
                startActivity(MainActivity.class);
                finish();
            }
        });
        settingIpTv = bindView(R.id.startstudy_setting_ip_iv);
        settingIpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIpDialog();
            }
        });


//        Bundle bundle = getIntent().getExtras();
//        data = (HomepageInformationData) bundle.getSerializable("HomepageInformationData");
//        if (data != null) {
//            findViewById(R.id.startstudy_aty_pb).setVisibility(View.GONE);
//            imageView.setVisibility(View.VISIBLE);
//        }
        findViewById(R.id.jump_up_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
                finish();

            }
        });
    }

    private void showIpDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final Window window = alertDialog.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.dialog_changeip);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        window.findViewById(R.id.ip_save_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) window.findViewById(R.id.ip_content_et);
                String s = editText.getText().toString();
                //  Toast.makeText(MainActivity.this, s + "链接失败", Toast.LENGTH_SHORT).show();
//                showIp(s);
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

    private void showIp(String s) {
        BASE_URL = "http://" + s;
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.homeInfoUrl + PreferenceHelper.getInstance(this).getIntValue(PreferenceHelper.USER_ID));
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    List<HomepageInformationData> hData = JSON.parseArray(jsonObject.getString("message"), HomepageInformationData.class);
                    PreferenceHelper.getInstance(StartStudyActivity.this).setStringValue(NetUrlContstant.URL_NAME, BASE_URL);
                    Toast.makeText(StartStudyActivity.this, "Ip设置成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("LaunchActivity", "Ip设置失败");
                Toast.makeText(StartStudyActivity.this, "Ip设置失败", Toast.LENGTH_SHORT).show();

            }
        });
    }


//    //线程类型
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getData(HomepageInformationData date) {
//        findViewById(R.id.startstudy_aty_pb).setVisibility(View.GONE);
//        imageView.setVisibility(View.VISIBLE);
//        data = date;
//    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }
}
