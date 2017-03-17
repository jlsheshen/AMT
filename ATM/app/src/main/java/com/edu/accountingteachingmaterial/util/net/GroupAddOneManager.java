package com.edu.accountingteachingmaterial.util.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;

/**
 * 教材页面列表网络管理
 * Created by Administrator on 2017/3/3.
 */

public class GroupAddOneManager extends BaseNetManager {
    private static GroupAddOneManager mSingleton;
    public AddGroupOneListener addGroupOneListener;

    private GroupAddOneManager(Context context) {
        super(context);
    }


    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static GroupAddOneManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (GroupAddOneManager.class) {
                if (mSingleton == null) {
                    mSingleton = new GroupAddOneManager(context);
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
    public void addGroupOne(AddGroupOneListener addGroupOneListener,long teamId) {
       String userId =  PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.USER_ID);
        String url = NetUrlContstant.getAddGroup() + userId + "-" + teamId;
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d(TAG, "url");
        sendRequest(entity);
        this.addGroupOneListener = addGroupOneListener;
    }
    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "有问题啊");
            }
            addGroupOneListener.onSuccess("添加成功");
        }else {

            addGroupOneListener.onFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        addGroupOneListener.onFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        addGroupOneListener.onFailure(s);

    }
    public interface AddGroupOneListener {
        void onSuccess(String message);
        void onFailure(String message);

    }
}
