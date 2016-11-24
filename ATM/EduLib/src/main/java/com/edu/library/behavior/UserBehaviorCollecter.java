package com.edu.library.behavior;

import java.util.HashMap;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.edu.library.util.ApplicationUtil;
import com.edu.library.util.TimeUtil;
import com.edu.library.util.VersionUtil;

/**
 * 用户行数据为收集，并把数据添加到爱丁用户中心
 * 
 * @author lucher
 * 
 */
public class UserBehaviorCollecter implements ActivityLifecycleCallbacks {

	private static final String TAG = "UserBehaviorCollecter";

	// 用户行为数据集合
	private HashMap<Activity, UserBehaviorData> behaviors = new HashMap<Activity, UserBehaviorData>();

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		// Log.e(TAG, "onActivityCreated:" +
		// activity.getClass().getSimpleName());
		startRecord(activity);
	}

	@Override
	public void onActivityStarted(Activity activity) {
		// Log.d(TAG, "onActivityStarted:" +
		// activity.getClass().getSimpleName());
		startRecord(activity);
	}

	@Override
	public void onActivityResumed(Activity activity) {
		// Log.i(TAG, "onActivityResumed:" +
		// activity.getClass().getSimpleName());
		startRecord(activity);
	}

	@Override
	public void onActivityPaused(Activity activity) {
		// Log.i(TAG, "onActivityPaused:" +
		// activity.getClass().getSimpleName());
		endRecord(activity);
	}

	@Override
	public void onActivityStopped(Activity activity) {
		// Log.d(TAG, "onActivityStopped:" +
		// activity.getClass().getSimpleName());
		endRecord(activity);
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
		// Log.v(TAG, "onActivitySaveInstanceState:" +
		// activity.getClass().getSimpleName());
	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		// Log.e(TAG, "onActivityDestroyed:" +
		// activity.getClass().getSimpleName());
		endRecord(activity);
	}

	/**
	 * 开始记录相关信息
	 * 
	 * @param activity
	 */
	private void startRecord(Activity activity) {
		UserBehaviorData behavior;
		// 初始化该记录的信息
		if (!behaviors.containsKey(activity)) {
			behavior = new UserBehaviorData();
			behavior.setPkgName(activity.getPackageName());
			behavior.setAppName(ApplicationUtil.getAppName(activity, behavior.getPkgName()));
			behavior.setActivityName(activity.getClass().getSimpleName());
			behavior.setVersion(VersionUtil.getVersionName(activity));
			behaviors.put(activity, behavior);
		} else {
			behavior = behaviors.get(activity);
		}
		// 记录开始时间，如果已经开始，则忽略
		if (behavior != null && behavior.getStartTime() == null) {
			behavior.setStartTime(TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			Log.d(TAG, "start record:" + behavior);
		} else {
			Log.e(TAG, behavior.getActivityName() + " already started");
		}
	}

	/**
	 * 结束记录相关信息
	 * 
	 * @param activity
	 */
	private void endRecord(Activity activity) {
		UserBehaviorData behavior = behaviors.get(activity);
		// 记录结束时间，前提是已经开始记录且没有结束记录
		if (behavior != null && behavior.getStartTime() != null && behavior.getEndTime() == null) {
			behavior.setEndTime(TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			Log.i(TAG, "end record:" + behavior);
			new InsertInfoThread(activity.getApplicationContext(), behavior).start();
		} else {
			Log.e(TAG, behavior.getActivityName() + " didn't start or already end");
		}
	}

	/**
	 * 把用户行为数据插入到爱丁用户中心的数据库表里
	 * 
	 * @author lucher
	 * 
	 */
	public class InsertInfoThread extends Thread {

		private Context mContext;
		private UserBehaviorData mBehavior;

		public InsertInfoThread(Context context, UserBehaviorData behavior) {
			mContext = context;
			mBehavior = behavior;
		}

		@Override
		public void run() {
			try {
				ContentValues values = new ContentValues();
				values.put("PKG_NAME", mBehavior.getPkgName());
				values.put("APP_NAME", mBehavior.getAppName());
				values.put("ACTIVITY_NAME", mBehavior.getActivityName());
				values.put("VERSION", mBehavior.getVersion());
				values.put("START_TIME", mBehavior.getStartTime());
				values.put("END_TIME", mBehavior.getEndTime());
				mContext.getContentResolver().insert(Uri.parse("content://com.edu.usercenter.behavior/"), values);

				mBehavior.setStartTime(null);
				mBehavior.setEndTime(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.run();
		}
	}
}
