package com.edu.accountingteachingmaterial.newsubject;

import com.edu.accountingteachingmaterial.newsubject.dao.SubjectTestDataDao;
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
	
	@Override
	protected List<BaseTestData> initDatas() {
			return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_UANSWER);
	}

	@Override
	protected void operationPager() {

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
