package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.GroupsAdapter;
import com.edu.accountingteachingmaterial.adapter.RvMultiTypeAdapter;
import com.edu.accountingteachingmaterial.adapter.TaskContentAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.view.NoRvScrollManager;

/**
 * 任务详情
 * Created by Administrator on 2017/2/28.
 */

public class TaskDetailActivity extends BaseActivity {

    GridView taskContentGv, groupsGv;//任务图片表,组员列表
    RecyclerView accessoryRv;//上传图片列表
    RvMultiTypeAdapter accessotyAdapter;
    BaseAdapter taskContentAdapter, groupsAdapter;
    private int taskModel;

    @Override
    public int setLayout() {
        return R.layout.activity_taskdetail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//        Intent intent = new Intent();
//        Bundle bundle = intent.getBundleExtra("");
//        taskModel = bundle.getInt("");


        taskContentGv = bindView(R.id.task_content_gv);
        groupsGv = bindView(R.id.task_groups_gv);
        accessoryRv = bindView(R.id.task_accessory_rv);

    }

    @Override
    public void initData() {
        accessotyAdapter = new RvMultiTypeAdapter();
        accessoryRv.setAdapter(accessotyAdapter);
        accessotyAdapter.setRvModel(taskModel);
        accessoryRv.setLayoutManager(new NoRvScrollManager(this, 6));

        taskContentAdapter = new TaskContentAdapter();
        taskContentGv.setAdapter(taskContentAdapter);
        groupsAdapter = new GroupsAdapter();
        groupsGv.setAdapter(groupsAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
