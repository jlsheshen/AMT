package com.edu.accountingteachingmaterial.subject.view;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.dao.ReviewTestDataDao;
import com.edu.library.common.PreferenceHelper;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectState;
import com.edu.subject.data.BaseSubjectData;
import com.edu.subject.data.BaseTestData;

import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_INCLASS;
import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_LOOK;
import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_NORMAL;
import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_TEST;

/**
 * 自定义题型视图的基类
 *
 * @author lucher
 */
public abstract class ReviewBaseScrollView extends RelativeLayout {

    /**
     * 题目数据保存
     */
    protected BaseTestData mTestData;
    protected BaseSubjectData mData;

    // 判断点击监听
    protected SubjectListener subjectListener;

    // preference的帮助类
    protected PreferenceHelper preHelper;

    ContentValues contentValues;

    TextView scoreTv;

    /**
     * 测试的模式，取值为TEST_MODE_NORMAL，TEST_MODE_TEST
     */
    protected int testMode;

    public ReviewBaseScrollView(Context context) {
        super(context);
    }

    public ReviewBaseScrollView(Context context, BaseTestData data, int testMode) {
        super(context);
        mTestData = data;
        this.testMode = testMode;
        mData = mTestData.getSubjectData();

    }

    public ReviewBaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReviewBaseScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 更改数据库表TB_TEST中单多判答题状态
     */
    private void updateState(String answer) {
        mTestData.setuAnswer(answer);

        if (answer.equals(mData.getAnswer())) {
            // 1是正确
            mTestData.setState(SubjectState.STATE_CORRECT);
            mTestData.setuScore(mData.getScore());
            contentValues = new ContentValues();
            contentValues.put(ReviewTestDataDao.STATE, SubjectState.STATE_CORRECT);
            contentValues.put(ReviewTestDataDao.UANSWER, answer);
            contentValues.put(ReviewTestDataDao.USCORE, mData.getScore());
            contentValues.put(ReviewTestDataDao.ERROR_COUNT, mTestData.getErrorCount());
            contentValues.put(ReviewTestDataDao.REMARK, "0");
            ReviewTestDataDao.getInstance(getContext()).updateData(String.valueOf(mTestData.getId()), contentValues);
        } else {
            // 2是错误
            mTestData.setState(SubjectState.STATE_WRONG);
            mTestData.setErrorCount(mTestData.getErrorCount() + 1);
            contentValues = new ContentValues();
            contentValues.put(ReviewTestDataDao.STATE, SubjectState.STATE_WRONG);
            contentValues.put(ReviewTestDataDao.UANSWER, answer);
            contentValues.put(ReviewTestDataDao.USCORE, 0);
            contentValues.put(ReviewTestDataDao.ERROR_COUNT, mTestData.getErrorCount());
            contentValues.put(ReviewTestDataDao.REMARK, "0");
            ReviewTestDataDao.getInstance(getContext()).updateData(String.valueOf(mTestData.getId()), contentValues);

        }
    }

    /**
     * 处理答案被选择的事件
     *
     * @param answer
     */
    protected void handleOnClick(String answer) {
        if (testMode == TEST_MODE_NORMAL) {// 选择答案后则显示正确答案，且不能进行修改,及时判分
            // 更新数据库答题状态
            updateState(answer);
//			showCorrectAnswer(answer.equals(mData.getAnswer()));
//            disableOption();
            gradeAnswer(answer);
        } else if (testMode == TEST_MODE_TEST) {
            gradeAnswer(answer);
        } else if (testMode == TEST_MODE_INCLASS) {
            // 更新数据库答题状态
            updateState(answer);
//		showCorrectAnswer(answer.equals(mData.getAnswer()));
//			disableOption();
            gradeAnswer(answer);
        } else if (testMode == TEST_MODE_LOOK) {
            disableOption();
        }

    }

    ;

    /**
     * 对当前答案进行判分
     *
     * @param answer
     */
    protected void gradeAnswer(String answer) {
        if (testMode == TEST_MODE_NORMAL || testMode == TEST_MODE_INCLASS) {
            gradeNormalAnswer(answer);
        } else if (testMode == TEST_MODE_TEST) {
            gradeTestAnswer(answer);
        }
    }

    /**
     * 显示正确答案
     */
    protected abstract void showCorrectAnswer(boolean correct);

    /**
     * 不能再选择
     */
    protected abstract void disableOption();

    /**
     * 重置
     */
    public abstract void reset();

    /**
     * 测试模式进行判分
     *
     * @param answer
     */
    private void gradeTestAnswer(String answer) {

    }

    /**
     * 对普通模式进行评分
     *
     * @param answer
     */
    private void gradeNormalAnswer(String answer) {
        // 更新mData里以及数据库里的用户答案
        mTestData.setuAnswer(answer);
        contentValues = new ContentValues();
        contentValues.put(ReviewTestDataDao.UANSWER, answer);
        if (answer.equals("")) {
            mData.setRemark("0");
        } else {
            mData.setRemark("1");
        }
        contentValues.put(ReviewTestDataDao.REMARK, mData.getRemark());
        ReviewTestDataDao.getInstance(getContext()).updateData(String.valueOf(mTestData.getId()), contentValues);
    }

    public void setSubjectListener(SubjectListener subjectListener) {
        this.subjectListener = subjectListener;
    }
}