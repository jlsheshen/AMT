package com.edu.accountingteachingmaterial.subject.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.library.common.PreferenceHelper;
import com.edu.subject.ISubject;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectState;
import com.edu.subject.data.BaseTestData;
import com.edu.accountingteachingmaterial.bean.SubjectBasicData;

import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_LOOK;
import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_NORMAL;
import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_TEST;

/**
 * 判断题 视图
 *
 * @author lucher
 */
public class SubjectJudgeView extends BaseScrollView implements ISubject, View.OnClickListener {

    /**
     * 问题，正确答案文本控件
     */
    private TextView tvQestion, tvAnswer;
    // 解析
    private TextView tvAnalysis;

    /**
     * 对，错选项按钮
     */
    private RadioButton rbTrue, rbFalse;
    // 题目类型，点击时弹出答题卡
    private TextView tvSubjectType;
    private RelativeLayout layout;


    /**
     * 处理延时自动翻页
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
//			if (mAutoJumpNextListener != null) {
//				mAutoJumpNextListener.autoJumpNext();
//			}
        }
    };

    public SubjectJudgeView(Context context, BaseTestData data, int testMode) {
        super(context, data, testMode);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_subject_judge, this);
        init((SubjectBasicData) data.getSubjectData());
        refreshAnswerState();
    }

    /**
     * 初始化控件
     *
     * @param data
     */
    private void init(SubjectBasicData data) {
        layout = (RelativeLayout) this.findViewById(R.id.layout_parent);
        layout.setOnClickListener(this);
        tvQestion = (TextView) this.findViewById(R.id.tv_question);
        tvQestion.setText(data.getQuestion());
        tvAnswer = (TextView) this.findViewById(R.id.tv_answer);
        tvSubjectType = (TextView) this.findViewById(R.id.tv_subject_type);
        tvSubjectType.setOnClickListener(this);
        tvAnalysis = (TextView) this.findViewById(R.id.tv_analysis);
//		tvSubjectType.setText("错误" + mTestData.getErrorCount() + "次");
        tvSubjectType.setText("判断 - " + data.getSubjectIndex());
        rbTrue = (RadioButton) this.findViewById(R.id.rb_A);
        rbFalse = (RadioButton) this.findViewById(R.id.rb_B);

        rbTrue.setOnClickListener(this);
        rbFalse.setOnClickListener(this);

        preHelper = PreferenceHelper.getInstance(getContext());

        // 初始化正确答案
        String answer = null;
        if (data.getAnswer().equals("0")) {
            answer = "错";
        } else {
            answer = "对";
        }
        tvAnswer.setText("正确答案：" + answer);
        tvAnalysis.setText(data.getAnalysis());
    }

    /**
     * 更新正确答案的显示状态以及用户的选择状态，普通模式下选择后就显示正确答案，乡试模式查看详细的时候显示正确答案
     */
    private void refreshAnswerState() {
        String userAnswer = mTestData.getuAnswer();
        userAnswer = userAnswer == null ? "" : userAnswer;

        int state = mTestData.getState();// 答题状态 0未答，1/2正误，3未完成
//		int sendState = mTestData.getSendState();// 发送状态 1已发送，0未发送

        if (testMode == TEST_MODE_NORMAL) {
            if (state == SubjectState.STATE_CORRECT || state == SubjectState.STATE_WRONG) {// 已完成 // 用户选择答案后显示正确答案，且不能进行修改
//                showCorrectAnswer(state == SubjectState.STATE_CORRECT);
                tvSubjectType.setVisibility(View.GONE);
                disableOption();
            }
        } else if (testMode == TEST_MODE_TEST) {
            showCorrectAnswer(state == SubjectState.STATE_CORRECT);
            tvSubjectType.setVisibility(View.VISIBLE);
            disableOption();
        } else if (testMode == TEST_MODE_LOOK) {
            tvSubjectType.setVisibility(View.VISIBLE);
            disableOption();
        } else {
            if (state == SubjectState.STATE_CORRECT || state == SubjectState.STATE_WRONG) {
                showCorrectAnswer(state == SubjectState.STATE_CORRECT);
                tvSubjectType.setVisibility(View.GONE);
                disableOption();
            }
        }

        // 设置指定答案的按钮为选中状态
        if (userAnswer.equals("1")) {
            rbTrue.setChecked(true);
        } else if (userAnswer.equals("0")) {
            rbFalse.setChecked(true);
        } else {
            userAnswer = "";
        }
        // handleOnClick(userAnswer);

    }

    @Override
    public void applyData(BaseTestData data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveAnswer() {
        // TODO Auto-generated method stub
    }

    @Override
    public float submit() {
        showCorrectAnswer(mTestData.getuAnswer().equals(mData.getAnswer()));
        disableOption();
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void showDetails() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void showCorrectAnswer(boolean correct) {
        tvAnswer.setVisibility(View.VISIBLE);
        tvAnalysis.setVisibility(View.VISIBLE);
        findViewById(R.id.rl_analysis).setVisibility(VISIBLE);

        if (correct) {
            tvAnswer.setTextColor(Color.parseColor("#6766cc"));
        } else {
            tvAnswer.setTextColor(Color.parseColor("#cc0000"));
        }
    }

    @Override
    protected void disableOption() {
        rbTrue.setEnabled(false);
        rbFalse.setEnabled(false);
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        mTestData.setuAnswer("");
        rbTrue.setTextColor(Color.BLACK);
        rbFalse.setTextColor(Color.BLACK);
        rbTrue.setChecked(false);
        rbFalse.setChecked(false);
        rbTrue.setEnabled(true);
        rbFalse.setEnabled(true);
        tvAnswer.setVisibility(View.GONE);
        tvAnalysis.setVisibility(View.GONE);
        mTestData.setRemark("0");
//		mTestData.setSendState(0);
        mTestData.setState(SubjectState.STATE_INIT);
//		mData.setUserAnswer("");
        mTestData.setuScore(0);
    }

    @Override
    public void setSubjectListener(SubjectListener listener) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_parent:
                handler.removeMessages(0);
                break;
            case R.id.rb_A:
                rbTrue.setTextColor(getResources().getColor(R.color.blue));
                rbFalse.setTextColor(Color.BLACK);
                String answerA = v.getTag().toString();

                handleOnClick(answerA);
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 300);
                break;
            case R.id.rb_B:
                rbFalse.setTextColor(getResources().getColor(R.color.blue));
                rbTrue.setTextColor(Color.BLACK);
                String answerB = v.getTag().toString();
                handleOnClick(answerB);
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 300);
                break;
            default:
                // String answer = v.getTag().toString();
                // handleOnClick(answer);
                // handler.removeMessages(0);
                // handler.sendEmptyMessageDelayed(0, 300);
                break;
        }
    }


}
