package com.edu.accountingteachingmaterial.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.view.AddAndSubTestView;
import com.edu.library.util.ToastUtil;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ClassReviewFragment extends BaseFragment implements View.OnClickListener {
    // * 加载题型view
    LinearLayout layout1;
    LinearLayout layout2;
    // * 题目类型数据
    private String[] strStem1;
    private String[] strStem2;
    Button btnStart;
    CheckBox cbEasy, cbNormal, cbHard;

    @Override
    protected int initLayout() {
        return R.layout.fragment_classs_review;
    }

    @Override
    protected void initView(View view) {
        layout1 = bindView(R.id.linearLayout1);
        layout2 = bindView(R.id.linearLayout2);
        btnStart = (Button) view.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        strStem1 = context.getResources().getStringArray(R.array.question1);
        strStem2 = context.getResources().getStringArray(R.array.question2);

//        layout1.removeAllViews();
//        layout2.removeAllViews();
        AddAndSubTestView addAndSubTestView = null;
        for (int i = 1; i < strStem1.length; i++) {
            addAndSubTestView = new AddAndSubTestView(context, 20, strStem1[i]);
            layout1.addView(addAndSubTestView);
        }
        for (int j = 1; j < strStem2.length; j++) {
            addAndSubTestView = new AddAndSubTestView(context, 20, strStem2[j]);
            layout2.addView(addAndSubTestView);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                ToastUtil.showToast(context, "智能组卷");
                break;
        }
    }

}
