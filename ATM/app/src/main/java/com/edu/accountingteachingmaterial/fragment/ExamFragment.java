package com.edu.accountingteachingmaterial.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.UnitTestActivity;
import com.edu.accountingteachingmaterial.adapter.ExamAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.dao.ExamOnLineListDao;
//import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.accountingteachingmaterial.entity.OnLineExamData;
import com.edu.accountingteachingmaterial.entity.OnLineExamListData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.OnLineExamDownloadManager;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ExamFragment extends BaseFragment {

    ListView listView;
    List<OnLineExamListData> datas;
    ExamAdapter examAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    int item;
    OnLineExamData onLineExamData;

    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_exam;
    }

    @Override
    protected void initView(View view) {
        listView = bindView(R.id.exam_lv);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

        uploadExamList();
//        loadData();
        examAdapter = new ExamAdapter(context);
        examAdapter.setDatas(datas);

        listView.setAdapter(examAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                item = i;
                if (datas.get(i).getState() == ClassContstant.EXAM_DOWNLOADING) {
                    return;
                } else if (datas.get(i).getState() == ClassContstant.EXAM_NOT) {
                    final ImageView imageView = (ImageView) view.findViewById(R.id.item_exam_type_iv);
                    imageView.setVisibility(View.GONE);
                    view.findViewById(R.id.item_exam_type_pb).setVisibility(View.VISIBLE);
                    OnLineExamDownloadManager.newInstance(context).getSubjects(NetUrlContstant.getSubjectListUrl() + datas.get(i).getExam_id(), datas.get(i).getExam_id());

                } else {

                    Bundle b = new Bundle();
                    b.putInt("examId", datas.get(i).getExam_id());
                    startActivity(UnitTestActivity.class, b);
                }
            }
        });

    }



    private void uploadExamList() {
        Log.d("ClassExerciseFragment", NetUrlContstant.getExamInfoUrlList() + PreferenceHelper.getInstance(context).getStringValue(PreferenceHelper.USER_ID));

        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.getExamInfoUrlList() + PreferenceHelper.getInstance(context).getStringValue(PreferenceHelper.USER_ID));
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    onLineExamData = JSONObject.parseObject(jsonObject.getString("message"), OnLineExamData.class);
                    datas = onLineExamData.getList();
                    ToastUtil.showToast(context, "" + onLineExamData.getList().get(0).getExam_name());
                    Log.d("UnitTestActivity", "uploadChapterList" + "success" + datas);
                    for (OnLineExamListData data : datas) {
                        OnLineExamListData data1 = (OnLineExamListData) ExamOnLineListDao.getInstance(context).getDataById(data.getExam_id());
                        if (data1 == null) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(ExamOnLineListDao.ID, data.getExam_id());
                            contentValues.put(ExamOnLineListDao.STATE, ClassContstant.EXAM_NOT);
                            contentValues.put(ExamOnLineListDao.TYPE, data.getExam_type());
                            contentValues.put(ExamOnLineListDao.USER_ID, data.getU_id());
                            ExamOnLineListDao.getInstance(context).insertData(contentValues);
                            data.setState(ClassContstant.EXAM_NOT);
                        } else {
                            data.setState(data1.getState());
                        }
                    }
                    examAdapter.setDatas(datas);
//                    loadData();
                }
            }

            @Override
            public void onFailure(String errorInfo) {

                ToastUtil.showToast(context, errorInfo);
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
            datas.get(item).setState(state);
//            if (state != ClassContstant.EXAM_NOT) {
//                datas.get(item).setTestList(SubjectTestDataDao.getInstance(context).getSubjects(TestMode.MODE_PRACTICE, datas.get(item).getId()));
//            }
            examAdapter.setDatas(datas);

        } else {
//            datas= ExamListDao.getInstance(context).getAllDatasByChapter();
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    //    private void loadData() {
//        datas = new ArrayList<>();
//        for (int i = 1; i < examListDatas.size(); i++) {
//            ExamBean examBean = new ExamBean();
//            examBean.setId(examListDatas.get(i).getId());
//            examBean.setExmaStatus(examListDatas.get(i).getState());
//            examBean.setTitle(examListDatas.get(i).getExam_name());
//            examBean.setTime(examListDatas.get(i).getCreate_date());
//            examBean.setPublisher("李有才");
//            examBean.setItemNumber((long) examListDatas.get(i).getTopic_num());
//            examBean.setStartTime(examListDatas.get(i).getStart_time() + "");
//            examBean.setDuration(60);
//            datas.add(examBean);
//        }
//        examAdapter.setDatas(datas);
//    }
//    private void loadData() {
//        datas = new ArrayList<>();
//        for (int i = 1; i < datas.size(); i++) {
//            ExamBean examBean = new ExamBean();
//            examBean.setId(examListDatas.get(i).getId());
//            examBean.setExmaStatus(examListDatas.get(i).getState());
//            examBean.setTitle(examListDatas.get(i).getExam_name());
//            examBean.setTime(examListDatas.get(i).getCreate_date());
//            examBean.setPublisher("李有才");
//            examBean.setItemNumber((long) examListDatas.get(i).getTopic_num());
//            examBean.setStartTime(examListDatas.get(i).getStart_time() + "");
//            examBean.setDuration(60);
//            datas.add(examBean);
//        }
//        examAdapter.setDatas(datas);

//    }
}
