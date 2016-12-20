package com.edu.accountingteachingmaterial.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.entity.ReviewTopicData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.ReviewTopicManager;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.accountingteachingmaterial.view.AddAndSubTestView;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ClassReviewFragment extends BaseFragment implements View.OnClickListener {
    // * 加载题型view
    LinearLayout layout1;
    LinearLayout layout2;
//    // * 题目类型数据
//    private String[] strStem1;
//    private String[] strStem2;
//    private String[单选题,多选题,判断题，填空题，简答题，综合题，表格题];

    Button btnStart;
    CheckBox cbEasy, cbNormal, cbHard;
    ReviewTopicData reviewTopicData;
    int totalNum = 0;
    AddAndSubTestView addAndSubTestView1 = null;
    AddAndSubTestView addAndSubTestView2 = null;
    AddAndSubTestView addAndSubTestView3 = null;
    AddAndSubTestView addAndSubTestView4 = null;
    AddAndSubTestView addAndSubTestView5 = null;
    AddAndSubTestView addAndSubTestView6 = null;
    AddAndSubTestView addAndSubTestView7 = null;


    @Override
    protected int initLayout() {
        return R.layout.fragment_classs_review;
    }

    @Override
    protected void initView(View view) {
        layout1 = bindView(R.id.ly_first);
        layout2 = bindView(R.id.ly_second);
        cbEasy = bindView(R.id.cb_easy);
        cbNormal = bindView(R.id.cb_normal);
        cbHard = bindView(R.id.cb_hard);
        btnStart = (Button) view.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);


    }

    @Override
    protected void initData() {

//        strStem1 = context.getResources().getStringArray(R.array.question1);
//        strStem2 = context.getResources().getStringArray(R.array.question2);

        layout1.removeAllViews();
        layout2.removeAllViews();

        addAndSubTestView1 = new AddAndSubTestView(context, 12, "单选题");
        addAndSubTestView2 = new AddAndSubTestView(context, 20, "多选题");
        addAndSubTestView3 = new AddAndSubTestView(context, 30, "判断题");
        addAndSubTestView4 = new AddAndSubTestView(context, 10, "填空题");
        addAndSubTestView1.setTag(1);
        addAndSubTestView2.setTag(2);
        addAndSubTestView3.setTag(3);
        addAndSubTestView4.setTag(4);
        layout1.addView(addAndSubTestView1);
        layout1.addView(addAndSubTestView2);
        layout1.addView(addAndSubTestView3);
        layout1.addView(addAndSubTestView4);
        addAndSubTestView5 = new AddAndSubTestView(context, 12, "简答题");
        addAndSubTestView6 = new AddAndSubTestView(context, 13, "综合题");
        addAndSubTestView7 = new AddAndSubTestView(context, 15, "表格题");
        addAndSubTestView5.setTag(5);
        addAndSubTestView6.setTag(6);
        addAndSubTestView7.setTag(7);
        layout2.addView(addAndSubTestView5);
        layout2.addView(addAndSubTestView6);
        layout2.addView(addAndSubTestView7);

//            layout1.addView(addAndSubTestView);
//        for (int i = 1; i < strStem1.length; i++) {
//            addAndSubTestView = new AddAndSubTestView(context, 0, strStem1[i]);
//            layout1.addView(addAndSubTestView);
//            addAndSubTestView.setTag(i);
//        }
//        for (int j = 5; j < strStem2.length; j++) {
//            addAndSubTestView = new AddAndSubTestView(context, 0, strStem2[j]);
//            layout2.addView(addAndSubTestView);
//            addAndSubTestView.setTag(j);
//        }


    }

    //刷新首页题目总题数
    private void refreshView() {
//        if (addAndSubTestView.getTag().equals(1)) {
//            addAndSubTestView.refresh(120);
//        } else if (layout1.getTag().equals(2)) {
//            addAndSubTestView.refresh(140);
//        } else if (layout1.getTag().equals(3)) {
//            addAndSubTestView.refresh(160);
//        } else if (layout1.getTag().equals(4)) {
//            addAndSubTestView.refresh(170);
//        }
//
//        if (layout2.getTag().equals(1)) {
//            addAndSubTestView.refresh(110);
//        } else if (layout2.getTag().equals(2)) {
//            addAndSubTestView.refresh(180);
//        } else if (layout2.getTag().equals(3)) {
//            addAndSubTestView.refresh(150);
//        }
        addAndSubTestView1.refresh(111);
        addAndSubTestView2.refresh(121);
        addAndSubTestView3.refresh(131);
        addAndSubTestView4.refresh(141);
        addAndSubTestView5.refresh(151);
        addAndSubTestView6.refresh(161);
        addAndSubTestView7.refresh(171);
    }

    //获取试题总数
    private void getTotalNum() {
        totalNum = addAndSubTestView1.getNum() + addAndSubTestView2.getNum() + addAndSubTestView3.getNum() + addAndSubTestView4.getNum() + addAndSubTestView5.getNum() + addAndSubTestView6.getNum() + addAndSubTestView7.getNum();
        Log.d("ClassReviewFragment", "2016-12-19 total  num:" + totalNum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                getTotalNum();
                if ((!cbEasy.isChecked() && !cbNormal.isChecked() && !cbHard.isChecked())) {
                    ToastUtil.showToast(context, "请选择测验难度！");
                    return;
                }
                if (totalNum < 1) {
                    ToastUtil.showToast(context, "请输入正确题目数量！");
                    return;
                }
//                uploading();
                ToastUtil.showToast(context, "智能组卷");
                break;
        }

    }

    //获取题数总数量接口
    private void loadTopicList() {

        Log.d("ClassReviewFragment", "每个题目总数量");

        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.reviewTopicUrl);
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    reviewTopicData = JSONObject.parseObject(jsonObject.getString("message"), ReviewTopicData.class);
                    Log.d("LaunchActivity", "线程启动获取成功");

                    refreshView();
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("LaunchActivity", "线程启动获取失败");

            }
        });
    }

    //刷新题数数量
    private void refreshTotalList() {

    }

    //上传自选题目数量
    private void uploading() {
        ReviewTopicManager.getReviewTopicInstance(context).setReviewTopicVOList(reviewTopicData).sendTopic();
    }

}
