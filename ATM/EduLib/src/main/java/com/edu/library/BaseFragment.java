package com.edu.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Fragment基类
 * 
 * @author lucher
 * 
 */
public abstract class BaseFragment extends Fragment {

	protected View mView;
	protected Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
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
	 * 界面跳转
	 * 
	 * @param cls
	 */
	protected void startActivity(Class cls) {
		Intent intent = new Intent(mContext, cls);
		startActivity(intent);
	}

	/**
	 * 界面跳转，带参数
	 * 
	 * @param context
	 * @param cls
	 * @param bundle
	 */
	protected void startActivity(Class cls, Bundle bundle) {
		Intent intent = new Intent(mContext, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}