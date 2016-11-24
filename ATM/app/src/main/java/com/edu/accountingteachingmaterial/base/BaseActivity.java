package com.edu.accountingteachingmaterial.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.zhy.autolayout.AutoLayoutActivity;

public abstract class BaseActivity extends AutoLayoutActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(setLayout());

		initView(savedInstanceState);
		initData();

	}
	public abstract int setLayout();

	public abstract void initView(Bundle savedInstanceState);

	public abstract void initData();
	/**
	 * findViewById方法,简化组件的绑定
	 * 
	 * @param id
	 * @return
	 */
	protected <K extends View> K bindView(int id) {
		return (K) findViewById(id);

	}

	/**
	 * 界面跳转
	 * 
	 * @param cls
	 */
	protected void startActivity(Class cls) {
		Intent intent = new Intent(this, cls);
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
		Intent intent = new Intent(this, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
