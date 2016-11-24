package com.edu.library;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * FragmentActivity基类,不支持滑动切页，包含活跃度搜集功能
 * 
 * @author lucher
 * 
 */
public abstract class EduBaseFragmentActivity extends FragmentActivity {

	protected Context mContext;

	// fragment操作相关类
	private FragmentManager fragmentManager;
	private Fragment currentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		fragmentManager = getSupportFragmentManager();
	}

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 初始化视图
	 */
	protected abstract void initView();

	/**
	 * 设置当前tab里显示的fragment页面
	 * 
	 * @param fragment
	 * @param layoutResId
	 *            fragment所在布局id
	 */
	protected void setFragment(Fragment fragment, int layoutResId) {
		currentFragment = fragment;
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
		ft.replace(layoutResId, fragment);
		ft.commit();
	}

	/**
	 * 切换fragment，不重新实例化
	 * 
	 * @param to
	 * @param layoutResId
	 *            fragment所在布局id
	 */
	public void switchFragment(Fragment to, int layoutResId) {
		if (currentFragment == null) {
			setFragment(to, layoutResId);
		} else if (currentFragment != to) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(currentFragment).add(layoutResId, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
		currentFragment = to;
	}
}
