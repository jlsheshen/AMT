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
		if (presenter == null) {
			presenter = initPresenter();
			if (presenter.mView == null) {
				presenter.attach((V) this);
			}
		}
	}

	@Override
	protected void onDestroy() {
		if (presenter == null){

		}else {
			presenter.dettach();
		}
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
