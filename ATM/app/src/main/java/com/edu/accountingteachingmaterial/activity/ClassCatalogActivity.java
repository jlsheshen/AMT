package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.ClassChapterExLvAdapter;
import com.edu.accountingteachingmaterial.adapter.HistoryPpwAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.entity.HistoryListData;
import com.edu.accountingteachingmaterial.entity.StudyHistoryVO;
import com.edu.accountingteachingmaterial.util.HistoryClickManager;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ClassCatalogActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    ExpandableListView expandableListView;
    List<ClassChapterData> datas;
    ClassChapterExLvAdapter chapterExLvAdapter;
    //    HistoryPpwAdapter todayAdapter, yestodayAdapter, agoAdapter;
    HistoryPpwAdapter ppwAdapter;
    PopupWindow popupWindow;
    ListView ppwList;
    //    NoScrollListView todayLv, yesterdayLv, agoLv;
//    TextView todayTv, yesterdayTv, agoTv;
    boolean ppwShowing = false;




    ImageView imgHistory;


    @Override
    public int setLayout() {
        return R.layout.activity_class_catalog;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        expandableListView = (ExpandableListView) bindView(R.id.class_classchapter_exlv);
        imgHistory = (ImageView) bindView(R.id.main_study_history_iv);
        imgHistory.setOnClickListener(this);
    }

    @Override
    public void initData() {

//		loadData();
        chapterExLvAdapter = new ClassChapterExLvAdapter(this);
        uploadChapter();
        expandableListView.setAdapter(chapterExLvAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//				String id1 = String.valueOf(datas.get(groupPosition).getSubChapters().get(childPosition).getId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("classData", datas.get(groupPosition).getSubChapters().get(childPosition));
                bundle.putInt("ChapterId", datas.get(groupPosition).getId());
                startActivity(ClassDetailActivity.class, bundle);

                // TODO Auto-generated method stub
                return false;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0, count = expandableListView.getExpandableListAdapter().getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {// 关闭其他分组
                        expandableListView.collapseGroup(i);

                    }
                }
            }
        });
        showPopupWindow(expandableListView);
        ppwAdapter = new HistoryPpwAdapter(this);
//        todayAdapter = new HistoryPpwAdapter(this);
//        yestodayAdapter = new HistoryPpwAdapter(this);
//        agoAdapter = new HistoryPpwAdapter(this);
    }

    private void uploadChapter() {
        int courseId = PreferenceHelper.getInstance(this).getIntValue(PreferenceHelper.COURSE_ID);
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        Log.d("ClassFragment", NetUrlContstant.getChapterUrl() + courseId);
        NetSendCodeEntity entity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getChapterUrl() + courseId);
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    datas = JSON.parseArray(jsonObject.getString("message"), ClassChapterData.class);
                    Log.d("UnitTestActivity", "uploadChapter" + "success" + jsonObject.getString("message"));
                    chapterExLvAdapter.setDatas(datas);
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                ToastUtil.showToast(ClassCatalogActivity.this, errorInfo + "请联网哟");
            }
        });
    }
    public void showPpw(){
        if (ppwShowing) {
            popupWindow.dismiss();
            ppwShowing = false;
        } else {
            popupWindow.showAsDropDown(imgHistory, 50, 50);
            ppwShowing = true;
//          expandableListView.setEnabled(false);
//          expandableListView.setAlpha(0.5f);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_study_history_iv:
                showPpw();

                break;
        }
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.ppw_his, null);
        ppwList = (ListView) contentView.findViewById(R.id.ppw_history_lv);
        ppwList.setOnItemClickListener(this);

        loadHistoryDatas();

        popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, view.getLayoutParams().height);

        popupWindow.setContentView(contentView);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_ppw));
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

    }

    private void itemClick(List<HistoryListData> data, int i, ListView listView, BaseAdapter listAdapter) {
        List<StudyHistoryVO> historyVOs = new ArrayList<>();
        ExampleBean exampleBean = new ExampleBean();

        historyVOs.add(data.get(i).getUpLoadingData(this));
        exampleBean.setUrl(data.get(i).getUri());

        HistoryClickManager.getHisInstance(this).setStudyHistoryVOList(historyVOs).sendHistory();
        Bundle bundle = new Bundle();
        bundle.putSerializable("exampleBeans", exampleBean);
        Log.d("ClassFragment", "exampleBean:" + exampleBean);
//        switch (aData.get(i).getFile_type()) {
//            case ClassContstant.MEADIA_TYPE:
//                startActivity(MediaActivity.class, bundle);
//
//                break;
//            default:
//                startActivity(PdfActivity.class, bundle);
//                break;
//
//        }

        int totalHeight = 0;
        for (int i1 = 0, len = listAdapter.getCount(); i1 < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i1, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);

    }


    private void loadHistoryDatas() {

        Log.d("LaunchActivity", NetUrlContstant.getFindHisUrl() + PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.USER_ID));
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getFindHisUrl() + PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(PreferenceHelper.USER_ID));
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    List<HistoryListData> hData = JSON.parseArray(jsonObject.getString("message"), HistoryListData.class);
                    List<HistoryListData> tData, yData, aData;

                    tData = new ArrayList<HistoryListData>();
                    yData = new ArrayList<HistoryListData>();
                    aData = new ArrayList<HistoryListData>();
                    for (HistoryListData historyListData : hData) {
                        historyListData.setUri(historyListData.getUri());
                        switch (historyListData.getDate_diff()) {
                            case ClassContstant.HISTORY_TODAY:
                                tData.add(historyListData);
                                break;
                            case ClassContstant.HISTORY_YESTODAY:
                                yData.add(historyListData);
                                break;
                            default:
                                aData.add(historyListData);
                                break;
                        }
                    }
                    List<HistoryListData> datas = new ArrayList<HistoryListData>();
                    if (tData.size() != 0) {
                        datas.add(null);
                        datas.addAll(tData);
                    }
                    if (yData.size() !=0 ) {
                        datas.add(null);
                        datas.addAll(yData);
                    }
                    if (aData.size() != 0) {
                        datas.add(null);
                        datas.addAll(aData);
                    }
                    ppwList.setAdapter(ppwAdapter);
                    ppwAdapter.setDatas(datas);
                    Log.d("LaunchActivity", "线程启动获取成功");
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("LaunchActivity", "线程启动获取失败" +errorInfo );

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if ( null == ppwAdapter.getData(position)){
            return;
        }
        List<StudyHistoryVO> historyVOs = new ArrayList<>();
        ExampleBean exampleBean = new ExampleBean();

        historyVOs.add(ppwAdapter.getData(position).getUpLoadingData(this));
        exampleBean.setUrl(ppwAdapter.getData(position).getUri());

        HistoryClickManager.getHisInstance(this).setStudyHistoryVOList(historyVOs).sendHistory();
        Bundle bundle = new Bundle();
        bundle.putSerializable("exampleBeans", exampleBean);
        Log.d("ClassFragment", "exampleBean:" + exampleBean);
        switch (ppwAdapter.getData(position).getFile_type()) {
            case ClassContstant.MEADIA_TYPE:
                startActivity(MediaActivity.class, bundle);
                break;
            default:
                startActivity(PdfActivity.class, bundle);
                break;
        }

    }
    public boolean isPpwShowing() {
        return ppwShowing;
    }
}


