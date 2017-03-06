package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.bean.ClassBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;

import java.util.List;

/**
 * 教材页面列表网络管理
 * Created by Administrator on 2017/3/3.
 */

public class ClassListManager extends BaseNetManager {
    private static ClassListManager mSingleton;
    public ClassListener classListener;

    private ClassListManager(Context context) {
        super(context);
    }


    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static ClassListManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (ClassListManager.class) {
                if (mSingleton == null) {
                    mSingleton = new ClassListManager(context);
                }
            }
        }
        return mSingleton;
    }
    /**
     * 发送答案和密码
     *
     * @param
     */
    public void getClassList(ClassListener classListener) {
       String classId =  PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.CLASS_ID);
        String url = NetUrlContstant.getClassList() + classId;
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d(TAG, "url");
        sendRequest(entity);
        this.classListener = classListener;

    }
    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "班级号有误");
            }
            List<ClassBean> data = JSON.parseArray(jsonObject.getString("message"), ClassBean.class);
            classListener.onSuccess(data);
        }else {

            classListener.onFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        classListener.onFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        classListener.onFailure(s);

    }
    public interface ClassListener{
        void onSuccess(List<ClassBean> books);
        void onFailure(String message);
    }
}
