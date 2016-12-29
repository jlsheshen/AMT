package com.edu.accountingteachingmaterial.base;

import android.os.Bundle;

public abstract class BaseMvpActivity<V, T extends BasePresenter<V>> extends BaseActivity {
	public T presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void initView(Bundle savedInstanceState) {
		presenter = initPresenter();
		presenter.attach((V) this);
	}

	public abstract T initPresenter();

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		presenter.dettach();
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
