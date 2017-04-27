package com.edu.accountingteachingmaterial.newsubject;

import android.content.ContentValues;
import android.os.Bundle;
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
    public int chapterId;
    boolean isBook;

    @Override
    protected List<BaseTestData> initDatas() {
        Bundle bundle = getIntent().getExtras();
        isExam = bundle.getBoolean(IS_EXAM);
        chapterId = bundle.getInt(CHAPTER_ID);
        isBook = PreferenceHelper.getInstance(this).getBooleanValue(PreferenceHelper.IS_TEXKBOOK);
        if (isExam){
            return SubjectOnlineTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_DETAILS, chapterId);
        }else {
            return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_DETAILS, chapterId);
        }

    }

    @Override
    protected void operationPager() {
        if (isExam) {
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
        tvTitle.setText("查看详情示例");
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
