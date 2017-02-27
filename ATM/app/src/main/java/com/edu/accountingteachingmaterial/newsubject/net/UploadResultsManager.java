package com.edu.accountingteachingmaterial.newsubject.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.newsubject.Constant;
import com.edu.library.util.ToastUtil;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.net.SubjectAnswerResult;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.JsonReqEntity;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * 发送答题结果到服务器管理类
 * 
 * @author lucher
 * 
 */
public class UploadResultsManager extends JsonNetReqManager {

	private Context mContext;
	// 需要上传答题结果的所有数据
	private List<SubjectAnswerResult> mAnswerResults;
	private static UploadResultsManager mSingleton;

	private UploadResultsManager(Context context) {
		mContext = context;
	}

	/**
	 * 单例模式获取实例
	 * 
	 * @param context
	 * @return
	 */
	public static UploadResultsManager getSingleton(Context context) {
		if (mSingleton == null) {
			mSingleton = new UploadResultsManager(context);
		}
		return mSingleton;
	}

	/**
	 * 设置题目测试数据
	 * 
	 * @param datas
	 */
	public UploadResultsManager setResults(List<BaseTestData> datas) {
		if (datas != null) {
			mAnswerResults = new ArrayList<SubjectAnswerResult>(datas.size());
			for (BaseTestData data : datas) {
				mAnswerResults.add(data.toResult());
			}
		}
		return mSingleton;
	}

	/**
	 * 上传答题结果
	 * 
	 * @param studentId
	 * @param examId
	 * @param seconds
	 */
	public void uploadResult(int studentId, int examId, int seconds) {
		if (mAnswerResults == null || mAnswerResults.size() <= 0) {
			ToastUtil.showToast(mContext, "发送结果为空");
			return;
		}
		uploadResult(studentId, examId, seconds, mAnswerResults);
	}

	/**
	 * 上传答题结果
	 * 
	 * @param studentId
	 * @param examId
	 * @param seconds
	 * @param answerResults
	 */
	public void uploadResult(int studentId, int examId, int seconds, List<SubjectAnswerResult> answerResults) {
		if (answerResults == null || answerResults.size() <= 0) {
			ToastUtil.showToast(mContext, "发送结果不能为空");
			return;
		}
		String url = Constant.SERVER_URL + "submitAnswer/" + studentId + "-" + examId + "-" + seconds;
		JsonReqEntity entity = new JsonReqEntity(mContext, RequestMethod.POST, url, JSON.toJSONString(answerResults));
		sendRequest(entity, "正在拼命上传成绩");
		Log.d(TAG, "uploadResult:" + JSON.toJSONString(answerResults));
	}

	@Override
	public void onConnectionSuccess(JSONObject json, Header[] arg1) {
		boolean result = json.getBoolean("result");
		String message = json.getString("message");
		if (result) {
			ToastUtil.showToast(mContext, "成绩上传成功");
		} else {
			ToastUtil.showToast(mContext, "成绩上传失败：" + message);
			Log.e(TAG, "uploadResult:" + json);
		}
	}

	@Override
	public void onConnectionError(String arg0) {
		ToastUtil.showToast(mContext, "成绩上传出错：" + arg0);
	}

	@Override
	public void onConnectionFailure(String arg0, Header[] arg1) {
		ToastUtil.showToast(mContext, "成绩上传失败：" + arg0);
	}

}
