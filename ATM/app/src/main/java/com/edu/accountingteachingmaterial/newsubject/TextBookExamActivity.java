package com.edu.accountingteachingmaterial.newsubject;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.dao.ExamOnLineListDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.model.ResultsListener;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectOnlineTestDataDao;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.UploadOnlineResultsManager;
import com.edu.library.data.BaseDataDao2;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.CommonSubjectData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 测试示例
 * 用于教材的课后课前,评测练习
 *
 * @author lucher
 */
public class TextBookExamActivity extends BaseSubjectsContentActivity implements ResultsListener {
    public int chapterId;
    public boolean isExam;
    BaseDataDao2 baseDataDao;

    @Override
    protected void init() {
        super.init();
        btnSubmit.setVisibility(View.VISIBLE);
//        initTimer(1000, 5 * 60 * 1000);
        initConfirmDialog();
    }

    @Override
    public void onRedoClicked() {
        mCardDialog.dismiss();
    }

    @Override
    protected List<BaseTestData> initDatas() {
        Bundle bundle = getIntent().getExtras();
        isExam = bundle.getBoolean(IS_EXAM);
        chapterId = bundle.getInt(CHAPTER_ID);
        if (isExam){
            return SubjectOnlineTestDataDao.getInstance(this).getSubjects(TestMode.MODE_EXAM, chapterId);
        }else {
            return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_EXAM, chapterId);
        }
    }

    @Override
    protected void operationPager() {

    }

    @Override
    protected void initTitle() {
        tvTitle.setText("测试模式示例");
    }

    @Override
    protected void refreshToolBar() {
        CommonSubjectData subject = mSubjectAdapter.getData(mCurrentIndex).getSubjectData();
        if (subject.getSubjectType() == SubjectType.SUBJECT_BILL || subject.getSubjectType() == SubjectType.SUBJECT_GROUP_BILL) {
            btnSign.setVisibility(View.VISIBLE);
            btnFlash.setVisibility(View.VISIBLE);
        } else {
            btnSign.setVisibility(View.GONE);
            btnFlash.setVisibility(View.GONE);
        }
    }

    @Override
    protected void handSubmit() {
        showConfirmDialog(CONFIRM_SUBMIT, "提交", "确认提交？");
    }

    @Override
    protected void handleBack() {
//        showConfirmDialog(CONFIRM_EXIT,  "退出", "确认退出？");
        saveAnswer();
        finish();
    }

    /**
     * 提交处理
     */
    private void submit() {

        float score = mSubjectAdapter.submit();
//        ToastUtil.showToast(this, "score:" + score);
        if (isExam) {
            UploadOnlineResultsManager.getSingleton(this).setResultsListener(this);
            UploadOnlineResultsManager.getSingleton(this).setResults(mSubjectAdapter.getDatas());
            String userId = PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.USER_ID);
            UploadOnlineResultsManager.getSingleton(this).uploadResult(userId,chapterId,0);
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_COMMIT);
//            SubjectOnlineTestDataDao.getInstance(this).updateData(chapterId, contentValues);
//            EventBus.getDefault().post(ClassContstant.EXAM_COMMIT);
//            finish();
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_COMMIT);
            ExamListDao.getInstance(this).updateData("" + chapterId, contentValues);
            EventBus.getDefault().post(ClassContstant.EXAM_COMMIT);
            finish();
        }
    }

    @Override
    protected void saveAnswer() {
        mSubjectAdapter.saveAnswer(mCurrentIndex,0);
    }

    @Override
    protected void onDatasError() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExamListDao.ID, chapterId);
        contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_NOT);
        if (isExam) {
            ExamOnLineListDao.getInstance(this).updateData("" + chapterId, contentValues);
        } else {
            ExamListDao.getInstance(this).updateData("" + chapterId, contentValues);
        }
        Toast.makeText(this, "需要重新下载", Toast.LENGTH_SHORT).show();
        finish();
        return;
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
            SubjectOnlineTestDataDao.getInstance(mContext).updateTestDatas(testDatas);

        }else {
            SubjectTestDataDao.getInstance(mContext).updateTestDatas(testDatas);
        }
    }

    @Override
    public void onTimeOut() {
        ToastUtil.showToast(mContext, "时间到");
        submit();
    }

    @Override
    public void onDialogConfirm(int confirmType) {
        switch (confirmType) {
            case CONFIRM_EXIT:
                saveAnswer();
                finish();
                break;

            case CONFIRM_SUBMIT:
                saveAnswer();
                submit() ;

                break;

            default:
                break;
        }
    }

    @Override
    public void onResultsSuccess() {
        Log.d("TextBookExamActivity", "成功回调");
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_COMMIT);
        ExamOnLineListDao.getInstance(this).updateData("" + chapterId, contentValues);
        EventBus.getDefault().post(ClassContstant.EXAM_COMMIT);
        finish();
    }

    @Override
    public void onResultsFialure() {
        Log.d("TextBookExamActivity", "失败回调");

//        finish();
    }
}
