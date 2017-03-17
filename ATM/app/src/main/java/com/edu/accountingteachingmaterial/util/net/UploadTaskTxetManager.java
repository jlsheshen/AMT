package com.edu.accountingteachingmaterial.util.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.bean.TaskAnswerBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonReqEntity;

import org.apache.http.Header;

/**
 * 教材页面列表网络管理
 * Created by Administrator on 2017/3/3.
 */

public class UploadTaskTxetManager extends BaseNetManager {
    private static UploadTaskTxetManager mSingleton;
    public SubmitTaskTextListener submitTaskTextListener;

    private UploadTaskTxetManager(Context context) {
        super(context);
    }


    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static UploadTaskTxetManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (UploadTaskTxetManager.class) {
                if (mSingleton == null) {
                    mSingleton = new UploadTaskTxetManager(context);
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
    public void submitTaskText(SubmitTaskTextListener submitTaskTextListener, long teamId ,int taskId,String answer) {
       String userId =  PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.USER_ID);
        TaskAnswerBean data = new TaskAnswerBean();
        data.setAnswer(answer);
        String url = NetUrlContstant.getUploadTaskText() + userId + "-" + teamId + "-" + taskId;
        JsonReqEntity entity = new JsonReqEntity(mContext, RequestMethod.POST, url, JSON.toJSONString(data));
        Log.d(TAG, "url");
        sendRequest(entity);
        this.submitTaskTextListener = submitTaskTextListener;
    }
    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "有问题啊");
            }
            submitTaskTextListener.onSuccess("添加成功");
        }else {

            submitTaskTextListener.onFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        submitTaskTextListener.onFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        submitTaskTextListener.onFailure(s);

    }
    public interface SubmitTaskTextListener {
        void onSuccess(String message);
        void onFailure(String message);

    }
}
