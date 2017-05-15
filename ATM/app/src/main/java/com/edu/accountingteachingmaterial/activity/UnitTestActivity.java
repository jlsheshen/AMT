package com.edu.accountingteachingmaterial.activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.bean.UpdateScoreBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.dao.ExamOnLineListDao;
import com.edu.accountingteachingmaterial.entity.StartExamData;
import com.edu.accountingteachingmaterial.entity.TestPaperListData;
import com.edu.accountingteachingmaterial.entity.TopicsBean;
import com.edu.accountingteachingmaterial.newsubject.BaseSubjectsContentActivity;
import com.edu.accountingteachingmaterial.newsubject.OnlineTestContentActivity;
import com.edu.accountingteachingmaterial.newsubject.ShowDetailsContentActivity;
import com.edu.accountingteachingmaterial.newsubject.ShowUAnswerContentActivity;
import com.edu.accountingteachingmaterial.newsubject.dao.SubjectOnlineTestDataDao;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SplitChapterIdUtil;
import com.edu.accountingteachingmaterial.util.net.GetScoreListManager;
import com.edu.accountingteachingmaterial.util.net.SendJsonNetReqManager;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_NORMAL;
import static com.edu.accountingteachingmaterial.constant.ClassContstant.TEST_MODE_TEST;
import static com.edu.accountingteachingmaterial.newsubject.BaseSubjectsContentActivity.IS_EXAM;
import static com.edu.accountingteachingmaterial.newsubject.BaseSubjectsContentActivity.TITLE;
import static com.edu.accountingteachingmaterial.newsubject.BaseSubjectsContentActivity.TOTAL_TIME;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.CHAPTER_ID;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.USER_ID;

/**
 * 考试详情界面
 * Created by Administrator on 2016/11/18.
 */

public class UnitTestActivity extends BaseActivity implements OnClickListener, GetScoreListManager.ExamScoreListListener {
    ImageView imgBack, imgShow;
    TextView testTitle, tvPublisher, tvReleaseTime, tvScore, tvSubmittingTime,
            tvUsedTime, tvStartTime, tvEndTime, tvChallengeTime,
            tvSingle, tvMultiple, tvJudge, tvFillIn, tvShort, tvComprehensive, tvForm, tvTotal;
    Button btnStart;
    LinearLayout rlScore, rlSubmitting, rlAnswerData;
    TestPaperListData testPaperListData;
    List<TopicsBean> topicsBeen;
    String examId;
    int textMode;

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
        tvForm = bindView(R.id.form_tv);
        tvTotal = bindView(R.id.total_tv);
        rlScore = bindView(R.id.ly_score);
        rlSubmitting = bindView(R.id.item_submitting_ly);
        rlAnswerData = bindView(R.id.item_answer_ly);
    }


    @Override
    public void initData() {

        Bundle bundle = getIntent().getExtras();
        examId = bundle.getString(BaseSubjectsContentActivity.CHAPTER_ID);
        uploadTestInfo();


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
                //可以考试时,进入试卷作答
                if (textMode == TEST_MODE_NORMAL) {
                    uploadTestTime();


                } else if (textMode == ClassContstant.TEST_MODE_LOOK) {
                    //试卷提交后,但是未发布答案,进入查看答案
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(IS_EXAM, true);
                    bundle.putString(CHAPTER_ID, examId);
                    bundle.putString(TITLE,testPaperListData.getExam_name());
                    startActivity(ShowUAnswerContentActivity.class, bundle);
                    finish();

                } else if (textMode == ClassContstant.TEST_MODE_TEST){
                    //试卷提交后,,答案也发布了,进入查看详情

                    GetScoreListManager.getSingleton(this).setExamId(this,examId);


                }else {
                    uploadTestTime();
                }

                break;
        }
    }

    /**
     * 获取试卷列表
     */

    private void uploadTestInfo() {
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        Log.d("UnitTestActivity", "uploadTestInfo");
        String useId = PreferenceHelper.getInstance(this).getStringValue(USER_ID);
        String sendExamId = SplitChapterIdUtil.spliterId(examId,useId);

//        String sendExamId[] = (String.valueOf(examId)).split(".");

        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getExamInfoUrl() + sendExamId + "-" + useId);
