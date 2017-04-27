package com.edu.subject.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.edu.subject.ISubject;
import com.edu.subject.R;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectState;
import com.edu.subject.TestMode;
import com.edu.subject.common.SlideableContainer;
import com.edu.subject.comprehensive.QuestionView;
import com.edu.subject.comprehensive.SubjectsAdapter;
import com.edu.subject.data.SubjectComprehensiveData;
import com.edu.subject.data.TestComprehensiveData;

/**
 * 综合题视图
 * @author lucher
 *
 */
public class ComprehensiveView extends FrameLayout implements ISubject, OnPageChangeListener {

	// 测试，题目数据
	private TestComprehensiveData mTestData;
	private int mTestMode;
	protected SubjectComprehensiveData mSubjectData;
	protected Context mContext;

	//问题视图
	private QuestionView questionView;
	//可拖动滑块自由调节高度的容器类
	private SlideableContainer slideableContainer;
	//滑块视图标签
	private TextView tvLabel;

	//子题的adapter
	private SubjectsAdapter mSubjectsAdapter;
	//子题当前页
	private int mCurrentPage;

	// 是否初始化
	protected boolean inited;

	public ComprehensiveView(Context context, TestComprehensiveData data) {
		super(context);

		mContext = context;
		mTestData = data;
		mTestMode = mTestData.getTestMode();
		mSubjectData = mTestData.getSubjectData();
		View.inflate(context, R.layout.loading_layout, this);
	}

	@Override
	public void onVisible() {
		if (!inited) {
			slideableContainer = new SlideableContainer(mContext);
			removeAllViews();
			addView(slideableContainer);

			init();
			inited = true;
			initContent();
		}
	}

	/**
	 * 初始化
	 */
	private void init() {
		//问题初始化
		questionView = new QuestionView(mContext);
		slideableContainer.setUnSlideableContent(questionView);
		questionView.setSubjectType("综合题(" + mSubjectData.getScore() + "分)");
		questionView.setQuestion(mSubjectData.getQuestion());
		//滑块初始化
		View sliderView = View.inflate(mContext, R.layout.layout_comprehensive_slider, null);
		tvLabel = (TextView) sliderView.findViewById(R.id.tvLabel);
		slideableContainer.setSliderContent(sliderView);
		tvLabel.setText("子题");
		//子题初始化
		ViewPager subjectsViewPager = new ViewPager(mContext);
		slideableContainer.setSlideableContent(subjectsViewPager);
		mSubjectsAdapter = new SubjectsAdapter(mContext, mTestData.getTestDatas(), mTestData);
		subjectsViewPager.setAdapter(mSubjectsAdapter);
		subjectsViewPager.setOnPageChangeListener(this);
	}

	/**
	 * 初始化内容
	 * 
	 * @param type
	 */
	private void initContent() {
		refreshSliderLabel();
		int state = mTestData.getState();
		//查看详情或者练习模式下已提交的题目，直接显示作答详情
		if (mTestMode == TestMode.MODE_SHOW_DETAILS) {
			initUAnswer(true);
			showDetails();
		} else if (mTestMode == TestMode.MODE_SHOW_UANSWER) {
			initUAnswer(false);
			disableSubject();
		} else if (mTestMode == TestMode.MODE_PRACTICE) {
			if (state == SubjectState.STATE_CORRECT || state == SubjectState.STATE_WRONG) {
				initUAnswer(true);
				showDetails();
			} else if (state == SubjectState.STATE_UNFINISH) {
				initUAnswer(false);
			}
		} else if (mTestMode == TestMode.MODE_EXAM) {
			if (state == SubjectState.STATE_CORRECT || state == SubjectState.STATE_WRONG || state == SubjectState.STATE_UNFINISH) {
				initUAnswer(false);
			}
		} else if (mTestMode == TestMode.MODE_EXAM_EMPTY) {
		}
	}

	/**
	 * 让该题不能再编辑
	 */
	private void disableSubject() {
		mSubjectsAdapter.disableSubject();
	}

	/**
	 * 初始化用户答案
	 * @param judge
	 */
	private void initUAnswer(boolean judge) {
		mSubjectsAdapter.initUAnswer(judge);
	}

	/**
	 * 刷新滑块标签内容
	 */
	private void refreshSliderLabel() {
		if (mTestData.getTestDatas() == null || mTestData.getTestDatas().size() <= 0) {
			tvLabel.setText("子题0/0");
		} else {
			String text = "子题" + (mCurrentPage + 1) + "/" + mSubjectsAdapter.getCount() + "(" + mTestData.getTestDatas().get(mCurrentPage).getSubjectData().getScore() + "分)";
			tvLabel.setText(text);
		}

	}

	/**
	 * 刷新用户分数
	 */
	private void refreshUscore() {
		questionView.setUscore("得" + mTestData.getuScore() + "分");
	}

	/**
	 * 判断答案
	 */
	private void judgeAnswer() {
		if (mTestData.getuScore() == mTestData.getSubjectData().getScore()) {
			mTestData.setState(SubjectState.STATE_CORRECT);
		} else {
			mTestData.setState(SubjectState.STATE_WRONG);
		}
	}

	@Override
	public void saveAnswer() {
		if (inited) {
			mTestData.setUAnswerData(mSubjectsAdapter.getUAnswer());
		}
	}

	@Override
	public float submit() {
		if (inited) {
			//计算得分
			float score = mSubjectsAdapter.submit();
			mTestData.setuScore(score);
			//保存答案
			saveAnswer();
			//判断答案
			judgeAnswer();
			if (mTestMode == TestMode.MODE_PRACTICE) {
				showDetails();
			}
		}
		return mTestData.getuScore();

	}

	@Override
	public void showDetails() {
		mSubjectsAdapter.showDetails();
		refreshUscore();
	}

	@Override
	public void reset() {
		if (inited) {
			mTestData.setState(SubjectState.STATE_INIT);
			mSubjectsAdapter.reset();
			questionView.hideUscore();
		}
	}

	@Override
	public void setSubjectListener(SubjectListener listener) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		mCurrentPage = arg0;
		refreshSliderLabel();
	}

}
