package com.edu.accountingteachingmaterial.base;

import android.os.Bundle;

public abstract class BaseMvpActivity<V, T extends BasePresenter<V>> extends BaseActivity {
	public T presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		presenter = initPresenter();

	}

	public abstract T initPresenter();

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		presenter.attach((V) this);
	}

	@Override
	protected void onDestroy() {
		presenter.dettach();
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
