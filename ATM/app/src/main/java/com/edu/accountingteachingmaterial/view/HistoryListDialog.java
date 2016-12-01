package com.edu.accountingteachingmaterial.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.entity.HistoryData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */

public class HistoryListDialog extends Dialog {
    private Context mContext;
    private List<HistoryData> historyData;

    public HistoryListDialog(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_history_list);
        // 窗口全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置窗口弹出动画
        getWindow().setWindowAnimations(com.edu.R.style.TranAnimation);
        // 设置对话框的位置
        getWindow().setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
        RelativeLayout rlyoutLayout = (RelativeLayout) findViewById(R.id.rlyout_content);
        historyData = new ArrayList<HistoryData>();
        for (int i = 0; i < 5; i++) {
            HistoryData data = new HistoryData();
            data.setChapterName("测试" + i);
            if (i == 0 || i == 3) {
                data.setData("2016-11-28");
            } else {
                data.setData("2016-11-30");
            }

            historyData.add(data);
        }
        HistoryListView historyListView = new HistoryListView(mContext, historyData);
        rlyoutLayout.addView(historyListView.init());
    }


}
