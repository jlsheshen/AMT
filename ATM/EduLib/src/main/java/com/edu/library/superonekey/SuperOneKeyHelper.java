package com.edu.library.superonekey;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * 一键注册帮助类，主要用于获取用户信息以及ip地址等信息
 * 
 * @author lucher
 * 
 */
public class SuperOneKeyHelper {

	private static final String TAG = "SuperOneKeyHelper";

	/**
	 * 获取用户信息,若未注册则返回null
	 * 
	 * @param context
	 * @return
	 */
	public static UserInfo getUserInfo(Context context) {
		UserInfo user = null;
		Cursor cursor = null;
		ContentResolver cr = context.getContentResolver();
		cursor = cr.query(Uri.parse("content://com.edu.superonekey.UsingInfoProvider/user_name"), null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			user = new UserInfo();
			user.setCareerName(cursor.getString(cursor.getColumnIndex("career_name")));
			user.setClassName(cursor.getString(cursor.getColumnIndex("class_name")));
			user.setDistrictName(cursor.getString(cursor.getColumnIndex("district_name")));
			user.setGender(cursor.getString(cursor.getColumnIndex("gender")));
			user.setGradeName(cursor.getString(cursor.getColumnIndex("grade_name")));
			user.setSchoolName(cursor.getString(cursor.getColumnIndex("school_name")));
			user.setStartYear(cursor.getString(cursor.getColumnIndex("start_year")));
			user.setStudentName(cursor.getString(cursor.getColumnIndex("student_name")));
			user.setStudentNum(cursor.getString(cursor.getColumnIndex("student_num")));
			user.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));

			// 下列信息在一键注册V1.2.5及以后版本提供
			if (cursor.getColumnIndex("province_name") != -1) {
				user.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				user.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				user.setFacultyName(cursor.getString(cursor.getColumnIndex("faculty_name")));
			}
		}
		Log.d(TAG, "getUserInfo:" + user);

		return user;
	}

	/**
	 * 获取系统更新中的ip地址和端口号信息,若未注册则返回null
	 * 
	 * @param context
	 * @return
	 */
	public static ServiceInfo getServiceInfo(Context context) {
		ServiceInfo service = null;
		ContentResolver cResolver = context.getContentResolver();
		Cursor cursor = cResolver.query(Uri.parse("content://com.edu.superonekey.UsingInfoProvider/service_url"), null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			service = new ServiceInfo();
			String serviceUrl = cursor.getString(cursor.getColumnIndex("service_url"));
			serviceUrl = serviceUrl.replace("http://", "");
			String ip = serviceUrl.substring(0, serviceUrl.indexOf(":"));
			int port = Integer.parseInt(serviceUrl.substring(serviceUrl.indexOf(":") + 1, serviceUrl.indexOf("/")));

			service.setIp(ip);
			service.setPort(port);
		}
		return service;
	}
}
