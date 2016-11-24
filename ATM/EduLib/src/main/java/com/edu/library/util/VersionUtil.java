package com.edu.library.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 应用版本号获取工具类
 * 
 * @author lucher
 * 
 */
public class VersionUtil {
	/**
	 * 获取当前app的版本号//(内部识别号)-相对于开发者
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取当前app的版本号//(内部识别号)-相对于用户
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return "V" + pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 获取指定app的版本号//(内部识别号)-相对于开发者
	 * 
	 * @param context
	 * @param pkg
	 * @return
	 */
	public static int getVersionCode(Context context, String pkg) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(pkg, 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 *  获取指定app的版本号//(内部识别号)-相对于用户
	 * 
	 * @param context
	 * @param pkg
	 * @return
	 */
	public static String getVersionName(Context context, String pkg) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(pkg, 0);
			return "V" + pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "error";
		}
	}
}
