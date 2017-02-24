package com.edu.accountingteachingmaterial.newsubject;

import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.edu.library.util.ToastUtil;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;
import com.edu.testsubject.dao.SubjectOnlineTestDataDao;
import com.edu.testsubject.net.UploadResultsManager;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.UrlReqEntity;

/**
 * 测试结果界面
 * @author lucher
 *
 */
public class TestResultActivity extends Activity {

	//得分
	private TextView tvScore;
	//上传成绩
	private Button btnUpload;
	//是否在线测试
	private boolean online;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 窗口全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_result);

		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		tvScore = (TextView) findViewById(R.id.tvScore);
		float score = getIntent().getFloatExtra("score", 0);
		tvScore.setText(score + "分");
		btnUpload = (Button) findViewById(R.id.btnUpload);

		online = getIntent().getBooleanExtra("online", false);
		if (online) {//在线考试显示上传成绩按钮
			btnUpload.setVisibility(View.VISIBLE);
		}
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnUpload:
			List<BaseTestData> testDatas = SubjectOnlineTestDataDao.getInstance(this).getSubjects(TestMode.MODE_PRACTICE);
			UploadResultsManager.getSingleton(this).setResults(testDatas).uploadResult(1, 2, 3);
			break;

		default:
			if (online) {
				requestAnswer();
			} else {
				Intent intent = new Intent(TestResultActivity.this, ShowDetailsContentActivity.class);
				intent.putExtra("online", online);
				startActivity(intent);
			}
			break;
		}
	}

	/**
	 * 请求答案
	 */
	private void requestAnswer() {
		UrlReqEntity entity = new UrlReqEntity(this, RequestMethod.POST, Constant.SERVER_URL + "requestAnswer/0");
		new JsonNetReqManager() {

			@Override
			public void onConnectionSuccess(JSONObject arg0, Header[] arg1) {
				ToastUtil.showToast(TestResultActivity.this, arg0.getString("message"));
				boolean result = arg0.getBooleanValue("result");
				if (result) {
					showDetails();
				} else {
					showUanswer();
				}
			}

			@Override
			public void onConnectionError(String msg) {
				ToastUtil.showToast(TestResultActivity.this, msg);
				showUanswer();
			}

			@Override
			public void onConnectionFailure(String msg, Header[] arg1) {
				ToastUtil.showToast(TestResultActivity.this, msg);
				showUanswer();
			}
		}.sendRequest(entity, "正在请求答案");
	}

	/**
	 * 显示详情
	 */
	private void showDetails() {
		Intent intent = new Intent(TestResultActivity.this, ShowDetailsContentActivity.class);
		intent.putExtra("online", online);
		startActivity(intent);
	}

	/**
	 * 显示用户答案
	 */
	private void showUanswer() {
		Intent intent = new Intent(TestResultActivity.this, ShowUAnswerContentActivity.class);
		intent.putExtra("online", online);
		startActivity(intent);
	}
}
