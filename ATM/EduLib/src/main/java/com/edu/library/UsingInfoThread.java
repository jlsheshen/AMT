package com.edu.library;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 把使用信息发给一键注册，让其传给服务器做活跃度统计的线程类
 * 
 * @author lucher
 * 
 */
public class UsingInfoThread extends Thread {

	private Context context;
	// 暂时为用
	private String activityName;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;

	public UsingInfoThread(Context context, String activityName, Date startTime, Date endTime) {
		this.context = context;
		this.activityName = activityName;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public void run() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ContentValues values = new ContentValues();
			values.put("app_name", context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString());
			values.put("package_name", context.getPackageName());
			values.put("app_module_name", activityName);
			values.put("start_time", df.format(startTime));
			values.put("end_time", df.format(endTime));
			System.out.println("Test-->OnStop()-->insert");
			context.getContentResolver().insert(Uri.parse("content://com.edu.superonekey.UsingInfoProvider/using_info"), values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.run();
	}
}
