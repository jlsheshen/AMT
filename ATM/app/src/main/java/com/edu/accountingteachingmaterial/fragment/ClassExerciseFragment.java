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
import com.edu.accountingteachingmaterial.activity.SubjectPracticeActivity;
import com.edu.accountingteachingmaterial.adapter.ExerciseExLvAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.accountingteachingmaterial.entity.SubjectTestData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

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
    @Override
    protected int initLayout() {
        return R.layout.fragment_class_exercise;
    }

    @Override
    protected void initView(View view) {
        expandableListView = bindView(R.id.exercise_exlv);

    }

    @Override
    protected void initData() {
//        loadDatas();
        adapter = new ExerciseExLvAdapter(context);
        uploadChapterList();
        expandableListView.setAdapter(adapter);

    }

    /**
     * 点击下载按钮触发事件,开始下载,下载时显示progroessbar,下载完成则显示未提交
     */
    private void downloadChildExercise(final View view, final int i){
        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.chapterTypeUrl + "151" + "-" + "4");
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d("ClassExerciseFragment", "333333333333:");
                if (jsonObject.getString("success").equals("true")) {
                    List<SubjectTestData> daa = JSON.parseArray(jsonObject.getString("message"), SubjectTestData.class);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ExamListDao.STATE,ClassContstant.EXAM_UNDONE);
                    ExamListDao.getInstance(context).updateData(String.valueOf(datas.get(i).getId()),contentValues);
                    datas.get(i).setState(ClassContstant.EXAM_UNDONE);
                    view.findViewById(R.id.item_exercise_type_pb).setVisibility(View.GONE);
                    stateIv.setImageResource(R.drawable.selector_exam_undown_type);
                    stateIv.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onFailure(String errorInfo) {
                Log.d("ClassExerciseFragment", "444444444444");
                ToastUtil.showToast(context, errorInfo);
            }
        });
    }
    

    //    private void loadDatas() {
//        datas = new ArrayList<>();
//        for (int i = 1; i <10 ; i++) {
//            ExerciseBean data = new ExerciseBean();
//            data.setTitle("会计凭证");
//            data.setItemNumber(i);
//            data.setTime("20161111");
//            data.setExerciseStatus(i%6);
//            switch (i%3){
//                case 0:
//                    data.setQuestionType(ClassContstant.EXERCISE_BEFORE_CLASS);
//                    break;
//                case 1:
//                    data.setQuestionType(ClassContstant.EXERCISE_IN_CLASS);
//                    List<ExercisePracticeBean> exercisePracticeBeans = new ArrayList<>();
//                    for (int j =1; j < 8; j++) {
//                        ExercisePracticeBean practiceBean = new ExercisePracticeBean();
//                        practiceBean.setQuestionType(j);
//                        practiceBean.setContent("试题内容");
//                        practiceBean.setPracticeNum(j);
//                        if (j ==4){
//                            practiceBean.setIsRight(ClassContstant.ANSWER_RIGHT);
//                        }else if (j==5){
//                            practiceBean.setIsRight(ClassContstant.ANSWER_ERROR);
//
//                        }else {
//                            practiceBean.setIsRight(ClassContstant.ANSWER_NODONE);
//                        }
//                        exercisePracticeBeans.add(practiceBean);
//                    }
//                    data.setExerciseBeanList(exercisePracticeBeans);
//                    break;
//                case 2:
//                    data.setQuestionType(ClassContstant.EXERCISE_AFTER_CLASS);
//
//                    break;
//
//            }
//            datas.add(data);
//
//        }
//
//    }
    private void uploadChapterList() {
        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.chapterTypeUrl + "179" + "-" + "0");
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    datas = JSON.parseArray(jsonObject.getString("message"), ExamListData.class);
                    ToastUtil.showToast(context, "" + datas.get(0).getExam_name());
                    Log.d("UnitTestActivity", "uploadChapterList" + "success" + datas);
                    Log.d("ClassExerciseFragment", "11111111111111");
                    for (ExamListData data : datas) {
                        data1  = (ExamListData) ExamListDao.getInstance(context).getDataById(data.getId());
                        if (data1 == null){
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(ExamListDao.ID , data.getId());
                            contentValues.put(ExamListDao.STATE,ClassContstant.EXAM_NOT);
                            contentValues.put(ExamListDao.TYPE,data.getExam_type());
                            ExamListDao.getInstance(context).insertData(contentValues);
                            data.setState(ClassContstant.EXAM_NOT);
                        }
                        data.setState(data1.getState());
                        Log.d("ClassExerciseFragment", "11111111111111-------111");

                    }
                    adapter.setDatas(datas);
                    Log.d("ClassExerciseFragment", "11111111111111-------222");

                    expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                            Log.d("ClassExerciseFragment", "点击确认");
                            if (datas.get(i).getState() == ClassContstant.EXAM_NOT) {
                                stateIv = (ImageView) view.findViewById(R.id.item_exercise_type_iv);
                                stateIv.setVisibility(View.GONE);
                                view.findViewById(R.id.item_exercise_type_pb).setVisibility(View.VISIBLE);
                                downloadChildExercise(view,i);
                            }else {
                                if(datas.get(i).getLesson_type() == ClassContstant.EXERCISE_BEFORE_CLASS||datas.get(i).getLesson_type() == ClassContstant.EXERCISE_AFTER_CLASS){
                                    Bundle b = new Bundle();
                                    b.putSerializable("ExamListData",datas.get(i));
                                    startActivity(SubjectPracticeActivity.class,b);


                                }else if (datas.get(i).getLesson_type() == ClassContstant.EXERCISE_IN_CLASS){

                                }

                            }

                            return false;
                        }
                    });
                }
            }
            @Override
            public void onFailure(String errorInfo) {
                Log.d("ClassExerciseFragment", "2222222222222222222");

                ToastUtil.showToast(context, errorInfo);
            }
        });
    }
}
