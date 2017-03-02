package com.edu.accountingteachingmaterial.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ClassCatalogActivity;
import com.edu.accountingteachingmaterial.adapter.ClassGvAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;

public class ClassFragment extends BaseFragment implements AdapterView.OnItemClickListener {
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
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ClassCatalogActivity.class);

    }

}

