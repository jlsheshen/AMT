package com.edu.accountingteachingmaterial.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.GroupsAdapter;
import com.edu.accountingteachingmaterial.adapter.RvMultiTypeAdapter;
import com.edu.accountingteachingmaterial.adapter.TaskContentAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.bean.GroupsListBean;
import com.edu.accountingteachingmaterial.bean.TaskDetailBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.AddTasktManager;
import com.edu.accountingteachingmaterial.util.FileUtil;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.DeteleAccessoryManager;
import com.edu.accountingteachingmaterial.util.net.UploadTaskTxetManager;
import com.edu.accountingteachingmaterial.view.NoRvScrollManager;
import com.edu.accountingteachingmaterial.view.dialog.JoinGroupDialog;
import com.edu.accountingteachingmaterial.view.dialog.SelectPictureDialog;
import com.edu.accountingteachingmaterial.view.dialog.TaskSubmitHistoryDialog;
import com.edu.library.util.ToastUtil;
import com.loopj.android.http.RequestParams;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.SimpleParamsReqEntity;
import com.lucher.net.req.impl.UploadNetReqManager;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static com.edu.accountingteachingmaterial.R.id.aty_title_back_iv;


/**
 * 任务详情
 * Created by Administrator on 2017/2/28.
 */

public class TaskDetailActivity extends BaseActivity implements RvMultiTypeAdapter.AccessoryListener, View.OnClickListener, UploadTaskTxetManager.SubmitTaskTextListener, DeteleAccessoryManager.DeteleAccessoryListener, AddTasktManager.AddTaskListener, SelectPictureDialog.OnButtonClickListener, AdapterView.OnItemClickListener {

    GridView groupsGv, taskContentGv;//任务图片表,组员列表
    RecyclerView accessoryRv;//上传图片列表
    EditText answerEt;//小组回答
    TextView groupNmaeTv, accessotyTv, teacherCommentTv, taskContentTv, answerManTv, groupNumberTv;//小组组名,附件tv,教师评价,任务内容最后一个提交人,小组人数
    RvMultiTypeAdapter accessotyAdapter;
    GroupsAdapter groupsAdapter;
    TaskContentAdapter taskContentAdapter;
    private int taskModel;
    private TaskDetailBean data;
    int taskId;
    ImageView historyIv, submitIv, backIv;//历史按钮,提交按钮
    SelectPictureDialog pictureDialog;//上传图片
    public static final int ALBUM = 111;//从相册取照片
    public static final int PHOTOGRAPH = 112;//照相
    String fileNmae;//照片路径
    TaskSubmitHistoryDialog historyDialog;//历史提交纪录dialog
    JoinGroupDialog dialog;


    @Override
    public int setLayout() {
        return R.layout.activity_taskdetail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        taskContentGv = bindView(R.id.task_content_gv);
        backIv = bindView(aty_title_back_iv);
        groupsGv = bindView(R.id.task_groups_gv);
        accessoryRv = bindView(R.id.task_accessory_rv);
        answerEt = bindView(R.id.task_groupanswer_et);
        groupNmaeTv = bindView(R.id.task_groupanswer_tv);
        answerManTv = bindView(R.id.task_answerman_tv);
        groupNumberTv = bindView(R.id.task_groups_tv);
        accessotyTv = bindView(R.id.task_accessory_tv);
        teacherCommentTv = bindView(R.id.task_teachersay_tv);
        historyIv = bindView(R.id.task_history_iv);
        submitIv = bindView(R.id.task_submit_iv);
        taskContentTv = bindView(R.id.task_content_tv);
        submitIv.setOnClickListener(this);
        historyIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        taskContentGv.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        taskModel = bundle.getInt(ClassContstant.TASK_STATE);
        Log.d("TaskDetailActivity", "taskModel:" + taskModel);

        taskId = bundle.getInt(ClassContstant.ID);
        data = (TaskDetailBean) bundle.getSerializable(ClassContstant.TASK_DETAIL);
        answerEt.setText(data.getAnswer());
        groupNmaeTv.setText(data.getStudentlist().get(0).getTeam_name() + "作答");
        groupNumberTv.setText("小组成员(" + data.getStudentlist().size() + "/" +data.getStu_count() + ")");
        taskContentAdapter = new TaskContentAdapter();

        if (data.getParas() != null) {
            taskContentTv.setText(data.getParas().getText());
            taskContentAdapter.setDatas(data.getParas().getImgSrc());
        }
        taskContentGv.setAdapter(taskContentAdapter);

        accessotyAdapter = new RvMultiTypeAdapter(this);
        refreshAccessory();
        accessotyAdapter.setAccessoryListener(this);
        accessoryRv.setAdapter(accessotyAdapter);
        accessotyAdapter.setRvModel(taskModel);
        accessoryRv.setLayoutManager(new NoRvScrollManager(this, 5));

        showAnswer();
        groupsAdapter = new GroupsAdapter();
        groupsAdapter.setDatas(data.getStudentlist());
        groupsGv.setAdapter(groupsAdapter);


    }

