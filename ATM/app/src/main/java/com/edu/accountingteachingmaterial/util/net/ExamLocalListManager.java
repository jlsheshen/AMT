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

public class ExamLocalListManager extends BaseNetManager {
    private static ExamLocalListManager mSingleton;
    public ExamLocalListener examLocalListener;

    private ExamLocalListManager(Context context) {
        super(context);
    }

    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static ExamLocalListManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (ExamLocalListManager.class) {
                if (mSingleton == null) {
                    mSingleton = new ExamLocalListManager(context);
                }
            }
        }
        return mSingleton;
    }

    /**
     * 获取练习试题列表
     * @param
     */
    public void getLoaclDatas(ExamLocalListener examLocalListener) {
        String url =  NetUrlContstant.getExamLocalUrlList() + PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.USER_ID);
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d("GroupTaskListManager", "url" + url);
        sendRequest(entity);
        this.examLocalListener = examLocalListener;

    }


    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
            }
                OnLineExamData data = JSON.parseObject(message, OnLineExamData.class);
                examLocalListener.onLocalSuccess(data);


        }else {

            examLocalListener.onFailure("数据为空");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        examLocalListener.onFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        examLocalListener.onFailure(s);

    }
    public interface ExamLocalListener {
        void onLocalSuccess(OnLineExamData localData);
        void onFailure(String message);
    }
}
