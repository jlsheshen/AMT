package com.edu.accountingteachingmaterial.util.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.bean.TextbookBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;

import java.util.List;

/**
 * 教材页面列表网络管理
 * Created by Administrator on 2017/3/3.
 */

public class TextBookListManager extends BaseNetManager {
    private static TextBookListManager mSingleton;
    public TextbookListener textbookListener;

    private TextBookListManager(Context context) {
        super(context);
    }


    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static TextBookListManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (TextBookListManager.class) {
                if (mSingleton == null) {
                    mSingleton = new TextBookListManager(context);
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
    public void getTextBookList(TextbookListener textbookListener) {
       String classId =  PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.CLASS_ID);
        String url = NetUrlContstant.getTextBookList() + classId;
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d(TAG, "url");
        sendRequest(entity);
        this.textbookListener = textbookListener;

    }
    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "班级号有误");
            }
            List<TextbookBean> data = JSON.parseArray(jsonObject.getString("message"), TextbookBean.class);
            textbookListener.onSuccess(data);
        }else {

            textbookListener.onFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        textbookListener.onFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        textbookListener.onFailure(s);

    }
    public interface TextbookListener{
        void onSuccess(List<TextbookBean> books);
        void onFailure(String message);

    }
}
