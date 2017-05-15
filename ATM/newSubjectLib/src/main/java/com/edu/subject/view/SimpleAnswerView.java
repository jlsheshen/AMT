package com.edu.subject.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.edu.subject.ISubject;
import com.edu.subject.R;
import com.edu.subject.SubjectState;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.TestBasicData;
import com.edu.subject.data.answer.BasicAnswerData;

/**
 * 简答题
 * @author lucher
 *
 */
public class SimpleAnswerView extends BasicSubjectView implements ISubject {

	//填空
	private EditText etBlank;

	public SimpleAnswerView(Context context, BaseTestData data) {
		super(context, data, "简答");
	}

	@Override
	protected void initBody(RelativeLayout layoutContent) {
		setFillViewport(false);//设置scrollview的fill属性，否则edittext无法动态扩大
		etBlank = (EditText) View.inflate(mContext, R.layout.view_subject_simple_answer, null);
		etBlank.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
		layoutContent.addView(etBlank);
	}

	/**
	 * 刷新正确答案
	 */
	protected void refreshAnswer() {
		tvAnswer.setText(getJudgeResult() + "正确答案是\n" + mSubjectData.getAnswer());
	}

	@Override
	public void disableSubject() {
		etBlank.setEnabled(false);
	}

	@Override
	public void saveAnswer() {
		if (inited) {
			mTestData.setUAnswer(etBlank.getText().toString().trim());
		}
	}

	@Override
	public void initUAnswer(boolean judge) {
		BasicAnswerData answerData = ((TestBasicData) mTestData).getUAnswerData();
		if (answerData != null) {
			etBlank.setText(answerData.getUanswer());
			if(judge) {
				if(mTestData.getuScore() == mTestData.getSubjectData().getScore()) {
					etBlank.setTextColor(Color.BLUE);
					etBlank.setBackgroundResource(R.drawable.shape_edittext_right);
				} else {
					etBlank.setBackgroundResource(R.drawable.shape_edittext_wrong);
					etBlank.setTextColor(Color.RED);
				}
			} else {
				etBlank.setTextColor(Color.BLACK);
				etBlank.setBackgroundResource(R.drawable.shape_edittext_normal);
			}
		} else {
			if (judge) {
				etBlank.setBackgroundResource(R.drawable.shape_edittext_wrong);
				etBlank.setHint("");
			}
		}
	}

	@Override
	protected void judgeAnswer() {
		BasicAnswerData answerData = ((TestBasicData) mTestData).getUAnswerData();
		if (answerData != null && answerData.getUanswer().replace(" ", "").equals(mSubjectData.getAnswer().getText())) {
			mTestData.setuScore(mSubjectData.getScore());

			mTestData.setState(SubjectState.STATE_CORRECT);
//			etBlank.setTextColor(Color.BLUE);
//			etBlank.setBackgroundResource(R.drawable.shape_edittext_right);
		} else {
			mTestData.setuScore(0);
			mTestData.setState(SubjectState.STATE_WRONG);
//			etBlank.setBackgroundResource(R.drawable.shape_edittext_wrong);
//			etBlank.setTextColor(Color.RED);
		}
	}

	@Override
	public void reset() {
		super.reset();
		if (inited) {
			etBlank.setEnabled(true);
			etBlank.setTextColor(Color.GRAY);
			etBlank.setBackgroundResource(R.drawable.shape_edittext_normal);
			etBlank.setText("");
			etBlank.setHint("请在此作答");
		}
	}
}
