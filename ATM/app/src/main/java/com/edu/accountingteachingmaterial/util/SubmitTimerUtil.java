package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.widget.Toast;

import com.edu.library.util.ToastUtil;

/**
 * 在2秒之内无法重复点击提交按钮
 * Created by Administrator on 2017/5/4.
 */

public class SubmitTimerUtil {
    static long temptime;


    public static void setTemptime (){
        temptime  = System.currentTimeMillis();

    }


    /**
     * 按两次返回键退出
     */
    public static boolean resetSubject(Context activity) {

        if (System.currentTimeMillis() - temptime < 2000) {

            ToastUtil.showToast(activity, "您刚刚提交过哦", Toast.LENGTH_SHORT);
            return false;
        } else {
            return true;
        }
    }
}
