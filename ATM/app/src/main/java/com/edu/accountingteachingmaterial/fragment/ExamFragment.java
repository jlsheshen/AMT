package com.edu.accountingteachingmaterial.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.UnitTestActivity;
import com.edu.accountingteachingmaterial.adapter.ExamAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.dao.ExamOnLineListDao;
import com.edu.accountingteachingmaterial.entity.OnLineExamData;
import com.edu.accountingteachingmaterial.entity.OnLineExamListData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.net.OnLineExamDownloadManager;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.SendJsonNetReqManager;
import com.edu.accountingteachingmaterial.view.RefreshListView;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 试卷页面
 */
public class ExamFragment extends BaseFragment implements RefreshListView.OnListMoveListener, CompoundButton.OnCheckedChangeListener {

    RefreshListView listView;
    List<OnLineExamListData> datas;
    ExamAdapter examAdapter;
    int item;
    OnLineExamData onLineExamData;
    private CheckBox examOrExercise;//
    private boolean isExercise;//当前状态,当为练习时值为ture

    /**
     * 下拉刷新完成
     */
    private final static int REFRESH_COMPLETE = 0;
    /**
     * 上拉加载完成
     */
    private final static int LOAD_COMPLETE = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    listView.setOnRefreshComplete();
                    examAdapter.notifyDataSetChanged();
                    listView.setSelection(0);
                    break;
                case LOAD_COMPLETE:
                    listView.setOnLoadMoreComplete();
                    examAdapter.notifyDataSetChanged();
                    listView.setSelection(datas.size());
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_exam;
    }

    @Override
    protected void initView(View view) {
        listView = bindView(R.id.exam_lv);
        examOrExercise = bindView(R.id.exam_chose_cb);
        examOrExercise.setOnCheckedChangeListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        uploadExamList();
//        loadData();
        examAdapter = new ExamAdapter(context);
        listView.setAdapter(examAdapter);
        listView.setOnListMoveListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l) {

                                                int pos = 0;
                                                if (i > 1) {
                                                    pos = i - 1;
                                                }
                                                item = pos;
                                                if (datas.get(pos).getState() == ClassContstant.EXAM_DOWNLOADING) {
                                                    return;
                                                } else if (datas.get(pos).getState() == ClassContstant.EXAM_NOT) {
                                                    final ImageView imageView = (ImageView) view.findViewById(R.id.item_exam_type_iv);
                                                    imageView.setVisibility(View.GONE);
                                                    view.findViewById(R.id.item_exam_type_pb).setVisibility(View.VISIBLE);
                                                    OnLineExamDownloadManager.newInstance(context).getSubjects(NetUrlContstant.getSubjectListUrl() + datas.get(pos).getExam_id(), datas.get(pos).getExam_id());

                                                } else {
                                                    if (isExercise){
                                                        
                                                    }else {
                                                    Bundle b = new Bundle();
                                                    b.putInt("examId", datas.get(pos).getExam_id());
                                                    startActivity(UnitTestActivity.class, b);}
                                                }
                                            }
                                        }
        );

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
                    List<OnLineExamListData> showDatas = new ArrayList<OnLineExamListData>();
                    ToastUtil.showToast(context, "" + onLineExamData.getList().get(0).getExam_name());
                    Log.d("UnitTestActivity", "uploadChapterList" + "success" + datas);

                    for (OnLineExamListData data : datas) {
                        OnLineExamListData data1 = (OnLineExamListData) ExamOnLineListDao.getInstance(context).getDataById(data.getExam_id());
                        int state = ExamOnLineListDao.getInstance(context).getState(data.getExam_id());
                        if (data1 == null) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(ExamOnLineListDao.ID, data.getExam_id());
                            contentValues.put(ExamOnLineListDao.STATE, ClassContstant.EXAM_NOT);
                            contentValues.put(ExamOnLineListDao.TYPE, data.getExam_type());
                            contentValues.put(ExamOnLineListDao.USER_ID, data.getU_id());
                            ExamOnLineListDao.getInstance(context).insertData(contentValues);
                            data.setState(ClassContstant.EXAM_NOT);
                        } else if (data1 != null && state == ClassContstant.EXAM_COMMIT && data.getShow_answer() == 0 && data.getIs_send() == 1) {//判断试卷是否已批阅
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(ExamOnLineListDao.STATE, ClassContstant.EXAM_READ);
                            ExamOnLineListDao.getInstance(context).updateData(String.valueOf(data1.getExam_id()), contentValues);
                            data.setState(ClassContstant.EXAM_READ);
                        } else if (data1 != null && state == ClassContstant.EXAM_COMMIT && data.getShow_answer() == 1) {//判断试卷是否已批阅
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(ExamOnLineListDao.STATE, ClassContstant.EXAM_READ);
                            ExamOnLineListDao.getInstance(context).updateData(String.valueOf(data1.getExam_id()), contentValues);
                            data.setState(ClassContstant.EXAM_READ);
                        } else {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(ExamOnLineListDao.STATE, data1.getState());
                            ExamOnLineListDao.getInstance(context).updateData(String.valueOf(data1.getExam_id()), contentValues);
                            data.setState(data1.getState());
                        }
                        if (isExercise) {
                            if (data.getExam_type() != 3) {
                                showDatas.add(data);
                            }
                        } else {
                            if (data.getExam_type() == 3) {
                                showDatas.add(data);
                            }
                        }
                    }
//                    examAdapter.setDatas(datas);
                    examAdapter.setDatas(showDatas);
                    examAdapter.setExercise(isExercise);

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

    @Override
    public void refreshView() {
        uploadExamList();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void loadMoreView() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mHandler.sendEmptyMessage(LOAD_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isExercise = isChecked;
        List<OnLineExamListData> showDatas = new ArrayList<OnLineExamListData>();
        //刷新adapter
        for (OnLineExamListData data : datas) {
            if (isExercise) {
                if (data.getExam_type() != 3) {
                    showDatas.add(data);
                }
            } else {
                if (data.getExam_type() == 3) {
                    showDatas.add(data);
                }
            }
        }
        examAdapter.setDatas(showDatas);
        examAdapter.setExercise(isChecked);

    }
}
