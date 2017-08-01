package com.edu.accountingteachingmaterial.util.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.bean.GroupTaskListBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;

/**
 * 分组任务列表
 * Created by Administrator on 2017/3/3.
 */

public class GroupTaskListManager extends BaseNetManager {
    private static GroupTaskListManager mSingleton;
    public GroupTaskListListener groupTaskListListener;

    private GroupTaskListManager(Context context) {
        super(context);
    }

    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static GroupTaskListManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (GroupTaskListManager.class) {
                if (mSingleton == null) {
                    mSingleton = new GroupTaskListManager(context);
                }
            }
        }
        return mSingleton;
    }
    /**
     *
     *
     * @param
     */
    public void setCourse(GroupTaskListListener groupTaskListListener) {
        String courseId  = PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.COURSE_ID);
        String classId = PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.CLASS_ID);
        String url = NetUrlContstant.getGroupTaskList() + courseId +"-" +  classId;
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d("GroupTaskListManager", "url" + url);
        sendRequest(entity);
        this.groupTaskListListener = groupTaskListListener;

    }
    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "班级号有误");
            }
            GroupTaskListBean data = JSON.parseObject(message, GroupTaskListBean.class);
            groupTaskListListener.onSuccess(data);
        }else {

            groupTaskListListener.onFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        groupTaskListListener.onFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        groupTaskListListener.onFailure(s);

    }
    public interface GroupTaskListListener {
        void onSuccess(GroupTaskListBean taskList);
        void onFailure(String message);
    }
}
