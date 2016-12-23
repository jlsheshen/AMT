package com.edu.accountingteachingmaterial.util;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ReviewExamListDao;
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


/**
 * 题目下载管理类
 *
 * @author lucher
 */
public class ReviewExamDownloadManager extends JsonNetReqManager {

    private Context mContext;
    private int chatperId;

    public ReviewExamDownloadManager(Context context) {
        mContext = context;
    }

    /**
     * 创建实例
     *
     * @param context
     * @return
     */
    public static ReviewExamDownloadManager newInstance(Context context) {
        return new ReviewExamDownloadManager(context);
    }

    /**
     * 下载题目数据
     *
     * @param url
     */
    public void getSubjects(String url, int chatperId) {
        this.chatperId = chatperId;
        UrlReqEntity entity = new UrlReqEntity(mContext, RequestMethod.GET, url);
        sendRequest(entity, "正在下载试题......");
    }

    @Override
    public void onConnectionSuccess(JSONObject json, Header[] arg1) {
        Log.d(TAG, "onConnectionSuccess:" + json);
        parseSubjectJson(json);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReviewExamListDao.STATE, ClassContstant.EXAM_UNDONE);

        contentValues.put(ReviewExamListDao.TYPE, 1);
        contentValues.put(ReviewExamListDao.CHAPTER_ID, chatperId);
        contentValues.put(ReviewExamListDao.ID, chatperId);
        contentValues.put(ReviewExamListDao.TITLE,"会计立体化测试");
        contentValues.put(ReviewExamListDao.NUM,"10");
        contentValues.put(ReviewExamListDao.DATE,"2016-12-23");
        contentValues.put(ReviewExamListDao.SCORE,"100");
        ReviewExamListDao.getInstance(mContext).insertData(contentValues);

        EventBus.getDefault().post(ClassContstant.DOWNLOAD_TYPE);
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
                        SubjectTestDataDao.getInstance(mContext).insertTest(subject.getSubjectType(), basicId, subject.getChapterId(), db);
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
                        SubjectTestDataDao.getInstance(mContext).insertTest(subject.getSubjectType(), billId, subject.getChapterId(), db);
                    }

                    break;
                default:
                    break;
            }
        }

    }
}
