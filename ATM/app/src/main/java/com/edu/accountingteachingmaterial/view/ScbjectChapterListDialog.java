package com.edu.accountingteachingmaterial.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.ClassChapterDialogAdapter;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */

public class ScbjectChapterListDialog extends Dialog {
    ExpandableListView expandableListView;
    List<ClassChapterData> datas;
    ClassChapterDialogAdapter chapterExLvAdapter;
    private Context mContext;

    public ScbjectChapterListDialog(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_chapter_list);
        // 窗口全屏显示
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置窗口弹出动画
        getWindow().setWindowAnimations(com.edu.R.style.TranAnimation);
        // 设置对话框的位置
        getWindow().setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        expandableListView = (ExpandableListView) this.findViewById(R.id.class_classchapter_exlv);
        chapterExLvAdapter = new ClassChapterDialogAdapter(mContext);
        uploadChapter();
        expandableListView.setAdapter(chapterExLvAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String id1 = String.valueOf(datas.get(groupPosition).getSubChapters().get(childPosition).getId());
                Log.e("www", id1);
//                startActivity(ClassDetailActivity.class);
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

    private void uploadChapter() {
        int courseId = PreferenceHelper.getInstance(this.getContext()).getIntValue(PreferenceHelper.COURSE_ID);
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();

        NetSendCodeEntity entity = new NetSendCodeEntity(this.getContext(), RequestMethod.POST, NetUrlContstant.chapterUrl + courseId);
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
                ToastUtil.showToast(mContext, errorInfo + "请联网哟");
            }
        });
    }
}
