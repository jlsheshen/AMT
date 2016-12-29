package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.SubjectErrorActivity;
import com.edu.accountingteachingmaterial.adapter.ErrorAdapter;
import com.edu.accountingteachingmaterial.adapter.ReviewHisAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.dao.ErrorTestDataDao;
import com.edu.accountingteachingmaterial.view.DeteleDialog;
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
        noErrorsLayout = bindView(R.id.my_view);

    }

    @Override
    protected void initData() {
        adapter = new ErrorAdapter(context);
        datas = (List<BaseTestData>) ErrorTestDataDao.getInstance(context).getErrors(TestMode.MODE_PRACTICE);
        if (datas == null) {
            listView.setVisibility(View.GONE);
            noErrorsLayout.setVisibility(View.VISIBLE);
        } else {
            adapter.setDatas(datas);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle b = new Bundle();
                    b.putInt(ERRORS_ITEM, position);
                    b.putSerializable(ERRORS_DATAS, (Serializable) datas);
                    startActivity(SubjectErrorActivity.class, b);
                }
            });

            adapter.setChecked(new ReviewHisAdapter.OnCheckedListener() {
                @Override
                public void onCheckeBoxChecked() {
                    int i = 0;
                    for (Boolean aBoolean : checkList) {
                        if (aBoolean) {
                            i++;
                        }
                    }
                    if (i > 0) {
                        deteleTv.setText("删除(" + i + ")");
                        deteleTv.setAlpha(1);
                    } else {
                        deteleTv.setText("删除");
                        deteleTv.setAlpha(0.5f);
                    }

                }
            });
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
        adapter.setDatas(datas);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_compile_tv:
                if (!layoutShow) {
                    adapter.setClickShow();
                    checkList = adapter.getIsChecked();
                    layout.setVisibility(View.VISIBLE);
                    layoutShow = true;
                } else {
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

}
