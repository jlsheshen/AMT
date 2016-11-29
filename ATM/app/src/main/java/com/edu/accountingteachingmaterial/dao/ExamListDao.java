package com.edu.accountingteachingmaterial.dao;

import android.content.Context;
import android.database.Cursor;

import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.library.data.BaseData;
import com.edu.library.data.BaseDataDao;
import com.edu.testbill.Constant;

/**
 * Created by Administrator on 2016/11/28.
 */

public class ExamListDao extends BaseDataDao {
    public static final String TYPE = "TYPE";
    public static final String STATE = "STATE";
    private static ExamListDao instance = null;

    public static ExamListDao getInstance(Context context) {
        if (instance == null)
            instance = new ExamListDao(context, Constant.DATABASE_NAME);
        return instance;
    }


    private ExamListDao(Context context, String dbName) {
        super(context, dbName);

    }
    @Override
    public void setTableName() {
        TABLE_NAME = "TB_EXAM";
    }

    @Override
    public BaseData parseCursor(Cursor curs) {
        ExamListData examListData  = new ExamListData();

        examListData.setId(curs.getInt(curs.getColumnIndex(ID)));
        examListData.setExam_type(curs.getInt(curs.getColumnIndex(TYPE)));
        examListData.setState(curs.getInt(curs.getColumnIndex(STATE)));


        return examListData;
    }
}
