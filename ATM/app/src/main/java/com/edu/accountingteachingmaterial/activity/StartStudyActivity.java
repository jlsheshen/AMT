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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.AccToken;
import com.edu.accountingteachingmaterial.entity.HomepageInformationData;
import com.edu.accountingteachingmaterial.util.GetBillTemplatesManager;
import com.edu.accountingteachingmaterial.util.LoginNetMananger;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.lucher.net.req.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.COURSE_ID;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.KEY_LOGIN_STATE;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.STUDNET_NUMBER;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.STUDNET_PASSWORD;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.TOKEN;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.URL_NAME;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.USER_ID;
import static com.edu.subject.BASE_URL.BASE_URL;

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
                login();
//                startActivity(MainActivity.class);
//                finish();
//                uploadHomepageInfo();
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
//       //     findViewById(R.id.startstudy_aty_pb).setVisibility(View.GONE);
//            imageView.setVisibility(View.VISIBLE);
//        }
        findViewById(R.id.jump_up_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
                finish();

            }
        });
        if (PreferenceHelper.getInstance(StartStudyActivity.this).getBooleanValue(KEY_LOGIN_STATE) == true) {
            login();
        }
    }

    private void login() {
        final String num = numEt.getText().toString();
        final String pw = passwerEt.getText().toString();
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        String url = NetUrlContstant.getLoginUrl() + "username=" + num + "&password=" + pw + "&rememberme=1";
        Log.d("StartStudyActivity", url);
        NetSendCodeEntity entity = new NetSendCodeEntity(this, RequestMethod.POST, url);
        sendJsonNetReqManager.sendRequest(entity, "登陆中");
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                boolean result = jsonObject.getBoolean("result");
                String message = jsonObject.getString("message");
                if (result) {
                    if (message == null || message.length() == 0) {
                        Log.d("StartStudyActivity", "学号有误");
                    }
                    Log.d("StartStudyActivity", "----" + message);
                    AccToken accToken = JSON.parseObject(jsonObject.getString("message"), AccToken.class);
                    PreferenceHelper.getInstance(StartStudyActivity.this).setStringValue(STUDNET_NUMBER, num);
                    PreferenceHelper.getInstance(StartStudyActivity.this).setStringValue(STUDNET_PASSWORD, pw);
                    PreferenceHelper.getInstance(StartStudyActivity.this).setStringValue(TOKEN, accToken.getLoginToken());
                    PreferenceHelper.getInstance(StartStudyActivity.this).setStringValue(PreferenceHelper.USER_ID, String.valueOf(accToken.getStuId()));
                    uploadHomepageInfo();
//                    startActivity(MainActivity.class);
//                    finish();
                    PreferenceHelper.getInstance(StartStudyActivity.this).setBooleanValue(KEY_LOGIN_STATE, true);
                } else {
                    Toast.makeText(StartStudyActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("LaunchActivity", "登陆失败");
                Toast.makeText(StartStudyActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 弹出设置ipdialog
     */
    private void showIpDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final Window window = alertDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

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
        BASE_URL = "http://" + s;
        Log.d("StartStudyActivity", NetUrlContstant.getSettingIpUrl());
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getSettingIpUrl());
        Log.d("StartStudyActivity", NetUrlContstant.getSettingIpUrl());
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    PreferenceHelper.getInstance(StartStudyActivity.this).setStringValue(URL_NAME, BASE_URL);

                    Toast.makeText(StartStudyActivity.this, "Ip设置成功", Toast.LENGTH_SHORT).show();
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
    public void getData(HomepageInformationData date) {
        //  findViewById(R.id.startstudy_aty_pb).setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        data = date;
    }

    /**
     * 登陆成功后获取课程的id
     */
    private void uploadHomepageInfo() {
        final String num = numEt.getText().toString();
        final String pw = passwerEt.getText().toString();

        Log.d("LaunchActivity", NetUrlContstant.getHomeInfoUrl() + PreferenceHelper.getInstance(this).getStringValue(USER_ID));

        String s = PreferenceHelper.getInstance(this).getStringValue(URL_NAME);

        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();

        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getHomeInfoUrl() + PreferenceHelper.getInstance(this).getStringValue(USER_ID));
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    List<HomepageInformationData> hData = JSON.parseArray(jsonObject.getString("message"), HomepageInformationData.class);
                    data = hData.get(0);
                    PreferenceHelper.getInstance(StartStudyActivity.this).setStringValue(STUDNET_NUMBER, num);
                    PreferenceHelper.getInstance(StartStudyActivity.this).setStringValue(STUDNET_PASSWORD, pw);
                    PreferenceHelper.getInstance(StartStudyActivity.this).setIntValue(COURSE_ID, data.getCourse_id());
                    GetBillTemplatesManager.newInstance(context).sendLocalTemplates();

//                    startActivity(MainActivity.class);
//                       finish();
//                    EventBus.getDefault().post(data);
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("StartStudyActivity", "线程启动获取失败");
            }
        });
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
