package com.edu.accountingteachingmaterial.newsubject;

import com.edu.accountingteachingmaterial.base.BaseSubjectsContentActivity;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectOnlineTestDataDao;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectTestDataDao;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;

import java.util.List;

/**
 * 
 * 查看详情示例
 * 
 * @author lucher
 * 
 */
public class ShowDetailsContentActivity extends BaseSubjectsContentActivity {
	
	@Override
	protected List<BaseTestData> initDatas() {
		boolean online = getIntent().getBooleanExtra("online", false);//是否在线考试
		if(online) {
			return SubjectOnlineTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_DETAILS);
		} else {
			return SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_DETAILS);
		}
	}

	@Override
	protected void initTitle() {
		tvTitle.setText("查看详情示例");
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
