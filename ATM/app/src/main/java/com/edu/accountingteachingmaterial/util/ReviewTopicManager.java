package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.entity.ReviewTopicData;
import com.edu.library.util.ToastUtil;
import com.edu.subject.BASE_URL;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.JsonReqEntity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;


/**
 * 上传要组成的试卷每个题目数量
 */

public class ReviewTopicManager extends JsonNetReqManager {
    private Context context;
    private static ReviewTopicManager reviewTopicInstance;
    private ReviewTopicData datas;

    private ReviewTopicManager(Context context) {
        this.context = context;
    }

    /**
     * 单例模式获取实例
     *
     * @param context
     * @return
     */
    public static ReviewTopicManager getReviewTopicInstance(Context context) {
        if (reviewTopicInstance == null) {
            reviewTopicInstance = new ReviewTopicManager(context);
        }
        return reviewTopicInstance;
    }

    public ReviewTopicManager setReviewTopicVOList(ReviewTopicData datas) {

        this.datas = datas;
        return this;

    }

    public void sendTopic() {
        if (datas == null) {
            ToastUtil.showToast(mContext, "发送结果为空");
            return;
        }
        String url = BASE_URL.BASE_URL;
        JsonReqEntity entity = new JsonReqEntity(context, RequestMethod.POST, url, JSON.toJSONString(datas));
        sendRequest(entity);
        Log.d(TAG, "uploadResult:" + JSON.toJSONString(datas));

    }

    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {
        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            ToastUtil.showToast(context, "上传题目数量记录");
            EventBus.getDefault().post(1);
        } else {
            ToastUtil.showToast(context, "上传题目数量失败：" + message);
            Log.e(TAG, "uploadResult:" + jsonObject);
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        ToastUtil.showToast(context, "上传题目数量失败：" + s);


    }

    @Override
    public void onConnectionError(String s) {
        ToastUtil.showToast(context, "上传题目数量失败：" + s);

    }
}
