package com.edu.accountingteachingmaterial.activity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.SubjectViewPagerAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
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
 * Created by Administrator on 2016/11/18.
 */

public class SubjectTestActivity extends BaseActivity implements AdapterView.OnItemClickListener, SubjectListener, SubjectCardAdapter.OnCardItemClickListener {

    // 显示题目的viewpager控件
    private ViewPager viewPager;
    private SubjectViewPagerAdapter mSubjectAdapter;

    private int mCurrentIndex;
    private TextView tvQuestion;

    // 印章选择对话框
    private SignChooseDialog signDialog;
    // 印章，闪电符按钮
    private ImageView btnSign, btnFlash;

    // 答题卡对话框
    private SubjectCardDialog mCardDialog;
    List<BaseTestData> datas;

    // 页面相关状态的监听
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        // 页面切换后调用
        @Override
        public void onPageSelected(int item) {
            mCurrentIndex = item;
            refreshToolBar();
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

        viewPager = (ViewPager) findViewById(R.id.vp_content);
        viewPager.setOnPageChangeListener(mPageChangeListener);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        btnSign = (ImageView) findViewById(R.id.btnSign);
        btnFlash = (ImageView) findViewById(R.id.btnFlash);
        datas = SubjectTestDataDao.getInstance(this).getSubjects(TestMode.MODE_PRACTICE);

        mSubjectAdapter = new SubjectViewPagerAdapter(getSupportFragmentManager(), datas, this, this);
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
        ;
        // 刷新题目数据
        tvQuestion.setText(mSubjectAdapter.getData(mCurrentIndex).getSubjectIndex() + "." + subject.getQuestion());
        if (subject.getSubjectType() == SubjectType.SUBJECT_BILL) {
            btnSign.setVisibility(View.VISIBLE);
            btnFlash.setVisibility(View.VISIBLE);

        } else {
            btnSign.setVisibility(View.GONE);
            btnFlash.setVisibility(View.GONE);
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
                float score = mSubjectAdapter.submit();

                ToastUtil.showToast(this, "score:" + score);
                finish();

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
        mSubjectAdapter.reset();
        ToastUtil.showToast(this, "全部重做操作完成");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDone(String message) {

        ToastUtil.showToast(this, "噶一首歌的原始股第一");

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
}

