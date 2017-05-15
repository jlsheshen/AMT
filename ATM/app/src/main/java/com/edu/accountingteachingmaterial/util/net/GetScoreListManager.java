package com.edu.accountingteachingmaterial.util.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.bean.UpdateScoreBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SplitChapterIdUtil;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;

import java.util.List;

/**
 * 分组任务列表
 * Created by Administrator on 2017/3/3.
 */

public class GetScoreListManager extends BaseNetManager {
    private static GetScoreListManager mSingleton;
    public ExamScoreListListener examScoreListListener;
    String chapter;

    private GetScoreListManager(Context context) {
        super(context);
    }

    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static GetScoreListManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (GetScoreListManager.class) {
                if (mSingleton == null) {
                    mSingleton = new GetScoreListManager(context);
                }
            }
        }
        return mSingleton;
    }
    /**
     *
     *
     * @param
     * @param examScoreListListener
     */
    public void setExamId(ExamScoreListListener examScoreListListener, String examId) {
        chapter = examId;
        String studentId = PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.USER_ID);
        String sendExamId = SplitChapterIdUtil.spliterId(examId,studentId);

        String url = NetUrlContstant.getFindExamScoreList() + sendExamId+ "-" + studentId;
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d("GroupTaskListManager", "url" + url);
        sendRequest(entity);
        this.examScoreListListener = examScoreListListener;

    }
    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "班级号有误");
            }
            List<UpdateScoreBean> datas = JSON.parseArray(message, UpdateScoreBean.class);
            examScoreListListener.onGetScoreSuccess(datas,chapter);
        }else {

            examScoreListListener.onGetScoreFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        examScoreListListener.onGetScoreFailure(s);

    }

    @Override
    public void onConnectionError(String s) {
        examScoreListListener.onGetScoreFailure(s);

    }
    public interface ExamScoreListListener {
        void onGetScoreSuccess(List<UpdateScoreBean> updateScoreBeanList,String chatperId);
        void onGetScoreFailure(String message);
    }
}
