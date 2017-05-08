package com.edu.accountingteachingmaterial.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.edu.library.data.BaseData;
import com.edu.library.data.BaseDataDao2;

/**
 * Created by Administrator on 2017/5/5.
 */

public class UpdateExamScoreListDao extends BaseDataDao2 {

    protected UpdateExamScoreListDao(Context context, String dbName) {
        super(context, dbName);
    }

    @Override
    public void setTableName() {
        TABLE_NAME = "TB_TEST";
    }

    @Override
    public synchronized long updateData(long id, ContentValues values) {
        return super.updateData(id, values);
    }

    public void updateScore(){



    }

    @Override
    public BaseData parseCursor(Cursor curs) {
        return null;
    }
}
