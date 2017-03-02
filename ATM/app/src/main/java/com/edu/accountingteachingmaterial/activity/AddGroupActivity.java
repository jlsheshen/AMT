package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.AddGroupAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;

/**
 * 添加小组界面
 * Created by Administrator on 2017/2/28.
 */

public class AddGroupActivity extends BaseActivity {
    private ListView listView;
    private AddGroupAdapter adapter;
    @Override
    public int setLayout() {
        return R.layout.activity_addgroup;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        listView = bindView(R.id.addgroup_lv);
    }

    @Override
    public void initData() {
        adapter = new AddGroupAdapter(this);
        listView.setAdapter(adapter);

    }
}
