package com.edu.accountingteachingmaterial.util.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;

/**
 * 教材页面列表网络管理
 * Created by Administrator on 2017/3/3.
 */

public class DeteleAccessoryManager extends BaseNetManager {
    private static DeteleAccessoryManager mSingleton;
    public DeteleAccessoryListener deteleAccessoryListener;

    private DeteleAccessoryManager(Context context) {
        super(context);
    }


    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static DeteleAccessoryManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (DeteleAccessoryManager.class) {
                if (mSingleton == null) {
                    mSingleton = new DeteleAccessoryManager(context);
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
    public void deteleAccessory(DeteleAccessoryListener deteleAccessoryListener, int fileId) {
        String url = NetUrlContstant.getDeleteAccessory() + fileId;
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d(TAG, "url");
        sendRequest(entity);
        this.deteleAccessoryListener = deteleAccessoryListener;
    }
    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "有问题啊");
            }
            deteleAccessoryListener.onSuccess();
        }else {

            deteleAccessoryListener.onFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        deteleAccessoryListener.onFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        deteleAccessoryListener.onFailure(s);

    }
    public interface DeteleAccessoryListener {
        void onSuccess();
        void onFailure(String message);

    }
}
