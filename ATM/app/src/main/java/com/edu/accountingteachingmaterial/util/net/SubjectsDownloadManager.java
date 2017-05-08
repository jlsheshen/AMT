package com.edu.accountingteachingmaterial.util.net;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.subject.SubjectType;
import com.edu.subject.dao.CommonSubjectDataDao;
import com.edu.subject.data.CommonSubjectData;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.UrlReqEntity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.TOKEN;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.USER_ID;


/**
 * 题目下载管理类
 *
 * @author lucher
 *
 */
public class SubjectsDownloadManager extends JsonNetReqManager {

	private Context mContext;
	private int chatperId;

	public SubjectsDownloadManager(Context context) {
		mAsyncClient.addHeader(TOKEN, PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(TOKEN));

		mContext = context;
	}

	/**
	 * 创建实例
	 *
	 * @param context
	 * @return
	 */
	public static SubjectsDownloadManager newInstance(Context context) {
		return new SubjectsDownloadManager(context);
	}

	/**
	 * 下载题目数据
	 *
	 * @param url
	 */
	public void getSubjects(String url, int chapterId) {
		this.chatperId = chapterId;

		String userId = PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.USER_ID);
		String sendExamId[] = (String.valueOf(chapterId)).split(String.valueOf(userId));


		UrlReqEntity entity = new UrlReqEntity(mContext, RequestMethod.GET, url +sendExamId[0] );
		sendRequest(entity);
	}

	@Override
	public void onConnectionSuccess(JSONObject json, Header[] arg1) {
		Log.d(TAG, "onConnectionSuccess:" + json);
		parseSubjectJson(json);
		ContentValues contentValues = new ContentValues();
		contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_UNDONE);
		ExamListDao.getInstance(mContext).updateData("" + chatperId, contentValues);
		EventBus.getDefault().post(ClassContstant.EXAM_UNDONE);
	}

	@Override
	public void onConnectionError(String arg0) {
		Log.e(TAG, "onConnectionError:" + arg0);
	}

	@Override
	public void onConnectionFailure(String arg0, Header[] arg1) {
		Log.e(TAG, "onConnectionFailure:" + arg0);
	}

	/**
	 * 解析json
	 *
	 * @param json
	 */
	private void parseSubjectJson(JSONObject json) {
		long start = System.currentTimeMillis();
		Log.d(TAG, "parse start:" + start);
		boolean result = json.getBoolean("result");
		String message = json.getString("message");
		if (result) {
			List<CommonSubjectData> subjects = JSON.parseArray(message, CommonSubjectData.class);
			Log.d(TAG, "------" + message + "---");
			saveSubjects(subjects);
			Log.d(TAG, "subjects:" + subjects);
		} else {
			Log.e(TAG, "parseSubjectJson:" + json);
		}
		Log.d(TAG, "parse end:" + (System.currentTimeMillis() - start) / 1000);
	}



	/**
	 * 保存题目数据到数据库
	 *
	 * @param subjects
	 */
	private void saveSubjects(List<CommonSubjectData> subjects) {
		String userId = PreferenceHelper.getInstance(mContext).getStringValue(USER_ID);
		for (CommonSubjectData subject : subjects) {
//			subject.setChapterId(chatperId);
			try {
				int subjectId = -1;
				if (subject.getSubjectType() == SubjectType.SUBJECT_COMPREHENSIVE) {//综合题需要插入子题
					// 插入题目数据
					String body = subject.getBody();
					subject.setBody("");//body内容不需要存入数据库
					subjectId = CommonSubjectDataDao.getInstance(mContext).insertData(subject);
					if (subjectId > 0) {
						//插入子题
//						List<CommonSubjectData> children = JSON.parseArray(body, CommonSubjectData.class);
//						for (CommonSubjectData child : children) {
//							child.setParentId(subjectId);
//							CommonSubjectDataDao.getInstance(mContext).insertData(child);
//						}
					}
				} else {
					// 插入题目数据
					subjectId = CommonSubjectDataDao.getInstance(mContext).insertData(subject);
				}
				if (subjectId > 0 && subject.getParentId() == -1) {
					SubjectTestDataDao.getInstance(mContext).insertTest(subjectId,chatperId,subject.getFlag());
				}
			} catch (Exception e) {
				Log.e(TAG, "题目插入出错：" + subject);
				e.printStackTrace();
			}
		}
	}


	}

