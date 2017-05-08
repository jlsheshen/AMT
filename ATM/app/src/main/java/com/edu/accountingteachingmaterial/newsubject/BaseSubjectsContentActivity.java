package com.edu.accountingteachingmaterial.newsubject;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.constant.Constant;
import com.edu.accountingteachingmaterial.newsubject.adapter.SubjectCardAdapter;
import com.edu.accountingteachingmaterial.newsubject.adapter.SubjectViewPagerAdapter;
import com.edu.accountingteachingmaterial.newsubject.common.SubjectCardDialog;
import com.edu.accountingteachingmaterial.newsubject.common.TestTimer;
import com.edu.accountingteachingmaterial.newsubject.dialog.SignChooseDialog;
import com.edu.accountingteachingmaterial.view.dialog.ExitDialog;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectListener;
import com.edu.subject.bill.SignData;
import com.edu.subject.bill.SignDataDao;
import com.edu.subject.data.BaseTestData;

import java.io.IOException;
import java.util.List;

/**
 * 
 * 答题页面基类
 * 
 * @author lucher
 * 改动 : 讲左右滑动页面保存改为页面切换保存
 */
public abstract class BaseSubjectsContentActivity extends FragmentActivity implements OnItemClickListener, SubjectListener, SubjectCardAdapter.OnCardItemClickListener, OnPageChangeListener, TestTimer.OnTimeOutListener, ExitDialog.SetDialogListener {
	/**
	 * 上个页面传来的节id
	 */
	public static final String CHAPTER_ID = "CHAPTER_ID";
	/**
	 * 上个页面传来的随堂练习item
	 */
	public static final String EXERCISE_ITEM = "EXERCISE_ITEM";

	/**
	 * 上个页面传来的试卷信息
	 */
	public static final String CONTENT_DATA = "CONTENT_DATA";
	/**
	 * 上个页面传来的试卷信息
	 */
	public static final String TOTAL_TIME = "TOTAL_TIME";
	/**
	 * 上个页面传来的是否是考试
	 */
	public static final String IS_EXAM = "IS_EXAM";
	/**
	 * 练习类型
	 */
	public static final String  EXERCISE_TYPE ="EXERCISE_TYPE";
	//确认类型-退出
	protected static final int CONFIRM_EXIT = 1;
	//确认类型-提交
	protected static final int CONFIRM_SUBMIT = 2;

	// 显示题目的viewpager控件
	protected ViewPager viewPager;
	protected SubjectViewPagerAdapter mSubjectAdapter;

	// 当前页面索引
	protected int mCurrentIndex;
	//用于获取上个页面的值
	Bundle mBundle;


	// 标题，例如xx测试
	protected TextView tvTitle;
	// 印章，闪电符，答题卡，提交按钮
	protected ImageView btnSign, btnFlash, btnCard, btnSubmit;

	// 印章选择对话框
	protected SignChooseDialog signDialog;
	// 答题卡对话框
	protected SubjectCardDialog mCardDialog;
	protected Context mContext;

	//计时器
	protected TestTimer mTimer;
	protected TextView tvTimer;

