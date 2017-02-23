package com.edu.subject.view;

import java.util.List;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.subject.ISubject;
import com.edu.subject.blank.FillInBlankView;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.TestBlankData;

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
		String question = ((TestBlankData) mTestData).getSubjectData().getBody();
		List<String> answers = ((TestBlankData) mTestData).getSubjectData().getAnswers();
		fillInBlankView = new FillInBlankView(mContext, question, answers);
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
		tvAnswer.setText(getJudgeResult() + "，正确答案是:\n" + builder.toString());
	}

	@Override
	public void saveAnswer() {

	}

	@Override
	public void disableSubject() {

	}

	@Override
	public void initUAnswer() {

	}

	@Override
	protected void judgeAnswer() {

	}
}
