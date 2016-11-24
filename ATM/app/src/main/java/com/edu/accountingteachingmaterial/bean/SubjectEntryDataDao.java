package com.edu.accountingteachingmaterial.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.edu.accountingteachingmaterial.constant.TbName;
import com.edu.library.data.BaseDataDao;
import com.edu.library.data.DBHelper;
import com.edu.testbill.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 分录数据操作dao
 *
 * @author lucher
 */
public class SubjectEntryDataDao extends BaseDataDao {

    private static final String TAG = "EntryDataDao";

    /**
     * 详细信息见doc/EduBasicAccounting_database.xls
     */
    public static final String SERVER_ID = "SERVER_ID";
    public static final String CHAPTER_ID = "CHAPTER_ID";
    public static final String QUESTION = "QUESTION";
    public static final String PICNAME = "PIC_NAME";
    public static final String BORROWANS = "BORROW";
    public static final String LOANANS = "LOAN";
    public static final String BORROWUSER = "BORROW_USER";
    public static final String LOANUSER = "LOAN_USER";
    public static final String IS_CORRECT = "IS_CORRECT";
    public static final String SCORE = "SCORE";
    public static final String USCORE = "USCORE";
    public static final String TYPE = "TYPE";
    public static final String CHILDREN = "CHILDREN";

    /**
     * 自身实例
     */
    private static SubjectEntryDataDao instance = null;

