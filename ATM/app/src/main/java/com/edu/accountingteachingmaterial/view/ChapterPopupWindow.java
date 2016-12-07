package com.edu.accountingteachingmaterial.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ClassDetailActivity;
import com.edu.accountingteachingmaterial.adapter.ClassChapterDialogAdapter;
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public class ChapterPopupWindow extends PopupWindow {

    ExpandableListView expandableListView;
    List<ClassChapterData> datas;
    ClassChapterDialogAdapter chapterExLvAdapter;
    private Context mContext;
    private View conentView;

    public ChapterPopupWindow(Context context) {
        super(context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.dialog_chapter_list, null);
        // 设置SPopupWindow的View
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        this.setContentView(conentView);
        // 设置PopupWindow弹出窗体的宽
        this.setWidth(windowManager.getDefaultDisplay().getWidth() / 3);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(windowManager.getDefaultDisplay().getHeight() - 65);
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDism
        // isslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview);
        expandableListView = (ExpandableListView) conentView.findViewById(R.id.class_classchapter_exlv);
        chapterExLvAdapter = new ClassChapterDialogAdapter(mContext);
        uploadChapter();
        expandableListView.setAdapter(chapterExLvAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(mContext, ClassDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("classData", datas.get(groupPosition).getSubChapters().get(childPosition));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ChapterPopupWindow.this.dismiss();
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
    }

    /**
     * 显示popupWindow
     * .     *
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
//            this.showAsDropDown(parent, 40, 10);
             this.showAtLocation(parent, Gravity.RIGHT|Gravity.BOTTOM, 15, 0);
        } else {
            this.dismiss();
        }
    }

    private void uploadChapter() {
        int courseId = PreferenceHelper.getInstance(mContext).getIntValue(PreferenceHelper.COURSE_ID);
        Log.d("ChapterPopupWindow", "courseId" + "courseId" + courseId);
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();

        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, NetUrlContstant.chapterUrl + courseId);
        sendJsonNetReqManager.sendRequest(entity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    datas = JSON.parseArray(jsonObject.getString("message"), ClassChapterData.class);
                    Log.d("ChapterPopupWindow", "uploadChapter" + "success" + datas);
                    chapterExLvAdapter.setDatas(datas);
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                ToastUtil.showToast(mContext, errorInfo + "请联网哟");
            }
        });
    }

}
