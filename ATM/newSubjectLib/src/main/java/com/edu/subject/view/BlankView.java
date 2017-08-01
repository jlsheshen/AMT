package com.edu.subject.view;

import android.content.Context;
import android.widget.RelativeLayout;

import com.edu.subject.ISubject;
import com.edu.subject.R;
import com.edu.subject.SubjectState;
import com.edu.subject.blank.FillInBlankView;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.TestBlankData;
import com.edu.subject.data.answer.BlankAnswer;
import com.edu.subject.data.answer.BlankAnswerData;

import java.util.List;

/**
 * 填空题
 * @author lucher
 *
 */
public class BlankView extends BasicSubjectView implements ISubject {

	//填空题控件
	private FillInBlankView fillInBlankView;

	public BlankView(Context context, BaseTestData data) {
		super(context, data, "填空");
	}

	@Override
	protected void initBody(RelativeLayout layoutContent) {
		String question = ((TestBlankData) mTestData).getSubjectData().getQuestion().getText().toString();
		List<String> answers = ((TestBlankData) mTestData).getSubjectData().getAnswers();
		//填空控件初始化
		fillInBlankView = new FillInBlankView(mContext, question, answers);
		fillInBlankView.setTextSize(getResources().getDimension(R.dimen.txt_size_small));
		layoutContent.addView(fillInBlankView);
	}

	@Override
	protected void refreshAnswer() {
		List<String> answers = ((TestBlankData) mTestData).getSubjectData().getAnswers();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < answers.size(); i++) {
			String answer = answers.get(i);
			if (i == 0) {
				builder.append(answer);
			} else {
				builder.append("\n" + answer);
			}
		}
		tvAnswer.setText(getJudgeResult() + "正确答案是:\n" + builder.toString());
	}

	@Override
	public void saveAnswer() {
		if (inited) {
			BlankAnswerData answer = fillInBlankView.getUAnswer(mTestData.getSubjectData().getScore());
			mTestData.setUAnswerData(answer);
		}
	}

	@Override
	public void disableSubject() {
		if (inited) {
			fillInBlankView.setEnabled(false);
		}
	}

	@Override
	public void reset() {
		super.reset();
		if (inited) {
			fillInBlankView.setEnabled(true);//使空可编辑，并且清空内容
		}
	}

	@Override
	public void initUAnswer(boolean judge) {
		BlankAnswerData answer = ((TestBlankData) mTestData).getUAnswerData();
		if (answer != null) {
			fillInBlankView.initUAnswer(answer.getAnswers(), judge);
		} else {
			if(judge) {
				fillInBlankView.setUAnswerError();
			}
		}
	}

	@Override
	protected void judgeAnswer() {
		//得分计算
		BlankAnswerData answer = ((TestBlankData) mTestData).getUAnswerData();
		float score = 0;

		if(answer == null){
			answer = new BlankAnswerData();
		}else {
		for (BlankAnswer blank : answer.getAnswers()) {
			score += blank.getScore();
		}}
		//此处废除判分逻辑
		mTestData.setuScore(0);
		//根据每个空的结果状态设置对应空的样式
//		fillInBlankView.judgeAnswer(answer.getAnswers());

		if (score == mTestData.getSubjectData().getScore()) {
			mTestData.setState(SubjectState.STATE_CORRECT);
		} else {
			mTestData.setState(SubjectState.STATE_WRONG);
		}
	}
}
