package com.edu.subject.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.edu.library.util.ToastUtil;
import com.edu.subject.R;
import com.edu.subject.SubjectState;
import com.edu.subject.basic.OptionData;
import com.edu.subject.basic.SelectItemAdapter;
import com.edu.subject.basic.SelectItemAdapter.SelectListener;
import com.edu.subject.common.FixedListView;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.TestBasicData;
import com.edu.subject.data.answer.BasicAnswerData;

/**
 * 选择类题型基类，主要包括：单多判
 * 
 * @author lucher
 * 
 */
public abstract class BaseSelectView extends BasicSubjectView implements SelectListener {

	// 选项视图adapter
	protected SelectItemAdapter mAdapter;

	public BaseSelectView(Context context, BaseTestData data, String type) {
		super(context, data, type);
	}

	@Override
	protected void initBody(RelativeLayout layoutContent) {
		FixedListView listView = (FixedListView) View.inflate(mContext, R.layout.layout_select_listview, null);
		listView.setId(1234);
		mAdapter = initAdapter();
		mAdapter.setSelectListener(this);
		if (mAdapter == null) {
			ToastUtil.showToast(mContext, "选项适配器不得为空");
		}
		listView.setAdapter(mAdapter);
		layoutContent.addView(listView);
	}

	@Override
	public void initUAnswer(boolean judge) {
		BasicAnswerData answerData = ((TestBasicData) mTestData).getUAnswerData();
		if (answerData != null) {
			mAdapter.setUAnswer(answerData.getUanswer());
		}
	}

	@Override
	protected void judgeAnswer() {
		BasicAnswerData answerData = ((TestBasicData) mTestData).getUAnswerData();
		if (answerData != null && answerData.getUanswer().equals(mSubjectData.getAnswer().getText())) {
			mTestData.setuScore(mSubjectData.getScore());
			mTestData.setState(SubjectState.STATE_CORRECT);
		} else {
			mTestData.setuScore(0);
			mTestData.setState(SubjectState.STATE_WRONG);
		}
	}

	@Override
	public void saveAnswer() {
		if (inited) {
			mTestData.setUAnswer(mAdapter.getUAnswer());
		}
	}

	@Override
	public void disableSubject() {
		if (inited) {
			mAdapter.setEnabled(false);
		}
	}

	@Override
	public void reset() {
		super.reset();
		if (inited) {
			mAdapter.clearSelect();
		}
	}

	@Override
	public void onItemSelected(OptionData data) {
		if (isChild) {//作为子题，点击后只保存答案，不提交
			saveAnswer();
		} else {
//			submit();
//			if (mTestMode == TestMode.MODE_PRACTICE && mSubjectListener != null) {
//				mSubjectListener.onSaveTestData(mTestData);
//			}
			saveAnswer();//方案调整，单选和判断也需要点击右上角提交按钮进行提交操作
		}
	}

	/**
	 * 初始化答案
	 */
	protected void refreshAnswer() {
		String uAnswer = "空";
		if (mTestData.getUAnswerData() != null) {
			BasicAnswerData answer = (BasicAnswerData) mTestData.getUAnswerData();
			uAnswer = answer.getUanswer();
			if (TextUtils.isEmpty(uAnswer)) {
				uAnswer = "空";
			} else {
				uAnswer = mAdapter.getAnswerLabel(uAnswer);
			}

		}
		tvAnswer.setText(getJudgeResult() + "正确答案是" + mAdapter.getAnswerLabel(mSubjectData.getAnswer().getText()) + ",您的答案是" + uAnswer);
	}

	/**
	 * 初始化选项adapter
	 */
	protected abstract SelectItemAdapter initAdapter();
}
