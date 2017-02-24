package com.edu.accountingteachingmaterial.newsubject;

import java.util.List;

import android.content.Intent;
import android.view.View;

import com.edu.accountingteachingmaterial.base.BaseSubjectsContentActivity;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.CommonSubjectData;
import com.edu.testsubject.dao.SubjectTestDataDao;

/**
 * 
 * 测试示例
 * 
 * @author lucher
 * 
 */
public class ExamContentActivity extends BaseSubjectsContentActivity {

	@Override
	protected void init() {
		super.init();
		btnSubmit.setVisibility(View.VISIBLE);
		initTimer(1000, 5 * 60 * 1000);
		initConfirmDialog();
	}

	@Override
	protected List<BaseTestData> initDatas() {
		return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_EXAM);
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
		Intent intent = new Intent(this, TestResultActivity.class);
		intent.putExtra("score", score);
		intent.putExtra("online", false);
		startActivity(intent);
		finish();
	}

	@Override
	protected void saveAnswer() {
		mSubjectAdapter.saveAnswer(mCurrentIndex);
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
}