	// 退出确认框
//	private AlertDialog  mConfirmDialog;
	private ExitDialog mConfirmDialog;
	//当前确认类型
	private int mConfirmType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 窗口全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		mContext = this;
		setContentView(R.layout.activity_test);
		init();
	}

	/**
	 * 初始化
	 */
	protected void init() {
//		@SuppressWarnings("unchecked")
		// 印章初始化
		List<SignData> signs = (List<SignData>) SignDataDao.getInstance(this, Constant.DATABASE_NAME).getAllDatas();
		signDialog = new SignChooseDialog(this, signs, this);
		// 控件初始化
		viewPager = (ViewPager) findViewById(R.id.vp_content);
		viewPager.setOnPageChangeListener(this);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
//		initTitle();
		btnSign = (ImageView) findViewById(R.id.btnSign);
		btnFlash = (ImageView) findViewById(R.id.btnFlash);
		btnCard = (ImageView) findViewById(R.id.btnCard);
		btnSubmit = (ImageView) findViewById(R.id.btnSubmit);
		mBundle = getIntent().getExtras();

		// 题目数据，答题卡初始化
		List<BaseTestData> datas = initDatas();
		if (datas == null || datas.size() == 0){
			onDatasError();
			finish();
			return;
		}
		mSubjectAdapter = new SubjectViewPagerAdapter(getSupportFragmentManager(), datas, this, this);
		viewPager.setAdapter(mSubjectAdapter);

		if (mSubjectAdapter.getCount() <= 0) {
			ToastUtil.showToast(mContext, "当前没有题目数据，无法继续");
			finish();
		} else {
			mCardDialog = new SubjectCardDialog(this, datas, this, mSubjectAdapter.getDatas().get(mCurrentIndex).getId());
		}
		operationPager();
	}



	/**
	 * timer初始化,需要使用计时器的，调用该方法初始化，并重写onTimeOut处理超时情況
	 * @param countDownInterval
	 * @param totalTime
	 */
	protected void initTimer(long countDownInterval, long totalTime) {
		tvTimer = (TextView) findViewById(R.id.tvTimer);
		findViewById(R.id.ly_time).setVisibility(View.VISIBLE);
		tvTimer.setVisibility(View.VISIBLE);
//		mTimer = new TestTimer(tvTimer, countDownInterval, totalTime);
//		mTimer.start();
//		mTimer.setOnTimeOutListener(this);
	}

	/**
	 * 初始化确认对话框，例如退出确认，提交确认
	 * 需要使用确认对话框的时候调用showConfirmDialog(int confirmType, String title, String message)
	 * 处理确认点击重写onDialogConfirm(int confirmType)
	 */
	protected void initConfirmDialog() {
		mConfirmDialog = new ExitDialog(this,mConfirmType);
		mConfirmDialog.setDialogListener(this);

//		mConfirmDialog = new AlertDialog.Builder(this).setTitle("").setMessage("").setIcon(null).
//				setPositiveButton("是", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				onDialogConfirm(mConfirmType);
//			}
//		}).setNegativeButton("否", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mConfirmDialog.dismiss();
//			}
//		}).create();

	}
	
	/**
	 * 显示确认对话框
	 * @param confirmType
	 * @param title
	 * @param message
	 */
	protected void showConfirmDialog(int confirmType, String title, String message) {
		if(mConfirmDialog == null) {
			ToastUtil.showToast(mContext, "使用该方法之前，请确保调用了initConfirmDialog方法");
			return ;
		}
		if (!mConfirmDialog.isShowing()) {
			mConfirmType = confirmType;
			mConfirmDialog.setTvTitle(title);
			mConfirmDialog.setTvText(message);

//			mConfirmDialog.setTitle(title);
//			mConfirmDialog.setMessage(message);
			mConfirmDialog.show();
		}
	}

	/**
	 * 初始化标题名称
	 */
	protected abstract void initTitle();

	/**
	 * 初始化测试数据
	 * 
	 * @return
	 */
	protected abstract List<BaseTestData> initDatas();

	/**
	 * 加载数据后的操作
	 */
	protected abstract void operationPager();

	/**
	 * 处理提交按钮点击
	 */
	protected abstract void handSubmit();

	/**
	 * 处理返回按钮点击，或者返回按键点击
	 */
	protected abstract void handleBack();

	/**
	 * 刷新工具栏状态，切页的时候，根据具体题型可能会某些按钮的可见性进行刷新
	 */
	protected abstract void refreshToolBar();

	/**
	 * 保存答案方法，在左右切页或者通过答题卡切页的时候调用
	 */
	protected abstract void saveAnswer();

	/**
	 * 如果试题为空
	 */
	protected abstract void onDatasError();


	public void onClick(View view) throws IOException {
		switch (view.getId()) {
		case R.id.btnSign:
			handleSign();
			break;

		case R.id.btnFlash:
			handleFlash();
			break;

		case R.id.btnSubmit:
			handSubmit();
			break;

		case R.id.btnCard:
			handlCard();
			break;

		case R.id.btnBack:
			handleBack();
			break;

		case R.id.btnLeft:
			scrollToLeft();
			break;

		case R.id.btnRight:
			scrollToRight();
			break;

		default:
			break;
		}
	}

	/**
	 * 处理印章按钮点击-显示印章选择对话框
	 */
	protected void handleSign() {
		if (!signDialog.isShowing()) {
			signDialog.show();
		}
	}

	/**
	 * 处理闪电符按钮点击
	 */
	protected void handleFlash() {
		mSubjectAdapter.showFlash(mCurrentIndex);
	}

	/**
	 * 处理答题卡点击
	 */
	protected void handlCard() {
		if (!mCardDialog.isShowing()) {
			mCardDialog.show(mSubjectAdapter.getData(mCurrentIndex).getId());
		}
	}

	@Override
	public final void onBackPressed() {
		handleBack();
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
	protected void scrollToLeft() {
		if (mCurrentIndex != 0) {
			saveAnswer();
			mCurrentIndex--;
			viewPager.setCurrentItem(mCurrentIndex, true);
		} else {
			ToastUtil.showToast(this, "已经是第一页");
	}
	}

	/**
	 * 滚动到右边页面
	 */
	protected void scrollToRight() {
		if (mCurrentIndex != mSubjectAdapter.getCount() - 1) {
			saveAnswer();
			mCurrentIndex++;
			viewPager.setCurrentItem(mCurrentIndex, true);
		} else {
			ToastUtil.showToast(this, "已经是最后一页");
		}
	}

	@Override
	public void onRedoClicked() {
	}

	@Override
	public void onComplete() {
		scrollToRight();
	}

	@Override
	public void onSaveTestData(BaseTestData testData) {

	}

	@Override
	public void onSaveTestDatas(List<BaseTestData> testDatas) {

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
		saveAnswer();
		mCardDialog.dismiss();
		int index = mSubjectAdapter.getDatas().indexOf(data);
		viewPager.setCurrentItem(index);
		return mSubjectAdapter.getDatas().get(index).getId();
	}

	// 页面切换后调用
	@Override
	public void onPageSelected(int item) {
		saveAnswer();

		mCurrentIndex = item;
		refreshToolBar();

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageScrollStateChanged(int item) {

	}

	@Override
	public void onTimeOut() {
	}

	/**
	 * 对话框确认点击
	 * @param confirmType
	 */
	public void onDialogConfirm(int confirmType) {
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mTimer != null) {
			mTimer.resume();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mTimer != null) {
			mTimer.pause();
		}
	}

	@Override
	protected void onDestroy() {
		if (mConfirmDialog != null){
		mConfirmDialog.dismiss();}
		super.onDestroy();
		if (mTimer != null) {
			mTimer.cancel();
		}
	}

	@Override
	public void onOkClicked() {
		onDialogConfirm(mConfirmType);
	}

	@Override
	public void onCancelClicked() {
		mConfirmDialog.dismiss();
	}
}
