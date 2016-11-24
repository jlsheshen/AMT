package com.edu.accountingteachingmaterial.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	protected Context context;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return LayoutInflater.from(context).inflate(initLayout(), container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	protected abstract int initLayout();

	protected abstract void initView(View view);

	protected abstract void initData();

	protected <T extends View> T bindView(int id) {
		return (T) getView().findViewById(id);
	}

	/**
	 * 界面跳转
	 * 
	 * @param cls
	 */
	protected void startActivity(Class cls) {
		Intent intent = new Intent(context, cls);
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
		Intent intent = new Intent(context, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
