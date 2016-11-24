package com.edu.library;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.edu.library.common.PreferenceHelper;
import com.edu.library.view.GuidanceView;
import com.edu.library.view.WelcomeView;

/**
 * 自带引导页和欢迎页的Activity，包含活跃度搜集功能,注：界面视图初始化必须在onMainViewLoaded中实现
 * 
 * @author lucher
 */
public abstract class EduGuidanceActivity extends EduBaseActivity {

	enum WhichView {// 界面:引导界面，欢迎界面，主界面
		GUIDANCE_VIEW, WELLCOM_VIEW, MAIN_VIEW;
	}

	// 当前界面
	protected WhichView currentView;

	// 引导页控件
	protected GuidanceView guidanceView;
	// 欢迎页控件
	protected WelcomeView welcomeView;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case EduConstant.MSG_GOTO_GUIDANCE_VIEW:
				gotoGuidanceView();

				break;
			case EduConstant.MSG_GOTO_WELCOME_VIEW:
				gotoWelcomeView();

				break;
			case EduConstant.MSG_GOTO_MAIN_VIEW:
				gotoMainView();

				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 窗口全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		initGuidanceView();
		initWelcomeView();

		PreferenceHelper preference = PreferenceHelper.getInstance(mContext);
		if (preference.isFirstTime(PreferenceHelper.KEY_GUIDANCE_PAGE)) {
			// 如果第一次使用，显示引导界面
			handler.sendEmptyMessage(EduConstant.MSG_GOTO_GUIDANCE_VIEW);
		} else {// 否则直接进入欢迎页面
			handler.sendEmptyMessage(EduConstant.MSG_GOTO_WELCOME_VIEW);
		}
	}

	/**
	 * 初始化引导页控件，如果不初始化则不显示
	 */
	protected abstract void initGuidanceView();

	/**
	 * 初始化欢迎页控件，如果不初始化则不显示
	 */
	protected abstract void initWelcomeView();

	/**
	 * 主界面加载完成，setContentView等操作需要在此方法里进行调用
	 */
	protected abstract void onMainViewLoaded();

	/**
	 * 进入引导界面
	 */
	private void gotoGuidanceView() {
		currentView = WhichView.GUIDANCE_VIEW;
		if (guidanceView == null) {
			handler.sendEmptyMessage(EduConstant.MSG_GOTO_WELCOME_VIEW);
			return;
		}
		setContentView(guidanceView);
	}

	/**
	 * 进入欢迎界面
	 */
	private void gotoWelcomeView() {
		currentView = WhichView.WELLCOM_VIEW;
		if (welcomeView == null) {
			handler.sendEmptyMessage(EduConstant.MSG_GOTO_MAIN_VIEW);
			return;
		}
		setContentView(welcomeView);
	}

	/**
	 * 进入主界面
	 */
	protected void gotoMainView() {
		currentView = WhichView.MAIN_VIEW;
		onMainViewLoaded();
	}

}
