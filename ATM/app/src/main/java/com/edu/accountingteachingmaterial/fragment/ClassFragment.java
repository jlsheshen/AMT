package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ClassDetailActivity;
import com.edu.accountingteachingmaterial.adapter.ClassChapterExLvAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.accountingteachingmaterial.view.NoScrollListView;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import java.util.List;

public class ClassFragment extends BaseFragment implements View.OnClickListener {

    ExpandableListView expandableListView;
    List<ClassChapterData> datas;
    ClassChapterExLvAdapter chapterExLvAdapter;
    PopupWindow popupWindow;
    NoScrollListView todayLv,yesterdayLv,agoLv;
    TextView todayTv,yesterdayTv,agoTv;



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
        todayLv = bindView(R.id.ppw_histort_today_lv);
        todayTv = bindView(R.id.ppw_histort_today_tv);
        yesterdayLv = bindView(R.id.ppw_histort_yesterday_lv);
        yesterdayTv = bindView(R.id.ppw_histort_yesterday_tv);
        agoLv = bindView(R.id.ppw_histort_ago_lv);
        agoTv = bindView(R.id.ppw_histort_ago_tv);


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
    }

    //	private void loadData() {
//		uploadChapter();
//		datas = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			ClassChapterBean chapterBean = new ClassChapterBean();
//			List<NodeBean> nodes = new ArrayList<>();
//			chapterBean.setChapterId(i);
//			chapterBean.setChapterNum("第" + i + "章");
//			chapterBean.setTitle("章节标题" + i);
//			for (int j = 0; j < 10; j++) {
//				NodeBean node = new NodeBean();
//				node.setNodeNum("第" + j + "节");
//				node.setNodeId(i + "--" + j);
//				node.setTitle("小节编号" + node.getNodeId());
//				nodes.add(node);
//
//			}
//			chapterBean.setNodes(nodes);
//			datas.add(chapterBean);
//
//		}
//
//	}
    private void uploadChapter() {
        int courseId = PreferenceHelper.getInstance(context).getIntValue(PreferenceHelper.COURSE_ID);
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.chapterUrl + courseId);
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

                if (popupWindow.isShowing()) {
//                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(imgHistory,10,0);
//                     expandableListView.setEnabled(false);
//                    expandableListView.setAlpha(0.5f);
                }
                break;
        }

    }
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.ppw_history, null);
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        getWindow().setAttributes(lp);
//         popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT,view.getLayoutParams().height);

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
}


