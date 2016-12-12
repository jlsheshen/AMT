package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.entity.StudyHistoryVO;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.JsonReqEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 历史记录点击类,用于上传历史记录,以后可能增加保存本地功能
 * Created by Administrator on 2016/12/9.
 */

public class HistoryClickManager extends JsonNetReqManager {
    private Context context;
    private static HistoryClickManager hisInstance;
    private List<StudyHistoryVO> datas;


    private HistoryClickManager(Context context) {
        this.context = context;
    }

    /**
     * 单例模式获取实例
     *
     * @param context
     * @return
     */
    public static HistoryClickManager getHisInstance(Context context) {
        if (hisInstance == null) {
            hisInstance = new HistoryClickManager(context);
        }
        return hisInstance;
    }

    public HistoryClickManager setStudyHistoryVOList(List<StudyHistoryVO> datas) {

        this.datas = datas;
        return this;

    }

    public void sendHistory() {
//        this.examId = examId;
        if (datas == null || datas.size() <= 0) {
            ToastUtil.showToast(mContext, "发送结果为空");
            return;
        }
        String url = NetUrlContstant.upLoadingHisUrl;
        JsonReqEntity entity = new JsonReqEntity(context, RequestMethod.POST, url, JSON.toJSONString(datas));
        sendRequest(entity);
        Log.d(TAG, "uploadResult:" + JSON.toJSONString(datas));

    }

    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {
        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            ToastUtil.showToast(context, "上传历史记录");
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_COMMIT);
//            ExamListDao.getInstance(mContext).updateData("" + examId, contentValues);
            EventBus.getDefault().post(ClassContstant.EXAM_COMMIT);
        } else {
            ToastUtil.showToast(context, "上传历史记录失败：" + message);
            Log.e(TAG, "uploadResult:" + jsonObject);
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        ToastUtil.showToast(context, "上传历史记录失败：" + s);


    }

    @Override
    public void onConnectionError(String s) {
        ToastUtil.showToast(context, "上传历史记录失败：" + s);

    }
}
