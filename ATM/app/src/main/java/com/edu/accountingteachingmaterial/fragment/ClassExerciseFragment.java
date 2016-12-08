package com.edu.accountingteachingmaterial.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.SubjectDetailsContentActivity;
import com.edu.accountingteachingmaterial.activity.SubjectPracticeActivity;
import com.edu.accountingteachingmaterial.activity.SubjectTestActivity;
import com.edu.accountingteachingmaterial.adapter.ExerciseExLvAdapter;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.accountingteachingmaterial.util.SubjectsDownloadManager;
import com.edu.library.util.ToastUtil;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;
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

    static final String EXAM_ID = "EXAM_ID";

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

    public void setData(ClassChapterData.SubChaptersBean data) {
        this.data = data;
        int i = data.getId();
        PreferenceHelper.getInstance(BaseApplication.getContext()).setIntValue(EXAM_ID, i);

    }

    @Override
    protected void initData() {

        adapter = new ExerciseExLvAdapter(context);
        uploadChapterList();
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Log.d("ClassExerciseFragment", NetUrlContstant.subjectListUrl + datas.get(i).getId());
                item = i;
                if (datas.get(i).getState() == ClassContstant.EXAM_DOWNLOADING) {
                    return false;

                }else if (datas.get(i).getState() == ClassContstant.EXAM_NOT) {
                    stateIv = (ImageView) view.findViewById(R.id.item_exercise_type_iv);
                    stateIv.setVisibility(View.GONE);
                    view.findViewById(R.id.item_exercise_type_pb).setVisibility(View.VISIBLE);
                    SubjectsDownloadManager.newInstance(context).getSubjects(NetUrlContstant.subjectListUrl + datas.get(i).getId(), datas.get(i).getId(), view);

                    //  datas.get(i).setState(ClassContstant.EXAM_UNDONE);

                    //    downloadChildExercise(view, i);
                } else if (datas.get(i).getState() == ClassContstant.EXAM_UNDONE&&datas.get(i).getLesson_type() != ClassContstant.EXERCISE_IN_CLASS) {
                    b.putInt("EXERCISE_TYPE",ClassContstant.EXERCISE_IN_CLASS);
                    b.putSerializable("ExamListData", datas.get(i));
                    startActivity(SubjectTestActivity.class, b);
//                    if (datas.get(i).getLesson_type() == ClassContstant.EXERCISE_BEFORE_CLASS || datas.get(i).getLesson_type() == ClassContstant.EXERCISE_AFTER_CLASS) {
//                        b.putSerializable("ExamListData", datas.get(i));
//                        startActivity(SubjectTestActivity.class, b);
//
//                    } else if (datas.get(i).getLesson_type() == ClassContstant.EXERCISE_IN_CLASS) {
//                    }
                } else if (datas.get(i).getState() == ClassContstant.EXAM_COMMIT&&datas.get(i).getLesson_type() != ClassContstant.EXERCISE_IN_CLASS) {
                    b.putSerializable("ExamListData", datas.get(i));
                    startActivity(SubjectDetailsContentActivity.class, b);
                } else if (datas.get(i).getState() == ClassContstant.EXAM_READ) {

                }else if (datas.get(i).getLesson_type() == ClassContstant.EXERCISE_IN_CLASS){
                    return false;
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                datas.get(i).getTestList().clear();
                b.putInt("ExamListDataItem",i1);
                b.putSerializable("ExamListData",  datas.get(i));
                startActivity(SubjectPracticeActivity.class, b);

                return false;
            }
        });
    }

    /**
     * 根据发来的状态,来刷新列表
     *
     * @param state
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(Integer state) {
        Log.d("ClassExerciseFragment", "走过了EventBus");

        if (datas != null) {
            if (datas.get(item).getLesson_type() == ClassContstant.EXERCISE_IN_CLASS&&state != ClassContstant.EXAM_NOT){
                datas.get(item).setTestList(SubjectTestDataDao.getInstance(context).getSubjects(TestMode.MODE_PRACTICE,datas.get(item).getId()));
            }else {
                datas.get(item).setState(state);
            }
            adapter.setDatas(datas);
        } else {
//            datas= ExamListDao.getInstance(context).getAllDatasByChapter();
        }

    }

    private void uploadChapterList() {
        Log.d("ClassExerciseFragment", NetUrlContstant.chapterTypeUrl + PreferenceHelper.getInstance(BaseApplication.getContext()).getIntValue(EXAM_ID));
        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.chapterTypeUrl + PreferenceHelper.getInstance(BaseApplication.getContext()).getIntValue(EXAM_ID) + "-0");
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
                            contentValues.put(ExamListDao.CHAPTER_ID, data.getChapter_id());
                            ExamListDao.getInstance(context).insertData(contentValues);
                            data.setState(ClassContstant.EXAM_NOT);
                        } else {
                            data.setState(data1.getState());
                        }
                        if (data.getLesson_type() == ClassContstant.EXERCISE_IN_CLASS && data.getState() != ClassContstant.EXAM_NOT){
                            List<BaseTestData> tests = SubjectTestDataDao.getInstance(context).getSubjects(TestMode.MODE_PRACTICE,data.getId());
                            data.setTestList(tests);
                            Log.d("ClassExerciseFragment", "看看怎么样" + data);
                        }
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
