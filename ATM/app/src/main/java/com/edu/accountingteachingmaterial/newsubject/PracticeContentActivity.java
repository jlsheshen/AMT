package com.edu.accountingteachingmaterial.newsubject;

import android.view.View;

import com.edu.accountingteachingmaterial.base.BaseSubjectsContentActivity;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectTestDataDao;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectState;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.CommonSubjectData;

import java.util.List;

/**
 * 
 * 练习示例
 * 
 * @author lucher
 * 
 */
public class PracticeContentActivity extends BaseSubjectsContentActivity {

	@Override
	protected void init() {
		super.init();
		mCardDialog.showRedo();
		initConfirmDialog();
	}

	@Override
	protected List<BaseTestData> initDatas() {
		return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_PRACTICE);
	}

	@Override
	protected void initTitle() {
		tvTitle.setText("练习模式示例");
	}

	@Override
	protected void refreshToolBar() {
		CommonSubjectData subject = mSubjectAdapter.getData(mCurrentIndex).getSubjectData();
		int type = subject.getSubjectType();
		if (type != SubjectType.SUBJECT_SINGLE && type != SubjectType.SUBJECT_JUDGE) {
			btnSubmit.setVisibility(View.VISIBLE);
			if (type == SubjectType.SUBJECT_BILL || type == SubjectType.SUBJECT_GROUP_BILL) {
				btnSign.setVisibility(View.VISIBLE);
				btnFlash.setVisibility(View.VISIBLE);
			}
			refreshSubmitState();
		} else {
			if (type == SubjectType.SUBJECT_BILL || type == SubjectType.SUBJECT_GROUP_BILL) {
				btnSign.setVisibility(View.GONE);
				btnFlash.setVisibility(View.GONE);
			}
			btnSubmit.setVisibility(View.GONE);
		}
	}

	/**
	 * 刷新提交按钮状态
	 */
	private void refreshSubmitState() {
		if (mSubjectAdapter.getData(mCurrentIndex).getState() == SubjectState.STATE_INIT || mSubjectAdapter.getData(mCurrentIndex).getState() == SubjectState.STATE_UNFINISH) {
		} else {
		}
	}

	@Override
	protected void handSubmit() {
		if (mSubjectAdapter.getData(mCurrentIndex).getState() == SubjectState.STATE_INIT || mSubjectAdapter.getData(mCurrentIndex).getState() == SubjectState.STATE_UNFINISH) {
			float score = mSubjectAdapter.submit(mCurrentIndex);
			ToastUtil.showToast(this, "score:" + score);
		} else {
			mSubjectAdapter.reset(mCurrentIndex);
		}
	}

	@Override
	protected void handleBack() {
		showConfirmDialog(CONFIRM_EXIT, "提示", "确认退出吗？");
	}

	@Override
	public void onRedoClicked() {
		mCardDialog.dismiss();
		mSubjectAdapter.reset();
		ToastUtil.showToast(this, "全部重做操作完成");
	}

	@Override
	protected void saveAnswer() {
		int subType = mSubjectAdapter.getData(mCurrentIndex).getSubjectData().getSubjectType();
		if (subType != SubjectType.SUBJECT_JUDGE && subType != SubjectType.SUBJECT_SINGLE) {//对于单项和判断，在点击选项的时候保存答案
			mSubjectAdapter.saveAnswer(mCurrentIndex);
		}
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
	public void onDialogConfirm(int confirmType) {
		saveAnswer();
		finish();
	}
}
