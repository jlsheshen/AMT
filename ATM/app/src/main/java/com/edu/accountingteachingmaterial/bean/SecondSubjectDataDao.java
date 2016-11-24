package com.edu.accountingteachingmaterial.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.edu.accountingteachingmaterial.constant.TbName;
import com.edu.library.data.BaseData;
import com.edu.library.data.BaseDataDao;
import com.edu.library.data.DBHelper;
import com.edu.testbill.Constant;

import java.util.ArrayList;
import java.util.List;

public class SecondSubjectDataDao extends BaseDataDao {

    private static final String TAG = "SecondSubjectDataDao";

    public static final String NAME = "NAME";

    public static final String DEPENDENTID = "DEPENDENT_ID";

    /**
     * 自身实例
     */
    private static SecondSubjectDataDao instance = null;

    public SecondSubjectDataDao(Context context) {
        super(context, Constant.DATABASE_NAME);
        TABLE_NAME = "TB_SECOND_SUBJECT";
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static SecondSubjectDataDao getInstance(Context context) {
        if (instance == null)
            instance = new SecondSubjectDataDao(context);
        return instance;
    }

    public List<BaseData> getAllDatas() {
        Cursor curs = null;
        List<BaseData> datas = null;

        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID ASC";
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);

            if (curs != null && curs.getCount() != 0) {
                curs.moveToFirst();
                int count = curs.getCount();
                datas = new ArrayList<BaseData>(count);

                for (int i = 0; i < count; i++) {
                    SecondSubjectData data = new SecondSubjectData();
                    data.setId(curs.getInt(curs.getColumnIndex(ID)));

                    data.setName(curs.getString(curs.getColumnIndex(NAME)));
                    data.setDependentid(curs.getInt(curs.getColumnIndex(DEPENDENTID)));
                    data.setRemark(curs.getString(curs.getColumnIndex(REMARK)));

                    datas.add(data);

                    Log.i(TAG, "data:" + data);
                    if (curs.moveToNext() == false)
                        break;
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

    public BaseData getDataById(int id) {

        SecondSubjectData data = null;
        Cursor curs = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id;
        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            curs = mDb.rawQuery(sql, null);
            if (curs != null) {
                if (curs.getCount() == 0)
                    return null;
                curs.moveToFirst();

                int idIndex = curs.getColumnIndex(ID);
                int nameIndex = curs.getColumnIndex(NAME);
                int dependentidIndex = curs.getColumnIndex(DEPENDENTID);
                int remarkIndex = curs.getColumnIndex(REMARK);
                data = new SecondSubjectData();
                data.setId(curs.getInt(idIndex));
                data.setName(curs.getString(nameIndex));
                data.setDependentid(curs.getInt(dependentidIndex));
                data.setRemark(curs.getString(remarkIndex));


                Log.d(TAG, "data:" + data);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeDb(mDb, curs);
        }

        return data;
    }

    /**
     * 获取指定DEPENDENT_ID的所有数据
     *
     * @param dependentId
     * @return
     */
    public List<SecondSubjectData> getDatasByDependid(int dependentId) {
        Cursor curs = null;
        List<SecondSubjectData> datas = null;

        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE DEPENDENT_ID = " + dependentId;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);

            if (curs != null && curs.getCount() != 0) {
                curs.moveToFirst();
                int count = curs.getCount();
                datas = new ArrayList<SecondSubjectData>(count);

                for (int i = 0; i < count; i++) {
                    int idIndex = curs.getColumnIndex(ID);
                    int nameIndex = curs.getColumnIndex(NAME);
                    int dependentidIndex = curs.getColumnIndex(DEPENDENTID);
                    int remarkIndex = curs.getColumnIndex(REMARK);
                    SecondSubjectData data = new SecondSubjectData();
                    //data = new SecondSubjectData();
                    data.setId(curs.getInt(idIndex));
                    data.setName(curs.getString(nameIndex));
                    data.setDependentid(curs.getInt(dependentidIndex));
                    data.setRemark(curs.getString(remarkIndex));
                    datas.add(data);


                    Log.i(TAG, "data:" + data);
                    if (curs.moveToNext() == false)
                        break;
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

    }

    @Override
    public BaseData parseCursor(Cursor curs) {
        // TODO Auto-generated method stub
        return null;
    }

}
