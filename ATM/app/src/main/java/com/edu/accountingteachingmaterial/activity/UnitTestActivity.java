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
import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.accountingteachingmaterial.entity.TestInfoData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.lucher.net.req.RequestMethod;

/**
 * Created by Administrator on 2016/11/18.
 */

public class UnitTestActivity extends BaseActivity implements OnClickListener {
    ImageView imgBack, imgShow;
    TextView testTitle, tvPublisher, tvReleaseTime, tvScore, tvSubmittingTime,
            tvAnswerTime, tvStartTime, tvEndTime, tvChallengeTime,
            tvSingle, tvMultiple, tvJudge, tvFillIn, tvShort, tvComprehensive, tvTotal;
    Button btnStart;
    TestInfoData infoData;
    LinearLayout rlScore, rlSubmitting, rlAnswerData;

    @Override
    public int setLayout() {
        return R.layout.activity_unit_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bindAndListener(imgBack, R.id.class_aty_back_iv);
        bindAndListener(imgShow, R.id.img_show);
        bindAndListener(testTitle, R.id.class_id_title_tv);
        bindAndListener(tvPublisher, R.id.publisher_tv);
        bindAndListener(tvReleaseTime, R.id.release_time_tv);
        bindAndListener(tvScore, R.id.score_tv);
        bindAndListener(tvSubmittingTime, R.id.submitting_time_tv);
        bindAndListener(tvAnswerTime, R.id.answer_time_tv);
        bindAndListener(tvStartTime, R.id.start_time_tv);
        bindAndListener(tvEndTime, R.id.end_time_tv);
        bindAndListener(tvChallengeTime, R.id.challeng_time_tv);
        bindAndListener(tvSingle, R.id.single_tv);
        bindAndListener(tvMultiple, R.id.multiple_tv);
        bindAndListener(tvJudge, R.id.judge_tv);
        bindAndListener(tvFillIn, R.id.fill_in_tv);
        bindAndListener(tvShort, R.id.short_tv);
        bindAndListener(tvComprehensive, R.id.comprehensive_tv);
        bindAndListener(tvTotal, R.id.total_tv);
        bindAndListener(btnStart, R.id.btn_start);


    }

    @Override
    public void initData() {
//        Bundle bundle = getIntent().getExtras();
//        infoData = (TestInfoData) bundle.getSerializable("TestInfoData");
//
//        if (ClassContstant.EXAM_COMMIT) {
//        } else if (ClassContstant.EXAM_UNDONE) {
//        } else if (ClassContstant.EXAM_READ) {
//        } else if (ClassContstant.EXAM_NOT) {
//        } else if (ClassContstant.EXAM_DOWNLOADING) {
//        }
//
//        if (TEST_MODE_NORMAL) {
        //开始比赛
//            btnStart.setBackgroundResource(R.drawable.selector_start);
//        } else if () {
        //查看作答
//        btnStart.setBackgroundResource(R.drawable.selector_answer);
//        } else if () {
        //查看答案
//            btnStart.setBackgroundResource(R.drawable.selector_check_answer;
//        }


        //未提交
//        imgShow.setBackgroundResource(R.mipmap.weitijao);
//        rlScore.findViewById(R.id.ly_score).setVisibility(View.GONE);
//        rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.GONE);
//        rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.GONE);
//        } else if () {
        //已提交
//        imgShow.setBackgroundResource(R.mipmap.yitijiao);
//        rlScore.findViewById(R.id.ly_score).setVisibility(View.GONE);
//        rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.GONE);
//        rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.GONE);
//        } else if () {
        //已批阅
//        imgShow.setBackgroundResource(R.mipmap.yipiyue);
//        rlScore.findViewById(R.id.ly_score).setVisibility(View.VISIBLE);
//        rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.VISIBLE);
//        rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.VISIBLE);
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
                ExamListData data = new ExamListData();
                data.setChapter_id(11);
                data.setId(1);
                bundle.putSerializable("ExamListData", data);
                startActivity(SubjectTestActivity.class, bundle);
                break;


        }
    }

    /**
     * 获取试卷列表
     */

    private void uploadTestInfo() {
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        Log.d("UnitTestActivity", "");
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.classicCaseUrl + "-2");
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    //infoData = JSON.parseArray(jsonObject.getString("message"), TestListData.class);
                    infoData = jsonObject.getObject(jsonObject.getString("message"), TestInfoData.class);
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("UnitTestActivity", errorInfo);

            }
        });
    }
}
