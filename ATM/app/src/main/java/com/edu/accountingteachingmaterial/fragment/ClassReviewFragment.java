package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.SubjectReViewActivity;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.ReviewTopicData;
import com.edu.accountingteachingmaterial.entity.ReviewTopicVo;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.ReviewExamDownloadManager;
import com.edu.accountingteachingmaterial.util.ReviewTopicManager;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.accountingteachingmaterial.view.AddAndSubTestView;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ClassReviewFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    // * 加载题型view
    LinearLayout layout1;
    LinearLayout layout2;
    //    private String[] str = {"单选题", "多选题", "判断题", "填空题", "简答题", "综合题", "表格题"};
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
    int chapterId;
    ReviewTopicVo topicVo;

    @Override
    protected int initLayout() {
        return R.layout.fragment_classs_review;
    }

    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);

        layout1 = bindView(R.id.ly_first);
        layout2 = bindView(R.id.ly_second);
        cbEasy = bindView(R.id.cb_easy);
        cbNormal = bindView(R.id.cb_normal);
        cbHard = bindView(R.id.cb_hard);
        btnStart = (Button) view.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);

        cbEasy.setOnCheckedChangeListener(this);
        cbNormal.setOnCheckedChangeListener(this);
        cbHard.setOnCheckedChangeListener(this);

    }

    public void setData(int chapterId) {
        this.chapterId = chapterId;
        Log.d("ClassReviewFragment", "chapterId:" + chapterId);
    }

    @Override
    protected void initData() {

        layout1.removeAllViews();
        layout2.removeAllViews();

        addAndSubTestView1 = new AddAndSubTestView(context, 0, "单选题");
        addAndSubTestView2 = new AddAndSubTestView(context, 0, "多选题");
        addAndSubTestView3 = new AddAndSubTestView(context, 0, "判断题");
        addAndSubTestView4 = new AddAndSubTestView(context, 0, "填空题");
        addAndSubTestView1.setTag(1);
        addAndSubTestView2.setTag(2);
        addAndSubTestView3.setTag(3);
        addAndSubTestView4.setTag(4);
        layout1.addView(addAndSubTestView1);
        layout1.addView(addAndSubTestView2);
        layout1.addView(addAndSubTestView3);
        layout1.addView(addAndSubTestView4);

        addAndSubTestView5 = new AddAndSubTestView(context, 0, "简答题");
        addAndSubTestView6 = new AddAndSubTestView(context, 0, "综合题");
        addAndSubTestView7 = new AddAndSubTestView(context, 0, "表格题");
        addAndSubTestView5.setTag(5);
        addAndSubTestView6.setTag(6);
        addAndSubTestView7.setTag(7);
        layout2.addView(addAndSubTestView5);
        layout2.addView(addAndSubTestView6);
        layout2.addView(addAndSubTestView7);
        loadAllTopicList();
        setNumChangeListener();
    }

    //获取试题总数
    private void getTotalNum() {
        totalNum = addAndSubTestView1.getNum() + addAndSubTestView2.getNum() + addAndSubTestView3.getNum() + addAndSubTestView4.getNum() + addAndSubTestView5.getNum() + addAndSubTestView6.getNum() + addAndSubTestView7.getNum();
        Log.d("ClassReviewFragment", "2016-12-19 total  num:" + totalNum);
    }

    //获取上传题目数据信息
    private void getTopicUploadData() {
        ArrayList<Integer> list = new ArrayList<>();
        topicVo = new ReviewTopicVo();
        topicVo.setOne(addAndSubTestView1.getNum());
        topicVo.setMulti(addAndSubTestView2.getNum());
        topicVo.setJudge(addAndSubTestView3.getNum());
        topicVo.setFilling(addAndSubTestView4.getNum());
        topicVo.setAsk(addAndSubTestView5.getNum());
        topicVo.setComp(addAndSubTestView6.getNum());
        topicVo.setTable(addAndSubTestView7.getNum());
        if (cbEasy.isChecked()) {
            list.add(1);
        }
        if (cbNormal.isChecked()) {
            list.add(2);
        }
        if (cbHard.isChecked()) {
            list.add(3);
        }
        topicVo.setLevel(list);

        uploading();
        Log.d("ClassReviewFragment", topicVo + "2016-12-19");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if ((!cbEasy.isChecked() && !cbNormal.isChecked() && !cbHard.isChecked())) {
                    ToastUtil.showToast(context, "请选择测验难度！");
                    return;
                }
                if (totalNum < 1) {
                    ToastUtil.showToast(context, "请输入正确题目数量！");
                    return;
                }
                getTopicUploadData();
                ToastUtil.showToast(context, "智能组卷");
//                Bundle bundle = new Bundle();
//                bundle.putInt("chapterId", chapterId);
//                startActivity(SubjectReViewActivity.class, bundle);
                break;
        }

    }

    //获取题数总数量接口
    private void loadAllTopicList() {

        Log.d("ClassReviewFragment", "每个题目总数量");
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.getGetReviewList() + "901");
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    reviewTopicData = JSONObject.parseObject(jsonObject.getString("message"), ReviewTopicData.class);
                    Log.d("ClassReviewFragment", "获取成功");

                    refreshView();
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("ClassReviewFragment", "获取失败");

            }
        });
    }

    //上传自选题目数量
    private void uploading() {
        ReviewTopicManager.getReviewTopicInstance(context).setReviewTopicVOList(reviewTopicData).sendTopic();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_easy:
                refreshView();
                break;
            case R.id.cb_normal:
                refreshView();
                break;
            case R.id.cb_hard:
                refreshView();
                break;
        }
    }


    /**
     * 根据发来的状态,来刷新列表
     *
     * @param state
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(Integer state) {

        Log.d("ClassReviewFragment", "ClassReviewFragment------走过了EventBus");

        if (state == 1) {
            ReviewExamDownloadManager.newInstance(context).getSubjects(NetUrlContstant.getSubjectListUrl() + chapterId, chapterId);
        } else if (state == 2) {
            Bundle bundle = new Bundle();
            bundle.putInt("chapterId", chapterId);
            startActivity(SubjectReViewActivity.class, bundle);
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }
    private void setNumChangeListener() {
        addAndSubTestView1.setOnNumChangeListener(new AddAndSubTestView.OnNumChangeListener() {
            @Override
            public void onNumChange(View view, int num) {
                refreshView();
                Log.d("ClassReviewFragment", "AddAndSubTestView1 2016-12-21" + "刷新");
            }
        });
        addAndSubTestView2.setOnNumChangeListener(new AddAndSubTestView.OnNumChangeListener() {
            @Override
            public void onNumChange(View view, int num) {
                refreshView();
                Log.d("ClassReviewFragment", "AddAndSubTestView1 2016-12-21" + "刷新");
            }
        });
        addAndSubTestView3.setOnNumChangeListener(new AddAndSubTestView.OnNumChangeListener() {
            @Override
            public void onNumChange(View view, int num) {
                refreshView();
                Log.d("ClassReviewFragment", "AddAndSubTestView1 2016-12-21" + "刷新");
            }
        });
        addAndSubTestView4.setOnNumChangeListener(new AddAndSubTestView.OnNumChangeListener() {
            @Override
            public void onNumChange(View view, int num) {
                refreshView();
                Log.d("ClassReviewFragment", "AddAndSubTestView1 2016-12-21" + "刷新");
            }
        });
        addAndSubTestView5.setOnNumChangeListener(new AddAndSubTestView.OnNumChangeListener() {
            @Override
            public void onNumChange(View view, int num) {
                refreshView();
                Log.d("ClassReviewFragment", "AddAndSubTestView1 2016-12-21" + "刷新");
            }
        });
        addAndSubTestView6.setOnNumChangeListener(new AddAndSubTestView.OnNumChangeListener() {
            @Override
            public void onNumChange(View view, int num) {
                refreshView();
                Log.d("ClassReviewFragment", "AddAndSubTestView1 2016-12-21" + "刷新");
            }
        });
        addAndSubTestView7.setOnNumChangeListener(new AddAndSubTestView.OnNumChangeListener() {
            @Override
            public void onNumChange(View view, int num) {
                refreshView();
                Log.d("ClassReviewFragment", "AddAndSubTestView1 2016-12-21" + "刷新");
            }
        });
    }

    //刷新首页题目总题数
    private void refreshView() {
        if (cbEasy.isChecked() && !cbNormal.isChecked() && !cbHard.isChecked()) {
            addAndSubTestView1.refresh(reviewTopicData.getOrdinary().getOne());
            addAndSubTestView2.refresh(reviewTopicData.getOrdinary().getMulti());
            addAndSubTestView3.refresh(reviewTopicData.getOrdinary().getJudge());
            addAndSubTestView4.refresh(reviewTopicData.getOrdinary().getFilling());
            addAndSubTestView5.refresh(reviewTopicData.getOrdinary().getAsk());
            addAndSubTestView6.refresh(reviewTopicData.getOrdinary().getComp());
            addAndSubTestView7.refresh(reviewTopicData.getOrdinary().getTable());
        } else if (!cbEasy.isChecked() && cbNormal.isChecked() && !cbHard.isChecked()) {
            addAndSubTestView1.refresh(reviewTopicData.getEasy().getOne());
            addAndSubTestView2.refresh(reviewTopicData.getEasy().getMulti());
            addAndSubTestView3.refresh(reviewTopicData.getEasy().getJudge());
            addAndSubTestView4.refresh(reviewTopicData.getEasy().getFilling());
            addAndSubTestView5.refresh(reviewTopicData.getEasy().getAsk());
            addAndSubTestView6.refresh(reviewTopicData.getEasy().getComp());
            addAndSubTestView7.refresh(reviewTopicData.getEasy().getTable());
        } else if (!cbEasy.isChecked() && !cbNormal.isChecked() && cbHard.isChecked()) {
            addAndSubTestView1.refresh(reviewTopicData.getHard().getOne());
            addAndSubTestView2.refresh(reviewTopicData.getHard().getMulti());
            addAndSubTestView3.refresh(reviewTopicData.getHard().getJudge());
            addAndSubTestView4.refresh(reviewTopicData.getHard().getFilling());
            addAndSubTestView5.refresh(reviewTopicData.getHard().getAsk());
            addAndSubTestView6.refresh(reviewTopicData.getHard().getComp());
            addAndSubTestView7.refresh(reviewTopicData.getHard().getTable());
        } else if (cbEasy.isChecked() && cbNormal.isChecked() && !cbHard.isChecked()) {
            addAndSubTestView1.refresh(reviewTopicData.getOrdinary().getOne() + reviewTopicData.getEasy().getOne());
            addAndSubTestView2.refresh(reviewTopicData.getOrdinary().getMulti() + reviewTopicData.getEasy().getMulti());
            addAndSubTestView3.refresh(reviewTopicData.getOrdinary().getJudge() + reviewTopicData.getEasy().getJudge());
            addAndSubTestView4.refresh(reviewTopicData.getOrdinary().getFilling() + reviewTopicData.getEasy().getFilling());
            addAndSubTestView5.refresh(reviewTopicData.getOrdinary().getAsk() + reviewTopicData.getEasy().getAsk());
            addAndSubTestView6.refresh(reviewTopicData.getOrdinary().getComp() + reviewTopicData.getEasy().getComp());
            addAndSubTestView7.refresh(reviewTopicData.getOrdinary().getTable() + reviewTopicData.getEasy().getTable());
        } else if (cbEasy.isChecked() && !cbNormal.isChecked() && cbHard.isChecked()) {
            addAndSubTestView1.refresh(reviewTopicData.getOrdinary().getOne() + reviewTopicData.getHard().getOne());
            addAndSubTestView2.refresh(reviewTopicData.getOrdinary().getMulti() + reviewTopicData.getHard().getMulti());
            addAndSubTestView3.refresh(reviewTopicData.getOrdinary().getJudge() + reviewTopicData.getHard().getJudge());
            addAndSubTestView4.refresh(reviewTopicData.getOrdinary().getFilling() + reviewTopicData.getHard().getFilling());
            addAndSubTestView5.refresh(reviewTopicData.getOrdinary().getAsk() + reviewTopicData.getHard().getAsk());
            addAndSubTestView6.refresh(reviewTopicData.getOrdinary().getComp() + reviewTopicData.getHard().getComp());
            addAndSubTestView7.refresh(reviewTopicData.getOrdinary().getTable() + reviewTopicData.getHard().getTable());
        } else if (!cbEasy.isChecked() && cbNormal.isChecked() && cbHard.isChecked()) {
            addAndSubTestView1.refresh(reviewTopicData.getEasy().getOne() + reviewTopicData.getHard().getOne());
            addAndSubTestView2.refresh(reviewTopicData.getEasy().getMulti() + reviewTopicData.getHard().getMulti());
            addAndSubTestView3.refresh(reviewTopicData.getEasy().getJudge() + reviewTopicData.getHard().getJudge());
            addAndSubTestView4.refresh(reviewTopicData.getEasy().getFilling() + reviewTopicData.getHard().getFilling());
            addAndSubTestView5.refresh(reviewTopicData.getEasy().getAsk() + reviewTopicData.getHard().getAsk());
            addAndSubTestView6.refresh(reviewTopicData.getEasy().getComp() + reviewTopicData.getHard().getComp());
            addAndSubTestView7.refresh(reviewTopicData.getEasy().getTable() + reviewTopicData.getHard().getTable());
        } else if (cbEasy.isChecked() && cbNormal.isChecked() && cbHard.isChecked()) {
            addAndSubTestView1.refresh(reviewTopicData.getOrdinary().getOne() + reviewTopicData.getEasy().getOne() + reviewTopicData.getHard().getOne());
            addAndSubTestView2.refresh(reviewTopicData.getOrdinary().getMulti() + reviewTopicData.getEasy().getMulti() + reviewTopicData.getHard().getMulti());
            addAndSubTestView3.refresh(reviewTopicData.getOrdinary().getJudge() + reviewTopicData.getEasy().getJudge() + reviewTopicData.getHard().getJudge());
            addAndSubTestView4.refresh(reviewTopicData.getOrdinary().getFilling() + reviewTopicData.getEasy().getFilling() + reviewTopicData.getHard().getFilling());
            addAndSubTestView5.refresh(reviewTopicData.getOrdinary().getAsk() + reviewTopicData.getEasy().getAsk() + reviewTopicData.getHard().getAsk());
            addAndSubTestView6.refresh(reviewTopicData.getOrdinary().getComp() + reviewTopicData.getEasy().getComp() + reviewTopicData.getHard().getComp());
            addAndSubTestView7.refresh(reviewTopicData.getOrdinary().getTable() + reviewTopicData.getEasy().getTable() + reviewTopicData.getHard().getTable());
        } else {
            addAndSubTestView1.refresh(0);
            addAndSubTestView2.refresh(0);
            addAndSubTestView3.refresh(0);
            addAndSubTestView4.refresh(0);
            addAndSubTestView5.refresh(0);
            addAndSubTestView6.refresh(0);
            addAndSubTestView7.refresh(0);
        }
        getTotalNum();
        if ((!cbEasy.isChecked() && !cbNormal.isChecked() && !cbHard.isChecked())) {
            btnStart.setClickable(false);
            return;
        }
        if (totalNum < 1) {
            btnStart.setClickable(false);
            return;
        }
        btnStart.setClickable(true);
    }

}