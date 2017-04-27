package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.ClassChapterLvAdapter;
import com.edu.accountingteachingmaterial.adapter.HistoryPpwAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.bean.ClassInfoBean;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.BASE_URL;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.HistoryListData;
import com.edu.accountingteachingmaterial.entity.StudyHistoryVO;
import com.edu.accountingteachingmaterial.entity.SubChaptersBean;
import com.edu.accountingteachingmaterial.util.ClassInfoManager;
import com.edu.accountingteachingmaterial.util.HistoryClickManager;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.SendJsonNetReqManager;
import com.edu.accountingteachingmaterial.view.CircleImageView;
import com.edu.library.imageloader.EduImageLoader;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 课堂目录界面
 * Created by Administrator on 2017/3/2.
 */

public class ClassCatalogActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, ClassInfoManager.ClassInfoListener {

//    ExpandableListView expandableListView;
    ListView listView;
    List<SubChaptersBean> datas;
//    ClassChapterExLvAdapter chapterExLvAdapter;
    ClassChapterLvAdapter classChapterLvAdapter;
    HistoryPpwAdapter ppwAdapter;
    PopupWindow popupWindow;
    ListView ppwList;
    boolean ppwShowing = false;
    ImageView imgHistory,infoClassHead;//历史按钮,教材封面
    CircleImageView infoTextbookHead;
    TextView infoTextbookName, infoTextbookAuthor,infoTextbookOrganization,infotextbookContext,titleTv;//教材名称,作者,内容//
    RelativeLayout bookRl,classRl;
    ImageView backBtn;
    //当前是否是教材入口
    private boolean isBook = PreferenceHelper.getInstance(BaseApplication.getContext()).getBooleanValue(PreferenceHelper.IS_TEXKBOOK);

    @Override
    public int setLayout() {
        return R.layout.activity_class_catalog;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        listView = bindView(R.id.class_classchapter_lv);
        imgHistory = (ImageView) bindView(R.id.main_study_history_iv);
        imgHistory.setOnClickListener(this);
        if (isBook) {
            imgHistory.setVisibility(View.GONE);
        }
        setInfoView();
//        typeShow();
//        if (isBook){
//            imgHistory.setVisibility(View.GONE);
//        }
    }

    private void typeShow() {
        if (isBook){
            titleTv.setText("教材");
        }
        else {
            titleTv.setText("课堂");
        }
    }

    private void setInfoView() {
        infoTextbookHead = bindView(R.id.catalog_textbook_bg_iv);
        infoTextbookAuthor = bindView(R.id.catalog_textbook_author_tv);
        infoTextbookName = bindView(R.id.catalog_textbook_name_tv);
        infotextbookContext = bindView(R.id.catalog_textbook_content_tv);
        infoTextbookOrganization = bindView(R.id.catalog_textbook_organization_tv);
        backBtn = bindView(R.id.class_aty_back_iv);
        titleTv = bindView(R.id.aty_title_tv);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        classChapterLvAdapter = new ClassChapterLvAdapter(this);

        String courseId = PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.COURSE_ID);
        ClassInfoManager.getSingleton(this).getClassInfo(courseId,this);

        uploadChapter();

        listView.setAdapter(classChapterLvAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("classData", datas.get(position));
                bundle.putInt("ChapterId", datas.get(position).getId());
                startActivity(ClassDetailActivity.class, bundle);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isBook){
            ppwAdapter = new HistoryPpwAdapter(this);

            showPopupWindow(listView);}
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!isBook){
            showPopupWindow(listView);}
    }

    private void uploadChapter() {
        String courseId = PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.COURSE_ID);
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        Log.d("ClassFragment", NetUrlContstant.getChapterUrl() + courseId);
        String url = null;
        if (isBook){
            url =  NetUrlContstant.getChapterUrl() + courseId + "-" + ClassContstant.TEXT_BOOK_TYPE;
        }else {
            String classId = PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.CLASS_ID);
             url =  NetUrlContstant.getChapterUrl() + courseId + "-" + classId;
        }
        NetSendCodeEntity entity = new NetSendCodeEntity(this, RequestMethod.POST,url);
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    try {
                        List<SubChaptersBean> tempDatas = JSON.parseArray(jsonObject.getString("message"), SubChaptersBean.class);
                        Log.d("UnitTestActivity", "uploadChapter" + "success" + jsonObject.getString("message"));
                        boolean isBook = PreferenceHelper.getInstance(ClassCatalogActivity.this).getBooleanValue(PreferenceHelper.IS_TEXKBOOK);
                        if (isBook){
                            datas = tempDatas.get(0).getSubChapters();
                        }else {
                            datas = tempDatas;
                        }
                        classChapterLvAdapter.setDatas(datas);
                    }catch (Exception e){
                        Toast.makeText(ClassCatalogActivity.this, "当前无课时", Toast.LENGTH_SHORT).show();
                    }



                }
            }
            @Override
            public void onFailure(String errorInfo) {
                ToastUtil.showToast(ClassCatalogActivity.this, errorInfo);
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
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_study_history_iv:
                showPpw();
                break;
            case R.id.class_aty_back_iv:
                finish();
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
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ppwShowing = false;
            }
        });

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

                    tData = new ArrayList<>();
                    yData = new ArrayList<>();
                    aData = new ArrayList<>();
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
    public void onBackPressed() {
        if (ppwShowing ){
            popupWindow.dismiss();

        }else {
        super.onBackPressed();}
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
            case ClassContstant.PDF_TYPE:
                startActivity(PdfActivity.class, bundle);
                break;
            case ClassContstant.HTML_TYPE:
                startActivity(WebActivity.class, bundle);
                break;

        }

    }
    public boolean isPpwShowing() {
        return ppwShowing;
    }

    @Override
    public void onSuccess(ClassInfoBean data) {
        ImageLoader.getInstance().displayImage(BASE_URL.getBaseImageUrl() + data.getPicture() , infoTextbookHead, EduImageLoader.getInstance().getDefaultBuilder().build());
        infoTextbookAuthor.setText(data.getCreator());
        infoTextbookName.setText(data.getTitle());
        titleTv.setText(data.getTitle());
        infotextbookContext.setText(data.getSummary());
        infoTextbookOrganization.setText(data.getSchool());
    }

    @Override
    public void onFailure(String message) {

    }
}