    public SubjectEntryDataDao(Context context) {
        super(context, Constant.DATABASE_NAME);
        TABLE_NAME = "TB_SUBJECT_ENTRY";
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static SubjectEntryDataDao getInstance(Context context) {
        if (instance == null)
            instance = new SubjectEntryDataDao(context);
        return instance;
    }

    /**
     * 获取ids指定id的所有数据
     *
     * @param ids
     * @param cleanUserAnswer
     * @return
     */
    public List<SubjectEntryData> getDatasIn(String ids, boolean cleanUserAnswer) {
        Cursor curs = null;
        List<SubjectEntryData> datas = null;

        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID IN (" + ids + ")";
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);

            if (curs != null && curs.getCount() != 0) {
                curs.moveToFirst();
                int count = curs.getCount();
                datas = new ArrayList<SubjectEntryData>(count);

                for (int i = 0; i < count; i++) {
                    SubjectEntryData data = parseCursor(curs);
                    if (cleanUserAnswer) {
                        data.setBorrowUser("");
                        data.setLoanUser("");
                    }
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
     * 获取ids指定id的所有数据
     *
     * @param ids
     * @param cleanUserAnswer
     * @return
     */
    public SubjectEntryData getDatasInTest(int ids, boolean cleanUserAnswer) {
        Cursor curs = null;
        SubjectEntryData data = null;

        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID =" + ids;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);

            if (curs != null && curs.getCount() != 0) {
                curs.moveToFirst();
                data = parseCursor(curs);
                if (cleanUserAnswer) {
                    data.setBorrowUser("");
                    data.setLoanUser("");
                }

                Log.i(TAG, "data:" + data);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb(mDb, curs);
        }
        return data;
    }

    /**
     * 获取所有id号的list
     *
     * @return
     */
    public List<Integer> getAllIds() {
        Cursor curs = null;
        List<Integer> ids = null;
        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);

            if (curs != null && curs.getCount() != 0) {
                curs.moveToFirst();
                int count = curs.getCount();
                ids = new ArrayList<Integer>(count);

                for (int i = 0; i < count; i++) {
                    int idIndex = curs.getColumnIndex(ID);
                    ids.add(curs.getInt(idIndex));

                    Log.i(TAG, "data:" + ids);
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
        return ids;
    }

    /**
     * 获得所有数据
     *
     * @param chapterId 章节id
     * @return
     */
    public List<SubjectEntryData> getAllIDatasByType(int chapterId) {
        Cursor curs = null;
        List<SubjectEntryData> datas = null;
        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "  SELECT TB_SUBJECT_ENTRY.ID,TB_SUBJECT_ENTRY.SERVER_ID,TB_SUBJECT_ENTRY.BORROW,TB_SUBJECT_ENTRY.BORROW_USER,TB_SUBJECT_ENTRY.CHAPTER_ID,TB_SUBJECT_ENTRY.CHILDREN,TB_SUBJECT_ENTRY.IS_CORRECT,TB_SUBJECT_ENTRY.LOAN,TB_SUBJECT_ENTRY.LOAN_USER,TB_SUBJECT_ENTRY.PIC_NAME, TB_SUBJECT_ENTRY.QUESTION,TB_SUBJECT_ENTRY.SCORE,TB_SUBJECT_ENTRY.TYPE,TB_SUBJECT_ENTRY.USCORE,TB_SUBJECT_ENTRY.REMARK  FROM "
                    + TABLE_NAME + " LEFT JOIN TB_CHAPTER ON TB_SUBJECT_ENTRY.CHAPTER_ID = TB_CHAPTER.ID WHERE PARENT_ID  = " + chapterId + " AND TYPE = 2 " + " OR TYPE  =  0  ";// AND SERVER_ID =  " + server_id
            //+ " AND SERVER_ID =  " + server_id;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);
            if (curs != null && curs.getCount() != 0) {
                datas = new ArrayList<SubjectEntryData>();
                while (curs.moveToNext()) {
                    SubjectEntryData data = parseCursor(curs);
                    datas.add(data);
                    Log.i(TAG, "data:" + data);
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
     * 获取id获取数据
     *
     * @return
     */
    public SubjectEntryData getEntryData(int id) {
        Cursor curs = null;
        SubjectEntryData data = null;

        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID =" + id;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);

            if (curs != null && curs.getCount() != 0) {
                curs.moveToFirst();
                data = parseCursor(curs);

                Log.i(TAG, "data:" + data);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb(mDb, curs);
        }
        return data;
    }

    /**
     * 获取最后一条数据
     *
     * @param chapterId
     * @param type
     * @param server_id
     * @return
     */
    public SubjectEntryData getDataLast(int chapterId, int type, int server_id) {

        SubjectEntryData data = null;
        Cursor curs = null;
        String sql = " SELECT * FROM " + TABLE_NAME + "  WHERE CHAPTER_ID  = " + chapterId + " AND TYPE =  " + type + " AND SERVER_ID = " + server_id;
        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            curs = mDb.rawQuery(sql, null);

            if (curs != null) {
                if (curs.getCount() == 0)
                    return null;
                curs.moveToLast();
                data = parseCursor(curs);

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
     * 拷贝
     *
     * @return
     */
    public void getCopyData(int id, int createServerId) {
        Cursor curs = null;
        SubjectEntryData data = null;

        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = " INSERT INTO  " + TABLE_NAME + " (SERVER_ID,CHAPTER_ID,QUESTION,PIC_NAME,BORROW,LOAN,BORROW_USER,LOAN_USER,IS_CORRECT,SCORE,USCORE,TYPE,CHILDREN,REMARK ) SELECT  "
                    + createServerId + " ,CHAPTER_ID,QUESTION,PIC_NAME,BORROW,LOAN,'','','',SCORE,'',TYPE,CHILDREN,REMARK  FROM " + TABLE_NAME + " WHERE ID  = " + id;
            Log.e(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);

            if (curs != null && curs.getCount() != 0) {
                curs.moveToFirst();
                data = parseCursor(curs);

                Log.i(TAG, "data:" + data);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Log.e(TAG, "entryDao" + "有问题");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "entryDao" + "有问题");
        } finally {
            closeDb(mDb, curs);
        }
        // return data;
    }

    /**
     * 获取最后一条数据
     *
     * @return
     */
    public SubjectEntryData getDataLast() {

        SubjectEntryData data = null;
        Cursor curs = null;
        String sql = "SELECT ID,TYPE,CHILDREN FROM " + TABLE_NAME;
        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            curs = mDb.rawQuery(sql, null);

            if (curs != null) {
                if (curs.getCount() == 0)
                    return null;
                curs.moveToLast();
                // data = parseCursor(curs);
                data = new SubjectEntryData();
                int idIndex = curs.getColumnIndex(ID);
                data.setId(curs.getInt(idIndex));
                data.setType(curs.getInt(curs.getColumnIndex(TYPE)));
                data.setChildren(curs.getString(curs.getColumnIndex(CHILDREN)));
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
     * 获取数据（用于章节测试分数计算）
     *
     * @return
     */
    public SubjectEntryData getDataForId(int id) {

        SubjectEntryData data = null;
        Cursor curs = null;
        String sql = "SELECT ID,TYPE,CHILDREN, IS_CORRECT,SCORE  FROM " + TABLE_NAME + " WHERE ID = " + id;
        try {
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            curs = mDb.rawQuery(sql, null);

            if (curs != null) {
                if (curs.getCount() == 0)
                    return null;
                curs.moveToLast();

                data = new SubjectEntryData();
                int idIndex = curs.getColumnIndex(ID);
                data.setId(curs.getInt(idIndex));
                data.setType(curs.getInt(curs.getColumnIndex(TYPE)));
                data.setChildren(curs.getString(curs.getColumnIndex(CHILDREN)));
                data.setCorrect(curs.getInt(curs.getColumnIndex(IS_CORRECT)) == 0 ? false : true);
                data.setScore(curs.getFloat(curs.getColumnIndex(SCORE)));
                Log.e("得分专用Log", "获取得分2" + data.getScore());


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
     * 更新数据
     *
     * @param id
     * @param values
     */
    public void updateDataS(String id, ContentValues values) {
        try {
            Log.d(TAG, TABLE_NAME + "-updateData:" + id);
            DBHelper helper = new DBHelper(mContext, TbName.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            mDb.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb(mDb);
        }
    }

    @Override
    public void setTableName() {

    }

    @Override
    public SubjectEntryData parseCursor(Cursor curs) {
        SubjectEntryData data = new SubjectEntryData();
        int idIndex = curs.getColumnIndex(ID);
        int questionIndex = curs.getColumnIndex(QUESTION);
        int picnameIndex = curs.getColumnIndex(PICNAME);
        int borrowansIndex = curs.getColumnIndex(BORROWANS);
        int loanansIndex = curs.getColumnIndex(LOANANS);
        int borrowuserIndex = curs.getColumnIndex(BORROWUSER);
        int loanuserIndex = curs.getColumnIndex(LOANUSER);
        int remarkIndex = curs.getColumnIndex(REMARK);
        int chapter_id = curs.getColumnIndex(CHAPTER_ID);
        data.setId(curs.getInt(idIndex));
        data.setServerId(curs.getInt(curs.getColumnIndex(SERVER_ID)));
        data.setCorrect(curs.getInt(curs.getColumnIndex(IS_CORRECT)) == 0 ? false : true);
//        data.setuScore(curs.getFloat(curs.getColumnIndex(USCORE)));
        data.setScore(curs.getFloat(curs.getColumnIndex(SCORE)));
        data.setType(curs.getInt(curs.getColumnIndex(TYPE)));
        data.setChildren(curs.getString(curs.getColumnIndex(CHILDREN)));
        data.setQuestion(curs.getString(questionIndex));
        data.setPicName(curs.getString(picnameIndex));
        data.setBorrowAns(curs.getString(borrowansIndex));
        data.setLoanAns(curs.getString(loanansIndex));
        data.setBorrowUser(curs.getString(borrowuserIndex));
        data.setLoanUser(curs.getString(loanuserIndex));
        data.setRemark(curs.getString(remarkIndex));
        data.setChapter_id(curs.getInt(chapter_id));

        Log.e("得分专用Log", "获取得分6" + data.getScore());

        return data;
    }


}
