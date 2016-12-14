package com.edu.accountingteachingmaterial.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.edu.library.data.BaseData;
import com.edu.library.data.BaseDataDao;
import com.edu.library.data.DBHelper;
import com.edu.testbill.Constant;

/**
 * Created by Administrator on 2016/12/13.
 */

public class TemplateElementsDao extends BaseDataDao {

    private static TemplateElementsDao instance = null;

    private TemplateElementsDao(Context context, String dbName) {
        super(context, dbName);
    }
    public static TemplateElementsDao getInstance(Context context) {
        if (instance == null)
            instance = new TemplateElementsDao(context, Constant.DATABASE_NAME);
        return instance;
    }
    /**
     * 删除数据
     *
     * @param id
     */
    public synchronized void deleteData(long id) {
        try {
            Log.e(TAG, TABLE_NAME + "-deleteData:" + id);
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            mDb.delete(TABLE_NAME, "TEMPLATE_ID =?", new String[] { String.valueOf(id) });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb(mDb);
        }
    }


    @Override
    public void setTableName() {
        TABLE_NAME = "TB_TEMPLATE_ELEMENTS";
    }

    @Override
    public BaseData parseCursor(Cursor curs) {
        return null;
    }
}
