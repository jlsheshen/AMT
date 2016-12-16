package com.edu.accountingteachingmaterial.util;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.dao.SubjectBasicDataDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.library.data.DBHelper;
import com.edu.subject.dao.SubjectBillDataDao;
import com.edu.subject.data.SubjectData;
import com.edu.testbill.Constant;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.UrlReqEntity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.TOKEN;


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
		mAsyncClient.addHeader(TOKEN,PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(TOKEN));

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
	public void getSubjects(String url, int chatperId) {
		this.chatperId = chatperId;
		UrlReqEntity entity = new UrlReqEntity(mContext, RequestMethod.GET, url);
		sendRequest(entity);
	}

	@Override
	public void onConnectionSuccess(JSONObject json, Header[] arg1) {
		Log.d(TAG, "onConnectionSuccess:" + json);
		parseSubjectJson(json);
		ContentValues contentValues = new ContentValues();
		contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_UNDONE);
		ExamListDao.getInstance(mContext).updateData("" + chatperId, contentValues);
//
//		view.findViewById(R.id.item_exercise_type_pb).setVisibility(View.GONE);
//		stateIv.setImageResource(R.drawable.selector_exam_undown_type);
//		stateIv.setVisibility(View.VISIBLE);
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
			List<SubjectData> subjects = JSON.parseArray(message, SubjectData.class);
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
	private void saveSubjects(List<SubjectData> subjects) {
		DBHelper helper = new DBHelper(mContext, Constant.DATABASE_NAME, null);
		SQLiteDatabase db = helper.getWritableDatabase();

		for (SubjectData subject : subjects) {
			subject.setChapterId(chatperId);
			switch (subject.getSubjectType()) {
				case ClassContstant.SUBJECT_SINGLE_CHOSE:
				case ClassContstant.SUBJECT_MULITI_CHOSE:
				case ClassContstant.SUBJECT_JUDGE:
					// 插入题目数据
					int basicId = SubjectBasicDataDao.getInstance(mContext, Constant.DATABASE_NAME).insertData(subject, db);

					if (basicId > 0) {
						SubjectTestDataDao.getInstance(mContext).insertTest(subject.getSubjectType(), basicId,subject.getChapterId(), db);
					}
					break;

				case ClassContstant.SUBJECT_ENTRY:
//				String ids = SubjectEntryDataDao.getInstance(mContext).insertData(subject, db);
//				if(ids.length()>0) {
//					//加入test表
//				}
					break;

				case ClassContstant.SUBJECT_BILL:
					int billId = SubjectBillDataDao.getInstance(mContext, Constant.DATABASE_NAME).insertData(subject, db);
					if (billId > 0) {
						SubjectTestDataDao.getInstance(mContext).insertTest(subject.getSubjectType(), billId,subject.getChapterId(), db);
					}

					break;
				default:
					break;
			}
		}

	}
}
