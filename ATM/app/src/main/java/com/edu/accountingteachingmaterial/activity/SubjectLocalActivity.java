package com.edu.accountingteachingmaterial.activity;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.SubjectViewPagerAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ExamListDao;
import com.edu.accountingteachingmaterial.dao.ExamOnLineListDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.accountingteachingmaterial.model.ResultsListener;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.UploadOnlineResultsManager;
import com.edu.accountingteachingmaterial.view.UnTouchableViewPager;
import com.edu.accountingteachingmaterial.view.dialog.ExitDialog;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.common.SubjectCardAdapter;
import com.edu.subject.common.SubjectCardDialog;
import com.edu.subject.dao.SignDataDao;
import com.edu.subject.data.BaseSubjectData;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.SignData;
import com.edu.testbill.Constant;
import com.edu.testbill.dialog.SignChooseDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;


/**
 * 页面内无重做功能
 * 练习页面的预习与课后调用此Activity
 * Created by Administrator on 2016/11/18.
 */

public class SubjectLocalActivity extends BaseActivity implements AdapterView.OnItemClickListener, SubjectListener, SubjectCardAdapter.OnCardItemClickListener, ResultsListener {

    // 显示题目的viewpager控件
    private UnTouchableViewPager viewPager;
    private SubjectViewPagerAdapter mSubjectAdapter;
    int dataId;

    private int mCurrentIndex;
    int examId;
    // 印章选择对话框
    private SignChooseDialog signDialog;
    // 印章，闪电符按钮
    private ImageView btnSign, btnFlash, backIv;

    // 答题卡对话框
    private SubjectCardDialog mCardDialog;
    List<BaseTestData> datas;
    //c测试数据
    ExamListData examListData;
    ExitDialog exitDialog;// 退出提示框
    // 页面相关状态的监听
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        // 页面切换后调用
        @Override
        public void onPageSelected(int item) {
            mCurrentIndex = item;
            refreshToolBar();
            viewPager.setType(datas.get(item).getSubjectType());
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int item) {
        }
    };

    @Override
    public int setLayout() {
        return R.layout.activity_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        List<SignData> signs = (List<SignData>) SignDataDao.getInstance(this, Constant.DATABASE_NAME).getAllDatas();
        signDialog = new SignChooseDialog(this, signs, this);
        viewPager = (UnTouchableViewPager) findViewById(R.id.vp_content);
        viewPager.setOnPageChangeListener(mPageChangeListener);
        btnSign = (ImageView) findViewById(R.id.btnSign);
        btnFlash = (ImageView) findViewById(R.id.btnFlash);
        backIv = (ImageView) findViewById(R.id.class_aty_back_iv);
        Bundle bundle = getIntent().getExtras();
        examId = bundle.getInt("examId");

//        examListData = (ExamListData) bundle.get("ExamListData");

        datas = SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_PRACTICE, examId);
        if (datas == null || datas.size() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ExamListDao.ID, examListData.getId());
            contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_NOT);
            contentValues.put(ExamListDao.TYPE, examListData.getExam_type());
            contentValues.put(ExamListDao.CHAPTER_ID, examListData.getChapter_id());
            ExamListDao.getInstance(this).updateData(String.valueOf(examListData.getId()), contentValues);
            Toast.makeText(this, "需要重新下载", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String s = JSONObject.toJSONString(datas);
        Log.d("SubjectTestActivity", s);

        mSubjectAdapter = new SubjectViewPagerAdapter(getSupportFragmentManager(), datas, this, this);
        mSubjectAdapter.setTestMode(ClassContstant.TEST_MODE_NORMAL);

        viewPager.setAdapter(mSubjectAdapter);
        mCardDialog = new SubjectCardDialog(this, datas, this, mSubjectAdapter.getDatas().get(mCurrentIndex).getId());
    }

    @Override
    public void initData() {

    }

    /**
     * 刷新工具栏状态
     */
    private void refreshToolBar() {
        if (mCurrentIndex < 0 || mCurrentIndex > mSubjectAdapter.getCount() - 1)
            return;
        BaseSubjectData subject = mSubjectAdapter.getData(mCurrentIndex).getSubjectData();

//         刷新题目数据

        if (subject.getSubjectType() == SubjectType.SUBJECT_BILL) {
            btnSign.setVisibility(View.VISIBLE);
        } else {
            btnSign.setVisibility(View.GONE);
        }
    }

    public void onClick(View view) throws IOException {
        switch (view.getId()) {
            case R.id.btnSign:
                sign();
                break;

            case R.id.btnFlash:
                mSubjectAdapter.showFlash(mCurrentIndex);
                break;

            case R.id.btnDone:
//                float score = mSubjectAdapter.submit();

//                ToastUtil.showToast(this, "score:" + score);
                float score = mSubjectAdapter.submit();
                UploadOnlineResultsManager.getSingleton(this).setResultsListener(this);
                UploadOnlineResultsManager.getSingleton(this).setResults(mSubjectAdapter.getDatas());
                int userId = Integer.parseInt(PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.USER_ID));
                int cost = 0;

                UploadOnlineResultsManager.getSingleton(this).uploadResult(userId, examId, cost);
                break;

            case R.id.btnCard:
                if (!mCardDialog.isShowing()) {
                    mCardDialog.show(mSubjectAdapter.getData(mCurrentIndex).getId());
                }
                break;

            case R.id.btnLeft:
                scrollToLeft();
                break;

            case R.id.btnRight:
                scrollToRight();
                break;
            case R.id.class_aty_back_iv:
                finish();
                break;
            default:
                break;
        }
    }


    /**
     * 显示印章选择对话框
     */
    private void sign() {
        if (!signDialog.isShowing()) {
            signDialog.show();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        refreshToolBar();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        signDialog.dismiss();
        mSubjectAdapter.sign(mCurrentIndex, (SignData) view.getTag());
    }

    /**
     * 滚动到左边页面
     */
    private void scrollToLeft() {
        if (mCurrentIndex != 0) {
            mSubjectAdapter.saveAnswer(mCurrentIndex);
            mCurrentIndex--;
            viewPager.setCurrentItem(mCurrentIndex, true);
        } else {
            ToastUtil.showToast(this, "已经是第一页");
        }
    }

    /**
     * 滚动到右边页面
     */
    private void scrollToRight() {
        if (mCurrentIndex != mSubjectAdapter.getCount() - 1) {
            mSubjectAdapter.saveAnswer(mCurrentIndex);
            mCurrentIndex++;
            viewPager.setCurrentItem(mCurrentIndex, true);
        } else {
            ToastUtil.showToast(this, "已经是最后一页");
        }
    }

    @Override
    public void onComplete() {
        scrollToRight();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_PAGE_UP) {
            scrollToLeft();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN) {
            scrollToRight();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public int onItemClicked(BaseTestData data) {
        mSubjectAdapter.saveAnswer(mCurrentIndex);
        mCardDialog.dismiss();
        int index = mSubjectAdapter.getDatas().indexOf(data);
        viewPager.setCurrentItem(index);
        return mSubjectAdapter.getDatas().get(index).getId();
    }

    @Override
    public void onRedoClicked() {
        mCardDialog.dismiss();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDone(String message) {
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        mSubjectAdapter.saveAnswer(mCurrentIndex);
        super.onBackPressed();
    }

    @Override
    public void onSuccess() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExamListDao.STATE, ClassContstant.EXAM_COMMIT);
        ExamOnLineListDao.getInstance(this).updateData("" + examId, contentValues);
        EventBus.getDefault().post(ClassContstant.EXAM_COMMIT);
        finish();


    }

    @Override
    public void onFialure() {
        finish();
    }
}

