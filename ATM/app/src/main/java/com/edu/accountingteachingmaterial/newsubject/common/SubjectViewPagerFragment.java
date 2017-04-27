package com.edu.accountingteachingmaterial.newsubject.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.library.util.ToastUtil;
import com.edu.subject.ISubject;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectType;
import com.edu.subject.bill.SignData;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.TestBillData;
import com.edu.subject.data.TestComprehensiveData;
import com.edu.subject.data.TestEntryData;
import com.edu.subject.data.TestGroupBillData;
import com.edu.subject.view.BillGroupView;
import com.edu.subject.view.BillView;
import com.edu.subject.view.BlankView;
import com.edu.subject.view.ComprehensiveView;
import com.edu.subject.view.EntryView;
import com.edu.subject.view.JudgeView;
import com.edu.subject.view.MultiSelectView;
import com.edu.subject.view.SimpleAnswerView;
import com.edu.subject.view.SingleSelectView;

/**
 * viewpager里存放的fragment
 * 
 * @author lucher
 * 
 */
public class SubjectViewPagerFragment extends Fragment {
String TAG = "SubjectViewPagerFragment";
	/**
	 * 题目内容数据
	 */
	private BaseTestData mData;
	// 题目视图
	private ISubject subjectView;

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
	 * @param data
	 *            对应数据
	 * @param listener
	 * @return
	 */
	public static SubjectViewPagerFragment newInstance(BaseTestData data, SubjectListener listener) {
		SubjectViewPagerFragment fragment = new SubjectViewPagerFragment();
		fragment.mData = data;
		fragment.mListener = listener;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();

		switch (mData.getSubjectData().getSubjectType()) {
		case SubjectType.SUBJECT_BILL:
			mView = new BillView(mContext, (TestBillData) mData);

			break;
		case SubjectType.SUBJECT_GROUP_BILL:
			mView = new BillGroupView(mContext, (TestGroupBillData) mData);

			break;
		case SubjectType.SUBJECT_SINGLE:
			mView = new SingleSelectView(mContext, mData);

			break;
		case SubjectType.SUBJECT_MULTI:
			mView = new MultiSelectView(mContext, mData);
			break;

		case SubjectType.SUBJECT_JUDGE:
			mView = new JudgeView(mContext, mData);
			break;

		case SubjectType.SUBJECT_ENTRY:
			mView = new EntryView(mContext, (TestEntryData) mData);

			break;

		case SubjectType.SUBJECT_COMPREHENSIVE:
			mView = new ComprehensiveView(mContext, (TestComprehensiveData) mData);

			break;
		case SubjectType.SUBJECT_SIMPLE_ANSWER:
			mView = new SimpleAnswerView(mContext, mData);

			break;
		case SubjectType.SUBJECT_BLANK:
			mView = new BlankView(mContext, mData);

			break;
		default:
			ToastUtil.showToast(mContext, "不支持该题型：" + mData.getSubjectData().getSubjectType());
			break;
		}
		if (mView != null) {
			subjectView = (ISubject) mView;
			subjectView.setSubjectListener(mListener);
			prepared = true;
		}

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
			Log.e(TAG, "~~~delayLoad , prepared false");
			return;
		}
		int delay = 300;
		Log.i(TAG, "~~~delayLoad:" + delay);
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
		Log.d(TAG, "~~~onVisible:" + mData.getId());
		subjectView.onVisible();
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
	 * @param signData
	 *            印章数据
	 */
	public void sign(SignData signData) {
		if (mData.getSubjectData().getSubjectType() == SubjectType.SUBJECT_BILL) {
			((BillView) subjectView).sign(signData);
		} else if (mData.getSubjectData().getSubjectType() == SubjectType.SUBJECT_GROUP_BILL) {
			((BillGroupView) subjectView).sign(signData);
		} else {
			ToastUtil.showToast(mContext, "该题型不支持盖章操作");
		}
	}

	/**
	 * 显示闪电符
	 * 
	 */
	public void showFlash() {
		if (mData.getSubjectData().getSubjectType() == SubjectType.SUBJECT_BILL) {
			((BillView) subjectView).showFlashes();
		} else if (mData.getSubjectData().getSubjectType() == SubjectType.SUBJECT_GROUP_BILL) {
			((BillGroupView) subjectView).showFlashes();
		} else {
			ToastUtil.showToast(mContext, "该题型不支持闪电符操作");
		}

	}
}
