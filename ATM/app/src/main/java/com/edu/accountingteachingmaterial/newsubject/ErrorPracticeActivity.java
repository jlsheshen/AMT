package com.edu.accountingteachingmaterial.newsubject;

import android.os.Bundle;
import android.view.View;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.dao.ErrorTestDataDao;
import com.edu.subject.SubjectState;
import com.edu.subject.SubjectType;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.CommonSubjectData;

import java.util.List;

import static com.edu.accountingteachingmaterial.fragment.ClassExerciseFragment.ERRORS_DATAS;
import static com.edu.accountingteachingmaterial.fragment.ClassExerciseFragment.ERRORS_ITEM;

/**
 * 
 * 练习示例
 * 
 * @author lucher
 * 
 */
public class ErrorPracticeActivity extends BaseSubjectsContentActivity  {
	public int pagerItem;
	public int chapterId;
	List<BaseTestData> datas;
	@Override
	protected void init() {
		super.init();
		mCardDialog.showRedo();
		initConfirmDialog();
		//		viewPager.setCurrentItem(mSubjectAdapter.getCount()-1);//跳转到最后一页
	}



	@Override
	protected List<BaseTestData> initDatas() {
		Bundle bundle = getIntent().getExtras();
		pagerItem = bundle.getInt(ERRORS_ITEM,0);
		datas = (List<BaseTestData>) bundle.get(ERRORS_DATAS);
		return datas;
	}
	@Override
	protected void operationPager() {
		viewPager.setCurrentItem(pagerItem);
	}

	@Override
	protected void initTitle() {
		tvTitle.setText("练习模式示例");
	}

	@Override
	protected void refreshToolBar() {
		CommonSubjectData subject = mSubjectAdapter.getData(mCurrentIndex).getSubjectData();
		int type = subject.getSubjectType();
		//		if (type != SubjectType.SUBJECT_SINGLE && type != SubjectType.SUBJECT_JUDGE) {
		btnSubmit.setVisibility(View.VISIBLE);//方案调整，所有题型都显示提交按钮
		refreshSubmitState();
		if (type == SubjectType.SUBJECT_BILL || type == SubjectType.SUBJECT_GROUP_BILL) {
			btnSign.setVisibility(View.VISIBLE);
			btnFlash.setVisibility(View.VISIBLE);
		} else {
			btnSign.setVisibility(View.GONE);
			btnFlash.setVisibility(View.GONE);
			//			btnSubmit.setVisibility(View.GONE);
		}
	}

	/**
	 * 刷新提交按钮状态
	 */
	private void refreshSubmitState() {
		if (mSubjectAdapter.getData(mCurrentIndex).getState() == SubjectState.STATE_INIT || mSubjectAdapter.getData(mCurrentIndex).getState() == SubjectState.STATE_UNFINISH) {
			btnSubmit.setImageResource(R.mipmap.icon_fasong_n);
		} else {
			btnSubmit.setImageResource(R.mipmap.icon_congzuo_n);
		}
	}

	@Override
	protected void handSubmit() {
		if (mSubjectAdapter.getData(mCurrentIndex).getState() == SubjectState.STATE_INIT || mSubjectAdapter.getData(mCurrentIndex).getState() == SubjectState.STATE_UNFINISH) {
			float score = mSubjectAdapter.submit(mCurrentIndex);
//			ToastUtil.showToast(this, "score:" + score);
//			btnSubmit.setImageResource(R.mipmap.icon_congzuo_n);
		}
		else {
			mSubjectAdapter.reset(mCurrentIndex);
//			btnSubmit.setImageResource(R.mipmap.icon_fasong_n);
		}
		refreshSubmitState();
	}

	@Override
	protected void handleBack() {
//		showConfirmDialog(CONFIRM_EXIT,  "退出", "确认退出？");
		saveAnswer();
		finish();
	}

	@Override
	public void onRedoClicked() {
		mCardDialog.dismiss();
//		mSubjectAdapter.reset();
//		ToastUtil.showToast(this, "全部重做操作完成");
	}

	@Override
	protected void saveAnswer() {
//		int subType = mSubjectAdapter.getData(mCurrentIndex).getSubjectData().getSubjectType();
//		if (subType != SubjectType.SUBJECT_JUDGE && subType != SubjectType.SUBJECT_SINGLE) {//对于单项和判断，在点击选项的时候保存答案
//			mSubjectAdapter.saveAnswer(mCurrentIndex);
//		}
		mSubjectAdapter.saveAnswer(mCurrentIndex);

	}

	@Override
	protected void onDatasError() {

	}

	@Override
	public void onSaveTestData(BaseTestData testData) {
		ErrorTestDataDao.getInstance(mContext).updateTestData(testData);
	}

	@Override
	public void onSaveTestDatas(List<BaseTestData> testDatas) {
		ErrorTestDataDao.getInstance(mContext).updateTestDatas(testDatas);
	}

	@Override
	public void onDialogConfirm(int confirmType) {
		switch (confirmType) {
			case CONFIRM_EXIT:
				saveAnswer();
				finish();
				break;

			case CONFIRM_SUBMIT:
				handSubmit();
				finish();
				break;

			default:
				break;
		}
	}


}
