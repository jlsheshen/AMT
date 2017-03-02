package com.edu.accountingteachingmaterial.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.AddGroupActivity;
import com.edu.accountingteachingmaterial.adapter.GroupTaskAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.view.RefreshListView;

/**
 * Created by Administrator on 2017/2/27.
 */

public class GroupTaskFragment extends BaseFragment implements RefreshListView.OnListMoveListener, AdapterView.OnItemClickListener {
    private RefreshListView listView;
    private GroupTaskAdapter groupTaskAdapter;
    @Override
    protected int initLayout() {
        return R.layout.fragment_grouptask;
    }

    @Override
    protected void initView(View view) {
        listView = bindView(R.id.grouptask_lv);
    }

    @Override
    protected void initData() {
        groupTaskAdapter = new GroupTaskAdapter(getContext());
        listView.setAdapter(groupTaskAdapter);
        listView.setOnListMoveListener(this);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void refreshView() {
        Toast.makeText(context, "下拉刷新完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMoreView() {
        Toast.makeText(context, "上拉加载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // groupTaskAdapter.getItemData(position);
        switch (2){
            case ClassContstant.STATE_RUNING:
                startActivity(AddGroupActivity.class);
            break;

            case ClassContstant.STATE_AFTER:

                break;
            case ClassContstant.STATE_FINSH:

                break;
        }
    }
}
