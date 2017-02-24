package com.edu.accountingteachingmaterial.newsubject;

import java.util.List;

import com.edu.accountingteachingmaterial.base.BaseSubjectsContentActivity;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;
import com.edu.testsubject.dao.SubjectOnlineTestDataDao;
import com.edu.testsubject.dao.SubjectTestDataDao;

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
		boolean online = getIntent().getBooleanExtra("online", false);//是否在线考试
		if(online) {
			return SubjectOnlineTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_UANSWER);
		} else {
			return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_UANSWER);
		}
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
	public void onSaveTestData(BaseTestData testData) {
	}

	@Override
	public void onSaveTestDatas(List<BaseTestData> testDatas) {
	}
}
