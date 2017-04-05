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
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.SubjectDetailsLocalActivity;
import com.edu.accountingteachingmaterial.activity.SubjectLocalActivity;
import com.edu.accountingteachingmaterial.activity.UnitTestActivity;
import com.edu.accountingteachingmaterial.adapter.ExamAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.dao.ExamOnLineListDao;
import com.edu.accountingteachingmaterial.entity.OnLineExamData;
import com.edu.accountingteachingmaterial.entity.OnLineExamListData;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.ExamLocalListManager;
import com.edu.accountingteachingmaterial.util.net.ExamOnlineListManager;
import com.edu.accountingteachingmaterial.util.net.OnLineExamDownloadManager;
import com.edu.accountingteachingmaterial.view.RefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 试卷页面
 */
public class ExamFragment extends BaseFragment implements RefreshListView.OnListMoveListener, CompoundButton.OnCheckedChangeListener, ExamOnlineListManager.ExamOnlineListener, ExamLocalListManager.ExamLocalListener {

    RefreshListView listView;
    List<OnLineExamListData> datas;
    ExamAdapter examAdapter;
    int item;
    List<OnLineExamListData> showDatas;
    OnLineExamData onLineExamData;
    private CheckBox examOrExercise;//
    private boolean isExercise;//当前状态,当为练习时值为ture
    private boolean inRefresh = false;
    ContentValues contentValues;

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
                    listView.setEnabled(true);
                    inRefresh = false;
                    refreshAdapter();
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
                                                Log.d("ExamFragment", "setOnItemClickListener" + JSONObject.toJSON(examAdapter.getItem(pos)));

                                                item = pos;
                                                if (showDatas.get(pos).getState() == ClassContstant.EXAM_DOWNLOADING) {
                                                    return;
                                                } else if (showDatas.get(pos).getState() == ClassContstant.EXAM_NOT) {
                                                    ImageView imageView = (ImageView) view.findViewById(R.id.item_exam_type_iv);
                                                    imageView.setVisibility(View.GONE);
                                                    view.findViewById(R.id.item_exam_type_pb).setVisibility(View.VISIBLE);
                                                    OnLineExamDownloadManager.newInstance(context).getSubjects(showDatas.get(pos).getExam_id());

                                                } else {
                                                    if (isExercise) {
                                                        Bundle b = new Bundle();
                                                        b.putBoolean("isExam", true);
                                                        b.putInt("examId", showDatas.get(pos).getExam_id());
                                                        if (showDatas.get(pos).getState() != ClassContstant.EXAM_UNDONE) {
                                                            b.putInt(ClassContstant.SUBJECT_DETAIL_ID, showDatas.get(pos).getExam_id());
//                                                        b.putSerializable("ExamListData", datas.get(i).getExam_id());
                                                            startActivity(SubjectDetailsLocalActivity.class, b);
                                                        } else {
//                                                            String examId[] = String.valueOf(showDatas.get(pos).getExam_id()).split(String.valueOf(showDatas.get(pos).getU_id()));
//                                                        b.putSerializable("ExamListData", datas.get(i).getExam_id());
                                                            startActivity(SubjectLocalActivity.class, b);
                                                        }
                                                    } else {
                                                        Bundle b = new Bundle();
                                                        b.putInt("examId", showDatas.get(pos).getExam_id());
                                                        startActivity(UnitTestActivity.class, b);
                                                    }
                                                }
                                            }
                                        }

        );

    }

    private void uploadExamList() {
        ExamOnlineListManager.getSingleton(context).getOnlineDatas(this);
        Log.d("ClassExerciseFragment", NetUrlContstant.getExamOnlineUrlList() + PreferenceHelper.getInstance(context).getStringValue(PreferenceHelper.USER_ID));

    }

    /**
     * 根据发来的状态,来刷新列表
     *
     * @param state
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(Integer state) {
        Log.d("ClassExerciseFragment", "走过了EventBus");
        if (showDatas != null && datas != null) {
            for (OnLineExamListData data : datas) {
                if (data.getExam_id() == showDatas.get(item).getExam_id()) {
                    data.setState(state);
                }
            }

            refreshAdapter();

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
        inRefresh = true;
        listView.setEnabled(false);

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
        refreshAdapter();
        examAdapter.setExercise(isChecked);

    }

    /**
     * 刷新列表
     */
    void refreshAdapter() {
        if (inRefresh){
            return;
        }
        showDatas = new ArrayList<OnLineExamListData>();
        if (datas == null || datas.size() < 1) {
            return;
        }
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
        examAdapter.setExercise(isExercise);

    }

    @Override
    public void onOnlineSuccess(OnLineExamData onLineExamData) {
        this.onLineExamData = onLineExamData;
        ExamLocalListManager.getSingleton(context).getLoaclDatas(this);
        datas = onLineExamData.getList();

    }

    @Override
    public void onLocalSuccess(OnLineExamData localData) {
        onLineExamData.getList().addAll(localData.getList());
        List<OnLineExamListData> showDatas = new ArrayList<OnLineExamListData>();
        saveExamDatas();
//                    examAdapter.setDatas(datas);

        refreshAdapter();
//        examAdapter.setDatas(showDatas);

    }
    void saveExamDatas(){

        for (OnLineExamListData data : datas) {
            data.setExam_id(Integer.valueOf("" + data.getExam_id() + data.getU_id()));
            OnLineExamListData data1 = (OnLineExamListData) ExamOnLineListDao.getInstance(context).getDataById(data.getExam_id());
                int state = ExamOnLineListDao.getInstance(context).getState(data.getExam_id());
            if (data1 == null) {
                contentValues = new ContentValues();
                contentValues.put(ExamOnLineListDao.ID,data.getExam_id() );
                contentValues.put(ExamOnLineListDao.STATE, ClassContstant.EXAM_NOT);
                contentValues.put(ExamOnLineListDao.TYPE, data.getExam_type());
                contentValues.put(ExamOnLineListDao.USER_ID, data.getU_id());
                ExamOnLineListDao.getInstance(context).insertData(contentValues);
                data.setState(ClassContstant.EXAM_NOT);
            } else if (data1 != null && state == ClassContstant.EXAM_COMMIT && data.getShow_answer() == 0 && data.getIs_send() == 1&&data.getRemaining()<0) {//判断试卷是否已批阅
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ExamOnLineListDao.STATE, ClassContstant.EXAM_READ);
                    ExamOnLineListDao.getInstance(context).updateData(String.valueOf(data1.getExam_id()), contentValues);

                    data.setState(ClassContstant.EXAM_READ);

            } else if (data1 != null && state == ClassContstant.EXAM_COMMIT && data.getShow_answer() == 1&&data.getRemaining()<0) {//判断试卷是否已批阅

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

            Log.d("ExamFragment", "JSONObject.toJSON(datas):" + JSONObject.toJSON(data));

        }

    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(context, "当前还没有评测", Toast.LENGTH_SHORT).show();
        Log.d("ExamFragment", message);
    }
}
