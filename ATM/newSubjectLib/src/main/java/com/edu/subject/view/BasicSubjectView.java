package com.edu.subject.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.edu.subject.ISubject;
import com.edu.subject.R;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectState;
import com.edu.subject.TestMode;
import com.edu.subject.common.rich.RichContentView;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.CommonSubjectData;
import com.edu.subject.data.answer.BasicAnswerData;

/**
 * 基础题型视图的基类,目前主要针对于：单多判，填空，简答,分录等
 * 
 * @author lucher
 * 
 */
public abstract class BasicSubjectView extends ScrollView implements ISubject {

	// 测试，题目数据
	protected BaseTestData mTestData;
	protected CommonSubjectData mSubjectData;

	// 自动跳转监听
	protected SubjectListener mSubjectListener;

	// 答题模式
	protected int mTestMode;
	protected Context mContext;

	// 题型，得分
	protected TextView tvSubjectType, tvUscore;
	//问题，答案，解析
	protected RichContentView richQuestion, tvAnswer, tvAnalysis;
	// 解析分割线
	protected View splitLine;
	// 问题，内容，解析容器
	protected RelativeLayout layoutQuestion, layoutContent, layoutAnalysis;

	//当前题型
	private String mType;
	// 是否初始化
	protected boolean inited;

	//是否为子题
	protected boolean isChild;

	public BasicSubjectView(Context context, BaseTestData data, String type) {
		super(context);
		setFillViewport(true);//如果不设置为true，scrollview可能无法铺满屏幕
		mContext = context;
		mTestData = data;
		mType = type;
		View.inflate(context, R.layout.loading_layout, this);
	}

	/**
	 * 用于延迟加载，当界面显示时再加载
	 */
	@Override
	public void onVisible() {
		if (!inited) {
			removeAllViews();
			View.inflate(mContext, R.layout.layout_basic_subject, this);
			init();
			inited = true;
			initContent(mType);
		}
	}

	/**
	 * 设置是否为子题
	 * @param isChild
	 */
	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}

	/**
	 * 初始化
	 */
	private void init() {
		mTestMode = mTestData.getTestMode();
		mSubjectData = mTestData.getSubjectData();
		// 视图初始化
		tvSubjectType = (TextView) findViewById(R.id.tvSubjectType);
		tvUscore = (TextView) findViewById(R.id.tvUscore);
		tvAnswer = (RichContentView) findViewById(R.id.tvAnswer);
		tvAnalysis = (RichContentView) findViewById(R.id.tvAnalysis);
		splitLine = findViewById(R.id.splitLine);
		richQuestion = (RichContentView) findViewById(R.id.tvQuestion);
		layoutQuestion = (RelativeLayout) findViewById(R.id.layoutQuestion);
		layoutContent = (RelativeLayout) findViewById(R.id.layoutContent);
		layoutAnalysis = (RelativeLayout) findViewById(R.id.layoutAnalysis);
	}

	/**
	 * 初始化内容
	 * 
	 * @param type
	 */
	private void initContent(String type) {
		initQuestion();
		initType(type);
		initBody(layoutContent);
		initAnalysis();
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
		} else if(mTestMode == TestMode.MODE_EXAM_EMPTY) {
			
		}
	}

	/**
	 * 该题内容初始化
	 * 
	 * @param layoutContent
	 */
	protected abstract void initBody(RelativeLayout layoutContent);

	/**
	 * 让该题不能再编辑
	 */
	public abstract void disableSubject();

	/**
	 * 初始化用户答案
	 * @param judge 是否需要判断答案正误
	 */
	public abstract void initUAnswer(boolean judge);

	/**
	 * 判断答案正误
	 */
	protected abstract void judgeAnswer();

	/**
	 * 初始化题目
	 */
	protected void initQuestion() {
		richQuestion.setRichData(mSubjectData.getQuestion());
	}

	/**
	 * 初始化题目类型
	 * 
	 * @param type
	 */
	protected void initType(String type) {
		if (isChild) {
			tvSubjectType.setVisibility(View.GONE);
		} else {
			tvSubjectType.setVisibility(View.VISIBLE);
			tvSubjectType.setText(type + "(" + mSubjectData.getScore() + "分)");
		}
	}

	/**
	 * 初始化解析
	 */
	protected void initAnalysis() {
		tvAnalysis.setRichData(mSubjectData.getAnalysis());
	}

	/**
	 * 刷新正确答案
	 */
	protected void refreshAnswer() {
		String uAnswer = "空";
		if (mTestData.getUAnswerData() != null) {
			BasicAnswerData answer = (BasicAnswerData) mTestData.getUAnswerData();
			uAnswer = answer.getUanswer();
			if (TextUtils.isEmpty(uAnswer)) {
				uAnswer = "空";
			}
		}
		tvAnswer.setText(getJudgeResult() + "，正确答案是" + mSubjectData.getAnswer() + ",您的答案是" + uAnswer);
	}

	/**
	 * 获取答题判断结果
	 * @return
	 */
	protected String getJudgeResult() {
		String result = null;
		if (mTestData.getState() == SubjectState.STATE_CORRECT) {
			result = "回答正确";
		} else {
			result = "回答错误";
		}
		return result;
	}

	/**
	 * 刷新用户分数
	 */
	protected void refreshUscore() {
		if (isChild) {
			tvUscore.setVisibility(View.GONE);
		} else {
			tvUscore.setVisibility(View.VISIBLE);
			tvUscore.setText("得" + mTestData.getuScore() + "分");
		}
	}

	@Override
	public float submit() {
		if (inited) {
			saveAnswer();
			judgeAnswer();
			if (mTestMode == TestMode.MODE_PRACTICE) {
				showDetails();
			}
		}
		return mTestData.getuScore();
	}

	@Override
	public void showDetails() {
		disableSubject();
		refreshUscore();
		refreshAnswer();
		layoutAnalysis.setVisibility(View.VISIBLE);
	}

	@Override
	public void reset() {
		if (inited) {
			layoutAnalysis.setVisibility(View.GONE);
			tvUscore.setVisibility(View.GONE);
			mTestData.setState(SubjectState.STATE_INIT);
		}
	}

	@Override
	public void setSubjectListener(SubjectListener listener) {
		mSubjectListener = listener;
	}

}