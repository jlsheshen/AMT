package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.dao.SubjectBasicDataDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.library.data.DBHelper;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectConstant;
import com.edu.subject.SubjectType;
import com.edu.subject.dao.SubjectBillDataDao;
import com.edu.subject.data.SubjectData;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.UrlReqEntity;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 题目下载管理类
 * 
 * @author lucher
 * 
 */
public class SubjectsDownloadManager extends JsonNetReqManager {

	private Context mContext;

	public SubjectsDownloadManager(Context context) {
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
	public void getSubjects(String url) {
		UrlReqEntity entity = new UrlReqEntity(mContext, RequestMethod.GET, url);
		sendRequest(entity, "正在拼命下载题目数据");
	}

	@Override
	public void onConnectionSuccess(JSONObject json, Header[] arg1) {
		Log.d(TAG, "onConnectionSuccess:" + json);
		parseSubjectJson(json);
		ToastUtil.showToast(mContext, "题目下载完成");
	}

	@Override
	public void onConnectionError(String arg0) {
		Log.e(TAG, "onConnectionError:" + arg0);
		ToastUtil.showToast(mContext, "题目下载出错："+arg0);
	}

	@Override
	public void onConnectionFailure(String arg0, Header[] arg1) {
		Log.e(TAG, "onConnectionFailure:" + arg0);
		ToastUtil.showToast(mContext, "题目下载失败："+arg0);
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
		DBHelper helper = new DBHelper(mContext, SubjectConstant.DATABASE_NAME, null);
		SQLiteDatabase db = helper.getWritableDatabase();

		for (SubjectData subject : subjects) {
			switch (subject.getSubjectType()) {
			case SubjectType.SUBJECT_SINGLE:
			case SubjectType.SUBJECT_MULTI:
			case SubjectType.SUBJECT_JUDGE:
				// 插入题目数据
				int basicId = SubjectBasicDataDao.getInstance(mContext, SubjectConstant.DATABASE_NAME).insertData(subject, db);
				if (basicId > 0) {
					SubjectTestDataDao.getInstance(mContext).insertTest(subject.getSubjectType(), basicId, db);
				}
				break;

			case SubjectType.SUBJECT_COMPLEX_ENTRY:
				String ids = SubjectEntryDataDao.getInstance(mContext).insertData(subject, db);
				if(ids.length()>0) {
					//加入test表
				}
				break;

			case SubjectType.SUBJECT_BILL:
			case SubjectType.SUBJECT_GROUP_BILL:
				int billId = SubjectBillDataDao.getInstance(mContext, SubjectConstant.DATABASE_NAME).insertData(subject, db);
				if (billId > 0) {
					SubjectTestDataDao.getInstance(mContext).insertTest(SubjectType.SUBJECT_BILL, billId, db);
				}
				
				break;
			default:
				break;
			}
		}
	}
}
