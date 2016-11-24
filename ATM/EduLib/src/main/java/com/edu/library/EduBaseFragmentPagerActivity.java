package com.edu.library;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentActivity基类,支持滑动切页，内部使用Viewpager实现，包含活跃度搜集功能
 * 
 * @author lucher
 * 
 */
public abstract class EduBaseFragmentPagerActivity extends FragmentActivity implements OnPageChangeListener, OnClickListener {

	protected Context mContext;
	// 滑动页面viewpager
	protected ViewPager mViewPager;

	// viewpager的adapter
	protected FragmentPagerAdapter mAdapter;
	private FragmentManager mFragmentManager;

	// 单选按钮们
	protected List<RadioButton> mTabIndicator = new ArrayList<RadioButton>();
	// tab页面们
	protected List<Fragment> mTabs;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	}

	/**
	 * 初始化，需要在初始化之前调用
	 * 
	 * @param viewPager
	 *            滑动控件
	 */
	protected void init(ViewPager viewPager) {
		mViewPager = viewPager;
		mViewPager.setOnPageChangeListener(this);
		mContext = this;
		mTabs = new ArrayList<Fragment>();
		mFragmentManager = getSupportFragmentManager();
		mAdapter = new FragmentPagerAdapter(mFragmentManager) {
			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mTabs.get(arg0);
			}
		};
		viewPager.setAdapter(mAdapter);
	}

	/**
	 * 加入fragment
	 * 
	 * @param radio
	 *            对应单项按钮
	 * @param fragment
	 *            对应的fragment页面
	 */
	protected void addFragment(RadioButton radio, BaseFragment fragment) {
		mTabIndicator.add(radio);
		mTabs.add(fragment);
		radio.setOnClickListener(this);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置当前选中的item
	 * 
	 * @param view
	 *            单选按钮
	 * @param smoothScroll
	 *            是否以动画方式滑动
	 */
	public void setCurrentItem(View view, boolean smoothScroll) {
		int index = mTabIndicator.indexOf(view);
		if (index >= 0 && index < mTabs.size()) {
			mViewPager.setCurrentItem(index, smoothScroll);
			mTabIndicator.get(index).setChecked(true);
		}
	}

	@Override
	public void onPageSelected(int position) {
		Log.i("EduBaseFragmentPagerActivity", "onPageSelected position:" + position);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if (positionOffset != 0.0f) {
			// RadioButton left = mTabIndicator.get(position);
			// RadioButton right = mTabIndicator.get(position + 1);
			//
			// left.setAlpha(1 - positionOffset);
			// right.setAlpha(positionOffset);
		} else {

		}
	}

	@Override
	public void onClick(View view) {
		setCurrentItem(view, true);
	}

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 初始化视图
	 */
	protected abstract void initView();
}
