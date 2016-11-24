package com.edu.accountingteachingmaterial.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.edu.R;
import com.edu.accountingteachingmaterial.adapter.SubjectViewPagerAdapter;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.common.SubjectCardAdapter.OnCardItemClickListener;
import com.edu.subject.common.SubjectCardDialog;
import com.edu.subject.dao.SignDataDao;
import com.edu.subject.data.BaseSubjectData;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.SignData;
import com.edu.testbill.Constant;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.testbill.dialog.SignChooseDialog;

import java.io.IOException;
import java.util.List;

/**
 * 
 * 测试示例
 * 
 * @author lucher
 * 
 */
public class ExamContentActivity extends FragmentActivity implements OnItemClickListener, SubjectListener, OnCardItemClickListener {

	// 显示题目的viewpager控件
	private ViewPager viewPager;
	private SubjectViewPagerAdapter mSubjectAdapter;

	private int mCurrentIndex;
	private TextView tvQuestion;

	// 印章选择对话框
	private SignChooseDialog signDialog;
	// 印章，闪电符按钮
	private Button btnSign, btnFlash;

	// 答题卡对话框
	private SubjectCardDialog mCardDialog;

	// 页面相关状态的监听
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		// 页面切换后调用
		@Override
		public void onPageSelected(int item) {
			mCurrentIndex = item;
			refreshToolBar();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int item) {
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 窗口全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_content);
		init();
	}

	/**
	 * 刷新工具栏状态
	 */
	private void refreshToolBar() {
		if (mCurrentIndex < 0 || mCurrentIndex > mSubjectAdapter.getCount() - 1)
			return;
		BaseSubjectData subject = mSubjectAdapter.getData(mCurrentIndex).getSubjectData();
		// 刷新题目数据
		tvQuestion.setText(mSubjectAdapter.getData(mCurrentIndex).getSubjectIndex() + "." + subject.getQuestion());
		if (subject.getSubjectType() == SubjectType.SUBJECT_BILL) {
			btnSign.setVisibility(View.VISIBLE);
			btnFlash.setVisibility(View.VISIBLE);
		} else {
			btnSign.setVisibility(View.GONE);
			btnFlash.setVisibility(View.GONE);
		}
	}

	/**
	 * 初始化
	 * 
	 * @throws IOException
	 */
	private void init() {
		List<SignData> signs = (List<SignData>) SignDataDao.getInstance(this, Constant.DATABASE_NAME).getAllDatas();
		signDialog = new SignChooseDialog(this, signs, this);

		viewPager = (ViewPager) findViewById(R.id.vp_content);
		viewPager.setOnPageChangeListener(mPageChangeListener);
		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		btnSign = (Button) findViewById(R.id.btnSign);
		btnFlash = (Button) findViewById(R.id.btnFlash);

		List<BaseTestData> datas = SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_EXAM);
		mSubjectAdapter = new SubjectViewPagerAdapter(getSupportFragmentManager(), datas, this, this);
		viewPager.setAdapter(mSubjectAdapter);

		mCardDialog = new SubjectCardDialog(this, datas, this, mSubjectAdapter.getDatas().get(mCurrentIndex).getId());
	}

	public void onClick(View view) throws IOException {
		int id = view.getId();
		if(id == R.id.btnSign){
			sign();

		}else if (id == R.id.btnFlash){
			mSubjectAdapter.showFlash(mCurrentIndex);

		}else if (id == R.id.btnDone){
			float score = mSubjectAdapter.submit();
			ToastUtil.showToast(this, "score:" + score);
			finish();
		}else if (id == R.id.btnCard){
			if (!mCardDialog.isShowing()) {
				mCardDialog.show(mSubjectAdapter.getData(mCurrentIndex).getId());
			}
		}else if (id == R.id.btnLeft){
			scrollToLeft();

		}else if (id == R.id.btnRight){
			scrollToRight();

		}
	}

	/**
	 * 显示印章选择对话框
	 */
	private void sign() {
		if (!signDialog.isShowing()) {
			signDialog.show();
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		refreshToolBar();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		signDialog.dismiss();
		mSubjectAdapter.sign(mCurrentIndex, (SignData) view.getTag());
	}

	/**
	 * 滚动到左边页面
	 */
	private void scrollToLeft() {
		if (mCurrentIndex != 0) {
			mSubjectAdapter.saveAnswer(mCurrentIndex);
			mCurrentIndex--;
			viewPager.setCurrentItem(mCurrentIndex, true);
		} else {
			ToastUtil.showToast(this, "已经是第一页");
		}
	}

	/**
	 * 滚动到右边页面
	 */
	private void scrollToRight() {
		if (mCurrentIndex != mSubjectAdapter.getCount() - 1) {
			mSubjectAdapter.saveAnswer(mCurrentIndex);
			mCurrentIndex++;
			viewPager.setCurrentItem(mCurrentIndex, true);
		} else {
			ToastUtil.showToast(this, "已经是最后一页");
		}
	}

	@Override
	public void onComplete() {
		scrollToRight();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_PAGE_UP) {
			scrollToLeft();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN) {
			scrollToRight();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public int onItemClicked(BaseTestData data) {
		mSubjectAdapter.saveAnswer(mCurrentIndex);
		mCardDialog.dismiss();
		int index = mSubjectAdapter.getDatas().indexOf(data);
		viewPager.setCurrentItem(index);
		return mSubjectAdapter.getDatas().get(index).getId();
	}

	@Override
	public void onRedoClicked() {
	}

	@Override
	public void onBackPressed() {
		mSubjectAdapter.saveAnswer(mCurrentIndex);
		super.onBackPressed();
	}
}
