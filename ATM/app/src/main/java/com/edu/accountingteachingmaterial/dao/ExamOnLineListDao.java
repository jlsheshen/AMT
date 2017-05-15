package com.edu.accountingteachingmaterial.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.edu.accountingteachingmaterial.constant.Constant;
import com.edu.accountingteachingmaterial.entity.OnLineExamListData;
import com.edu.library.data.BaseData;
import com.edu.library.data.BaseDataDao;
import com.edu.library.data.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */

public class ExamOnLineListDao extends BaseDataDao {
    public static final String TYPE = "TYPE";
    public static final String STATE = "STATE";
    private static ExamOnLineListDao instance = null;
    public static final String USER_ID = "USER_ID";




    public static ExamOnLineListDao getInstance(Context context) {
        if (instance == null)
            instance = new ExamOnLineListDao(context, Constant.DATABASE_NAME);
        return instance;
    }


    private ExamOnLineListDao(Context context, String dbName) {
        super(context, dbName);
    }
    /**
     * 获取所有题目
     *
     * @param
     */
    public List<OnLineExamListData> getDataByUser(int user) {
        Cursor curs = null;
        List<OnLineExamListData> datas = null;
        try {
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ID + " = " + user;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);
            if (curs != null) {
                datas = new ArrayList<OnLineExamListData>(curs.getCount());
                int index = 1;
                while (curs.moveToNext()) {

                    datas.add((OnLineExamListData) parseCursor(curs));
                    // 初始化测试数据
//                    BaseTestData testData = initTestData(curs);
//                    testData.setSubjectIndex(String.valueOf(index++));
//                    Log.d(TAG, "testData.getState():" + testData.getState() + "---" + testData.getuAnswer() + "---" + testData.getRemark());
//                    datas.add(testData);
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
        TABLE_NAME = "TB_ONLINE_EXAM_LIST";
    }


    @Override
    public BaseData parseCursor(Cursor curs) {
        OnLineExamListData examListData  = new OnLineExamListData();

        examListData.setExam_id(curs.getInt(curs.getColumnIndex(ID)));
        examListData.setExam_type(curs.getInt(curs.getColumnIndex(TYPE)));
        examListData.setState(curs.getInt(curs.getColumnIndex(STATE)));
        examListData.setU_id(curs.getInt(curs.getColumnIndex(USER_ID)));

        return examListData;
    }

    public int  getState(String chapter) {
        Cursor curs = null;
        int state = 0;
        try {
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT STATE FROM " + TABLE_NAME + " WHERE " + ID + " = " + chapter;
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
