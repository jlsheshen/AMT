package com.edu.accountingteachingmaterial.activity;

/**
 * Created by Administrator on 2016/12/9.
 */

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.SubjectViewPagerAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.model.ResultsListener;
import com.edu.accountingteachingmaterial.util.CountryTestTimer;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.UploadOnlineResultsManager;
import com.edu.accountingteachingmaterial.view.ExitDialog;
import com.edu.accountingteachingmaterial.view.UnTouchableViewPager;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectType;
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
 * 在线考试答题界面
 */

public class SubjectExamActivity extends BaseActivity implements AdapterView.OnItemClickListener, SubjectListener, SubjectCardAdapter.OnCardItemClickListener, CountryTestTimer.OnTimeOutListener, ResultsListener {

    // 显示题目的viewpager控件
    private UnTouchableViewPager viewPager;
    private SubjectViewPagerAdapter mSubjectAdapter;

    private int mCurrentIndex;
    private TextView tvBillQuestion;

    // 印章选择对话框
    private SignChooseDialog signDialog;
    // 印章，闪电符按钮
    private ImageView btnSign, btnFlash, backIv;

    // 答题卡对话框
    private SubjectCardDialog mCardDialog;
    List<BaseTestData> datas;
    int examId;//试卷ID
    int textMode;//测试模式
    ExitDialog exitDialog;// 退出提示框
    // 页面相关状态的监听
    private CountryTestTimer timer;
    int totalTime;
    private TextView tvTime;
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
        return R.layout.activity_exam;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        List<SignData> signs = (List<SignData>) SignDataDao.getInstance(this, Constant.DATABASE_NAME).getAllDatas();
        signDialog = new SignChooseDialog(this, signs, this);

        viewPager = (UnTouchableViewPager) findViewById(R.id.vp_content);
        viewPager.setOnPageChangeListener(mPageChangeListener);
        tvBillQuestion = (TextView) findViewById(R.id.tv_bill_question);
        btnSign = (ImageView) findViewById(R.id.btnSign);
        btnFlash = (ImageView) findViewById(R.id.btnFlash);
        backIv = (ImageView) findViewById(R.id.class_aty_back_iv);
        tvTime = (TextView) findViewById(R.id.tv_time);

        Bundle bundle = getIntent().getExtras();
        examId = bundle.getInt("examId");
        textMode = bundle.getInt("textMode");
        totalTime = bundle.getInt("totalTime");
        Log.d("SubjectExamActivity", "totalTime:" + totalTime);

        datas = SubjectTestDataDao.getInstance(this).getSubjects(textMode, examId);

        String s = JSONObject.toJSONString(datas);
        Log.d("SubjectTestActivity", s);

        mSubjectAdapter = new SubjectViewPagerAdapter(getSupportFragmentManager(), datas, this, this);
        mSubjectAdapter.setTestMode(textMode);

        viewPager.setAdapter(mSubjectAdapter);
        mCardDialog = new SubjectCardDialog(this, datas, this, mSubjectAdapter.getDatas().get(mCurrentIndex).getId());

        if (textMode == ClassContstant.TEST_MODE_NORMAL) {
            findViewById(R.id.btnDone).setVisibility(View.VISIBLE);
            if (totalTime > 0) {
                setTime();
            } else {
                findViewById(R.id.ly_time).setVisibility(View.GONE);
            }
        } else {
            findViewById(R.id.ly_time).setVisibility(View.GONE);
            findViewById(R.id.btnDone).setVisibility(View.INVISIBLE);

        }

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
//        tvQuestion.setText(mSubjectAdapter.getData(mCurrentIndex).getSubjectIndex() + "." + subject.getQuestion());
        if (subject.getSubjectType() == SubjectType.SUBJECT_BILL && textMode == ClassContstant.TEST_MODE_NORMAL) {
            btnSign.setVisibility(View.VISIBLE);
            btnFlash.setVisibility(View.VISIBLE);
//            tvBillQuestion.setText(subject.getQuestion());
//            tvBillQuestion.setVisibility(View.VISIBLE);
        } else {
            btnSign.setVisibility(View.GONE);
            btnFlash.setVisibility(View.GONE);
            tvBillQuestion.setVisibility(View.GONE);
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

                exitDialog = new ExitDialog(this);
                //在答题时间范围内答题
                if (totalTime > 0) {
                    if (!exitDialog.isShowing() && timer != null) {
                        exitDialog.show();
                    }
                    exitDialog.setDialogListener(new ExitDialog.SetDialogListener() {
                        @Override
                        public void onOkClicked() {
                            sendScore();
                        }

                        @Override
                        public void onCancelClicked() {
                            if (exitDialog.isShowing()) {
                                exitDialog.dismiss();
                            }
                        }
                    });
                    //不在考试时间范围内
                } else {
                    if (!exitDialog.isShowing()) {
                        exitDialog.show();
                    }
                    exitDialog.setDialogListener(new ExitDialog.SetDialogListener() {
                        @Override
                        public void onOkClicked() {
                            sendScore();
                        }

                        @Override
                        public void onCancelClicked() {
                            if (exitDialog.isShowing()) {
                                exitDialog.dismiss();
                            }
                        }
                    });
                }
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
                mSubjectAdapter.saveAnswer(mCurrentIndex);
                if (timer != null) {
                    timer.cancel();
                }
                finish();

                break;

            default:
                break;
        }
    }

    private void sendScore() {
        float score = mSubjectAdapter.submit();
        UploadOnlineResultsManager.getSingleton(this).setResultsListener(this);
        UploadOnlineResultsManager.getSingleton(this).setResults(mSubjectAdapter.getDatas());
        int userId = Integer.parseInt(PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.USER_ID));
        int cost = 0;
        if (timer != null) {
            cost = timer.getUsedTime();
        }
        UploadOnlineResultsManager.getSingleton(this).uploadResult(userId, examId, cost);
        //UploadOnlineResultsManager.getSingleton(this).uploadResult(userId, examId, 10000);
        //EventBus.getDefault().post(userId);
        //ToastUtil.showToast(this, "score:" + score);
    }

    /**
     * 根据发来的状态,来刷新列表
     *
     * @param state
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(Integer state) {
        Log.d("SubjectExamActivity", "----13  走过了EventBus");

        if (datas != null && state == ClassContstant.EXAM_COMMIT) {
            Bundle bundle = new Bundle();
            bundle.putInt("examId", examId);
            bundle.putInt("textMode", textMode);
            startActivity(UnitTestActivity.class, bundle);
            finish();
        } else if (datas != null && state == ClassContstant.EXAM_FAILD) {
            Bundle bundle = new Bundle();
            bundle.putInt("examId", examId);
            bundle.putInt("textMode", textMode);
            startActivity(UnitTestActivity.class, bundle);
            finish();
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
        if (timer != null) {
            timer.cancel();
        }
        finish();
        super.onBackPressed();
    }


    //开始倒计时
    private void setTime() {
        // 倒计时时间设置
        timer = new CountryTestTimer(tvTime, 1000, totalTime * 1000, SubjectExamActivity.this);
        timer.setOnTimeOutListener(this);
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    @Override
    public void onTimeOut() {
        //倒计时结束发送成绩
        sendScore();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFialure() {

    }

}



