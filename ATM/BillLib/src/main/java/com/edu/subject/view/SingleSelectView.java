package com.edu.subject.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.edu.R;
import com.edu.subject.ISubject;
import com.edu.subject.SubjectListener;
import com.edu.subject.data.BaseTestData;

/**
 * 单选题
 * 
 * @author lucher
 * 
 */
public class SingleSelectView extends BaseScrollView implements ISubject, OnClickListener {

	private static final String TAG = "SingleSelectView";
	/**
	 * 问题，正确答案文本控件
	 */
	private TextView tvQestion, tvAnswer;
	// 解析
	private TextView tvAnalysis;

	/**
	 * 选项组
	 */
	private RadioGroup rgOption;

	/**
	 * 四个选项按钮
	 */
	private RadioButton rbA, rbB, rbC, rbD;
	// 题目类型，点击时弹出答题卡
	private TextView tvSubjectType;

	/**
	 * 发送按钮
	 */
	private Button btnSend;

	private Context mContext;

	public SingleSelectView(Context context, BaseTestData data) {
		super(context, data);
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.subject_single_select, this);
		init(mTestData);
		refreshAnswerState(mTestData);
	}

	/**
	 * 初始化控件
	 * 
	 * @param mData
	 */
	private void init(BaseTestData data) {
		tvSubjectType = (TextView) this.findViewById(R.id.tv_subject_type);
		tvSubjectType.setOnClickListener(this);

		tvQestion = (TextView) this.findViewById(R.id.tv_question);
		tvAnswer = (TextView) this.findViewById(R.id.tv_answer);
		tvAnalysis = (TextView) this.findViewById(R.id.tv_analysis);

		rgOption = (RadioGroup) this.findViewById(R.id.rg_option);

		rbA = (RadioButton) this.findViewById(R.id.rb_A);
		rbB = (RadioButton) this.findViewById(R.id.rb_B);
		rbC = (RadioButton) this.findViewById(R.id.rb_C);
		rbD = (RadioButton) this.findViewById(R.id.rb_D);
		rbA.setOnClickListener(this);
		rbB.setOnClickListener(this);
		rbC.setOnClickListener(this);
		rbD.setOnClickListener(this);

		btnSend = (Button) this.findViewById(R.id.btn_send);
		btnSend.setOnClickListener(this);
		btnSend.setVisibility(View.GONE);
	}

	/**
	 * 按照固定格式解析答案选项，并显示在选项里 此处根据>>>解析
	 * 
	 * @param option
	 */
	private void parseOption(String option) {
		String[] options = option.split(">>>");
		if (options.length != 4) {
			// ToastUtil.showDbProblem(getContext(), "SingleSelect-id:" +
			// mData.getId() + "," + option);
			return;
		}

		rbA.setText("A" + options[0].substring(options[0].indexOf("."), options[0].length()));
		rbA.setTag(options[0].substring(0, options[0].indexOf(".")));
		rbB.setText("B" + options[1].substring(options[1].indexOf("."), options[1].length()));
		rbB.setTag(options[1].substring(0, options[1].indexOf(".")));
		rbC.setText("C" + options[2].substring(options[2].indexOf("."), options[2].length()));
		rbC.setTag(options[2].substring(0, options[2].indexOf(".")));
		rbD.setText("D" + options[3].substring(options[3].indexOf("."), options[3].length()));
		rbD.setTag(options[3].substring(0, options[3].indexOf(".")));
	}

	/**
	 * 更新正确答案的显示状态以及用户的选择状态
	 */
	private void refreshAnswerState(BaseTestData data) {

	}

	@Override
	public void disableOption() {
		rbA.setEnabled(false);
		rbB.setEnabled(false);
		rbC.setEnabled(false);
		rbD.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_subject_type:
//
//			break;
//		default:
//			break;
//		}
	}

	@Override
	public void applyData(BaseTestData data) {

	}

	@Override
	public void saveAnswer() {
		// TODO Auto-generated method stub

	}

	@Override
	public float submit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void showDetails() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSubjectListener(SubjectListener listener) {
		// TODO Auto-generated method stub
		
	}

}
