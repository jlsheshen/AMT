package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.bean.GroupsListBean;
import com.edu.accountingteachingmaterial.bean.TaskDetailBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.net.BaseNetManager;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;
import java.util.List;

/**
 * 教材页面列表网络管理
 * Created by Administrator on 2017/3/3.
 */

public class AddTasktManager extends BaseNetManager {
    private static AddTasktManager mSingleton;
    public AddTaskListener addTaskListener;

    private AddTasktManager(Context context) {
        super(context);
    }


    /**
     * 单例模式获取实例
     *
     * @param context
     * @return
     */
    public static AddTasktManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (AddTasktManager.class) {
                if (mSingleton == null) {
                    mSingleton = new AddTasktManager(context);
                }
            }
        }
        return mSingleton;
    }

    /**
     * 发送答案和密码
     *
     * @param
     *
     */
    public void getTaskData(AddTaskListener addTaskListener, int taskId) {
        String studentId = PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.USER_ID);
        String url = NetUrlContstant.getTaskDetail() + studentId + "-" + taskId;
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d(TAG, "url");
        sendRequest(entity);
        this.addTaskListener = addTaskListener;
    }

    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        Log.d("onConnectionSuccess","message---------" +  message);
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "班级号有误");
            }
            if (message.charAt(0) == '[') {
                List<GroupsListBean> datas = JSON.parseArray(jsonObject.getString("message"), GroupsListBean.class);
                addTaskListener.goAddGroup(datas);
            } else if (message.charAt(0) == '{') {
                TaskDetailBean data = JSON.parseObject(jsonObject.getString("message"), TaskDetailBean.class);
                addTaskListener.goTaskDetail(data);
            } else {
                addTaskListener.onFailure("数据有问题啊" + message);
            }
        } else {

            addTaskListener.onFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        addTaskListener.onFailure(s);
    }

    @Override
    public void onConnectionError(String s) {
        addTaskListener.onFailure(s);
    }

    public interface AddTaskListener {
        void goAddGroup(List<GroupsListBean> datas);

        void goTaskDetail(TaskDetailBean data);

        void onFailure(String message);
    }
}
