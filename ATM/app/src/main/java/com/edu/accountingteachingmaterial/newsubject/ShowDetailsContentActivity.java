package com.edu.accountingteachingmaterial.newsubject;

import android.content.ContentValues;
import android.view.View;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectOnlineTestDataDao;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * 查看详情示例
 *
 * @author lucher
 */
public class ShowDetailsContentActivity extends BaseSubjectsContentActivity {
    public boolean isExam;
    public String chapterId;
    boolean isBook;
    int examType = 0;

    @Override
    protected List<BaseTestData> initDatas() {
        isExam = mBundle.getBoolean(IS_EXAM);
        titleContent = mBundle.getString(TITLE);

        if (!isExam){
            examType = mBundle.getInt(EXERCISE_TYPE);

        }
        chapterId =  mBundle.getString(CHAPTER_ID);
        isBook = PreferenceHelper.getInstance(this).getBooleanValue(PreferenceHelper.IS_TEXKBOOK);
        if (isExam){
            return SubjectOnlineTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_DETAILS, chapterId);
        }else {
            return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_DETAILS, chapterId);
        }

    }

    @Override
    protected void operationPager() {
        if (isExam || examType ==ClassContstant.EXERCISE_BEFORE_CLASS) {
            btnSubmit.setVisibility(View.GONE);
        } else {
            btnSubmit.setImageResource(R.mipmap.icon_congzuo_n);
        }

    }
    @Override
    public void onRedoClicked() {
        mCardDialog.dismiss();
    }

    @Override
    protected void initTitle() {
        tvTitle.setText(titleContent);
    }

    @Override
    protected void handleBack() {
        finish();
    }

    @Override
    protected void handSubmit() {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_UNDONE);
            ExamListDao.getInstance(this).updateData("" + chapterId, contentValues);
            EventBus.getDefault().post(ClassContstant.EXAM_UNDONE);
            mSubjectAdapter.reset();
            finish();
    }

    @Override
    protected void refreshToolBar() {
    }

    @Override
    protected void saveAnswer() {
    }

    @Override
    protected void onDatasError() {

    }

    @Override
    public void onSaveTestData(BaseTestData testData) {
        if (isExam){
            SubjectOnlineTestDataDao.getInstance(mContext).updateTestData(testData);
        }else {
            SubjectTestDataDao.getInstance(mContext).updateTestData(testData);
        }
    }

    @Override
    public void onSaveTestDatas(List<BaseTestData> testDatas) {
        if (isExam){
             SubjectOnlineTestDataDao.getInstance(this).updateTestDatas(testDatas);
        }else {
             SubjectTestDataDao.getInstance(this).updateTestDatas(testDatas);
        }
    }
}
