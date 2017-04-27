package com.edu.accountingteachingmaterial.newsubject;

import android.os.Bundle;

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
	int chapterId;
	public boolean isExam;

	@Override
	protected List<BaseTestData> initDatas() {
		Bundle bundle = getIntent().getExtras();
		chapterId = bundle.getInt(CHAPTER_ID);
		isExam = bundle.getBoolean(IS_EXAM);

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
		tvTitle.setText("显示用户答案示例");
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
