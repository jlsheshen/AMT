package com.edu.accountingteachingmaterial.subject.view;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.subject.ISubject;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectState;
import com.edu.subject.data.BaseTestData;
import com.edu.accountingteachingmaterial.bean.SubjectBasicData;

import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_LOOK;
import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_NORMAL;
import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_TEST;

/**
 * 单项选择题 视图
 *
 * @author lucher
 */
public class SubjectSingleSelectView extends BaseScrollView implements OnClickListener, ISubject {


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

    private RelativeLayout layout;

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


    public SubjectSingleSelectView(Context context, BaseTestData data, int testMode) {
        super(context, data, testMode);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_subject_single_select, this);
        init((SubjectBasicData) mData);
        scoreTv = (TextView) findViewById(R.id.tv_score);
        scoreTv.setText("(" + (int) mData.getScore() + "分)");
        refreshAnswerState((SubjectBasicData) mData);
    }

    /**
     * 初始化控件
     *
     * @param data
     */
    private void init(SubjectBasicData data) {
        tvSubjectType = (TextView) this.findViewById(R.id.tv_subject_type);
        tvSubjectType.setOnClickListener(this);

        layout = (RelativeLayout) this.findViewById(R.id.layout_parent);
        layout.setOnClickListener(this);
        tvSubjectType = (TextView) this.findViewById(R.id.tv_subject_type);
        tvSubjectType.setOnClickListener(this);
//		tvSubjectType.setText("错误" + mTestData.getErrorCount() + "次");
        tvSubjectType.setText("单选 - " + mTestData.getSubjectIndex());
        tvQestion = (TextView) this.findViewById(R.id.tv_question);
        // 刷新题目数据
//        tvQestion.setText(data.getSubjectIndex() + "." + data.getQuestion());
        tvQestion.setText(data.getQuestion());
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

        String[] options = data.getOption().split(">>>");
        for (int i = 0; i < options.length; i++) {
            if (options[i].substring(0, options[i].indexOf(".")).equals(data.getAnswer())) {
                tvAnswer.setText("正确答案：" + (char) (i + 65));
            }
        }
        // tvAnswer.setText("正确答案：" + data.getAnswer());
        //tvAnalysis.setText("解析：" + data.getAnalysis());
        tvAnalysis.setText(data.getAnalysis());
        parseOption(data.getOption());
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
    private void refreshAnswerState(SubjectBasicData data) {

        String userAnswer = mTestData.getuAnswer();
        int state = mTestData.getState();// 答题状态 0未答，1/2正误，3未完成
        if (testMode == TEST_MODE_NORMAL) {
            if (state == SubjectState.STATE_CORRECT || state == SubjectState.STATE_WRONG) {// 已完成 // 用户选择答案后显示正确答案，且不能进行修改
//                showCorrectAnswer(state == SubjectState.STATE_CORRECT);
//                tvSubjectType.setVisibility(View.GONE);
//                disableOption();
            }
        } else if (testMode == TEST_MODE_TEST) {
            showCorrectAnswer(state == SubjectState.STATE_CORRECT);
//            tvSubjectType.setVisibility(View.VISIBLE);
            disableOption();
        } else if (testMode == TEST_MODE_LOOK) {
//            tvSubjectType.setVisibility(View.VISIBLE);
            disableOption();
        } else {
            if (state == SubjectState.STATE_CORRECT || state == SubjectState.STATE_WRONG) {
                showCorrectAnswer(state == SubjectState.STATE_CORRECT);
//                tvSubjectType.setVisibility(View.GONE);
                disableOption();
            }
        }

        // 设置指定答案的按钮为选中状态
        String[] tempAns = data.getOption().split(">>>");
        if (userAnswer.equals(tempAns[0].substring(0, tempAns[0].indexOf(".")))) {
            rbA.setChecked(true);
        } else if (userAnswer.equals(tempAns[1].substring(0, tempAns[1].indexOf(".")))) {
            rbB.setChecked(true);
        } else if (userAnswer.equals(tempAns[2].substring(0, tempAns[2].indexOf(".")))) {
            rbC.setChecked(true);
        } else if (userAnswer.equals(tempAns[3].substring(0, tempAns[3].indexOf(".")))) {
            rbD.setChecked(true);
        } else {
            userAnswer = "";
        }
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
    public void disableOption() {
        rbA.setEnabled(false);
        rbB.setEnabled(false);
        rbC.setEnabled(false);
        rbD.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_subject_type:

                break;
            default:
                try {
                    String answer = v.getTag().toString();
                    handleOnClick(answer);
                } catch (Exception e) {

                }
                break;
        }
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
//        showCorrectAnswer(mTestData.getuAnswer().equals(mData.getAnswer()));
//        disableOption();
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
        mTestData.setuAnswer("");
        rbA.setChecked(false);
        rbB.setChecked(false);
        rbC.setChecked(false);
        rbD.setChecked(false);
        rbA.setEnabled(true);
        rbB.setEnabled(true);
        rbC.setEnabled(true);
        rbD.setEnabled(true);
        tvAnswer.setVisibility(View.GONE);
        tvAnalysis.setVisibility(View.GONE);
        // 需要将对应的数据库中的内容也修改掉
//		TestDataModel.getInstance(getContext()).updateAllState(mTestData.getId(), 0);
//		SubjectModel.getInstance(getContext()).cleanUserAnswerAndUscore(mData.getId(), "", 0, false);

    }


    @Override
    public void setSubjectListener(SubjectListener listener) {
        // TODO Auto-generated method stub

    }

}
