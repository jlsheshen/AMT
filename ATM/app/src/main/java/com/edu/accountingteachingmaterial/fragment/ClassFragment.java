package com.edu.accountingteachingmaterial.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ClassCatalogActivity;
import com.edu.accountingteachingmaterial.adapter.ClassGvAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.ClassBean;
import com.edu.accountingteachingmaterial.util.ClassListManager;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;

import java.util.List;

/**
 * 课堂fragment
 */
public class ClassFragment extends BaseFragment implements AdapterView.OnItemClickListener, ClassListManager.ClassListener {
    GridView gridView ;
    ClassGvAdapter adapter;
    @Override
    protected int initLayout() {
        return R.layout.fragment_class;
    }
    @Override
    protected void initView(View view) {
        gridView = bindView(R.id.class_gv);
    }

    @Override
    protected void initData() {
        adapter = new ClassGvAdapter();
        ClassListManager.getSingleton(getContext()).getClassList(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PreferenceHelper.getInstance(getContext()).setBooleanValue(PreferenceHelper.IS_TEXKBOOK,false);
        PreferenceHelper.getInstance(getContext()).setStringValue(PreferenceHelper.COURSE_ID,"" + adapter.getData(position).getId());
        startActivity(ClassCatalogActivity.class);
    }

    @Override
    public void onSuccess(List<ClassBean> books) {
        adapter.setDatas(books);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onFailure(String message) {
    }
}

