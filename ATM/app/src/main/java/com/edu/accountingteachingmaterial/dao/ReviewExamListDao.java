package com.edu.accountingteachingmaterial.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.library.data.BaseData;
import com.edu.library.data.BaseDataDao;
import com.edu.library.data.DBHelper;
import com.edu.testbill.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */

public class ReviewExamListDao extends BaseDataDao {
    public static final String TYPE = "TYPE";
    public static final String STATE = "STATE";
    private static ReviewExamListDao instance = null;
    public static final String CHAPTER_ID = "CHAPTER_ID";




    public static ReviewExamListDao getInstance(Context context) {
        if (instance == null)
            instance = new ReviewExamListDao(context, Constant.DATABASE_NAME);
        return instance;
    }


    private ReviewExamListDao(Context context, String dbName) {
        super(context, dbName);
    }
    /**
     * 获取所有题目
     *
     * @param
     */
    public List<ExamListData> getDataByChatper(int chapter) {
        Cursor curs = null;
        List<ExamListData> datas = null;
        try {
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + CHAPTER_ID + " = " + chapter;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);
            if (curs != null) {
                datas = new ArrayList<ExamListData>(curs.getCount());
                int index = 1;
                while (curs.moveToNext()) {

                    datas.add((ExamListData) parseCursor(curs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb(mDb, curs);
        }
        return datas;
    }
    @Override
    public void setTableName() {
        TABLE_NAME = "TB_REVIEW_EXAM_LIST";
    }


    @Override
    public BaseData parseCursor(Cursor curs) {
        ExamListData examListData  = new ExamListData();

        examListData.setId(curs.getInt(curs.getColumnIndex(ID)));
        examListData.setExam_type(curs.getInt(curs.getColumnIndex(TYPE)));
        examListData.setState(curs.getInt(curs.getColumnIndex(STATE)));
        examListData.setChapter_id(curs.getInt(curs.getColumnIndex(CHAPTER_ID)));

        return examListData;
    }

    public int  getState(int chapter) {
        Cursor curs = null;
       int state = 0;
        try {
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT STATE FROM " + TABLE_NAME + " WHERE " + CHAPTER_ID + " = " + chapter;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);
            if (curs != null&& curs.getCount() !=0) {
                while (curs.moveToNext()) {
                    state = curs.getInt(0);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb(mDb, curs);
        }
        return state;
    }
}