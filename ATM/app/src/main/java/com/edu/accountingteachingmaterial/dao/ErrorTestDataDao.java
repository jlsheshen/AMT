package com.edu.accountingteachingmaterial.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.edu.accountingteachingmaterial.constant.Constant;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.library.data.BaseDataDao2;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectState;
import com.edu.subject.SubjectType;
import com.edu.subject.bill.template.BillTemplate;
import com.edu.subject.bill.template.BillTemplateFactory;
import com.edu.subject.dao.CommonSubjectDataDao;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.CommonSubjectData;
import com.edu.subject.data.SubjectBillData;
import com.edu.subject.data.SubjectBlankData;
import com.edu.subject.data.SubjectComprehensiveData;
import com.edu.subject.data.SubjectEntryData;
import com.edu.subject.data.SubjectSelectData;
import com.edu.subject.data.TestBasicData;
import com.edu.subject.data.TestBillData;
import com.edu.subject.data.TestBlankData;
import com.edu.subject.data.TestComprehensiveData;
import com.edu.subject.data.TestEntryData;
import com.edu.subject.data.TestGroupBillData;
import com.edu.subject.data.UserAnswerData;
import com.edu.subject.data.answer.BillAnswerData;

import java.util.ArrayList;
import java.util.List;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.USER_ID;

/**
 * 测试数据数据库操作dao层
 *
 * @author lucher
 */
public class ErrorTestDataDao extends BaseDataDao2 {

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

    public static final String USERID = "USERID";
    String userId  = null;


    private static ErrorTestDataDao instance = null;

    private ErrorTestDataDao(Context context) {

        super(context, Constant.DATABASE_NAME);
        userId = PreferenceHelper.getInstance(context).getStringValue(USER_ID);
    }

