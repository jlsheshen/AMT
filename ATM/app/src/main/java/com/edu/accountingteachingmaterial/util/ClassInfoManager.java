package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.bean.ClassInfoBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;

/**
 * 教材页面列表网络管理
 * Created by Administrator on 2017/3/3.
 */

public class ClassInfoManager extends BaseNetManager {
    private static ClassInfoManager mSingleton;
    public ClassInfoListener classInfoListener;

    private ClassInfoManager(Context context) {
        super(context);
    }

    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static ClassInfoManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (ClassInfoManager.class) {
                if (mSingleton == null) {
                    mSingleton = new ClassInfoManager(context);
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
    public void getClassInfo(String courseId,ClassInfoListener classInfoListener) {
        String url = NetUrlContstant.getClassInfo() + courseId;
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d(TAG, "url");
        sendRequest(entity);
        this.classInfoListener = classInfoListener;

    }
    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "班级号有误");
            }
            ClassInfoBean data = JSON.parseObject(message, ClassInfoBean.class);
            classInfoListener.onSuccess(data);
        }else {

            classInfoListener.onFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        classInfoListener.onFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        classInfoListener.onFailure(s);

    }
    public interface ClassInfoListener{
        void onSuccess(ClassInfoBean books);
        void onFailure(String message);

    }
}
