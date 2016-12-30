package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ClassDetailActivity;
import com.edu.accountingteachingmaterial.activity.MediaActivity;
import com.edu.accountingteachingmaterial.activity.PdfActivity;
import com.edu.accountingteachingmaterial.adapter.ClassChapterExLvAdapter;
import com.edu.accountingteachingmaterial.adapter.HistoryPpwAdapter;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.base.BaseFragment;
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

public class ClassFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

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


    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_class;
    }

    ImageView imgHistory;

    @Override
    protected void initView(View view) {
        expandableListView = (ExpandableListView) bindView(R.id.class_classchapter_exlv);
        imgHistory = (ImageView) bindView(R.id.main_study_history_iv);
        imgHistory.setOnClickListener(this);


    }

    @Override
    protected void initData() {

//		loadData();
        chapterExLvAdapter = new ClassChapterExLvAdapter(context);
        uploadChapter();
        expandableListView.setAdapter(chapterExLvAdapter);

        expandableListView.setOnChildClickListener(new OnChildClickListener() {
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
        expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {

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
        ppwAdapter = new HistoryPpwAdapter(context);
//        todayAdapter = new HistoryPpwAdapter(context);
//        yestodayAdapter = new HistoryPpwAdapter(context);
//        agoAdapter = new HistoryPpwAdapter(context);
    }

    private void uploadChapter() {
        int courseId = PreferenceHelper.getInstance(context).getIntValue(PreferenceHelper.COURSE_ID);
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        Log.d("ClassFragment", NetUrlContstant.getChapterUrl() + courseId);
        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.getChapterUrl() + courseId);
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    datas = JSON.parseArray(jsonObject.getString("message"), ClassChapterData.class);
                    Log.d("UnitTestActivity", "uploadChapter" + "success" + datas);
                    chapterExLvAdapter.setDatas(datas);
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                ToastUtil.showToast(context, errorInfo + "请联网哟");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_study_history_iv:

                if (ppwShowing) {
                    popupWindow.dismiss();
                    ppwShowing = false;
                } else {
                    popupWindow.showAsDropDown(imgHistory, 50, 50);
                    ppwShowing = true;
//                     expandableListView.setEnabled(false);
//                    expandableListView.setAlpha(0.5f);
                }
                break;
        }
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.ppw_his, null);
        ppwList = (ListView) contentView.findViewById(R.id.ppw_history_lv);
        ppwList.setOnItemClickListener(this);

//        todayLv = (NoScrollListView) contentView.findViewById(R.id.ppw_histort_today_lv);
//        todayTv = (TextView) contentView.findViewById(R.id.ppw_histort_today_tv);
//        yesterdayLv = (NoScrollListView) contentView.findViewById(R.id.ppw_histort_yesterday_lv);
//        yesterdayTv = (TextView) contentView.findViewById(R.id.ppw_histort_yesterday_tv);
//        agoLv = (NoScrollListView) contentView.findViewById(R.id.ppw_histort_ago_lv);
//        agoTv = (TextView) contentView.findViewById(R.id.ppw_histort_ago_tv);
//        todayLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                itemClick(tData,position,todayLv,todayAdapter);
//            }
//
//        });
//        yesterdayLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                itemClick(yData,position,yesterdayLv,yestodayAdapter);
//            }
//        });
//        agoLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                itemClick(aData,position,agoLv,agoAdapter);
//            }
//        });
        loadHistoryDatas();
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        getWindow().setAttributes(lp);
//         popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

        historyVOs.add(data.get(i).getUpLoadingData(context));
        exampleBean.setUrl(data.get(i).getUri());

        HistoryClickManager.getHisInstance(context).setStudyHistoryVOList(historyVOs).sendHistory();
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

        Log.d("LaunchActivity", NetUrlContstant.getFindHisUrl() + PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(PreferenceHelper.USER_ID));
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.getFindHisUrl() + PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(PreferenceHelper.USER_ID));
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
//                    if (tData != null && tData.size() > 0) {
//                        todayLv.setAdapter(todayAdapter);
//                        todayAdapter.setDatas(tData);
//                    }
//                    if (yData != null && yData.size() > 0) {
//                        yesterdayLv.setAdapter(yestodayAdapter);
//                        yestodayAdapter.setDatas(yData);
//                    }
//                    if (aData != null && aData.size() > 0) {
//                        agoLv.setAdapter(agoAdapter);
//                        agoAdapter.setDatas(aData);
//                    }
                    Log.d("LaunchActivity", "线程启动获取成功");
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("LaunchActivity", "线程启动获取失败");

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

        historyVOs.add(ppwAdapter.getData(position).getUpLoadingData(context));
        exampleBean.setUrl(ppwAdapter.getData(position).getUri());

        HistoryClickManager.getHisInstance(context).setStudyHistoryVOList(historyVOs).sendHistory();
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

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        List<StudyHistoryVO> historyVOs = new ArrayList<>();
//        ExampleBean exampleBean = new ExampleBean();
//
//        switch (view.getId()) {
//            case R.id.ppw_histort_today_lv:
//                historyVOs.add(tData.get(i).getUpLoadingData(context));
//                exampleBean.setUrl(tData.get(i).getUri());
//
//            case R.id.ppw_histort_yesterday_lv:
//                historyVOs.add(yData.get(i).getUpLoadingData(context));
//                exampleBean.setUrl(yData.get(i).getUri());
//
//            case R.id.ppw_histort_ago_lv:
//                historyVOs.add(aData.get(i).getUpLoadingData(context));
//                exampleBean.setUrl(aData.get(i).getUri());
//                break;
//        }
//        HistoryClickManager.getHisInstance(context).setStudyHistoryVOList(historyVOs).sendHistory();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("exampleBeans", exampleBean);
//        Log.d("ClassFragment", "exampleBean:" + exampleBean);
//        switch (aData.get(i).getFile_type()){
//            case ClassContstant.MEADIA_TYPE:
//                startActivity(MediaActivity.class, bundle);
//
//                break;
//            default:
//                startActivity(PdfActivity.class, bundle);
//                break;
//
//        }
//
//    }
}


