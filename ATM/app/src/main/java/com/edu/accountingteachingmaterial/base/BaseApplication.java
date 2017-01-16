package com.edu.accountingteachingmaterial.base;

import android.content.Context;

import com.lucher.net.req.LogUtil;

public class BaseApplication extends EduCrashApplication {
	//找不到context时使用此context
	private static Context context ;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = this;
		// 允许打印网络请求日志
		LogUtil.LOG_ENABLED = true;
	}

	public static Context getContext() {
		return context;
	}

}
