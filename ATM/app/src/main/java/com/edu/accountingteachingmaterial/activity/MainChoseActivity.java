package com.edu.accountingteachingmaterial.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.fragment.ClassFragment;
import com.edu.accountingteachingmaterial.fragment.ExamFragment;
import com.edu.accountingteachingmaterial.fragment.MyFragment;
import com.edu.accountingteachingmaterial.fragment.TextbookFragment;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.SendJsonNetReqManager;
import com.edu.library.util.DoubleClickExitUtil;
import com.edu.subject.common.UnTouchableViewPager;
import com.lucher.net.req.RequestMethod;

import java.util.List;

import static com.edu.accountingteachingmaterial.constant.BASE_URL.BASE_URL;
import static com.edu.accountingteachingmaterial.constant.BASE_URL.TEMP_URL;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.STUDENT_NAME;

/**
 * 首页选择界面,在1.0重构时建立,分为教材,评测,课堂三个模块
 */
public class MainChoseActivity extends BaseActivity implements OnClickListener, DrawerListener {


    private RadioButton textbookButton, classButton, examButton, myButton;//通往四个界面的按钮
    private Button settingButton;//设置按钮
    private List<Fragment> fragments;//fragment 列表
    private UnTouchableViewPager viewPager;
    EditText editText;//ip地址的et
    Fragment examFragment, myFragment, textBookFragment;
    ClassFragment classFragment;
    DrawerLayout drawerLayout;//弹出的设置界面
    LinearLayout changeIpLy;//弹出的设置ipdialog
    TextView reLoginTv, studentName;//从新登陆按钮,学生姓名显示框
    AlertDialog alertDialog;
    private int drawerLayoutIsShow = 0;


    @Override
    public int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.activity_main_chose;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bindAndListener(classButton, R.id.class_iv);
        bindAndListener(examButton, R.id.exam_iv);
        bindAndListener(myButton, R.id.my_iv);
        bindAndListener(textbookButton, R.id.textbook_iv);
        bindAndListener(settingButton, R.id.setting_iv);
        bindAndListener(reLoginTv, R.id.main_relogin);
        drawerLayout = bindView(R.id.main_aty_seeting);
        bindAndListener(changeIpLy, R.id.change_ip_ly);
//      bindAndListener(studentName,R.id.my_head_student_name_tv);
        studentName = bindView(R.id.my_head_student_name_tv);
        studentName.setOnClickListener(this);
        // TODO Auto-generated method stub
//        findViewById(R.id.main_my_iv).bringToFront();
    }

    @Override
    public void initData() {
        String stuName = PreferenceHelper.getInstance(this).getStringValue(STUDENT_NAME);
        studentName.setText(stuName);
        if (null == textBookFragment) {
            textBookFragment = new TextbookFragment();
        }
        replaceFragment(textBookFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.class_iv:
                if (null == classFragment) {
                    classFragment = new ClassFragment();
                }
                replaceFragment(classFragment);
                break;
            case R.id.textbook_iv:
                if (null == textBookFragment) {
                    textBookFragment = new TextbookFragment();
                }
                replaceFragment(textBookFragment);
                break;
            case R.id.exam_iv:
                if (null == examFragment) {
                    examFragment = new ExamFragment();
                }
                replaceFragment(examFragment);

                break;
            case R.id.my_iv:
                if (null == myFragment) {
                    myFragment = new MyFragment();
                }
                replaceFragment(myFragment);
                break;
            case R.id.setting_iv:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                drawerLayout.setDrawerListener(this);
                //从左侧划出
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.change_ip_ly:
                startActivity(StartStudyActivity.class);
                finish();
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                alertDialog = builder.create();
//                alertDialog.show();
//                final Window window = alertDialog.getWindow();
//                // *** 主要就是在这里实现这种效果的.
//                // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
//                window.setBackgroundDrawableResource(android.R.color.transparent);
//
//                window.setContentView(R.layout.dialog_changeip);
//                alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//                editText = (EditText) window.findViewById(R.id.ip_content_et);
//                String oldBaseUrl[] = PreferenceHelper.getInstance(MainChoseActivity.this).getStringValue(PreferenceHelper.URL_NAME).split("http://");
//                editText.setText(oldBaseUrl.length>1?oldBaseUrl[1]:"");
//                window.findViewById(R.id.ip_save_iv).setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String s = editText.getText().toString();
//                        Log.d("MainActivity", "-------------" + s);
//                        if (s.length() < 4) {
//                            Toast.makeText(MainChoseActivity.this, "请输入正确IP地址", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        showIp(s);
//                        //  Toast.makeText(MainActivity.this, s + "链接失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                window.findViewById(R.id.ip_close_iv).setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog.dismiss();
//                    }
//                });
//                alertDialog.show();

                break;
            case R.id.main_relogin:

                startActivity(StartStudyActivity.class);
                finish();
                break;
            case R.id.my_head_student_name_tv:
                startActivity(StartStudyActivity.class);
                finish();
                break;

        }
        // TODO Auto-generated method stub

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout != null) {
            if (drawerLayoutIsShow == 1) {
                drawerLayout.closeDrawers();
                return;
            }
        }
        DoubleClickExitUtil.doubleClickExit(this);
    }

    private void showIp(String s) {
        TEMP_URL = "http://" + s;
        Log.d("MainActivity", NetUrlContstant.getSettingIpUrl());
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getSettingIpUrl());
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    BASE_URL = TEMP_URL;
                    PreferenceHelper.getInstance(MainChoseActivity.this).setStringValue(PreferenceHelper.URL_NAME, BASE_URL);
                    Toast.makeText(MainChoseActivity.this, "Ip设置成功", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Toast.makeText(MainChoseActivity.this, "Ip设置失败", Toast.LENGTH_SHORT).show();
                Log.d("LaunchActivity", "Ip设置失败" + BASE_URL);


            }
        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_aty_view, fragment);
        // Commit the transaction
        transaction.commit();
    }

    private void bindAndListener(View view, int id) {
        view = bindView(id);
        view.setOnClickListener(this);
    }

    @Override
    public void onDrawerClosed(View arg0) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayoutIsShow = 0;

    }

    @Override
    public void onDrawerOpened(View arg0) {
        drawerLayoutIsShow = 1;
        // TODO Auto-generated method stub

    }

    @Override
    public void onDrawerSlide(View arg0, float arg1) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }

    @Override
    public void onDrawerStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }


}
