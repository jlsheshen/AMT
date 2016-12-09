package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.bean.ExamBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.accountingteachingmaterial.entity.TestPaperListData;
import com.edu.accountingteachingmaterial.entity.TopicsBean;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.lucher.net.req.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */

public class UnitTestActivity extends BaseActivity implements OnClickListener {
    ImageView imgBack, imgShow;
    TextView testTitle, tvPublisher, tvReleaseTime, tvScore, tvSubmittingTime,
            tvAnswerTime, tvStartTime, tvEndTime, tvChallengeTime,
            tvSingle, tvMultiple, tvJudge, tvFillIn, tvShort, tvComprehensive, tvTotal;
    Button btnStart;
    ExamBean examBean;
    LinearLayout rlScore, rlSubmitting, rlAnswerData;
    TestPaperListData testPaperListData;
    List<TopicsBean> topicsBeen;
    int examId;
    ExamListData examListData;

    @Override
    public int setLayout() {
        return R.layout.activity_unit_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bindAndListener(imgBack, R.id.class_aty_back_iv);
        bindAndListener(btnStart, R.id.btn_start);
        bindAndListener(imgShow, R.id.img_show);
        testTitle = bindView(R.id.class_id_title_tv);
        tvPublisher = bindView(R.id.publisher_tv);
        tvReleaseTime = bindView(R.id.release_time_tv);
        tvScore = bindView(R.id.score_tv);
        tvSubmittingTime = bindView(R.id.submitting_time_tv);
        tvAnswerTime = bindView(R.id.answer_time_tv);
        tvStartTime = bindView(R.id.start_time_tv);
        tvEndTime = bindView(R.id.end_time_tv);
        tvChallengeTime = bindView(R.id.challeng_time_tv);
        tvSingle = bindView(R.id.single_tv);
        tvMultiple = bindView(R.id.multiple_tv);
        tvJudge = bindView(R.id.judge_tv);
        tvFillIn = bindView(R.id.fill_in_tv);
        tvShort = bindView(R.id.short_tv);
        tvComprehensive = bindView(R.id.comprehensive_tv);
        tvTotal = bindView(R.id.total_tv);


    }


    @Override
    public void initData() {

//        Bundle bundle = getIntent().getExtras();
//        examBean = (ExamBean) bundle.getSerializable("examBean");
        Bundle bundle = getIntent().getExtras();
        examId = bundle.getInt("examId");
        examListData = (ExamListData) bundle.getSerializable("ExamListData");
        uploadTestInfo();
//        //未提交
//        if (testPaperListData.getStatus() == ClassContstant.EXAM_UNDONE) {
//            imgShow.setBackgroundResource(R.mipmap.weitijao);
//            rlScore.findViewById(R.id.ly_score).setVisibility(View.GONE);
//            rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.GONE);
//            rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.GONE);
//            //开始比赛
//            btnStart.setBackgroundResource(R.drawable.selector_start);
//        } else if (testPaperListData.getStatus() == ClassContstant.EXAM_COMMIT) {
//            //已提交
//            imgShow.setBackgroundResource(R.mipmap.yitijiao);
//            rlScore.findViewById(R.id.ly_score).setVisibility(View.GONE);
//            rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.GONE);
//            rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.GONE);
//            //查看作答
//            btnStart.setBackgroundResource(R.drawable.selector_answer);
//        } else if (testPaperListData.getStatus() == ClassContstant.EXAM_READ) {
//            //已批阅
//            imgShow.setBackgroundResource(R.mipmap.yipiyue);
//            rlScore.findViewById(R.id.ly_score).setVisibility(View.VISIBLE);
//            rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.VISIBLE);
//            rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.VISIBLE);
//            //查看答案
//            btnStart.setBackgroundResource(R.drawable.selector_check_answer;
//        }


    }


    private void bindAndListener(View view, int id) {
        view = bindView(id);
        view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.class_aty_back_iv:
                this.finish();
                break;

            case R.id.btn_start:
                //ExamListData考试数据（测试）
                Bundle bundle = new Bundle();
                bundle.putSerializable("ExamListData", examListData);
                startActivity(SubjectExamActivity.class, bundle);
                break;


        }
    }

    /**
     * 获取试卷列表
     */

    private void uploadTestInfo() {
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        Log.d("UnitTestActivity", "uploadTestInfo");
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.examInfoUrl + examId);
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    testPaperListData = JSONObject.parseObject(jsonObject.getString("message"), TestPaperListData.class);
                    Log.d("UnitTestActivity", "uploadTestInfo" + testPaperListData.getExam_name());
                    if (testPaperListData != null && testPaperListData.getTopics().size() > 0) {
                        topicsBeen = new ArrayList<TopicsBean>();
                        topicsBeen = testPaperListData.getTopics();
                        Log.d("UnitTestActivity", "uploadTestInfo" + topicsBeen.size());
                    }
                    refreshView();
                }

            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("UnitTestActivity", errorInfo);

            }
        });
    }

    private void refreshView() {
        int single = 0, multiple = 0, judge = 0, fillin = 0, shortin = 0, comprehensive = 0;
        for (int i = 0; i < topicsBeen.size(); i++) {
            if (topicsBeen.get(i).getType() == ClassContstant.SUBJECT_SINGLE_CHOSE) {
                single += 1;
            } else if (topicsBeen.get(i).getType() == ClassContstant.SUBJECT_MULITI_CHOSE) {
                multiple += 1;
            } else if (topicsBeen.get(i).getType() == ClassContstant.SUBJECT_JUDGE) {
                judge += 1;
            }
        }
        testTitle.setText(testPaperListData.getExam_name());
        tvPublisher.setText(testPaperListData.getCreator_name());
        tvReleaseTime.setText(testPaperListData.getCreate_date());
//        tvScore.setText(testPaperListData.getScore()+"分");
//        tvSubmittingTime.setText("");
//        tvAnswerTime.setText("");
//        tvStartTime.setText(testPaperListData.getStart_time()+"");
//        tvEndTime.setText(testPaperListData.getEnd_time()+"");
//        tvChallengeTime.setText("");
        tvSingle.setText(single + "道");
        tvMultiple.setText(multiple + "道");
        tvJudge.setText(judge + "道");
//        tvFillIn.setText("道");
//        tvShort.setText("道");
//        tvComprehensive.setText("道");
        tvTotal.setText(topicsBeen.size() + "道");
    }


}