    @Override
    public void setTableName() {
        TABLE_NAME = "TB_ERROR_TEST";
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static ErrorTestDataDao getInstance(Context context) {
        if (instance == null)
            instance = new ErrorTestDataDao(context);
        return instance;
    }

    public List<BaseTestData> getErrors(int testMode ,String userId) {
        Cursor curs = null;
        List<BaseTestData> datas = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME  + " WHERE " + USERID + " = " + userId;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);
            if (curs != null) {
                datas = new ArrayList<BaseTestData>(curs.getCount());
                int index = 1;
                while (curs.moveToNext()) {
                    // 初始化测试数据
                    BaseTestData testData = initTestData(curs, testMode);
                    testData.setSubjectIndex(String.valueOf(index++));
                    datas.add(testData);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCursor(curs);
        }
        return datas;


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
        CommonSubjectData subjectData = null;
        // 初始化题目数据
        int subjectId = curs.getInt(curs.getColumnIndex(SUBJECT_ID));
//        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + subjectId;
//        curs = mDb.rawQuery(sql, null);
        subjectData = CommonSubjectDataDao.getInstance(mContext).getData(subjectId);

        switch (subjectData.getSubjectType()) {
            case SubjectType.SUBJECT_BILL:
                testData = initBillSubject(curs, subjectData, testMode);

                break;
            //选择类题目初始化
            case SubjectType.SUBJECT_SINGLE:
            case SubjectType.SUBJECT_MULTI:
            case SubjectType.SUBJECT_JUDGE:
                testData = initSelectSubject(curs, subjectData, testMode);

                break;
            case SubjectType.SUBJECT_ENTRY:
                testData = initEntrySubject(curs, subjectData, testMode);

                break;
            case SubjectType.SUBJECT_COMPREHENSIVE:
                testData = initComprehensiveSubject(curs, subjectData, testMode);

                break;
            case SubjectType.SUBJECT_BLANK:
                testData = initBlankSubject(curs, subjectData, testMode);

                break;
            default:
                testData = initCommonSubject(curs, subjectData, testMode);

                break;
        }

        return testData;
    }

    /**
     * 综合类题型初始化
     *
     * 此处修改为通过flag遍历子题
     * @param curs
     * @param subjectData
     * @param testMode
     * @return
     */
    private BaseTestData initComprehensiveSubject(Cursor curs, CommonSubjectData subjectData, int testMode) {
        TestComprehensiveData testData = new TestComprehensiveData();
        testData.setTestMode(testMode);
        SubjectComprehensiveData subject = (SubjectComprehensiveData) subjectData;
        testData.setSubjectData(subject);
        parseCursor(curs, testData, -1);

        //加载子题
        List<CommonSubjectData> subjects = CommonSubjectDataDao.getInstance(mContext).getDatasByParentId(subject.getFlag());
        List<BaseTestData> testDatas = new ArrayList<BaseTestData>(subjects.size());
        List<UserAnswerData> answerDatas = null;
        if (testData.getUAnswerData() != null) {//用户答案为空判断
            answerDatas = testData.getUAnswerData().getAnswerDatas();
        }
        for (int i = 0; i < subjects.size(); i++) {//初始化每个子题的测试数据
            UserAnswerData answer = null;
            if (answerDatas != null) {
                answer = answerDatas.get(i);
            }
            BaseTestData childTestData = initChildSubject(subjects.get(i), testMode, answer);
            testDatas.add(childTestData);
        }
        testData.setTestDatas(testDatas);

        return testData;
    }

    /**
     * 初始化子题测试数据
     * @param subjectData
     * @param testMode
     * @param answerData
     * @return
     */
    private BaseTestData initChildSubject(CommonSubjectData subjectData, int testMode, UserAnswerData answerData) {
        BaseTestData testData = null;
        // 初始化题目数据
        switch (subjectData.getSubjectType()) {
            //选择类题目初始化
            case SubjectType.SUBJECT_SINGLE:
            case SubjectType.SUBJECT_MULTI:
            case SubjectType.SUBJECT_JUDGE:
                testData = initSelectSubject(null, subjectData, testMode);

                break;
            case SubjectType.SUBJECT_ENTRY:
                testData = initEntrySubject(null, subjectData, testMode);

                break;
            case SubjectType.SUBJECT_BLANK:
                testData = initBlankSubject(null, subjectData, testMode);

                break;
            default:
                testData = initCommonSubject(null, subjectData, testMode);

                break;
        }
        testData.parseUAnswerData(answerData);

        return testData;
    }

    /**
     * 通用题型初始化
     * @param curs
     * @param subjectData
     * @param testMode
     * @return
     */
    private BaseTestData initCommonSubject(Cursor curs, CommonSubjectData subjectData, int testMode) {
        TestBasicData testData = new TestBasicData();
        testData.setTestMode(testMode);
        testData.setSubjectData(subjectData);
        parseCursor(curs, testData, -1);
        return testData;
    }

    /**
     * 填空题型初始化
     * @param curs
     * @param subjectData
     * @param testMode
     * @return
     */
    private BaseTestData initBlankSubject(Cursor curs, CommonSubjectData subjectData, int testMode) {
        TestBlankData testData = new TestBlankData();
        testData.setTestMode(testMode);
        SubjectBlankData blank = (SubjectBlankData) subjectData;
        blank.makeAnswers();
        testData.setSubjectData(blank);
        parseCursor(curs, testData, -1);
        return testData;
    }

    /**
     * 分录类题型初始化
     * @param curs
     * @param subjectData
     * @param testMode
     * @return
     */
    private BaseTestData initEntrySubject(Cursor curs, CommonSubjectData subjectData, int testMode) {
        BaseTestData testData = new TestEntryData();
        testData.setTestMode(testMode);
        SubjectEntryData entry = (SubjectEntryData) subjectData;
        entry.makeBody();
        testData.setSubjectData(entry);
        parseCursor(curs, testData, -1);
        return testData;
    }

    /**
     * 选择类型题型初始化
     * @param curs
     * @param subjectData
     * @param testMode
     * @return
     */
    private BaseTestData initSelectSubject(Cursor curs, CommonSubjectData subjectData, int testMode) {
        // 初始化测试数据
        BaseTestData testData = new TestBasicData();
        testData.setTestMode(testMode);
        SubjectSelectData select = (SubjectSelectData) subjectData;
        select.makeBody();
        testData.setSubjectData(select);
        parseCursor(curs, testData, -1);
        return testData;
    }

    /**
     * 单据类题型初始化
     * @param curs
     * @param subjectData
     * @param testMode
     * @return
     */
    private BaseTestData initBillSubject(Cursor curs, CommonSubjectData subjectData, int testMode) {
        BaseTestData testData = null;
        SubjectBillData bill = (SubjectBillData) subjectData;
        bill.makeBody();
        // 题型初始化
        int size = bill.getBills().size();
        if (size <= 1) {//一张单据
            bill.setSubjectType(SubjectType.SUBJECT_BILL);
            testData = new TestBillData();
            testData.setSubjectData(bill);
            testData.setTestMode(testMode);
            // 初始化,加载模板数据
            BillTemplate template = BillTemplateFactory.createTemplate(mDb, ((SubjectBillData) subjectData).getBills().get(0).getTemplateId(), mContext);
            parseCursor(curs, testData, -1);
            ((TestBillData) testData).loadTemplate(template, mContext, 0);
        } else {//多张单据
            bill.setSubjectType(SubjectType.SUBJECT_GROUP_BILL);
            testData = new TestGroupBillData();
            testData.setSubjectData(bill);
            testData.setTestMode(testMode);
            ((TestGroupBillData) testData).setScore(bill.getScore());
            // 初始化题目数据,分组单据存在多张单据题目
            List<TestBillData> testBills = new ArrayList<TestBillData>(size);
            ((TestGroupBillData) testData).setTestDatas(testBills);

            for (int i = 0; i < size; i++) {
                SubjectBillData subject = new SubjectBillData();
                subject.setBills(bill.getBills());
                subject.setLabel(bill.getBills().get(i).getLabel());
                subject.setSubjectType(SubjectType.SUBJECT_GROUP_BILL);
                subject.setQuestion(bill.getQuestion());
                subject.setId(bill.getId());

                TestBillData testBill = new TestBillData();
                testBill.setTestMode(testMode);
                testBill.setSubjectData(subject);
                parseCursor(curs, testBill, i);
                testBills.add(testBill);
                // 初始化模板数据
                BillTemplate template = BillTemplateFactory.createTemplate(mDb, ((SubjectBillData) subject).getBills().get(i).getTemplateId(), mContext);
                testBill.loadTemplate(template, mContext, i);
            }
            parseCursor(curs, testData, -1);
        }
        return testData;
    }

    /**
     * cursor解析
     *
     * @param curs
     * @param data
     * @param index 大于1代表多组单据的当前单据索引，小于1代表其他题型
     */
    public void parseCursor(Cursor curs, BaseTestData data, int index) {
        if (curs != null) {
            data.setId(curs.getInt(curs.getColumnIndex(ID)));
            data.setFlag(curs.getInt(curs.getColumnIndex(FLAG)));
            data.setSubjectId(curs.getInt(curs.getColumnIndex(SUBJECT_ID)));
            data.setRemark(curs.getString(curs.getColumnIndex(REMARK)));
//            if (data.getTestMode() == TestMode.MODE_EXAM) {// 测试模式不加载用户数据
            if (false){
                data.setUAnswer(null);
                data.setuScore(0);
                data.setState(SubjectState.STATE_INIT);
            } else {
                data.setuScore(curs.getInt(curs.getColumnIndex(USCORE)));
                data.setState(curs.getInt(curs.getColumnIndex(STATE)));

                //用户答案初始化
                String uAnswer = curs.getString(curs.getColumnIndex(UANSWER));
                if (!TextUtils.isEmpty(uAnswer)) {
                    UserAnswerData answer = JSON.parseObject(uAnswer, UserAnswerData.class);
                    if (data.getSubjectData().getSubjectType() == SubjectType.SUBJECT_GROUP_BILL) {//分组单据做特殊处理
                        if (index > -1) {//分组单据里的子单据，都是单张单据类型
                            List<BillAnswerData> answers = JSON.parseArray(answer.getUanswer(), BillAnswerData.class);
                            if (answers != null && index < answers.size()) {
                                ((TestBillData) data).setUAnswerData(answers.get(index));
                            }
                        } else {
                            data.parseUAnswerData(answer);
                        }
                    } else {
                        data.parseUAnswerData(answer);
                    }
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
            long id = data.getId();
            ContentValues values = new ContentValues();
            String uAnswer = data.getUAnswer() == null ? null : data.getUAnswer().toString();
            values.put(UANSWER, uAnswer);
            values.put(USCORE, data.getuScore());
            values.put(STATE, data.getState());
            mDb.update(TABLE_NAME, values, ID + "=?", new String[] { String.valueOf(id) });
        } catch (Exception e) {
            e.printStackTrace();
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
            mDb.beginTransaction();
            for (BaseTestData data : datas) {
                long id = data.getId();
                ContentValues values = new ContentValues();
                String uAnswer = data.getUAnswer() == null ? null : data.getUAnswer().toString();
                values.put(UANSWER, uAnswer);
                values.put(USCORE, data.getuScore());
                values.put(STATE, data.getState());
                updateData(id, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.setTransactionSuccessful();
            mDb.endTransaction();
        }
    }

    @Override
    public BaseTestData parseCursor(Cursor curs) {
        return null;
    }

    /**
     * 插入test数据
     *
     * @param subjectId
     */
    public void insertTest(int subjectId) {
        String sql = "select id from " + TABLE_NAME + " where SUBJECT_ID = " + subjectId;
        Cursor curs = mDb.rawQuery(sql, null);
        int id = 0;//如果存在代表原id，否則代表新增的id
        try {
            boolean exist = false;//是否存在该测试
            if (curs != null && curs.moveToNext()) {
                id = curs.getInt(0);
                if (id > 0) {
                    exist = true;
                    Log.d(TAG, "insertTest test exists:" + id);
                }
            }
            //不存在则插入新数据
            if (!exist) {
                ContentValues values = new ContentValues();
                values.put("FLAG", -1);
                values.put("SUBJECT_ID", subjectId);
                values.put("UANSWER", "");
                values.put("USCORE", 0);
                values.put("STATE", 0);
                String userId = PreferenceHelper.getInstance(mContext).getStringValue( USER_ID);
                values.put("USERID", userId);
                id = (int) mDb.replace(TABLE_NAME, null, values);
                if (id < 0) {
                    ToastUtil.showToast(mContext, "测试数据插入出错：" + subjectId);
                    Log.e(TAG, "insertTest error:" + id );
                } else {
                    Log.d(TAG, "insertTest success:" + id );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (curs != null) {
                curs.close();
            }
        }}
}
