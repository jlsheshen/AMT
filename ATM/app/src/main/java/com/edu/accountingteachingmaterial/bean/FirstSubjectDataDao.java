package com.edu.accountingteachingmaterial.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.edu.accountingteachingmaterial.constant.Constant;
import com.edu.accountingteachingmaterial.constant.TbName;
import com.edu.library.data.BaseData;
import com.edu.library.data.BaseDataDao;
import com.edu.library.data.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class FirstSubjectDataDao extends BaseDataDao {

    private static final String TAG = "FirstSubjectDataDao";

    public static final String SUBTYPE = "SUB_TYPE";

    public static final String NAME = "NAME";

    public static final String PARENTID = "PARENT_ID";

    /**
     * 自身实例
     */
    private static FirstSubjectDataDao instance = null;

    public FirstSubjectDataDao(Context context) {
        super(context, Constant.DATABASE_NAME);
        TABLE_NAME = "TB_FIRST_SUBJECT";
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static FirstSubjectDataDao getInstance(Context context) {
        if (instance == null)
            instance = new FirstSubjectDataDao(context);
        return instance;
    }

    /**
     * 以BaseData的格式返回所有科目数据
     *
     * @return
     */
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
                    FirstSubjectData data = new FirstSubjectData();
                    data.setId(curs.getInt(curs.getColumnIndex(ID)));
                    data.setSubtype(curs.getInt(curs.getColumnIndex(SUBTYPE)));
                    data.setName(curs.getString(curs.getColumnIndex(NAME)));
                    data.setParentid(curs.getInt(curs.getColumnIndex(PARENTID)));
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

        FirstSubjectData data = null;
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
                int subtypeIndex = curs.getColumnIndex(SUBTYPE);
                int nameIndex = curs.getColumnIndex(NAME);
                int parentidIndex = curs.getColumnIndex(PARENTID);
                int remarkIndex = curs.getColumnIndex(REMARK);
                data = new FirstSubjectData();
                data.setId(curs.getInt(idIndex));
                data.setSubtype(curs.getInt(subtypeIndex));
                data.setName(curs.getString(nameIndex));
                data.setParentid(curs.getInt(parentidIndex));
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
     * 获取指定SUB_TYPE的所有数据 1 or 2
     *
     * @param subType
     * @return
     */
    public List<FirstSubjectData> getDatasByType(int subType) {
        Cursor curs = null;
        List<FirstSubjectData> datas = null;

        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE SUB_TYPE = " + subType;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);

            if (curs != null && curs.getCount() != 0) {
                curs.moveToFirst();
                int count = curs.getCount();
                datas = new ArrayList<FirstSubjectData>(count);

                for (int i = 0; i < count; i++) {
                    int idIndex = curs.getColumnIndex(ID);
                    int subtypeIndex = curs.getColumnIndex(SUBTYPE);
                    int nameIndex = curs.getColumnIndex(NAME);
                    int parentidIndex = curs.getColumnIndex(PARENTID);
                    int remarkIndex = curs.getColumnIndex(REMARK);
                    FirstSubjectData data = new FirstSubjectData();
                    //data = new FirstSubjectData();
                    data.setId(curs.getInt(idIndex));
                    data.setSubtype(curs.getInt(subtypeIndex));
                    data.setName(curs.getString(nameIndex));
                    data.setParentid(curs.getInt(parentidIndex));
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

    /**
     * 获取指定PARENT_ID的所有数据
     *
     * @param parentId
     * @return
     */
    public List<FirstSubjectData> getDatasByParentid(int parentId) {
        Cursor curs = null;
        List<FirstSubjectData> datas = null;

        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE PARENT_ID = " + parentId;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);

            if (curs != null && curs.getCount() != 0) {
                curs.moveToFirst();
                int count = curs.getCount();
                datas = new ArrayList<FirstSubjectData>(count);

                for (int i = 0; i < count; i++) {
                    int idIndex = curs.getColumnIndex(ID);
                    int subtypeIndex = curs.getColumnIndex(SUBTYPE);
                    int nameIndex = curs.getColumnIndex(NAME);
                    int parentidIndex = curs.getColumnIndex(PARENTID);
                    int remarkIndex = curs.getColumnIndex(REMARK);
                    FirstSubjectData data = new FirstSubjectData();
                    //data = new FirstSubjectData();
                    data.setId(curs.getInt(idIndex));
                    data.setSubtype(curs.getInt(subtypeIndex));
                    data.setName(curs.getString(nameIndex));
                    data.setParentid(curs.getInt(parentidIndex));
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

//	@Override
//	public void setTableName() {
//		TABLE_NAME = "TB_FIRST_SUBJECT";
//		
//	}

    @Override
    public void setTableName() {

    }

    @Override
    public BaseData parseCursor(Cursor curs) {
        // TODO Auto-generated method stub
        return null;
    }


}
