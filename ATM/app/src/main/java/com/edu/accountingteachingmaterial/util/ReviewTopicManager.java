package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.ReviewTopicVo;
import com.edu.accountingteachingmaterial.view.LoadingDialog;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.JsonReqEntity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.USER_ID;


/**
 * 上传要组成的试卷每个题目数量
 */

public class ReviewTopicManager extends JsonNetReqManager {
    private Context context;
    private static ReviewTopicManager reviewTopicInstance;
    private ReviewTopicVo datas;

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

    public ReviewTopicManager setReviewTopicVOList(ReviewTopicVo datas) {

        this.datas = datas;
        return this;

    }

    public void sendTopic(int chapterId) {
        LoadingDialog dialog = new LoadingDialog(context);
        if (datas == null) {
            ToastUtil.showToast(mContext, "发送题目数据为空");
            return;
        }
        String useId = PreferenceHelper.getInstance(context).getStringValue(USER_ID);
        String url = NetUrlContstant.getUploadingReviewList() + chapterId + "-" + useId;
        //String url = NetUrlContstant.getUploadingReviewList() + "/901-39261";
        JsonReqEntity entity = new JsonReqEntity(context, RequestMethod.POST, url, JSON.toJSONString(datas));
        sendRequest(entity, dialog);
        Log.d(TAG, "ReviewTopicManager:" + JSON.toJSONString(datas));

    }

    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {
        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            ToastUtil.showToast(context, "onConnectionSuccess 上传题目数量记录");
            PreferenceHelper.getInstance(context).setStringValue(PreferenceHelper.EXAM_ID, message);

            EventBus.getDefault().post(ClassContstant.UPLOAD_TYPE);
        } else {
            ToastUtil.showToast(context, "上传题目数量记录失败：");
            Log.e(TAG, "ReviewTopicManager:" + jsonObject);
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        ToastUtil.showToast(context, "onConnectionFailure 上传题目数量失败：" + s);


    }

    @Override
    public void onConnectionError(String s) {
        ToastUtil.showToast(context, "onConnectionError 上传题目数量失败：" + s);

    }
}