//        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getExamInfoUrl() + examId);
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


    /**
     * 获取当前考试剩余时间IME
     */

    private void uploadTestTime() {
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        String useId = PreferenceHelper.getInstance(this).getStringValue(USER_ID);
        String sendExamId = SplitChapterIdUtil.spliterId(examId,useId);
//        String sendExamId[] = (String.valueOf(examId)).split(".");

        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getUploadingTestTime() + sendExamId + "-" + useId);
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    StartExamData startExamData = JSONObject.parseObject(jsonObject.getString("message"), StartExamData.class);
                    if (startExamData.getStarted_time() > 0) {

                        //ExamListData考试数据（测试）
                        Bundle bundle = new Bundle();
                        bundle.putString(CHAPTER_ID, examId);
//                        bundle.putInt("textMode", textMode);
                        bundle.putInt(TOTAL_TIME, startExamData.getRemaining());
                        startActivity(OnlineTestContentActivity.class, bundle);
                        finish();
                    } else {
                        ToastUtil.showToast(UnitTestActivity.this, "当前考试还没开始，请稍后!");
                        return;
                    }
                }
            }
            @Override
            public void onFailure(String errorInfo) {
                ToastUtil.showToast(UnitTestActivity.this, errorInfo);


            }
        });
    }

    //设置显示测验信息，试题信息
    private void refreshView() {

        testTitle.setText(testPaperListData.getExam_name());
        tvPublisher.setText(testPaperListData.getCreator_name());
        tvReleaseTime.setText(testPaperListData.getCreate_date());
        tvStartTime.setText(testPaperListData.getStart_time() + "");
        tvEndTime.setText(testPaperListData.getEnd_time() + "");
        tvChallengeTime.setText(testPaperListData.getLast_time() + "分钟");
        tvSingle.setText(testPaperListData.getOne() + "道");
        tvMultiple.setText(testPaperListData.getMulti() + "道");
        tvJudge.setText(testPaperListData.getJudge() + "道");
        tvFillIn.setText(testPaperListData.getFilling() + "道");
        tvShort.setText(testPaperListData.getAsk() + "道");
        tvComprehensive.setText(testPaperListData.getComp() + "道");
        tvForm.setText(testPaperListData.getTb() + "道");
        tvTotal.setText(testPaperListData.getSum() + "道");
        tvSubmittingTime.setText(testPaperListData.getUpload_time() + "");
//        tvUsedTime.setText(testPaperListData.getStu_last_time() + "");
        setTime(testPaperListData.getStu_last_time());
        tvScore.setText(testPaperListData.getStu_score() + "");
        refreshState();
    }

    //设置用时
    private void setTime(int time) {
        int usedTime = time * 1000;
        long hour = (usedTime / 1000) / 60 / 60;
        long minute = (usedTime / 1000) / 60 - hour * 60;
        long second = usedTime / 1000 - minute * 60 - hour * 60 * 60;
        tvUsedTime.setText((hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second);
    }

    //刷新试卷不同状态下的试图（提交,未提交，批阅，分数）
    private void refreshState() {
        int state = ExamOnLineListDao.getInstance(this).getState(examId);

        //未提交 state:2
        if (state == ClassContstant.EXAM_UNDONE) {
            imgShow.setBackgroundResource(R.mipmap.weitijao);
            rlScore.findViewById(R.id.ly_score).setVisibility(View.GONE);
            rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.GONE);
            rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.GONE);
            //开始比赛
            btnStart.setBackgroundResource(R.drawable.selector_start);
            textMode = TEST_MODE_NORMAL;
        } else if (state == ClassContstant.EXAM_COMMIT) {
            //已提交 state:1
            if (testPaperListData.getIs_send() == 1 && testPaperListData.getRemaining() < 0) {
                state = ClassContstant.EXAM_READ;
                ContentValues contentValues = new ContentValues();
                contentValues.put(ExamOnLineListDao.STATE, state);
                ExamOnLineListDao.getInstance(this).updateData(examId, contentValues);
                textMode = TEST_MODE_TEST;
                refreshState();
                return;
            }
            imgShow.setBackgroundResource(R.mipmap.yitijiao);
            rlScore.findViewById(R.id.ly_score).setVisibility(View.GONE);
            rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.GONE);
            rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.GONE);
            //查看作答
            btnStart.setBackgroundResource(R.drawable.selector_answer);
            textMode = ClassContstant.TEST_MODE_LOOK;
        } else if (state == ClassContstant.EXAM_READ) {
            //已批阅 state:3
            imgShow.setBackgroundResource(R.mipmap.yipiyue);
            rlScore.findViewById(R.id.ly_score).setVisibility(View.VISIBLE);
            rlSubmitting.findViewById(R.id.item_submitting_ly).setVisibility(View.VISIBLE);
            rlAnswerData.findViewById(R.id.item_answer_ly).setVisibility(View.VISIBLE);
//            tvSubmittingTime.setText(testPaperListData.getUpload_time() + "");
//            tvUsedTime.setText(testPaperListData.getStu_last_time() + "");
//            tvScore.setText(testPaperListData.getStu_score() + "");
            //查看答案
            btnStart.setBackgroundResource(R.drawable.selector_check_answer);
            textMode = TEST_MODE_TEST;
        }
    }

    @Override
    public void onGetScoreSuccess(List<UpdateScoreBean> updateScoreBeanList, String chapterId) {
        SubjectOnlineTestDataDao.getInstance(this).updateScores(updateScoreBeanList,chapterId);

        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_EXAM, true);
        bundle.putString(CHAPTER_ID, examId);
        bundle.putString(TITLE,testPaperListData.getExam_name());
        startActivity(ShowDetailsContentActivity.class, bundle);
        finish();
    }

    @Override
    public void onGetScoreFailure(String message) {

    }
}
