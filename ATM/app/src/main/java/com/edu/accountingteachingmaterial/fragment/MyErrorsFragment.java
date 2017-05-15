package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.ErrorAdapter;
import com.edu.accountingteachingmaterial.adapter.ReviewHisAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.dao.ErrorTestDataDao;
import com.edu.accountingteachingmaterial.newsubject.ErrorPracticeActivity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.view.dialog.DeteleDialog;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;

import java.io.Serializable;
import java.util.List;

import static com.edu.accountingteachingmaterial.fragment.ClassExerciseFragment.ERRORS_DATAS;
import static com.edu.accountingteachingmaterial.fragment.ClassExerciseFragment.ERRORS_ITEM;

/**
 * Created by Administrator on 2016/12/26.
 */

public class MyErrorsFragment extends BaseFragment implements View.OnClickListener {
    ListView listView;
    ErrorAdapter adapter;
    List<BaseTestData> datas;
    TextView compileTv, deteleTv, allCheckTv;
    ViewGroup layout, noErrorsLayout;
    DeteleDialog deteleDialog;
    List<Boolean> checkList;
    boolean layoutShow = false;


    @Override
    protected int initLayout() {
        return R.layout.fragment_my_errors;
    }

    @Override
    protected void initView(View view) {
        listView = bindView(R.id.error_lv);
        compileTv = bindView(R.id.error_compile_tv);
        compileTv.setOnClickListener(this);
        deteleTv = bindView(R.id.bolw_bar_delete_tv);
        deteleTv.setOnClickListener(this);
        allCheckTv = bindView(R.id.blow_bar_allchecked_tv);
        allCheckTv.setOnClickListener(this);
        layout = bindView(R.id.bolw_bar);
        noErrorsLayout = bindView(R.id.my_error_view);

    }

    @Override
    protected void initData() {
        adapter = new ErrorAdapter(context);
        String userId = PreferenceHelper.getInstance(context).getStringValue(PreferenceHelper.USER_ID);
        datas =  ErrorTestDataDao.getInstance(context).getErrors(TestMode.MODE_PRACTICE, userId);
        if (shouBackground()) {

        } else {
            adapter.setDatas(datas);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle b = new Bundle();
                    b.putInt(ERRORS_ITEM, position);
                    String s = JSON.toJSONString(datas.get(position));
                    Log.d("MyErrorsFragment", s);
                    b.putSerializable(ERRORS_DATAS, (Serializable) datas);
                    startActivity(ErrorPracticeActivity.class, b);
                }
            });

            adapter.setChecked(new ReviewHisAdapter.OnCheckedListener() {
                @Override
                public void onCheckeBoxChecked() {
                    deteleState();
                }
            });
        }
    }

    void deteleState() {
        int i = 0;
        try {
            for (Boolean aBoolean : checkList) {
                if (aBoolean) {
                    i++;
                }
            }
        } catch (NullPointerException e) {
        }
        if (i > 0) {
            deteleTv.setText("删除(" + i + ")");
            deteleTv.setAlpha(1);
            deteleTv.setClickable(true);
        } else {
            deteleTv.setText("删除");
            deteleTv.setAlpha(0.5f);
            deteleTv.setClickable(false);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        dismissLayout();

    }

    public void setData() {
        if (context == null || adapter == null) {
            return;
        } else {
            String userId = PreferenceHelper.getInstance(context).getStringValue(PreferenceHelper.USER_ID);
            datas = (List<BaseTestData>) ErrorTestDataDao.getInstance(context).getErrors(TestMode.MODE_PRACTICE, userId);
            if (datas.size() > 0) {
                compileTv.setVisibility(View.VISIBLE);
            } else {
                compileTv.setVisibility(View.GONE);
            }
            adapter.setDatas(datas);
        }
    }

    private void showDeteleDialog() {
        deteleDialog = new DeteleDialog(context);
        if (!deteleDialog.isShowing()) {
            deteleDialog.show();
        }
        deteleDialog.setDialogListener(new DeteleDialog.SetDialogListener() {
            @Override
            public void onOkClicked() {
                deteleData();
            }

            @Override
            public void onCancelClicked() {
                if (deteleDialog.isShowing()) {
                    deteleDialog.dismiss();
                }
            }
        });

    }

    void deteleData() {
        for (int i = 0; i < checkList.size(); i++) {
            if (checkList.get(i)) {
//                ReviewExamListDao.getInstance(context).deleteData(datas.get(i).getId());
//                SubjectTestDataDao.getInstance(context).deleteData(datas.get(i).getId());
                ErrorTestDataDao.getInstance(context).deleteData(datas.get(i).getId());
                checkList.remove(i);
                datas.remove(i);
                i--;
            }
        }
        dismissLayout();
        adapter.setDatas(datas);
    }

    void dismissLayout() {
        if (shouBackground()) {
            layout.setVisibility(View.GONE);
            compileTv.setVisibility(View.GONE);
            layoutShow = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_compile_tv:
                if (!layoutShow) {
                    compileTv.setText("取消");
                    deteleState();
                    adapter.setClickShow();
                    checkList = adapter.getIsChecked();
                    layout.setVisibility(View.VISIBLE);
                    layoutShow = true;
                } else {
                    compileTv.setText("编辑");

                    adapter.setClickConceal();
                    layout.setVisibility(View.GONE);
                    layoutShow = false;

                }
                break;
            case R.id.bolw_bar_delete_tv:
                showDeteleDialog();

                break;
            case R.id.blow_bar_allchecked_tv:
                adapter.setAllchecked();
                break;
        }
    }

    boolean shouBackground() {
        if (datas == null || datas.size() < 1) {
            listView.setVisibility(View.GONE);
            noErrorsLayout.setVisibility(View.VISIBLE);
        } else {
            noErrorsLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        return datas == null || datas.size() < 1;
    }
}
