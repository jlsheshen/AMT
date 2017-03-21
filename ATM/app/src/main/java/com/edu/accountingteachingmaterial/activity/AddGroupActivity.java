package com.edu.accountingteachingmaterial.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.AddGroupAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.bean.GroupsListBean;
import com.edu.accountingteachingmaterial.bean.TaskDetailBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.util.AddTasktManager;
import com.edu.accountingteachingmaterial.util.net.GroupAddOneManager;
import com.edu.accountingteachingmaterial.view.dialog.JoinGroupDialog;

import java.util.List;

/**
 * 添加小组界面
 * Created by Administrator on 2017/2/28.
 */

public class AddGroupActivity extends BaseActivity implements AdapterView.OnItemClickListener, GroupAddOneManager.AddGroupOneListener,  AddTasktManager.AddTaskListener, AddGroupAdapter.ItemFootViewClickListener, JoinGroupDialog.OnButtonClickListener, View.OnClickListener {
    private ListView listView;
    private AddGroupAdapter adapter;
    private TextView titleTv;
    ImageView backBtn;
    Bitmap bitmap = null;
    Drawable drawable = null;
    Html.ImageGetter imageGetter;
    int taskId;
    JoinGroupDialog dialog;


    @Override
    public int setLayout() {
        return R.layout.activity_addgroup;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        listView = bindView(R.id.addgroup_lv);
        titleTv = bindView(R.id.addgroup_tv);
        backBtn = bindView(R.id.aty_title_back_iv);
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();

        List<GroupsListBean> datas = (List<GroupsListBean>) bundle.getSerializable(ClassContstant.GROUPS);
        taskId = bundle.getInt(ClassContstant.ID);
        String title = bundle.getString(ClassContstant.TASK_TITLE);
        titleTv.setText(title);
        adapter = new AddGroupAdapter(this);
        adapter.setItemFootViewClickListener(this);
        listView.setOnItemClickListener(this);
        adapter.setDatas(datas);
        listView.setAdapter(adapter);
        backBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GroupAddOneManager.getSingleton(this).addGroupOne(this, adapter.getItemId(position));
    }
    @Override
    public void onItemFootClick(int pos) {
        dialog =  new JoinGroupDialog(this);
        dialog.setTitle("只有一次选择小组的机会,\n确定加入小组" + adapter.getData(pos).getTeam_name() + "吗?");
        dialog.setOnButtonClickListener(this,pos);
        dialog.show();
    }

    @Override
    public void onOkClick(int pos) {
        Toast.makeText(this, "点击事件添加", Toast.LENGTH_SHORT).show();
        GroupAddOneManager.getSingleton(this).addGroupOne(this, adapter.getItemId(pos));
    }

    @Override
    public void onCancelClick() {
        dialog.dismiss();
    }
    @Override
    public void onSuccess(String message) {
        AddTasktManager.getSingleton(this).getTaskData(this,taskId);
    }

    @Override
    public void goAddGroup(List<GroupsListBean> datas) {
        Toast.makeText(this, "服务器返回数据错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goTaskDetail(TaskDetailBean data) {
        Bundle bundle = new Bundle();
        bundle.putInt(ClassContstant.TASK_STATE,ClassContstant.STATE_RUNING);
        bundle.putInt(ClassContstant.ID,taskId);
        bundle.putSerializable(ClassContstant.TASK_DETAIL, data);
        startActivity(TaskDetailActivity.class,bundle);
        finish();
    }

    @Override
    public void onFailure(String message) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.aty_title_back_iv:
                finish();
                break;

        }
    }
}
