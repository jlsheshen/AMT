//package com.edu.accountingteachingmaterial.fragment;
//
//import android.content.ContentValues;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.ListView;
//
//import com.alibaba.fastjson.JSONObject;
//import com.edu.NetUrlContstant;
//import com.edu.accountingteachingmaterial.R;
//import com.edu.accountingteachingmaterial.activity.UnitTestActivity;
//import com.edu.accountingteachingmaterial.adapter.ExamAdapter;
//import com.edu.accountingteachingmaterial.base.BaseApplication;
//import com.edu.accountingteachingmaterial.base.BaseFragment;
//import com.edu.accountingteachingmaterial.constant.ClassContstant;
//import com.edu.accountingteachingmaterial.dao.ExamOnLineListDao;
//import com.edu.accountingteachingmaterial.entity.ExamListData;
//import com.edu.accountingteachingmaterial.entity.OnLineExamData;
//import com.edu.accountingteachingmaterial.entity.OnLineExamListData;
//import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
//import com.edu.accountingteachingmaterial.util.OnLineExamDownloadManager;
//import com.edu.accountingteachingmaterial.util.PreferenceHelper;
//import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
//import com.edu.library.util.ToastUtil;
//import com.lucher.net.req.RequestMethod;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.List;
//
//import static com.edu.accountingteachingmaterial.fragment.ClassExerciseFragment.EXAM_ID;
//
//public class ExamFragment extends BaseFragment {
//
//    ListView listView;
//    List<OnLineExamListData> datas;
//    ExamAdapter examAdapter;
//    private Handler mHandler = new Handler(Looper.getMainLooper());
//    int item;
//    OnLineExamData onLineExamData;
//
//    @Override
//    protected int initLayout() {
//        // TODO Auto-generated method stub
//        return R.layout.fragment_exam;
//    }
//
//    @Override
//    protected void initView(View view) {
//        listView = bindView(R.id.exam_lv);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void initData() {
//
////        uploadExamList();
//        loadData();
//        examAdapter = new ExamAdapter(context);
//        examAdapter.setDatas(datas);
//
//        listView.setAdapter(examAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l) {
//                item = i;
//                if (datas.get(i).getState() == ClassContstant.EXAM_DOWNLOADING) {
//                    return;
//                } else if (datas.get(i).getState() == ClassContstant.EXAM_NOT) {
//                    final ImageView imageView = (ImageView) view.findViewById(R.id.item_exam_type_iv);
//                    imageView.setVisibility(View.GONE);
//                    view.findViewById(R.id.item_exam_type_pb).setVisibility(View.VISIBLE);
//                    OnLineExamDownloadManager.newInstance(context).getSubjects(NetUrlContstant.subjectListUrl + datas.get(i).getExam_id(), datas.get(i).getExam_id());
//                } else {
//
//                    Bundle b = new Bundle();
//                    b.putInt("examId", datas.get(i).getExam_id());
//                    startActivity(UnitTestActivity.class, b);
//                }
//            }
//        });
//
//    }
//
//    /**
//     * 根据发来的状态,来刷新列表
//     *
//     * @param state
//     */
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getData(Integer state) {
//        Log.d("ClassExerciseFragment", "走过了EventBus");
//
//        if (datas != null) {
//            datas.get(item).setState(state);
//            examAdapter.setDatas(datas);
//        } else {
////            datas= ExamOnLineListDao.getInstance(context).getAllDatasByChapter();
//        }
//
//    }
//
//    private void uploadExamList() {
//        Log.d("ClassExerciseFragment", NetUrlContstant.chapterTypeUrl + PreferenceHelper.getInstance(BaseApplication.getContext()).getIntValue(EXAM_ID));
//
//        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.getExamInfoUrl + "5926");
//        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
//        sendJsonNetReqManager.sendRequest(entity);
//        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
//            @Override
//            public void onSuccess(JSONObject jsonObject) {
//                if (jsonObject.getString("success").equals("true")) {
//                    onLineExamData = JSONObject.parseObject(jsonObject.getString("message"), OnLineExamData.class);
//                    datas = onLineExamData.getList();
//                    ToastUtil.showToast(context, "" + onLineExamData.getList().get(0).getExam_name());
//                    Log.d("UnitTestActivity", "uploadChapterList" + "success" + datas);
//                    for (OnLineExamListData data : datas) {
//                        ExamListData data1 = (ExamListData) ExamOnLineListDao.getInstance(context).getDataById(data.getU_id());
//                        if (data1 == null) {
//                            ContentValues contentValues = new ContentValues();
//                            contentValues.put(ExamOnLineListDao.ID, data.getId());
//                            contentValues.put(ExamOnLineListDao.STATE, ClassContstant.EXAM_NOT);
//                            contentValues.put(ExamOnLineListDao.TYPE, data.getExam_type());
//                            contentValues.put(ExamOnLineListDao.USER_ID, data.getU_id());
//                            ExamOnLineListDao.getInstance(context).insertData(contentValues);
//                            data.setState(ClassContstant.EXAM_NOT);
//                        } else {
//                            data.setState(data1.getState());
//                        }
//                    }
//                    examAdapter.setDatas(datas);
////                    loadData();
//                }
//            }
//
//            @Override
//            public void onFailure(String errorInfo) {
//
//                ToastUtil.showToast(context, errorInfo);
//            }
//        });
//    }
//
//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//
//        super.onDestroy();
//    }
//
//    //    private void loadData() {
////        datas = new ArrayList<>();
////        for (int i = 1; i < examListDatas.size(); i++) {
////            ExamBean examBean = new ExamBean();
////            examBean.setId(examListDatas.get(i).getId());
////            examBean.setExmaStatus(examListDatas.get(i).getState());
////            examBean.setTitle(examListDatas.get(i).getExam_name());
////            examBean.setTime(examListDatas.get(i).getCreate_date());
////            examBean.setPublisher("李有才");
////            examBean.setItemNumber((long) examListDatas.get(i).getTopic_num());
////            examBean.setStartTime(examListDatas.get(i).getStart_time() + "");
////            examBean.setDuration(60);
////            datas.add(examBean);
////        }
////        examAdapter.setDatas(datas);
////    }
//    private void loadData() {
////        datas = new ArrayList<>();
////        for (int i = 1; i < datas.size(); i++) {
////            ExamBean examBean = new ExamBean();
////            examBean.setId(examListDatas.get(i).getId());
////            examBean.setExmaStatus(examListDatas.get(i).getState());
////            examBean.setTitle(examListDatas.get(i).getExam_name());
////            examBean.setTime(examListDatas.get(i).getCreate_date());
////            examBean.setPublisher("李有才");
////            examBean.setItemNumber((long) examListDatas.get(i).getTopic_num());
////            examBean.setStartTime(examListDatas.get(i).getStart_time() + "");
////            examBean.setDuration(60);
////            datas.add(examBean);
////        }
////        examAdapter.setDatas(datas);
//
//    }
//}

package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.UnitTestActivity;
import com.edu.accountingteachingmaterial.adapter.ExamAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.ExamBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.library.util.DBCopyUtil;
import com.edu.testbill.Constant;
import com.edu.testbill.util.SoundPoolUtil;

import java.util.ArrayList;
import java.util.List;

public class ExamFragment extends BaseFragment {

    ListView listView;
    List<ExamBean> datas;
    ExamAdapter examAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_exam;
    }

    @Override
    protected void initView(View view) {
        listView = bindView(R.id.exam_lv);

    }

    @Override
    protected void initData() {

        // 检测数据库是否已拷贝
        DBCopyUtil fileCopyUtil = new DBCopyUtil(context);
        fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);
        SoundPoolUtil.getInstance().init(context);


        loadData();

        examAdapter = new ExamAdapter(context);
        examAdapter.setDatas(datas);

        listView.setAdapter(examAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                if (datas.get(i).getExmaStatus() == ClassContstant.EXAM_NOT) {
                    final ImageView imageView = (ImageView) view.findViewById(R.id.item_exam_type_iv);
                    imageView.setVisibility(View.GONE);
                    view.findViewById(R.id.item_exam_type_pb).setVisibility(View.VISIBLE);
                    datas.get(i).setExmaStatus(ClassContstant.EXAM_DOWNLOADING);
                    CountDownTimer timer = new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            datas.get(i).setExmaStatus(ClassContstant.EXAM_UNDONE);
                            ExamListDao.getInstance(context).updateState(datas.get(i).getExamId(), ClassContstant.EXAM_UNDONE);
                            view.findViewById(R.id.item_exam_type_pb).setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.mipmap.btn_weitijiao_n);
                        }
                    }.start();


                } else {
                    Bundle bundle = new Bundle();
//                    bundle.putInt("ExmaStatus", ExamListDao.getInstance(context).getState(datas.get(i).getExamId()));
                    bundle.putInt("ExmaID", datas.get(i).getExamId());
                    bundle.putString("title", datas.get(i).getTitle());
                    startActivity(UnitTestActivity.class, bundle);
                }
            }
        });

    }

    private void loadData() {

        datas = new ArrayList<>();

        ExamBean examBean1 = new ExamBean();
        int exmaStatus = ExamListDao.getInstance(context).getState(1210);
        if (exmaStatus == 0) {
            examBean1.setExmaStatus(4);
        } else {
            examBean1.setExmaStatus(exmaStatus);
        }
        examBean1.setTitle("浙江技能高考试题：单据题");
        examBean1.setTime("2016-12-11");
        examBean1.setPublisher("章敏");
        examBean1.setItemNumber((long) 20);
        examBean1.setStartTime("2016-12-11 10:30");
        examBean1.setDuration(60);
        examBean1.setExamId(1210);
        datas.add(examBean1);
        ExamBean examBean2 = new ExamBean();
        int exmaStatus2 = ExamListDao.getInstance(context).getState(1211);
        if (exmaStatus2 == 0) {
            examBean2.setExmaStatus(4);
        } else {
            examBean2.setExmaStatus(exmaStatus2);
        }
        examBean2.setTitle("浙江技能高考试题：财务报表");
        examBean2.setTime("2016-12-11");
        examBean2.setPublisher("章敏");
        examBean2.setItemNumber((long) 139);
        examBean2.setStartTime("2016-12-11 10:30");
        examBean2.setDuration(60);
        examBean2.setExamId(1211);
        datas.add(examBean2);
        ExamBean examBean3 = new ExamBean();
        int exmaStatus3 = ExamListDao.getInstance(context).getState(1212);
        if (exmaStatus3 == 0) {
            examBean3.setExmaStatus(4);
        } else {
            examBean3.setExmaStatus(exmaStatus3);
        }
        examBean3.setTitle("浙江技能高考试题：原始凭证");
        examBean3.setTime("2016-12-11");
        examBean3.setPublisher("章敏");
        examBean3.setItemNumber((long) 200);
        examBean3.setStartTime("2016-12-11 10:30");
        examBean3.setDuration(60);
        examBean3.setExamId(1212);
        datas.add(examBean3);
        ExamBean examBean4 = new ExamBean();
        int exmaStatus4 = ExamListDao.getInstance(context).getState(1213);
        if (exmaStatus4 == 0) {
            examBean4.setExmaStatus(4);
        } else {
            examBean4.setExmaStatus(exmaStatus4);
        }
        examBean4.setTitle("浙江技能高考试题：账簿");
        examBean4.setTime("2016-12-11");
        examBean4.setPublisher("章敏");
        examBean4.setItemNumber((long) 200);
        examBean4.setStartTime("2016-12-11 10:30");
        examBean4.setDuration(60);
        examBean4.setExamId(1213);
        datas.add(examBean4);
        // TODO Auto-generated method stub

    }


}
