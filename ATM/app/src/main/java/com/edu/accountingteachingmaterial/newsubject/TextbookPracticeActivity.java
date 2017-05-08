package com.edu.accountingteachingmaterial.newsubject;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.util.SubmitTimerUtil;
import com.edu.subject.SubjectState;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.CommonSubjectData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 
 * 练习示例
 * 
 * @author lucher
 * 
 */
public class TextbookPracticeActivity extends BaseSubjectsContentActivity  {
	public int pagerItem;
	public int chapterId;
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
		 chapterId = bundle.getInt(CHAPTER_ID);
		pagerItem = bundle.getInt(EXERCISE_ITEM);
		return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_PRACTICE,chapterId);
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
			SubmitTimerUtil.setTemptime();

//			ToastUtil.showToast(this, "score:" + score);
			ContentValues contentValues = new ContentValues();
			contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_COMMIT);
			ExamListDao.getInstance(this).updateData("" + chapterId, contentValues);
			EventBus.getDefault().post(ClassContstant.EXAM_COMMIT);
//			finish();
		} else {
			if (SubmitTimerUtil.resetSubject(this)){

				mSubjectAdapter.reset(mCurrentIndex);
			EventBus.getDefault().post(ClassContstant.EXAM_COMMIT);
			}else {
				return;
			}
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
			mSubjectAdapter.saveAnswer(mCurrentIndex);
//		}
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
	public void onSaveTestData(BaseTestData testData) {
		SubjectTestDataDao.getInstance(mContext).updateTestData(testData);
	}

	@Override
	public void onSaveTestDatas(List<BaseTestData> testDatas) {
		SubjectTestDataDao.getInstance(mContext).updateTestDatas(testDatas);
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

	@Override
	protected void onDestroy() {


		super.onDestroy();
	}
}
