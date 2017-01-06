package com.edu.accountingteachingmaterial.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edu.accountingteachingmaterial.bean.SubjectBasicData;
import com.edu.accountingteachingmaterial.bean.SubjectEntryDataDao;
import com.edu.accountingteachingmaterial.bean.TestBasicData;
import com.edu.accountingteachingmaterial.bean.TestEntryData;
import com.edu.library.data.BaseData;
import com.edu.library.data.BaseDataDao;
import com.edu.library.data.DBHelper;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectConstant;
import com.edu.subject.SubjectState;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.bill.template.BillTemplate;
import com.edu.subject.bill.template.BillTemplateFactory;
import com.edu.subject.dao.SubjectBillDataDao;
import com.edu.subject.data.BaseSubjectData;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.SubjectBillData;
import com.edu.subject.data.TestBillData;
import com.edu.subject.data.TestGroupBillData;
import com.edu.testbill.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试数据数据库操作dao层
 *
 * @author lucher
 */
public class ReviewTestDataDao extends BaseDataDao {

    //题目类型
    public static final String SUBJECT_ID = "SUBJECT_ID";
    //试卷类型
    public static final String FLAG = "FLAG";

    //试卷id
    public static final String CHAPTER_ID = "CHAPTER_ID";

    // 用户答案
    public static final String UANSWER = "UANSWER";
    // 用户印章-单据题
    public static final String USIGNS = "USIGNS";
    // 用户得分
    public static final String USCORE = "USCORE";
    // 题目状态
    public static final String STATE = "STATE";
    //题目类型
    public static final String SUBJECT_TYPE = "SUBJECT_TYPE";
    //错误次数
    public static final String ERROR_COUNT = "ERROR_COUNT";

    //REMARK
    public static final String REMARK = "REMARK";


    /**
     * 自身引用
     */
    private static ReviewTestDataDao instance = null;

    private ReviewTestDataDao(Context context) {
        super(context, Constant.DATABASE_NAME);
    }

