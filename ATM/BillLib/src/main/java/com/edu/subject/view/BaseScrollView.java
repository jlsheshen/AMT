package com.edu.subject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.edu.library.common.PreferenceHelper;
import com.edu.subject.data.BaseSubjectData;
import com.edu.subject.data.BaseTestData;

/**
 * 自定义题型视图的基类
 * 
 * @author lucher
 * 
 */
public abstract class BaseScrollView extends RelativeLayout {

	/**
	 * 题目数据保存
	 */
	protected BaseTestData mTestData;
	protected BaseSubjectData mData;

	// preference的帮助类
	protected PreferenceHelper preHelper;

	protected String sendExamIdStr;

	/**
	 * 测试的模式，取值为TEST_MODE_NORMAL，TEST_MODE_TEST
	 */
	protected int testMode;

	public BaseScrollView(Context context) {
		super(context);
	}

	public BaseScrollView(Context context, BaseTestData data) {
		super(context);
		mTestData = data;
		this.testMode = mTestData.getTestMode();
		mData = mTestData.getSubjectData();
	}

	public BaseScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 更改数据库表TB_TEST中单多判答题状态
	 */
	private void updateState(String answer) {

		if (answer.equals(mData.getAnswer())) {
		} else {
		}
	}

	/**
	 * 处理答案被选择的事件
	 * 
	 * @param answer
	 */
	protected void handleOnClick(String answer) {
	};

	/**
	 * 对当前答案进行判分
	 * 
	 * @param answer
	 */
	protected void gradeAnswer(String answer) {
	}

	/**
	 * 不能再选择
	 */
	protected abstract void disableOption();

	/**
	 * 重置
	 */
	public abstract void reset();

	/**
	 * 测试模式进行判分
	 * 
	 * @param answer
	 */
	private void gradeTestAnswer(String answer) {
	}

	/**
	 * 对普通模式进行评分
	 * 
	 * @param answer
	 */
	private void gradeNormalAnswer(String answer) {
	}
}