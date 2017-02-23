package com.edu.subject.comprehensive;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectState;
import com.edu.subject.SubjectType;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.TestComprehensiveData;
import com.edu.subject.data.TestEntryData;
import com.edu.subject.data.UserAnswerData;
import com.edu.subject.data.answer.BasicAnswerData;
import com.edu.subject.data.answer.CommonAnswerData;
import com.edu.subject.data.answer.ComprehensiveAnswerData;
import com.edu.subject.view.BasicSubjectView;
import com.edu.subject.view.BlankView;
import com.edu.subject.view.EntryView;
import com.edu.subject.view.JudgeView;
import com.edu.subject.view.MultiSelectView;
import com.edu.subject.view.SimpleAnswerView;
import com.edu.subject.view.SingleSelectView;

/**
 * 综合题中子题adapter
 * @author lucher
 *
 */
public class SubjectsAdapter extends PagerAdapter {

	private Context mContext;

	//所有子题数据
	private List<BaseTestData> mSubjectDatas;
	//所有子题视图
	private List<BasicSubjectView> mViews;
	//综合题测试数据
	private TestComprehensiveData mTestData;

	public SubjectsAdapter(Context context, List<BaseTestData> subjects, TestComprehensiveData testData) {
		mContext = context;
		mSubjectDatas = subjects;
		mTestData = testData;
		initViews();
	}

	/**
	 * 初始化各子题视图
	 */
	private void initViews() {
		if (mSubjectDatas != null) {
			mViews = new ArrayList<BasicSubjectView>(mSubjectDatas.size());
			for (int i = 0; i < mSubjectDatas.size(); i++) {
				//设置答题状态
				if (mTestData.getState() == SubjectState.STATE_CORRECT || mTestData.getState() == SubjectState.STATE_WRONG) {
					if (mSubjectDatas.get(i).getuScore() == mSubjectDatas.get(i).getSubjectData().getScore()) {
						mSubjectDatas.get(i).setState(SubjectState.STATE_CORRECT);
					} else {
						mSubjectDatas.get(i).setState(SubjectState.STATE_WRONG);
					}
				}

				BasicSubjectView subjectView = createSubjectView(mSubjectDatas.get(i));
				mViews.add(subjectView);
				subjectView.setChild(true);
				subjectView.onVisible();
			}
		}
	}

	/**
	 * 创建对应的题型视图
	 * @param testData
	 * @return
	 */
	private BasicSubjectView createSubjectView(BaseTestData testData) {
		BasicSubjectView subjectView = null;
		switch (testData.getSubjectData().getSubjectType()) {

		case SubjectType.SUBJECT_SINGLE:
			subjectView = new SingleSelectView(mContext, testData);

			break;
		case SubjectType.SUBJECT_MULTI:
			subjectView = new MultiSelectView(mContext, testData);
			break;

		case SubjectType.SUBJECT_JUDGE:
			subjectView = new JudgeView(mContext, testData);
			break;

		case SubjectType.SUBJECT_ENTRY:
			subjectView = new EntryView(mContext, (TestEntryData) testData);

			break;

		case SubjectType.SUBJECT_SIMPLE_ANSWER:
			subjectView = new SimpleAnswerView(mContext, testData);
			break;

		case SubjectType.SUBJECT_BLANK:
			subjectView = new BlankView(mContext, testData);
			break;

		default:
			ToastUtil.showToast(mContext, "子题不支持该题型：" + testData.getSubjectData().getSubjectType());
			break;
		}
		return subjectView;
	}

	@Override
	public int getCount() {
		if (mViews == null) {
			return 0;
		}
		return mViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mViews.get(position));//添加页卡  
		return mViews.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViews.get(position));//删除页卡  
	}

	/**
	 * 获取用户答案
	 * @return
	 */
	public ComprehensiveAnswerData getUAnswer() {
		ComprehensiveAnswerData answer = new ComprehensiveAnswerData();
		List<UserAnswerData> childAnswers = new ArrayList<UserAnswerData>(mViews.size());
		for (int i = 0; i < mViews.size(); i++) {
			CommonAnswerData answerData = mSubjectDatas.get(i).getUAnswerData();
			UserAnswerData userAnswer = new UserAnswerData();
			if (answerData instanceof BasicAnswerData) {//基础类题型，直接设置uAnswer为字符串
				userAnswer.setUanswer(((BasicAnswerData) answerData).getUanswer());
			} else {
				userAnswer.setUanswer(JSON.toJSONString(answerData));
			}
			childAnswers.add(userAnswer);
		}
		answer.setAnswerDatas(childAnswers);

		return answer;
	}

	/**
	 * 重置子题
	 */
	public void reset() {
		for (int i = 0; i < mViews.size(); i++) {
			BasicSubjectView subject = mViews.get(i);
			subject.reset();
		}
	}

	/**
	 * 提交子题
	 * @return
	 */
	public float submit() {
		float score = 0;
		for (int i = 0; i < mViews.size(); i++) {
			BasicSubjectView subject = mViews.get(i);
			subject.submit();
			score += mSubjectDatas.get(i).getuScore();
		}
		return score;
	}

	/**
	 * 让子题显示详情
	 */
	public void showDetails() {
		for (int i = 0; i < mViews.size(); i++) {
			BasicSubjectView subject = mViews.get(i);
			subject.showDetails();
		}
	}

	/**
	 * 初始化子题用户答案
	 */
	public void initUAnswer() {
		for (int i = 0; i < mViews.size(); i++) {
			BasicSubjectView subject = mViews.get(i);
			subject.initUAnswer();
		}
	}

	/**
	 * 禁用子题
	 */
	public void disableSubject() {
		for (int i = 0; i < mViews.size(); i++) {
			BasicSubjectView subject = mViews.get(i);
			subject.disableSubject();
		}
	}
}
