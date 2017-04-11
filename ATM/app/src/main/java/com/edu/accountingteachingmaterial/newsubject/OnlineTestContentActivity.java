package com.edu.accountingteachingmaterial.newsubject;

import android.os.Bundle;
import android.view.View;

import com.edu.accountingteachingmaterial.model.ResultsListener;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectTestDataDao;
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
 * @author lucher
 *
 */
public class OnlineTestContentActivity extends BaseSubjectsContentActivity implements ResultsListener {
	public int chapterId;
	public int totalTime;
	// 页面相关状态的监听
	private CountryTestTimer timer;

	@Override
	protected void init() {
		super.init();
		btnSubmit.setVisibility(View.VISIBLE);
		initTimer(1000,  totalTime * 1000);
		initConfirmDialog();
	}

	@Override
	protected List<BaseTestData> initDatas() {
		Bundle bundle = getIntent().getExtras();
		chapterId = bundle.getInt(CHAPTER_ID);
//		textMode = bundle.getInt("textMode");
		totalTime = bundle.getInt(TOTAL_TIME);
		return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_EXAM, chapterId);
	}

	@Override
	protected void operationPager() {

	}

	@Override
	protected void initTitle() {
		tvTitle.setText("在线测试示例");
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
		showConfirmDialog(CONFIRM_SUBMIT, "提示", "确认提交？");
	}

	@Override
	protected void handleBack() {
		showConfirmDialog(CONFIRM_EXIT, "提示", "退出将直接提交答案，确认退出？");
	}

	/**
	 * 提交处理
	 */
	private void submit() {
		float score = mSubjectAdapter.submit();
		UploadOnlineResultsManager.getSingleton(this).setResultsListener(this);
		UploadOnlineResultsManager.getSingleton(this).setResults(mSubjectAdapter.getDatas());
		int userId = Integer.parseInt(PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.USER_ID));
		int cost = 0;
		UploadOnlineResultsManager.getSingleton(this).uploadResult(userId, chapterId, cost);
		finish();
	}

	@Override
	protected void saveAnswer() {
		mSubjectAdapter.saveAnswer(mCurrentIndex);
	}

	@Override
	protected void onDatasError() {

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
		ToastUtil.showToast(mContext, "时间到");
		submit();
	}

	@Override
	public void onDialogConfirm(int confirmType) {
		switch (confirmType) {
		case CONFIRM_EXIT:
		case CONFIRM_SUBMIT:
			submit();
			break;

		default:
			break;
		}
	}

	@Override
	public void onResultsSuccess() {

	}

	@Override
	public void onResultsFialure() {

	}
}
