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
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
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
            tvUsedTime, tvStartTime, tvEndTime, tvChallengeTime,
            tvSingle, tvMultiple, tvJudge, tvFillIn, tvShort, tvComprehensive, tvTotal;
    Button btnStart;
    LinearLayout rlScore, rlSubmitting, rlAnswerData;
    TestPaperListData testPaperListData;
    List<TopicsBean> topicsBeen;
    int examId;
    int exmaStatus;
    int textMode;
    String title;
    TextView testTitleTv;

    @Override
    public int setLayout() {
        return R.layout.activity_unit_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bindAndListener(imgBack, R.id.class_aty_back_iv);
        bindAndListener(btnStart, R.id.btn_start);
        btnStart = bindView(R.id.btn_start);
        imgShow = bindView(R.id.img_show);
        testTitle = bindView(R.id.class_id_title_tv);
        tvPublisher = bindView(R.id.publisher_tv);
        tvReleaseTime = bindView(R.id.release_time_tv);
        tvScore = bindView(R.id.score_tv);
        tvSubmittingTime = bindView(R.id.submitting_time_tv);
        tvUsedTime = bindView(R.id.used_time_tv);
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
        rlScore = bindView(R.id.ly_score);
        rlSubmitting = bindView(R.id.item_submitting_ly);
        rlAnswerData = bindView(R.id.item_answer_ly);
        testTitleTv = bindView(R.id.test_title_tv);
    }


    @Override
    public void initData() {

        Bundle bundle = getIntent().getExtras();
//        exmaStatus = bundle.getInt("ExmaStatus");
        examId = bundle.getInt("ExmaID");
        title = bundle.getString("title");
//        uploadTestInfo();
        refreshState();
        rView();
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
                bundle.putInt("ExmaID", examId);
                bundle.putInt("textMode", textMode);
                bundle.putString("title", title);
                startActivity(SubjectExamActivity.class, bundle);
                finish();
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

    //设置显示测验信息，试题信息
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
//        tvStartTime.setText(testPaperListData.getStart_time()+"");
//        tvEndTime.setText(testPaperListData.getEnd_time()+"");
//        tvChallengeTime.setText(testPaperListData.getLast_time() + "分钟");
        tvSingle.setText(single + "道");
        tvMultiple.setText(multiple + "道");
        tvJudge.setText(judge + "道");
        tvFillIn.setText(fillin + "道");
        tvShort.setText(shortin + "道");
        tvComprehensive.setText(comprehensive + "道");
        tvTotal.setText(topicsBeen.size() + "道");
    }

    //刷新试卷不同状态下的试图（提交,未提交，批阅，分数）
    private void refreshState() {
        int exmaStatus = ExamListDao.getInstance(this).getState(examId);
        //int state = 2;
        //未提交 state:2
        if (exmaStatus == ClassContstant.EXAM_UNDONE) {
            imgShow.setImageResource(R.mipmap.weitijao);
            imgShow.setVisibility(View.INVISIBLE);
            rlScore.findViewById(R.id.ly_score).setVisibility(View.GONE);
            rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.GONE);
            rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.GONE);
            //开始比赛
            btnStart.setBackgroundResource(R.drawable.selector_start);
            textMode = ClassContstant.TEST_MODE_NORMAL;
        } else if (exmaStatus == ClassContstant.EXAM_COMMIT) {
            //已提交 state:1
            imgShow.setImageResource(R.mipmap.yitijiao);
            imgShow.setVisibility(View.VISIBLE);
            rlScore.findViewById(R.id.ly_score).setVisibility(View.GONE);
            rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.GONE);
            rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.GONE);
            //查看作答
            btnStart.setBackgroundResource(R.drawable.selector_answer);
            if (examId == 1210) {
                textMode = ClassContstant.TEST_MODE_LOOK;
            } else {
                textMode = ClassContstant.TEST_MODE_TEST;
            }
        } else if (exmaStatus == ClassContstant.EXAM_READ) {
            //已批阅 state:3
            imgShow.setImageResource(R.mipmap.yipiyue);
            imgShow.setVisibility(View.VISIBLE);
            rlScore.findViewById(R.id.ly_score).setVisibility(View.VISIBLE);
            rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.VISIBLE);
            rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.VISIBLE);
            tvScore.setText("90");
            tvSubmittingTime.setText("2016-11-15 10;30");
            tvUsedTime.setText("00:59:00");
            //查看答案
            btnStart.setBackgroundResource(R.drawable.selector_check_answer);
            textMode = ClassContstant.TEST_MODE_TEST;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshState();
    }

    private void rView() {
        testTitleTv.setText(title);
        testTitle.setText(title);
        tvPublisher.setText("章敏");
        tvReleaseTime.setText("2016-12-11 10:30");
        tvStartTime.setText("2016-12-11 10:30");
        tvEndTime.setText("2016-12-31 10:30");
        tvChallengeTime.setText("60分钟");

        if (examId == 1210) {
            tvSingle.setText("0道");
            tvMultiple.setText("0道");
            tvJudge.setText("0道");
            tvFillIn.setText("0道");
            tvShort.setText("0道");
            tvComprehensive.setText("20道");
            tvTotal.setText("20道");
        } else if (examId == 1211) {
            tvSingle.setText("18道");
            tvMultiple.setText("54道");
            tvJudge.setText("67道");
            tvFillIn.setText("0道");
            tvShort.setText("0道");
            tvComprehensive.setText("0道");
            tvTotal.setText("139道");
        } else if (examId == 1212) {
            tvSingle.setText("41道");
            tvMultiple.setText("88道");
            tvJudge.setText("71道");
            tvFillIn.setText("0道");
            tvShort.setText("0道");
            tvComprehensive.setText("0道");
            tvTotal.setText("200道");
        } else if (examId == 1213) {
            tvSingle.setText("34道");
            tvMultiple.setText("79道");
            tvJudge.setText("87道");
            tvFillIn.setText("0道");
            tvShort.setText("0道");
            tvComprehensive.setText("0道");
            tvTotal.setText("200道");
        }
    }
}
