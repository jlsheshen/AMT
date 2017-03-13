package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.AddGroupActivity;
import com.edu.accountingteachingmaterial.activity.TaskDetailActivity;
import com.edu.accountingteachingmaterial.adapter.GroupTaskAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.GroupTaskListBean;
import com.edu.accountingteachingmaterial.bean.GroupsListBean;
import com.edu.accountingteachingmaterial.bean.TaskDetailBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.util.AddTasktManager;
import com.edu.accountingteachingmaterial.util.GroupTaskListManager;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.view.RefreshListView;

import java.io.Serializable;
import java.util.List;

/**
 * 任务列表界面
 * Created by Administrator on 2017/2/27.
 */

public class GroupTaskFragment extends BaseFragment implements RefreshListView.OnListMoveListener, AdapterView.OnItemClickListener, GroupTaskListManager.GroupTaskListListener, AddTasktManager.AddTaskListener {
    private RefreshListView listView;
    private GroupTaskAdapter groupTaskAdapter;
    private Bundle bundle;
    /**
     * 下拉刷新完成
     */
    private final static int REFRESH_COMPLETE = 0;
    /**
     * 上拉加载完成
     */
    private final static int LOAD_COMPLETE = 1;
    /**
     * 判断刷新加载相应的动作
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    listView.setOnRefreshComplete();
                    groupTaskAdapter.notifyDataSetChanged();

                    break;
                case LOAD_COMPLETE:
                    listView.setOnLoadMoreComplete();
                    groupTaskAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }

        ;
    };
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
        String courseId = PreferenceHelper.getInstance(getContext()).getStringValue(PreferenceHelper.COURSE_ID);
        GroupTaskListManager.getSingleton(getContext()).setCourse(courseId,this);
        listView.setOnListMoveListener(this);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void refreshView() {
        Toast.makeText(context, "下拉刷新完成", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void loadMoreView() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mHandler.sendEmptyMessage(LOAD_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Toast.makeText(context, "上拉加载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AddTasktManager.getSingleton(getContext()).getTaskData(this,groupTaskAdapter.getItemData(position-1).getId());
        if (bundle == null){
            bundle = new Bundle();
        }else {
            bundle.clear();
        }
        bundle.putInt(ClassContstant.TASK_STATE,groupTaskAdapter.getItemData(position-1).getStatus());
        bundle.putString(ClassContstant.TASK_TITLE,groupTaskAdapter.getItemData(position-1).getContent());
    }

    /**
     * 获取列表成功
     * @param taskList
     */
    @Override
    public void onSuccess(GroupTaskListBean taskList) {
        groupTaskAdapter.setDatas(taskList.getList());
        listView.setAdapter(groupTaskAdapter);
    }

    /**
     * 进入添加小组界面
     * @param datas
     */
    @Override
    public void goAddGroup(List<GroupsListBean> datas) {
        bundle.putSerializable(ClassContstant.GROUPS, (Serializable) datas);
        startActivity(AddGroupActivity.class,bundle);
    }

    /**
     * 进入任务详情
     * @param data
     */
    @Override
    public void goTaskDetail(TaskDetailBean data) {
        bundle.putSerializable(ClassContstant.TASK_DETAIL,data);
        startActivity(TaskDetailActivity.class,bundle);
    }

    @Override
    public void onFailure(String message) {
        Log.d("GroupTaskFragment", message);
    }
}
