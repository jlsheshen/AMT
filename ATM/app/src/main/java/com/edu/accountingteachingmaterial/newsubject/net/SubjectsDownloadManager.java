package com.edu.accountingteachingmaterial.newsubject.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectOnlineTestDataDao;
import com.edu.subject.SubjectType;
import com.edu.subject.dao.CommonSubjectDataDao;
import com.edu.subject.data.CommonSubjectData;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.UrlReqEntity;

import org.apache.http.Header;

import java.util.List;

/**
 * 题目下载管理类
 * 
 * @author lucher
 * 
 */
public class SubjectsDownloadManager extends JsonNetReqManager {

	private Context mContext;
	private SubjectDownloadListener mListener;

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
	}

	@Override
	public void onConnectionError(String arg0) {
		Log.e(TAG, "onConnectionError:" + arg0);
		if (mListener != null) {
			mListener.onFailure("题目下载出错：" + arg0);
		}
	}

	@Override
	public void onConnectionFailure(String arg0, Header[] arg1) {
		Log.e(TAG, "onConnectionFailure:" + arg0);
		if (mListener != null) {
			mListener.onFailure("题目下载失败：" + arg0);
		}
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
			saveSubjects(subjects);
			Log.d(TAG, "subjects:" + subjects);
			if (mListener != null) {
				mListener.onSuccess("题目下载成功");
			}
		} else {
			Log.e(TAG, "parseSubjectJson:" + json);
			if (mListener != null) {
				mListener.onFailure("题目下载失败");
			}
		}
		Log.d(TAG, "parse end:" + (System.currentTimeMillis() - start) / 1000);
	}

	/**
	 * 保存题目数据到数据库
	 * 
	 * @param subjects
	 */
	private void saveSubjects(List<CommonSubjectData> subjects) {
		for (CommonSubjectData subject : subjects) {
			try {
				int subjectId = -1;
				if (subject.getSubjectType() == SubjectType.SUBJECT_COMPREHENSIVE) {//综合题需要插入子题
					// 插入题目数据
					String body = subject.getBody();
					subject.setBody("");//body内容不需要存入数据库
					subjectId = CommonSubjectDataDao.getInstance(mContext).insertData(subject);
					if (subjectId > 0) {
						//插入子题
						List<CommonSubjectData> children = JSON.parseArray(body, CommonSubjectData.class);
						for (CommonSubjectData child : children) {
							child.setParentId(subjectId);
							CommonSubjectDataDao.getInstance(mContext).insertData(child);
						}
					}
				} else {
					// 插入题目数据
					subjectId = CommonSubjectDataDao.getInstance(mContext).insertData(subject);
				}
				if (subjectId > 0) {
					SubjectOnlineTestDataDao.getInstance(mContext).insertTest(subjectId);
				}
			} catch (Exception e) {
				Log.e(TAG, "题目插入出错：" + subject);
				e.printStackTrace();
			}
		}
	}

	public void setSubjectDownloadListener(SubjectDownloadListener listener) {
		mListener = listener;
	}

	/**
	 * 题目下载listener
	 * @author lucher
	 *
	 */
	public interface SubjectDownloadListener {
		/**下载成功
		 * @param message
		 */
		void onSuccess(String message);

		/**下载失败
		 * @param message
		 */
		void onFailure(String message);
	}
}
