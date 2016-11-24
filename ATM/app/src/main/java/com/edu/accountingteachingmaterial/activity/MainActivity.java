package com.edu.accountingteachingmaterial.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.fragment.ClassFragment;
import com.edu.accountingteachingmaterial.fragment.ExamFragment;
import com.edu.accountingteachingmaterial.fragment.MyFragment;

public class MainActivity extends BaseActivity implements OnClickListener, DrawerListener {


    RadioButton classButton, examButton, myButton, settingButton;
    Fragment classFragment, examFragment, myFragment;
    DrawerLayout drawerLayout;
    LinearLayout changeIpLy;

    @Override
    public int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bindAndListener(classButton, R.id.main_class_iv);
        bindAndListener(examButton, R.id.main_exam_iv);
        bindAndListener(myButton, R.id.main_my_iv);
        bindAndListener(settingButton, R.id.main_setting_iv);
        drawerLayout = bindView(R.id.main_aty_seeting);
        bindAndListener(changeIpLy, R.id.change_ip_ly);
        // TODO Auto-generated method stub
    }

    @Override
    public void initData() {
        if (null == classFragment) {
            classFragment = new ClassFragment();
        }
        replaceFragment(classFragment);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_class_iv:
                if (null == classFragment) {
                    classFragment = new ClassFragment();
                }
                replaceFragment(classFragment);

                break;
            case R.id.main_exam_iv:
                if (null == examFragment) {
                    examFragment = new ExamFragment();
                }
                replaceFragment(examFragment);

                break;
            case R.id.main_my_iv:
                if (null == myFragment) {
                    myFragment = new MyFragment();
                }
                replaceFragment(myFragment);

                break;
            case R.id.main_setting_iv:

                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                drawerLayout.setDrawerListener(this);
                //从左侧划出
                drawerLayout.openDrawer(Gravity.LEFT);

                //设置背景颜色
                //       drawerLayout.setScrimColor(Color.TRANSPARENT);

                break;
            case R.id.change_ip_ly:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                final Window window = alertDialog.getWindow();
                // *** 主要就是在这里实现这种效果的.
                // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
                window.setContentView(R.layout.dialog_changeip);
                alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


                window.findViewById(R.id.ip_save_iv).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText editText = (EditText) window.findViewById(R.id.ip_content_et);
                        String s = editText.getText().toString();
                        Toast.makeText(MainActivity.this, s + "链接失败", Toast.LENGTH_SHORT).show();
                    }
                });

                window.findViewById(R.id.ip_close_iv).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();



                break;

        }
        // TODO Auto-generated method stub

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

    }

    @Override
    public void onDrawerOpened(View arg0) {

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
