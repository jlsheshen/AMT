package com.edu.accountingteachingmaterial.newsubject;

import android.view.View;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.model.ResultsListener;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectOnlineTestDataDao;
import com.edu.accountingteachingmaterial.util.CountryTestTimer;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.UploadOnlineResultsManager;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.CommonSubjectData;

import java.util.List;

/**
 * 在线测试示例
 *
 * @author lucher
 */
public class OnlineTestContentActivity extends BaseSubjectsContentActivity implements ResultsListener, CountryTestTimer.OnTimeOutListener {
    public String chapterId;
    public int totalTime;

    // 页面相关状态的监听
    private CountryTestTimer timer;

    @Override
    protected void init() {
        super.init();
        btnSubmit.setVisibility(View.VISIBLE);
		initTimer(1000,  totalTime * 1000);
        if (totalTime > 0) {
            setTime();
        } else {
            findViewById(R.id.ly_time).setVisibility(View.GONE);
        }
        initConfirmDialog();
    }
    @Override
    public void onRedoClicked() {
        mCardDialog.dismiss();
    }

    @Override
    protected List<BaseTestData> initDatas() {
        chapterId = mBundle.getString(CHAPTER_ID);
        titleContent = mBundle.getString(TITLE);

//		textMode = bundle.getInt("textMode");
        totalTime = mBundle.getInt(TOTAL_TIME);

        return SubjectOnlineTestDataDao.getInstance(this).getSubjects(TestMode.MODE_EXAM, chapterId);
    }

    @Override
    protected void operationPager() {
        mSubjectAdapter.setOnLineExam();
    }

    @Override
    protected void initTitle() {
        tvTitle.setText(titleContent);
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
        showConfirmDialog(CONFIRM_EXIT, "退出", "确认退出？");
    }

    /**
     * 提交处理
     */
    private void submit() {
        float score = mSubjectAdapter.submit();
        UploadOnlineResultsManager.getSingleton(this).setResultsListener(this);
        UploadOnlineResultsManager.getSingleton(this).setResults(mSubjectAdapter.getDatas());
        String userId = PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.USER_ID);
        int cost = 0;
        if (timer != null) {
            cost = timer.getUsedTime();
        }
        UploadOnlineResultsManager.getSingleton(this).uploadResult(userId, chapterId, cost);
    }

    //开始倒计时
    private void setTime() {
        // 倒计时时间设置
        timer = new CountryTestTimer(tvTimer, 1000, totalTime * 1000, this);
        timer.setOnTimeOutListener(this);
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    @Override
    protected void saveAnswer() {
        mSubjectAdapter.saveAnswer(mCurrentIndex,0);
    }

    @Override
    protected void onDatasError() {

    }

    @Override
    public void onSaveTestData(BaseTestData testData) {
        SubjectOnlineTestDataDao.getInstance(mContext).updateTestData(testData);
    }

    @Override
    public void onSaveTestDatas(List<BaseTestData> testDatas) {
        SubjectOnlineTestDataDao.getInstance(mContext).updateTestDatas(testDatas);
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
                if (timer != null) {
                    timer.cancel();
                }
                saveAnswer();
                finish();
                break;

            case CONFIRM_SUBMIT:
                saveAnswer();

                submit();

                break;

            default:
                break;
        }
    }

    @Override
    public void onResultsSuccess() {
        finish();

    }

    @Override
    public void onResultsFialure() {
        finish();

    }
}
