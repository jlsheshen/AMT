package com.edu.accountingteachingmaterial.util;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.ChapterData;
import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.accountingteachingmaterial.entity.HomepageInformationData;
import com.edu.library.util.ToastUtil;
import com.google.gson.reflect.TypeToken;
import com.lucher.net.req.RequestMethod;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */

public class UnitTestActivity extends BaseActivity implements OnClickListener {
    ImageView imgBack, imgShow;
    TextView testTitle, tvPublisher, tvReleaseTime, tvScore, tvSubmittingTime,
            tvAnswerTime, tvStartTime, tvEndTime, tvChallengeTime,
            tvSingle, tvMultiple, tvJudge, tvFillIn, tvShort, tvComprehensive;
    Button btnStart;
    SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();


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
        bindAndListener(btnStart, R.id.btn_start);

        Type type = new TypeToken<ArrayList<HomepageInformationData>>(){}.getType();
        SingleRequestQueue.getInstance().addArrayRequest(NetUrlContstant.homeInfoUrl + "5926", type,
                new Response.Listener<ArrayList<HomepageInformationData>>() {

                    @Override
                    public void onResponse(ArrayList<HomepageInformationData> response) {
                        ToastUtil.showToast(UnitTestActivity.this, "" + response + response.get(0).getId() + response.get(0).getTitle());
                        Log.d("UnitTestActivity", "uploadHomepageInfo" + "success" + response);



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                },"");
//        uploadHomepageInfo();
//        uploadChapter();
//        uploadChapterList();
    }

    @Override
    public void initData() {

    }

    private void bindAndListener(View view, int id) {
        view = bindView(id);
        view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.class_aty_back_iv:
                uploadHomepageInfo();
                break;

            case R.id.btn_start:
//                uploadChapter();
                uploadChapterList();
                break;


        }
    }

    private void uploadHomepageInfo() {
//        UserData userData = UserCenterHelper.getUserInfo(this);
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(UnitTestActivity.this, RequestMethod.POST, NetUrlContstant.homeInfoUrl + "5926");
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    List<HomepageInformationData> hData = JSON.parseArray(jsonObject.getString("message"), HomepageInformationData.class);
                    ToastUtil.showToast(UnitTestActivity.this, "" + hData + hData.get(0).getId() + hData.get(0).getTitle());
                    Log.d("UnitTestActivity", "uploadHomepageInfo" + "success" + hData);
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                ToastUtil.showToast(UnitTestActivity.this, errorInfo);
            }
        });
    }

    private void uploadChapter() {
        NetSendCodeEntity entity = new NetSendCodeEntity(UnitTestActivity.this, RequestMethod.POST, NetUrlContstant.chapterUrl + "111");
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    List<ChapterData> chapterData = JSON.parseArray(jsonObject.getString("message"), ChapterData.class);
                    ToastUtil.showToast(UnitTestActivity.this, "" + chapterData + chapterData.get(0).getTitle() + chapterData.get(1).getTitle());
                    Log.d("UnitTestActivity", "uploadChapter" + "success" + chapterData);
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                ToastUtil.showToast(UnitTestActivity.this, errorInfo);
            }
        });
    }

    private void uploadChapterList() {
        NetSendCodeEntity entity = new NetSendCodeEntity(UnitTestActivity.this, RequestMethod.POST, NetUrlContstant.chapterTypeUrl + "151" + "-" + "4");
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    List<ExamListData> examListData = JSON.parseArray(jsonObject.getString("message"), ExamListData.class);
                    ToastUtil.showToast(UnitTestActivity.this, "" + examListData.get(0).getExam_name());
                    Log.d("UnitTestActivity", "uploadChapterList" + "success" + examListData);
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                ToastUtil.showToast(UnitTestActivity.this, errorInfo);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendJsonNetReqManager.cancelRequest();
    }

}
