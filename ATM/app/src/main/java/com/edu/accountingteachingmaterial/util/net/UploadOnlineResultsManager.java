package com.edu.accountingteachingmaterial.util.net;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.dao.ExamOnLineListDao;
import com.edu.accountingteachingmaterial.model.ResultsListener;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.library.util.ToastUtil;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.net.AnswerResult;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.JsonReqEntity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.TOKEN;


/**
 * 发送答题结果到服务器管理类
 *
 * @author lucher
 */
public class UploadOnlineResultsManager extends JsonNetReqManager {

    private Context mContext;
    // 需要上传答题结果的所有数据
    private List<AnswerResult> mAnswerResults;
    private static UploadOnlineResultsManager mSingleton;
    int examId;
    ResultsListener resultsListener;

    public void setResultsListener(ResultsListener resultsListener) {
        this.resultsListener = resultsListener;
    }

    private UploadOnlineResultsManager(Context context) {
        mAsyncClient.addHeader(TOKEN, PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(TOKEN));

        mContext = context;
    }

    /**
     * 单例模式获取实例
     *
     * @param context
     * @return
     */
    public static UploadOnlineResultsManager getSingleton(Context context) {
        if (mSingleton == null) {
            mSingleton = new UploadOnlineResultsManager(context);
        }
        return mSingleton;
    }

    /**
     * 设置答题结果数据
     *
     * @param datas
     */
    public void setResults(List<BaseTestData> datas) {
        if (datas != null) {
            mAnswerResults = new ArrayList<AnswerResult>(datas.size());
            for (BaseTestData data : datas) {
                mAnswerResults.add(data.toResult());
            }
        }
    }

    /**
     * 设置单独提交答题结果数据
     *
     * @param data
     */
    public void setSingleResults(BaseTestData data) {
        if (data != null) {
            mAnswerResults = new ArrayList<>();
            mAnswerResults.add(data.toResult());

        }
    }

    /**
     * 上传答题结果
     *
     * @param studentId
     * @param examId
     * @param seconds
     */
    public void uploadResult(int studentId, int examId, int seconds) {
        this.examId = examId;
        if (mAnswerResults == null || mAnswerResults.size() <= 0) {
            ToastUtil.showToast(mContext, "发送结果为空");
            return;
        }
        Log.d("UploadResultsManager", JSON.toJSONString(mAnswerResults));
        String url = NetUrlContstant.getSubjectSubmitUrl() + studentId + "-" + examId + "-" + seconds;
        JsonReqEntity entity = new JsonReqEntity(mContext, RequestMethod.POST, url, JSON.toJSONString(mAnswerResults));
        sendRequest(entity, "正在拼命上传成绩");
        Log.d(TAG, "uploadResult:" + JSON.toJSONString(mAnswerResults));
    }

    /**
     * 上传单道答题结果
     *
     * @param studentId
     * @param examId
     */
    public void uploadResult(int studentId, int examId) {
        this.examId = examId;
        if (mAnswerResults == null || mAnswerResults.size() <= 0) {
            ToastUtil.showToast(mContext, "发送结果为空");
            return;
        }
        String url = NetUrlContstant.getSubjectSingleSubmitUrl() + studentId + "-" + examId;
        Log.d("UploadResultsManager", "mAnswerResults.get(0):" + mAnswerResults.get(0) + "--" + url);
        JsonReqEntity entity = new JsonReqEntity(mContext, RequestMethod.POST, url, JSON.toJSONString(mAnswerResults));
        sendRequest(entity, "正在拼命上传成绩");
        Log.d(TAG, "uploadResult:" + JSON.toJSONString(mAnswerResults));
    }

    @Override
    public void onConnectionSuccess(JSONObject json, Header[] arg1) {
        boolean result = json.getBoolean("result");
        String message = json.getString("message");
        if (result) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_COMMIT);
            ExamOnLineListDao.getInstance(mContext).updateData("" + examId, contentValues);
            resultsListener.onSuccess();
            ToastUtil.showToast(mContext, "成绩上传成功!");
            EventBus.getDefault().post(ClassContstant.EXAM_COMMIT);
        } else {
            ToastUtil.showToast(mContext, "成绩上传失败：" + message);
            Log.e(TAG, "uploadResult:" + json);
            EventBus.getDefault().post(ClassContstant.EXAM_FAILD);
        }
    }

    @Override
    public void onConnectionError(String arg0) {
        Log.d(TAG, "成绩上传出错：" + arg0);
        ToastUtil.showToast(mContext, "成绩上传出错：" + arg0);
        if (resultsListener != null) {
            resultsListener.onFialure();
        }
        EventBus.getDefault().post(ClassContstant.EXAM_FAILD);
    }

    @Override
    public void onConnectionFailure(String arg0, Header[] arg1) {
        Log.d(TAG, "成绩上传出错：" + arg0);

        resultsListener.onFialure();

        ToastUtil.showToast(mContext, "成绩上传失败：" + arg0);
        EventBus.getDefault().post(ClassContstant.EXAM_FAILD);
    }

}