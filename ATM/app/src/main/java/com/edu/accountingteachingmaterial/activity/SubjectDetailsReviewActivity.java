package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.SubjectViewPagerAdapter;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ReviewTestDataDao;
import com.edu.accountingteachingmaterial.view.UnTouchableViewPager;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectType;
import com.edu.subject.TestMode;
import com.edu.subject.common.SubjectCardAdapter.OnCardItemClickListener;
import com.edu.subject.common.SubjectCardDialog;
import com.edu.subject.dao.SignDataDao;
import com.edu.subject.data.BaseSubjectData;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.SignData;
import com.edu.testbill.Constant;
import com.edu.testbill.dialog.SignChooseDialog;

import java.io.IOException;
import java.util.List;

/**
 * 本地自测答案
 *
 * @author lucher
 */
public class SubjectDetailsReviewActivity extends FragmentActivity implements OnItemClickListener, OnCardItemClickListener {

    // 显示题目的viewpager控件
    private UnTouchableViewPager viewPager;
    private SubjectViewPagerAdapter mSubjectAdapter;
    List<BaseTestData> datas;
    private int mCurrentIndex;
    private ImageView backIv;

    // 印章选择对话框
    private SignChooseDialog signDialog;

    // 答题卡对话框
    private SubjectCardDialog mCardDialog;

    // 页面相关状态的监听
    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

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
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 窗口全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
    }

    /**
     * 刷新工具栏状态
     */
    private void refreshToolBar() {
        if (mCurrentIndex < 0 || mCurrentIndex > mSubjectAdapter.getCount() - 1)
            return;
        BaseSubjectData subject = mSubjectAdapter.getData(mCurrentIndex).getSubjectData();
        if (subject.getSubjectType() == SubjectType.SUBJECT_BILL) {

        } else {
        }
    }

    /**
     * 初始化
     *
     * @throws IOException
     */
    private void init() {
        List<SignData> signs = (List<SignData>) SignDataDao.getInstance(this, Constant.DATABASE_NAME).getAllDatas();
        signDialog = new SignChooseDialog(this, signs, this);
        viewPager = (UnTouchableViewPager) findViewById(R.id.vp_content);
        viewPager.setOnPageChangeListener(mPageChangeListener);

        findViewById(R.id.btnDone).setVisibility(View.GONE);
        backIv = (ImageView) findViewById(R.id.class_aty_back_iv);

        Bundle bundle = getIntent().getExtras();
        int chapterId = bundle.getInt(ClassContstant.SUBJECT_REVIEW_ID);
        datas = ReviewTestDataDao.getInstance(this).getSubjects(TestMode.MODE_SHOW_DETAILS, chapterId);

        mSubjectAdapter = new SubjectViewPagerAdapter(getSupportFragmentManager(), datas, this, null);
        mSubjectAdapter.setTestMode(ClassContstant.TEST_MODE_TEST);
        viewPager.setAdapter(mSubjectAdapter);

        mCardDialog = new SubjectCardDialog(this, datas, this, mSubjectAdapter.getDatas().get(mCurrentIndex).getId());
    }

    public void onClick(View view) throws IOException {
        switch (view.getId()) {
            case R.id.btnCard:
                if (!mCardDialog.isShowing()) {
                    mCardDialog.show(mSubjectAdapter.getData(mCurrentIndex).getId());
                }
                break;
            case R.id.btnDone:
//				mSubjectAdapter.reset();
//				finish();
                break;
            case R.id.btnLeft:
                if (mCurrentIndex != 0) {
                    mCurrentIndex--;
                    viewPager.setCurrentItem(mCurrentIndex, true);
                } else {
                    ToastUtil.showToast(this, "已经是第一页");

                }
                break;

            case R.id.btnRight:
                if (mCurrentIndex != mSubjectAdapter.getCount() - 1) {
                    mCurrentIndex++;
                    viewPager.setCurrentItem(mCurrentIndex, true);
                } else {
                    ToastUtil.showToast(this, "已经是最后一页");
                }
                break;
            case R.id.class_aty_back_iv:
                finish();
                break;

            default:
                break;
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
            mCurrentIndex++;
            viewPager.setCurrentItem(mCurrentIndex, true);
        } else {
            ToastUtil.showToast(this, "已经是最后一页");
        }

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
        mCardDialog.dismiss();
        int index = mSubjectAdapter.getDatas().indexOf(data);
        viewPager.setCurrentItem(index);
        return mSubjectAdapter.getDatas().get(index).getId();
    }

    @Override
    public void onRedoClicked() {
        mCardDialog.dismiss();
    }
}
