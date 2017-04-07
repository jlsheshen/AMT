package com.edu.accountingteachingmaterial.newsubject.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.edu.accountingteachingmaterial.newsubject.common.SubjectViewPagerFragment;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectState;
import com.edu.subject.bill.SignData;
import com.edu.subject.bill.element.info.BaseElementInfo;
import com.edu.subject.bill.element.info.BlankInfo;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.TestBillData;
import com.edu.subject.data.TestComprehensiveData;
import com.edu.subject.data.TestGroupBillData;

import java.util.ArrayList;
import java.util.List;

/**
 * 题目练习界面用的adapter
 * 
 * @author lucher
 * 
 */
public class SubjectViewPagerAdapter extends FragmentPagerAdapter {

	private static final String TAG = "SubjectViewPagerAdapter";

	/**
	 * 对应界面集合
	 */
	private ArrayList<SubjectViewPagerFragment> mPagerList = new ArrayList<SubjectViewPagerFragment>();

	/**
	 * 题目的集合
	 */
	private ArrayList<BaseTestData> mSubjectList = new ArrayList<BaseTestData>();
	private Context mContext;
	private SubjectListener mListener;

	public SubjectViewPagerAdapter(FragmentManager childFragmentManager, List<BaseTestData> datas, Context context, SubjectListener listener) {
		super(childFragmentManager);
		mContext = context;
		mListener = listener;
		if (datas != null) {
			for (BaseTestData subject : datas) {
				mPagerList.add(null);
				mSubjectList.add(subject);
			}
		}
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public Fragment getItem(int position) {
		Log.d(TAG, "get item...." + position);
		if (mPagerList.get(position) == null) {
			mPagerList.set(position, SubjectViewPagerFragment.newInstance(mSubjectList.get(position), mListener));
		}
		return mPagerList.get(position);
	}

	@Override
	public int getCount() {
		if (mSubjectList == null) {
			return -1;
		}
		return mSubjectList.size();
	}

	/**
	 * 获取指定数据
	 * 
	 * @param position
	 * @return
	 */
	public BaseTestData getData(int position) {
		return mSubjectList.get(position);
	}

	public List<BaseTestData> getDatas() {
		return mSubjectList;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		try {
			// 销毁fragment
			SubjectViewPagerFragment fragment = mPagerList.get(position);
			fragment = null;
			mPagerList.set(position, fragment);
			FragmentManager manager = ((Fragment) object).getFragmentManager();
			FragmentTransaction trans = manager.beginTransaction();
			trans.remove((Fragment) object);
			trans.commit();
			// 销毁视图结构
			super.destroyItem(container, position, object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 盖章
	 * 
	 * @param index
	 *            题目索引
	 * @param sign
	 *            印章数据
	 */
	public void sign(int index, SignData signData) {
		mPagerList.get(index).sign(signData);
	}

	/**
	 * 显示闪电符
	 * 
	 * @param index
	 *            题目索引
	 */
	public void showFlash(int index) {
		mPagerList.get(index).showFlash();
	}

	/**
	 * 提交指定index的题
	 * 
	 * @param index
	 * @return
	 */
	public float submit(int index) {
		if (mSubjectList.size() <= 0)
			return 0;
		submitSubject(index);
		float totalScore = mSubjectList.get(index).getuScore();
		Log.d(TAG, "totalScore:" + totalScore);
		if (mListener != null) {
			mListener.onSaveTestData(mSubjectList.get(index));
		}

		return totalScore;

	}

	/**
	 * 提交
	 * 
	 * @return 得分
	 */
	public float submit() {
		float totalScore = 0;
		for (int i = 0; i < mPagerList.size(); i++) {
			submitSubject(i);
			totalScore += mSubjectList.get(i).getuScore();
		}
		Log.d(TAG, "totalScore:" + totalScore);
		if (mListener != null) {
			mListener.onSaveTestDatas(mSubjectList);
		}

		return totalScore;
	}

	/**
	 * 提交指定索引的题
	 * 
	 * @param index
	 */
	private void submitSubject(int index) {
		SubjectViewPagerFragment pager = mPagerList.get(index);
		if (pager != null) {
			pager.submit();
		}
		// 判断正误，在此之前需要把对应的题得分设置到data中
		if (mSubjectList.get(index).getSubjectData().getScore() == mSubjectList.get(index).getuScore()) {
			mSubjectList.get(index).setState(SubjectState.STATE_CORRECT);
		} else {
			mSubjectList.get(index).setState(SubjectState.STATE_WRONG);
		}
	}

	/**
	 * 重置所有题
	 */
	public void reset() {
		for (int i = 0; i < mPagerList.size(); i++) {
			resetSubject(i);
		}
		if (mListener != null) {
			mListener.onSaveTestDatas(mSubjectList);
		}
	}

	/**
	 * 重置指定题
	 * 
	 * @param index
	 */
	public void reset(int index) {
		if (mSubjectList.size() <= 0)
			return;
		resetSubject(index);
		if (mListener != null) {
			mListener.onSaveTestData(mSubjectList.get(index));
		}
	}

	/**
	 * 重置指定索引的题
	 * 
	 * @param index
	 */
	private void resetSubject(int index) {
		mSubjectList.get(index).setState(SubjectState.STATE_INIT);
		// 界面重置
		SubjectViewPagerFragment pager = mPagerList.get(index);
		if (pager != null) {
			pager.reset();
		}
		// 数据重置
		mSubjectList.get(index).setUAnswer(null);
		mSubjectList.get(index).setUAnswerData(null);
		mSubjectList.get(index).setuScore(0);
		if (mSubjectList.get(index) instanceof TestBillData) {// 对于单据题，需要把每个空的内容清空，需要把用户印章答案清空
			List<BaseElementInfo> elements = ((TestBillData) mSubjectList.get(index)).getTemplate().getElementDatas();
			for (BaseElementInfo element : elements) {
				if (element instanceof BlankInfo) {
					((BlankInfo) element).setuAnswer(null);
					((BlankInfo) element).setRight(false);
				}
			}
		} else if (mSubjectList.get(index) instanceof TestGroupBillData) {// 分组单据题，需要把每一个单据的状态清除
			// 数据重置
			List<TestBillData> datas = ((TestGroupBillData) mSubjectList.get(index)).getTestDatas();
			for (int i = 0; i < datas.size(); i++) {
				datas.get(i).setUAnswer(null);
				datas.get(i).setUAnswerData(null);
				datas.get(i).setState(SubjectState.STATE_INIT);
				datas.get(i).setuScore(0);
				for (BaseElementInfo element : datas.get(i).getTemplate().getElementDatas()) {
					if (element instanceof BlankInfo) {
						((BlankInfo) element).setuAnswer(null);
						((BlankInfo) element).setRight(false);
					}
				}
			}
		} else if (mSubjectList.get(index) instanceof TestComprehensiveData) {//综合题重置
			// 数据重置
			List<BaseTestData> datas = ((TestComprehensiveData) mSubjectList.get(index)).getTestDatas();
			for (int i = 0; i < datas.size(); i++) {
				datas.get(i).setUAnswer(null);
				datas.get(i).setUAnswerData(null);
				datas.get(i).setState(SubjectState.STATE_INIT);
				datas.get(i).setuScore(0);
			}
		}
	}

	/**
	 * 保存答案
	 * 
	 * @param index
	 */
	public void saveAnswer(final int index) {
		if (mSubjectList.size() <= 0)
			return;

		new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "save answer start:" + index);
				if (mSubjectList.get(index).getState() == SubjectState.STATE_CORRECT || mSubjectList.get(index).getState() == SubjectState.STATE_WRONG) {
					return;
				}
				mPagerList.get(index).saveAnswer();
				if (mSubjectList.get(index).getState() == SubjectState.STATE_INIT) {
					mSubjectList.get(index).setState(SubjectState.STATE_UNFINISH);
				}
				if (mListener != null) {
					mListener.onSaveTestData(mSubjectList.get(index));
				}
				Log.i(TAG, "save answer over:" + index);
			}
		}.run();
	}
}
