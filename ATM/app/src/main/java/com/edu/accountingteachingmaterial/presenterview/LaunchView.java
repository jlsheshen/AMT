package com.edu.accountingteachingmaterial.presenterview;


public interface LaunchView {
	/**
	 * 进行登录
	 */
	void launchLogin();
	/**
	 * 加载用户数据,及数据更新
	 */
	void loadData();

	/**
	 * 跳转Activity
	 */
	void startActivity( );

	/**
	 * 登录成功后跳转到主页面
	 *
	 */
	void jumpMain();
	/**
	 * 登录失败后后跳转到登录页面
	 */
	void jumpLogin();

}
