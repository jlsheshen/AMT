package com.edu.accountingteachingmaterial.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.accountingteachingmaterial.subject.view.FenLuContentView;
import com.edu.accountingteachingmaterial.subject.view.SubjectJudgeView;
import com.edu.accountingteachingmaterial.subject.view.SubjectMultiSelectView;
import com.edu.accountingteachingmaterial.subject.view.SubjectSingleSelectView;
import com.edu.library.util.ToastUtil;
import com.edu.subject.ISubject;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectType;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.SignData;
import com.edu.subject.data.TestBillData;
import com.edu.subject.data.TestGroupBillData;
import com.edu.subject.view.BillView;
import com.edu.subject.view.GroupBillView;

import org.greenrobot.eventbus.EventBus;

/**
 * viewpager里存放的fragment
 *
 * @author lucher
 */
public class SubjectViewPagerFragment extends Fragment {

    private static final String TAG = "SubjectViewPagerFragment";
    /**
     * 题目内容数据
     */
    private BaseTestData mData;
    // 题目视图
    private ISubject subjectView;
    /**
     * 是否可以查看答案
     */
    int testMode;

    /**
     * 对应的视图
     */
    private View mView;

    // 是否初始化
    private boolean prepared;

    private Context mContext;
    private SubjectListener mListener;

    /**
     * 新建实例
     *
     * @param data     对应数据
     * @param listener
     * @return
     */
    public static SubjectViewPagerFragment newInstance(BaseTestData data, SubjectListener listener) {
        SubjectViewPagerFragment fragment = new SubjectViewPagerFragment();
        fragment.mData = data;
        fragment.mListener = listener;
        return fragment;
    }

    public void setTestMode(int testMode) {
        this.testMode = testMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        EventBus.getDefault().post("1");
        if (mData.getSubjectType()==SubjectType.SUBJECT_ENTRY||mData.getSubjectType()==SubjectType.SUBJECT_GROUP_BILL) {
            int a = 1;
        }
        Log.d(TAG, "mData.getSubjectId()" + mData.getSubjectId() + "---" + mData.getSubjectType());
        switch (mData.getSubjectType()) {
            case SubjectType.SUBJECT_BILL:
                mView = new BillView(mContext, (TestBillData) mData);

                break;

            case SubjectType.SUBJECT_GROUP_BILL:
                mView = new GroupBillView(mContext, (TestGroupBillData) mData);
                break;

            case SubjectType.SUBJECT_SINGLE:
                //	mView = new SingleSelectView(mContext, mData);
                mView = new SubjectSingleSelectView(mContext, mData, testMode);
                break;
            case SubjectType.SUBJECT_JUDGE:
                mView = new SubjectJudgeView(mContext, mData,testMode);
                break;
            case SubjectType.SUBJECT_MULTI:
                mView = new SubjectMultiSelectView(mContext, mData,testMode);
                break;
            case SubjectType.SUBJECT_ENTRY:
                mView = new FenLuContentView(mContext,mData,testMode);
                break;
            default:
                break;
        }
        subjectView = (ISubject) mView;
        subjectView.setSubjectListener(mListener);
        prepared = true;

        return mView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            delayLoad();
        } else {
            onInvisible();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            delayLoad();
        }
    }

    /**
     * 延时加载,主要对于耗时的题型，为了不影响翻页速度，将采取延时加载措施
     */
    private void delayLoad() {
        if (!prepared) {
            return;
        }
        int delay = 0;
        if (mData.getSubjectType() == SubjectType.SUBJECT_BILL || mData.getSubjectType() == SubjectType.SUBJECT_GROUP_BILL) {
            delay = 300;
        }
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                onVisible();
            }
        }, delay);
    }

    /**
     * fragment可见时回调
     */
    private void onVisible() {
        if (mData.getSubjectType() == SubjectType.SUBJECT_BILL) {
            ((BillView) subjectView).onVisible();
        } else if (mData.getSubjectType() == SubjectType.SUBJECT_GROUP_BILL) {
            ((GroupBillView) subjectView).onVisible();
        }
    }

    /**
     * fragment不可见时回调
     */
    private void onInvisible() {
    }

    /**
     * 重置
     */
    public void reset() {
        subjectView.reset();
    }

    /**
     * 提交
     *
     * @return 得分
     */
    public float submit() {
        return subjectView.submit();
    }


    /**
     * 保存答案
     */
    public void saveAnswer() {
        subjectView.saveAnswer();
    }

    /**
     * 盖章
     *
     * @param signData 印章数据
     */
    public void sign(SignData signData) {
        if (mData.getSubjectType() == SubjectType.SUBJECT_BILL) {
            ((BillView) subjectView).sign(signData);
        } else if (mData.getSubjectType() == SubjectType.SUBJECT_GROUP_BILL) {
            ((GroupBillView) subjectView).sign(signData);
        } else {
            ToastUtil.showToast(mContext, "该题型不支持盖章操作");
        }
    }

    /**
     * 显示闪电符
     */
    public void showFlash() {
        if (mData.getSubjectType() == SubjectType.SUBJECT_BILL) {
            ((BillView) subjectView).showFlashes();
        } else if (mData.getSubjectType() == SubjectType.SUBJECT_GROUP_BILL) {
            ((GroupBillView) subjectView).showFlashes();
        } else {
            ToastUtil.showToast(mContext, "该题型不支持闪电符操作");
        }

    }
}