    void showAnswer() {
        if (taskModel == ClassContstant.STATE_AFTER) {
            answerEt.setSaveEnabled(false);
            answerEt.setFocusable(false);
            answerEt.setFocusableInTouchMode(false);
            teacherCommentTv.setVisibility(View.VISIBLE);
            teacherCommentTv.setText(data.getComment());
            findViewById(R.id.task_teachersay_title_tv).setVisibility(View.VISIBLE);
            submitIv.setVisibility(View.GONE);
        } else if (taskModel == ClassContstant.STATE_FINSH) {
            answerEt.setSaveEnabled(false);
            answerEt.setFocusable(false);
            answerEt.setFocusableInTouchMode(false);
            teacherCommentTv.setVisibility(View.VISIBLE);
            submitIv.setVisibility(View.GONE);
            teacherCommentTv.setText(data.getComment());
            findViewById(R.id.task_teachersay_title_tv).setVisibility(View.VISIBLE);
        } else {

        }
    }

    /**
     * 刷新后改变ui
     *
     * @return
     */
    int refreshAccessory() {
        int accessorys = data.getFilelist() == null ? 0 : data.getFilelist().size();
        accessotyTv.setText(accessorys + "/9 任务附件");
        if (accessorys < 9 && taskModel == ClassContstant.STATE_RUNING) {
            TaskDetailBean.FileListBean fileListBean = new TaskDetailBean.FileListBean();
            fileListBean.setFoot(true);
            data.getFilelist().add(fileListBean);
        }
        String s = JSONObject.toJSONString(data.getFilelist());
        Log.d("TaskDetailActivity", "-------------" + s);
        accessotyAdapter.setDatas(data.getFilelist());
        if (data.getHistory().size() > 0) {
            answerManTv.setText("最后提交:" + data.getHistory().get(data.getHistory().size() - 1).getName());
            answerManTv.setVisibility(View.VISIBLE);
        } else {
            answerManTv.setVisibility(View.GONE);
        }

        return accessorys;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ALBUM) {
                Uri uri = data.getData();
                Log.e("uri", uri.toString());
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    File file = FileUtil.getUriFile(this, data.getData());
                    sendBitmap(file);
//
//                /* 将Bitmap设定到ImageView */
//                showIv.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(), e);
                }
            } else {
                File file = new File(fileNmae);
                sendBitmap(file);

            }
        }
    }

    @Override
    protected void onDestroy() {
        if (pictureDialog != null && pictureDialog.isShowing()) {
            pictureDialog.cancel();
            pictureDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void addAccessoryListener() {
        pictureDialog = new SelectPictureDialog(this);
        pictureDialog.setOnButtonClickListener(this);
        pictureDialog.show();
    }

    @Override
    public void deteleAccessoryListener(int fileId) {
        DeteleAccessoryManager.getSingleton(this).deteleAccessory(this, fileId);
    }

    @Override
    public void showAccessoryImage(String url) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ClassContstant.DATA, url);
        startActivity(OneImageActivity.class, bundle);
    }

    private void sendBitmap(File file) {
        String studentId = PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.USER_ID);
        String teamId = String.valueOf(this.data.getTeam_id());
        try {

            final String contentType = RequestParams.APPLICATION_OCTET_STREAM;
            // 添加文件对象用于上传
            SimpleParamsReqEntity entity = new SimpleParamsReqEntity(this, RequestMethod.POST, NetUrlContstant.getUploadAccessory() + studentId + "-" + teamId + "-" + taskId);
            RequestParams params = new RequestParams();
            params.put("file", file, contentType);
            params.setHttpEntityIsRepeatable(true);
            params.setUseJsonStreamer(false);
            entity.setRequestParams(params);

            new UploadNetReqManager() {

                @Override
                public void onConnectionFailure(String errorInfo, Header[] headers) {
                    ToastUtil.showToast(mContext, "文件上传失败");
                }

                @Override
                public void onConnectionError(String errorInfo) {
                    ToastUtil.showToast(mContext, "文件上传出错");
                }

                @Override
                public void onConnectionSuccess(byte[] responseBody, Header[] headers) {
                    ToastUtil.showToast(mContext, "文件上传成功");
                    AddTasktManager.getSingleton(TaskDetailActivity.this).getTaskData(TaskDetailActivity.this, taskId);
                    pictureDialog.dismiss();
                }

                @Override
                public void onConnectionProgress(long bytesWritten, long totalSize) {
                    ToastUtil.showToast(mContext, "文件上传进度更新：" + bytesWritten + "/" + totalSize);
                }
            }
                    .sendRequest(entity, "文件上传中,请稍等...");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_history_iv:
                Log.d("TaskDetailActivity", "发生点击事件");

                historyDialog = new TaskSubmitHistoryDialog(this);
                int[] location = new int[2];
                historyIv.getLocationOnScreen(location);
                historyDialog.setDatas(data.getHistory());
                historyDialog.show(location[0], location[1]);

                break;
            case R.id.task_submit_iv:
                String string = answerEt.getText().toString();
                UploadTaskTxetManager.getSingleton(this).submitTaskText(this, data.getTeam_id(), taskId, string);
                break;
            case R.id.aty_title_back_iv:
                goBack();

                break;
        }
    }
    void goBack(){
        if (taskModel == 1){
        dialog = new JoinGroupDialog(this);
        dialog.setTitle("确定放弃修改并返回吗?");
        dialog.setOnButtonClickListener(new JoinGroupDialog.OnButtonClickListener() {
            @Override
            public void onOkClick(int pos) {
                finish();
            }

            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        });
        dialog.show();}else {
            finish();
        }

    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(this, "答案上传成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "删除附件成功", Toast.LENGTH_SHORT).show();
        AddTasktManager.getSingleton(this).getTaskData(this, taskId);

    }

    @Override
    public void goAddGroup(List<GroupsListBean> datas) {

    }

    @Override
    public void goTaskDetail(TaskDetailBean data) {
        this.data = data;
        refreshAccessory();
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onPhotograph() {
        fileNmae = Environment.getExternalStorageDirectory().getAbsolutePath() + "/filename.jpg";
        File temp = new File(fileNmae);
        Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
        startActivityForResult(intent, PHOTOGRAPH);
    }

    @Override
    public void onAlbum() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {//因为Android SDK在4.4版本后图片action变化了 所以在这里先判断一下
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, ALBUM);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.task_content_gv:
                Bundle bundle = new Bundle();
                bundle.putSerializable(ClassContstant.DATA, taskContentAdapter.getItem(position));
                startActivity(OneImageActivity.class, bundle);

                break;

        }
    }
    @SuppressLint("NewApi")
    private void requestReadExternalPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                // 0 是自己定义的请求coude
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        } else {
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
            default:
                break;

        }
    }
}
