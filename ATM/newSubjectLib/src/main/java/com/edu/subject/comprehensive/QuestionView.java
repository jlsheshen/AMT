package com.edu.subject.comprehensive;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.edu.subject.R;
import com.edu.subject.common.rich.RichContentView;
import com.edu.subject.common.rich.RichTextData;

/**
 * 综合题的问题视图
 * @author lucher
 *
 */
public class QuestionView extends ScrollView {
	
	// 题型，得分
	protected TextView tvSubjectType, tvUscore;
	// 问题
	protected RichContentView richQuestion;

	public QuestionView(Context context) {
		super(context);
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		View.inflate(getContext(), R.layout.layout_comprehensive_question, this);
		// 视图初始化
		tvSubjectType = (TextView) findViewById(R.id.tvSubjectType);
		tvUscore = (TextView) findViewById(R.id.tvUscore);
		richQuestion = (RichContentView) findViewById(R.id.tvQuestion);
	}
	
	/**
	 * 设置问题内容
	 * @param text
	 */
	public void setQuestion(RichTextData data) {
		richQuestion.setRichData(data);
	}
	
	/**
	 * 设置题目类别信息
	 * @param type
	 */
	public void setSubjectType(String type) {
		tvSubjectType.setText(type);
	}

	/**
	 * 设置用户得分
	 * @param score
	 */
	public void setUscore(String score) {
		tvUscore.setVisibility(View.VISIBLE);
		tvUscore.setText(score);
	}
	
	/**
	 * 隐藏用户得分
	 */
	public void hideUscore() {
		tvUscore.setVisibility(View.GONE);
	}
}
