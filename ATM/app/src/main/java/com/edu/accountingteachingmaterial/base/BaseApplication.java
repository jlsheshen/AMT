package com.edu.accountingteachingmaterial.base;

import android.content.Context;
import android.widget.TextView;

import com.edu.library.util.DBCopyUtil;
import com.edu.subject.util.SoundPoolUtil;
import com.edu.subject.util.SubjectImageLoader;
import com.edu.testbill.Constant;

public class BaseApplication extends EduCrashApplication {
	//找不到context时使用此context
	private static Context context ;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 检测数据库是否已拷贝
		DBCopyUtil fileCopyUtil = new DBCopyUtil(this);
		fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);
		// 初始化声音播放工具，如果不初始化，盖章没声
		SoundPoolUtil.getInstance().init(this);

		// 预加载网络图片
		SubjectImageLoader.getInstance(this).preloadAllPics();
		TextView s;
		context = this;
	}

	public static Context getContext() {
		return context;
	}

}
