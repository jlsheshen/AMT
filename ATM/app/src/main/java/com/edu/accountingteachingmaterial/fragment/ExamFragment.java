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
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.UnitTestActivity;
import com.edu.accountingteachingmaterial.adapter.ExamAdapter;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.ExamBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.entity.ExamData;
import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.accountingteachingmaterial.util.SubjectsDownloadManager;
import com.edu.library.util.DBCopyUtil;
import com.edu.library.util.ToastUtil;
import com.edu.testbill.Constant;
import com.edu.testbill.util.SoundPoolUtil;
import com.lucher.net.req.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.edu.accountingteachingmaterial.fragment.ClassExerciseFragment.EXAM_ID;

public class ExamFragment extends BaseFragment {

    ListView listView;
    List<ExamBean> datas;
    ExamAdapter examAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    List<ExamListData> examListDatas;
    int item;
    ExamData examData;

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

        // 检测数据库是否已拷贝
        DBCopyUtil fileCopyUtil = new DBCopyUtil(context);
        fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);
        SoundPoolUtil.getInstance().init(context);

//        uploadExamList();
        loadData();
        examAdapter = new ExamAdapter(context);
        examAdapter.setDatas(datas);

        listView.setAdapter(examAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                item = i;
                if (datas.get(i).getExmaStatus() == ClassContstant.EXAM_DOWNLOADING) {
                    return;
                } else if (datas.get(i).getExmaStatus() == ClassContstant.EXAM_NOT) {
                    final ImageView imageView = (ImageView) view.findViewById(R.id.item_exam_type_iv);
                    imageView.setVisibility(View.GONE);
                    view.findViewById(R.id.item_exam_type_pb).setVisibility(View.VISIBLE);
                    SubjectsDownloadManager.newInstance(context).getSubjects(NetUrlContstant.subjectListUrl + datas.get(i).getId(), datas.get(i).getId(), view);
                } else {
                    Bundle b = new Bundle();
                    b.putInt("examId", 1179);
                    startActivity(UnitTestActivity.class, b);
                }
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

        if (examListDatas != null) {
            if (state != ClassContstant.EXAM_NOT) {
                datas.get(item).setExmaStatus(state);
            } else {
                datas.get(item).setExmaStatus(state);
            }
            examAdapter.setDatas(datas);
        }

    }

    private void uploadExamList() {
        Log.d("ClassExerciseFragment", NetUrlContstant.chapterTypeUrl + PreferenceHelper.getInstance(BaseApplication.getContext()).getIntValue(EXAM_ID));
        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.getExamInfoUrl + "5926");
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    examData = JSONObject.parseObject(jsonObject.getString("message"), ExamData.class);

                    ToastUtil.showToast(context, "" + examListDatas.get(0).getExam_name());
                    Log.d("UnitTestActivity", "uploadChapterList" + "success" + datas);
                    for (ExamData.ListBean data : examData.getList()) {
                        ExamListData data1 = (ExamListData) ExamListDao.getInstance(context).getDataById(data.getId());
                        if (data1 == null) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(ExamListDao.ID, data.getId());
                            contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_NOT);
                            contentValues.put(ExamListDao.TYPE, data.getExam_type());
                            contentValues.put(ExamListDao.CHAPTER_ID, data.getExam_id());
                            ExamListDao.getInstance(context).insertData(contentValues);
                            data.setState(ClassContstant.EXAM_NOT);
                        } else {
                            data.setState(data1.getState());
                        }
                    }
                    loadData();
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
    private void loadData() {

        datas = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            ExamBean examBean = new ExamBean();

            examBean.setExmaStatus(i);
            examBean.setTitle("会计立体化教材");
            examBean.setTime("20161111");
            examBean.setPublisher("赵铁柱");
            examBean.setItemNumber((long) 130);
            examBean.setStartTime("2016-11-11 10:30");
            examBean.setDuration(60);
            datas.add(examBean);
        }
        // TODO Auto-generated method stub

    }
}
