package com.edu.accountingteachingmaterial.newsubject;

import android.view.View;

import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectOnlineTestDataDao;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;

import java.util.List;

/**
 * 
 * 显示用户答案示例
 * 
 * @author lucher
 * 
 */
public class ShowUAnswerContentActivity extends BaseSubjectsContentActivity {
	String chapterId;
	public boolean isExam;

	@Override
	protected List<BaseTestData> initDatas() {
		chapterId = mBundle.getString(CHAPTER_ID);
		isExam = mBundle.getBoolean(IS_EXAM);
		titleContent = mBundle.getString(TITLE);

		btnSubmit.setVisibility(View.GONE);

		if (isExam){
			return SubjectOnlineTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_UANSWER, chapterId);
		}else {
			return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_UANSWER, chapterId);
		}
	}

	@Override
	protected void operationPager() {

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
	}

	@Override
	public void onSaveTestDatas(List<BaseTestData> testDatas) {
	}
}
