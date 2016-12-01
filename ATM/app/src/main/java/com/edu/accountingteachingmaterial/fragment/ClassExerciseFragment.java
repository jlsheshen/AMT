package com.edu.accountingteachingmaterial.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.SubjectDetailsContentActivity;
import com.edu.accountingteachingmaterial.activity.SubjectTestActivity;
import com.edu.accountingteachingmaterial.adapter.ExerciseExLvAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.accountingteachingmaterial.entity.SubjectTestData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.accountingteachingmaterial.util.SubjectsDownloadManager;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ClassExerciseFragment extends BaseFragment {
    ExpandableListView expandableListView;
    List<ExamListData> datas;
    ExerciseExLvAdapter adapter;
    ImageView stateIv;
    ExamListData data1;
    Bundle b = new Bundle();
    ClassChapterData.SubChaptersBean data;

    int item;

    @Override
    protected int initLayout() {
        return R.layout.fragment_class_exercise;
    }

    @Override
    protected void initView(View view) {
        expandableListView = bindView(R.id.exercise_exlv);
        EventBus.getDefault().register(this);
    }

    public ClassExerciseFragment(ClassChapterData.SubChaptersBean data) {
        this.data = data;
    }

    public ClassExerciseFragment() {
    }

    @Override
    protected void initData() {

//        loadDatas();
        adapter = new ExerciseExLvAdapter(context);
        uploadChapterList();
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Log.d("ClassExerciseFragment", "点击确认");
                if (datas.get(i).getState() == ClassContstant.EXAM_NOT) {
                    stateIv = (ImageView) view.findViewById(R.id.item_exercise_type_iv);
                    stateIv.setVisibility(View.GONE);
                    view.findViewById(R.id.item_exercise_type_pb).setVisibility(View.VISIBLE);
                    SubjectsDownloadManager.newInstance(context).getSubjects(NetUrlContstant.subjectListUrl + datas.get(i).getId(),datas.get(i).getId());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_UNDONE);
                    ExamListDao.getInstance(context).updateData("" +datas.get(i).getId(), contentValues);
                    datas.get(i).setState(ClassContstant.EXAM_UNDONE);
                    item=i;
                    view.findViewById(R.id.item_exercise_type_pb).setVisibility(View.GONE);
                    stateIv.setImageResource(R.drawable.selector_exam_undown_type);
                    stateIv.setVisibility(View.VISIBLE);

                //    downloadChildExercise(view, i);
                } else if (datas.get(i).getState() == ClassContstant.EXAM_UNDONE){
                    if (datas.get(i).getLesson_type() == ClassContstant.EXERCISE_BEFORE_CLASS || datas.get(i).getLesson_type() == ClassContstant.EXERCISE_AFTER_CLASS) {
                       b.putSerializable("ExamListData",datas.get(i));
                        startActivity(SubjectTestActivity.class, b);

                    } else if (datas.get(i).getLesson_type() == ClassContstant.EXERCISE_IN_CLASS) {

                    }
                }else if (datas.get(i).getState() == ClassContstant.EXAM_COMMIT){
                    b.putSerializable("ExamListData",datas.get(i));
                    startActivity(SubjectDetailsContentActivity.class,b);
                }
                return false;
            }
        });
    }
    //线程类型
    @Subscribe(threadMode= ThreadMode.MAIN)
    public void getData(String string){


    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        uploadChapterList();
//
//    }

    /**
     * 点击下载按钮触发事件,开始下载,下载时显示progroessbar,下载完成则显示未提交
     */
    private void downloadChildExercise(final View view, final int i) {
        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.chapterTypeUrl + "151" + "-" + "4");
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    List<SubjectTestData> daa = JSON.parseArray(jsonObject.getString("message"), SubjectTestData.class);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_UNDONE);
                    ExamListDao.getInstance(context).updateData(String.valueOf(datas.get(i).getId()), contentValues);
                    datas.get(i).setState(ClassContstant.EXAM_UNDONE);
                    view.findViewById(R.id.item_exercise_type_pb).setVisibility(View.GONE);
                    stateIv.setImageResource(R.drawable.selector_exam_undown_type);
                    stateIv.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(String errorInfo) {
                ToastUtil.showToast(context, errorInfo);
            }
        });
    }

    private void uploadChapterList() {
        Log.d("ClassExerciseFragment", "崩溃之前");
        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.chapterTypeUrl + data.getId() + "-0");
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    datas = JSON.parseArray(jsonObject.getString("message"), ExamListData.class);
                    ToastUtil.showToast(context, "" + datas.get(0).getExam_name());
                    Log.d("UnitTestActivity", "uploadChapterList" + "success" + datas);
                    for (ExamListData data : datas) {
                        data1 = (ExamListData) ExamListDao.getInstance(context).getDataById(data.getId());
                        if (data1 == null) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(ExamListDao.ID, data.getId());
                            contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_NOT);
                            contentValues.put(ExamListDao.TYPE, data.getExam_type());
                            ExamListDao.getInstance(context).insertData(contentValues);
                            data.setState(ClassContstant.EXAM_NOT);
                        }
                        data.setState(data1.getState());

                    }
                    adapter.setDatas(datas);


                }
            }

            @Override
            public void onFailure(String errorInfo) {

                ToastUtil.showToast(context, errorInfo);
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }
}
