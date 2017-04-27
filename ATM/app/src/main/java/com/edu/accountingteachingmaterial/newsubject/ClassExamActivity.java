package com.edu.accountingteachingmaterial.newsubject;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.model.ResultsListener;
import com.edu.accountingteachingmaterial.util.net.UploadResultsManager;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.CommonSubjectData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 测试示例
 *
 * @author lucher
 */
public class ClassExamActivity extends BaseSubjectsContentActivity implements ResultsListener {
    public int chapterId;

    @Override
    protected void init() {
        super.init();
        btnSubmit.setVisibility(View.VISIBLE);
//		initTimer(1000, 5 * 60 * 1000);
        initConfirmDialog();
    }

    @Override
    protected List<BaseTestData> initDatas() {
        Bundle bundle = getIntent().getExtras();
        chapterId = bundle.getInt(CHAPTER_ID);
        return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_EXAM, chapterId);
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
//		showConfirmDialog(CONFIRM_EXIT, "提示", "退出将直接提交答案，确认退出？");
        showConfirmDialog(CONFIRM_EXIT, "退出", "确认退出？");
    }

    /**
     * 提交处理
     */
    private void submit() {
        saveAnswer();
        float score = mSubjectAdapter.submit();
        ToastUtil.showToast(this, "score:" + score);
        UploadResultsManager.getSingleton(this).setResultsListener(this);
        UploadResultsManager.getSingleton(this).setResults(mSubjectAdapter.getDatas());
        UploadResultsManager.getSingleton(this).uploadResult(chapterId, 10000);
//		Intent intent = new Intent(this, TestResultActivity.class);
//		intent.putExtra("score", score);
//		intent.putExtra("online", false);
//		startActivity(intent);
//		finish();
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
        ExamListDao.getInstance(this).updateData(String.valueOf(chapterId), contentValues);
        Toast.makeText(this, "需要重新下载", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRedoClicked() {
        mCardDialog.dismiss();
    }

    @Override
    public void onSaveTestData(BaseTestData testData) {
        SubjectTestDataDao.getInstance(mContext).updateTestData(testData);
    }

    @Override
    public void onSaveTestDatas(List<BaseTestData> testDatas) {
        SubjectTestDataDao.getInstance(mContext).updateTestDatas(testDatas);
    }

    @Override
    public void onTimeOut() {
//		ToastUtil.showToast(mContext, "时间到");
//		submit();
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
                submit();
                finish();

                break;

            default:
                break;
        }
    }

    @Override
    public void onResultsSuccess() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResultsFialure() {

    }
}
