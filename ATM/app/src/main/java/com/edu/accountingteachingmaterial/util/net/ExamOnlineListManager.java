package com.edu.accountingteachingmaterial.util.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.OnLineExamData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;

/**
 * 评测在线测试列表
 * Created by Administrator on 2017/3/3.
 */

public class ExamOnlineListManager extends BaseNetManager {
    private static ExamOnlineListManager mSingleton;
    public ExamOnlineListener examOnlineListener;

    private ExamOnlineListManager(Context context) {
        super(context);
    }

    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static ExamOnlineListManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (ExamOnlineListManager.class) {
                if (mSingleton == null) {
                    mSingleton = new ExamOnlineListManager(context);
                }
            }
        }
        return mSingleton;
    }
    /**
     * 获取在线试题列表
     * @param
     */
    public void getOnlineDatas(ExamOnlineListener examOnlineListener) {
        String url =  NetUrlContstant.getExamOnlineUrlList() + PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.USER_ID);
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d("GroupTaskListManager", "url" + url);
        sendRequest(entity);
        this.examOnlineListener = examOnlineListener;

    }


    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
            }
                OnLineExamData data = JSON.parseObject(message, OnLineExamData.class);
                        examOnlineListener.onOnlineSuccess(data);


        }else {

            examOnlineListener.onFailure("数据为空");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        examOnlineListener.onFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        examOnlineListener.onFailure(s);

    }
    public interface ExamOnlineListener {
        void onOnlineSuccess(OnLineExamData onLineExamData);
        void onFailure(String message);
    }
}