    @Override
    public void setTableName() {
        TABLE_NAME = "TB_REVIEW_TEST";
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static ReviewTestDataDao getInstance(Context context) {
        if (instance == null)
            instance = new ReviewTestDataDao(context);
        return instance;
    }

    /**
     * 获取所有题目
     *
     * @param testMode 测试模式，见{@link TestMode}
     * @return
     */
    public List<BaseTestData> getSubjects(int testMode,int chapter) {
        Cursor curs = null;
        List<BaseTestData> datas = null;
        try {
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + CHAPTER_ID + " = " + chapter;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);
            if (curs != null) {
                datas = new ArrayList<BaseTestData>(curs.getCount());
                int index = 1;
                while (curs.moveToNext()) {
                    // 初始化测试数据
                    BaseTestData testData = initTestData(curs, testMode);
                    testData.setSubjectIndex(String.valueOf(index++));
                    Log.d(TAG, "testData.getState():" + testData.getState() + "---" + testData.getuAnswer() + "---" + testData.getRemark());
                    datas.add(testData);
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

    public List<BaseTestData> getErrors(int testMode) {
        Cursor curs = null;
        List<BaseTestData> datas = null;
        try {
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TABLE_NAME;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);
            if (curs != null) {
                datas = new ArrayList<BaseTestData>(curs.getCount());
                int index = 1;
                while (curs.moveToNext()) {
                    // 初始化测试数据
                    BaseTestData testData = initTestData(curs, testMode);
                    testData.setSubjectIndex(String.valueOf(index++));
                    Log.d(TAG, "testData.getState():" + testData.getState() + "---" + testData.getuAnswer() + "---" + testData.getRemark());
                    datas.add(testData);
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
         * 删除数据
         *
         * @param examId
         */
    public synchronized void deleteData(long examId) {
        try {
            Log.e(TAG, TABLE_NAME + "-deleteData:" + examId);
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            mDb.delete(TABLE_NAME, ID + "=?", new String[] { String.valueOf(examId) });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb(mDb);
        }
    }



    /**
     * 初始化测试数据
     *
     * @param curs
     * @param testMode
     * @return
     */
    private BaseTestData initTestData(Cursor curs, int testMode) {
        BaseTestData testData = null;
        BaseSubjectData subjectData = null;
        int subjectType = curs.getInt(curs.getColumnIndex(SUBJECT_TYPE));

        switch (subjectType) {
            case SubjectType.SUBJECT_GROUP_BILL:
                // 初始化测试数据
                testData = new TestGroupBillData();
                testData.setTestMode(testMode);
                parseCursor(curs, testData, -1);
                // 初始化题目数据,分组单据存在多张单据题目
                List<SubjectBillData> subjcets = SubjectBillDataDao.getInstance(mContext, Constant.DATABASE_NAME).getDatas(testData.getSubjectId(), mDb);
                List<TestBillData> testBills = new ArrayList<TestBillData>(subjcets.size());
                ((TestGroupBillData) testData).setTestDatas(testBills);
                for (int i = 0; i < subjcets.size(); i++) {
                    SubjectBillData subject = subjcets.get(i);
                    ((TestGroupBillData) testData).setScore(subject.getScore());
                    TestBillData testBill = new TestBillData();
                    testBill.setTestMode(testMode);
                    testBills.add(testBill);
                    parseCursor(curs, testBill, i);
                    testBill.setSubjectData(subject);
                    // 初始化模板数据
                    BillTemplate template = BillTemplateFactory.createTemplate(mDb, Integer.parseInt(((SubjectBillData) subject).getTemplateId()), mContext);
                    testBill.setTemplate(template);
                    String result = testBill.loadTemplate(mContext);
                    if (result.equals("success")) {
                        Log.d(TAG, "load data success:" + testData);
                    } else {
                        Log.e(TAG, "load data error:" + result);
                        ToastUtil.showToast(mContext, result);
                    }
                }
                break;
            case SubjectType.SUBJECT_BILL:
                // 初始化测试数据
                testData = new TestBillData();
                testData.setTestMode(testMode);
                parseCursor(curs, testData);
                // 初始化题目数据
                subjectData = SubjectBillDataDao.getInstance(mContext, Constant.DATABASE_NAME).getData(testData.getSubjectId(), mDb);
                testData.setSubjectData(subjectData);
                // 初始化模板数据
                BillTemplate template = BillTemplateFactory.createTemplate(mDb, Integer.parseInt(((SubjectBillData) subjectData).getTemplateId()), mContext);
                ((TestBillData) testData).setTemplate(template);
                String result = ((TestBillData) testData).loadTemplate(mContext);
                if (result.equals("success")) {
                    Log.d(TAG, "load data success:" + testData);
                } else {
                    Log.e(TAG, "load data error:" + result);
                    ToastUtil.showToast(mContext, result);
                }

                break;
            case SubjectType.SUBJECT_ENTRY:
                testData = new TestEntryData();
                testData.setTestMode(testMode);
                parseCursor(curs, testData);
                subjectData = (BaseSubjectData) SubjectEntryDataDao.getInstance(mContext).getDataById(Integer.valueOf(testData.getSubjectId().split(">>>")[0]));
                testData.setSubjectData(subjectData);
                int a = 0;
                break;


            case SubjectType.SUBJECT_SINGLE:
                testData = new TestBasicData();
                testData.setTestMode(testMode);
                parseCursor(curs, testData);
                // 初始化题目数据
                subjectData = (SubjectBasicData) SubjectBasicDataDao.getInstance(mContext, Constant.DATABASE_NAME).getData(testData.getSubjectId(), mDb);
                testData.setSubjectData(subjectData);
                break;

            case SubjectType.SUBJECT_MULTI:
                // 初始化测试数据
                testData = new TestBasicData();
                testData.setTestMode(testMode);
                parseCursor(curs, testData);
                // 初始化题目数据
                subjectData = (SubjectBasicData) SubjectBasicDataDao.getInstance(mContext, Constant.DATABASE_NAME).getData(testData.getSubjectId(), mDb);
                testData.setSubjectData(subjectData);
                break;

            case SubjectType.SUBJECT_JUDGE:
                // 初始化测试数据
                testData = new TestBasicData();
                testData.setTestMode(testMode);
                parseCursor(curs, testData);
                // 初始化题目数据
                subjectData = (SubjectBasicData) SubjectBasicDataDao.getInstance(mContext, Constant.DATABASE_NAME).getData(testData.getSubjectId(), mDb);
                testData.setSubjectData(subjectData);

                break;

            default:
                break;
        }

        return testData;
    }

    /**
     * cursor解析
     *
     * @param curs
     * @param data
     */
    public void parseCursor(Cursor curs, BaseTestData data) {
        data.setId(curs.getInt(curs.getColumnIndex("ID")));
        data.setFlag(curs.getInt(curs.getColumnIndex(FLAG)));
        data.setSubjectType(curs.getInt(curs.getColumnIndex(SUBJECT_TYPE)));
        data.setSubjectId(curs.getString(curs.getColumnIndex(SUBJECT_ID)));
        data.setRemark(curs.getString(curs.getColumnIndex(REMARK)));
        if (data.getTestMode() == TestMode.MODE_EXAM) {// 测试模式不加载用户数据
            data.setuAnswer(null);
            data.setuScore(0);
            data.setState(SubjectState.STATE_INIT);
            if (data.getSubjectType() == SubjectType.SUBJECT_BILL) {
                ((TestBillData) data).setuSigns(null);
            }
        } else {
            data.setuAnswer(curs.getString(curs.getColumnIndex(UANSWER)));
            data.setuScore(curs.getInt(curs.getColumnIndex(USCORE)));
            data.setState(curs.getInt(curs.getColumnIndex(STATE)));
            data.setErrorCount(curs.getInt(curs.getColumnIndex(ERROR_COUNT)));
            if (data.getSubjectType() == SubjectType.SUBJECT_BILL) {
                ((TestBillData) data).setuSigns(curs.getString(curs.getColumnIndex(USIGNS)));
            }
        }
    }
    /**
     * 根据chatperid获取对应数据
     *
     * @param id
     * @return
     */
    public synchronized BaseData getDataByChatperId(int chatperid) {
        BaseData data = null;
        Cursor curs = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE CHAPTER_ID = " + CHAPTER_ID;
        try {
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            curs = mDb.rawQuery(sql, null);
            if (curs != null) {
                if (curs.getCount() == 0)
                    return null;
                curs.moveToFirst();
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
     * cursor解析,用于多组单据
     *
     * @param curs
     * @param data
     * @param index
     */
    public void parseCursor(Cursor curs, BaseTestData data, int index) {
        data.setId(curs.getInt(curs.getColumnIndex("ID")));
        data.setFlag(curs.getInt(curs.getColumnIndex(FLAG)));
        data.setSubjectType(curs.getInt(curs.getColumnIndex(SUBJECT_TYPE)));
        data.setSubjectId(curs.getString(curs.getColumnIndex(SUBJECT_ID)));
        data.setRemark(curs.getString(curs.getColumnIndex(REMARK)));
        if (data.getTestMode() == TestMode.MODE_EXAM) {// 测试模式不加载用户数据
            data.setuAnswer(null);
            data.setuScore(0);
            data.setState(SubjectState.STATE_INIT);
            if (data instanceof TestBillData) {
                ((TestBillData) data).setuSigns(null);
            } else if (data instanceof TestGroupBillData) {
                ((TestGroupBillData) data).setuSigns(null);
            }
        } else {
            data.setuScore(curs.getInt(curs.getColumnIndex(USCORE)));
            data.setState(curs.getInt(curs.getColumnIndex(STATE)));
            if (index == -1) {// group
                ((TestGroupBillData) data).setuSigns(curs.getString(curs.getColumnIndex(USIGNS)));
                data.setuAnswer(curs.getString(curs.getColumnIndex(UANSWER)));
            } else {
                // 解析对应单据的用户答案
                String answer = curs.getString(curs.getColumnIndex(UANSWER));
                String sign = curs.getString(curs.getColumnIndex(USIGNS));
                if (answer != null && !answer.equals("")) {
                    String[] answers = answer.split(SubjectConstant.SEPARATOR_GROUP);
                    data.setuAnswer(answers[index]);
                } else {
                    data.setuAnswer(answer);
                }
                if (sign != null && !sign.equals("")) {
                    String[] signs = sign.split(SubjectConstant.SEPARATOR_GROUP);
                    ((TestBillData) data).setuSigns(signs[index]);
                } else {
                    ((TestBillData) data).setuSigns(sign);
                }
            }
        }
    }

    /**
     * 更新指定的测试数据
     *
     * @param data
     */
    public synchronized void updateTestData(BaseTestData data) {
        try {
            Log.d(TAG, TABLE_NAME + "-updateData");
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            long id = data.getId();
            ContentValues values = new ContentValues();
            values.put(UANSWER, data.getuAnswer());
            values.put(USCORE, data.getuScore());
            values.put(STATE, data.getState());
            if (data instanceof TestBillData) {
                values.put(USIGNS, ((TestBillData) data).getuSigns());
            } else if (data instanceof TestGroupBillData) {
                values.put(USIGNS, ((TestGroupBillData) data).getuSigns());
            }
            mDb.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb(mDb);
        }
    }

    /**
     * 批量更新测试数据
     *
     * @param datas
     */
    public synchronized void updateTestDatas(List<BaseTestData> datas) {
        try {
            Log.d(TAG, TABLE_NAME + "-updateDatas");
            DBHelper helper = new DBHelper(mContext, dbName, null);
            mDb = helper.getWritableDatabase();
            mDb.beginTransaction();
            for (BaseTestData data : datas) {
                long id = data.getId();
                ContentValues values = new ContentValues();
                values.put(UANSWER, data.getuAnswer());
                values.put(USCORE, data.getuScore());
                values.put(STATE, data.getState());
                if (data instanceof TestBillData) {
                    values.put(USIGNS, ((TestBillData) data).getuSigns());
                }
                mDb.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(id)});
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
            closeDb(mDb);
        }
    }
    /**
     * 插入test数据
     * @param subjectType
     * @param subjectId
     * @param db
     */
    public void insertTest(int subjectType, int subjectId,int chapterid, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("FLAG", -1);
        values.put("SUBJECT_TYPE", subjectType);
        values.put("SUBJECT_ID", subjectId);
        values.put(CHAPTER_ID,chapterid);
        values.put("USCORE", 0);
        values.put("STATE", 0);
        db.replace(TABLE_NAME, null, values);
    }


    @Override
    public BaseTestData parseCursor(Cursor curs) {
        return null;
    }
}
