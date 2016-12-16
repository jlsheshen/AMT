package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.AccToken;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.JsonReqEntity;

import org.apache.http.Header;

/**
 * Created by Administrator on 2016/12/15.
 */

public class LoginNetMananger extends JsonNetReqManager {

    private Context mContext;
    // 需要上传答题结果的所有数据
    private static LoginNetMananger mSingleton;
    int studentNumber;
    String studentPassword;
    public static final String STUDNET_NUMBER = "STUDNET_NUMBER";

    public static final String STUDNET_PASSWORD = "STUDNET_PASSWORD";

    public static final String TOKEN = "TOKEN";



    private LoginNetMananger(Context context) {
        mAsyncClient.addHeader(TOKEN,PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(TOKEN));

        mContext = context;
    }

    /**
     * 单例模式获取实例
     *
     * @param context
     * @return
     */
    public static LoginNetMananger getSingleton(Context context) {
        if (mSingleton == null) {
            mSingleton = new LoginNetMananger(context);
        }
        return mSingleton;
    }

    /**
     * 发送答案和密码
     *
     * @param
     */
    public void login(String number,String passWord) {

        String url = NetUrlContstant.getLoginUrl() + "username="  + number + "&password=" + passWord + "&rememberme=1";
        JsonReqEntity entity = new JsonReqEntity(mContext, RequestMethod.POST, url);
        sendRequest(entity, "登陆中");
        Log.d(TAG, "url");

    }

    @Override
    public void onConnectionSuccess(JSONObject json, Header[] arg1) {

        boolean result = json.getBoolean("result");
        String message = json.getString("message");
        if (result) {
            AccToken accTokens =  JSON.parseObject(message, AccToken.class);
            PreferenceHelper.getInstance(mContext).setIntValue(STUDNET_NUMBER,studentNumber);
            PreferenceHelper.getInstance(mContext).setStringValue(STUDNET_PASSWORD,studentPassword);
            PreferenceHelper.getInstance(mContext).setStringValue(TOKEN,accTokens.getLoginToken());

        } else {
        }
    }

    @Override
    public void onConnectionError(String arg0) {
        ToastUtil.showToast(mContext, "成绩上传出错：" + arg0);
    }

    @Override
    public void onConnectionFailure(String arg0, Header[] arg1) {
        ToastUtil.showToast(mContext, "成绩上传失败：" + arg0);
    }

}