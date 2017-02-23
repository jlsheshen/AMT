package com.edu.library.usercenter;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * 用户中心使用帮助类,提供功能：1.卸载app 2.切换输入法（使用见com.edu.library.common.EduImeHelper） 3.
 * 获取用户信息
 * 
 * @author lucher
 * 
 */
public class UserCenterHelper {

	private static final String TAG = UserCenterHelper.class.getSimpleName();

	/**
	 * 卸载单个应用
	 * 
	 * @param pkg
	 *            包名
	 * @param context
	 */
	public static void uninstallApp(String pkg, Context context) {
		Intent intent = new Intent();
		intent.setAction("com.edu.systemapp.updata");
		intent.putExtra("Type", 111);
		intent.putExtra("packagename", pkg);
		context.sendBroadcast(intent);
	}

	/**
	 * 卸载多个应用
	 * 
	 * @param pkgs
	 *            包名list
	 * @param context
	 */
	public static void uninstallApp(ArrayList<String> pkgs, Context context) {
		Intent intent = new Intent();
		intent.setAction("com.edu.systemapp.updata");
		intent.putExtra("Type", 333);
		intent.putStringArrayListExtra("packagenames", pkgs);
		context.sendBroadcast(intent);
	}

	/**
	 * 获取爱丁用户中心的用户信息,若未注册则返回null
	 * 
	 * @param context
	 * @return
	 */
	public static UserData getUserInfo(Context context) {
		UserData user = null;
		Cursor cursor = null;
		ContentResolver cr = context.getContentResolver();
		try {
			cursor = cr.query(Uri.parse("content://com.edu.usercenter.userinfo/items"), null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				user = new UserData();
				int idIndex = cursor.getColumnIndex("ID");
				int userId = cursor.getColumnIndex("USER_ID");
				int aidingNum = cursor.getColumnIndex("AIDING_NUM");
				int stuName = cursor.getColumnIndex("STU_NAME");
				int gender = cursor.getColumnIndex("GENDER");
				int headPhoto = cursor.getColumnIndex("HEAD_PHOTO");
				int mac = cursor.getColumnIndex("MAC");
				int province = cursor.getColumnIndex("PROVINCE");
				int cityName = cursor.getColumnIndex("CITY_NAME");
				int schoolName = cursor.getColumnIndex("SCHOOL_NAME");
				int className = cursor.getColumnIndex("CLASS_NAME");
				int category = cursor.getColumnIndex("CATEGORY");
				int phone = cursor.getColumnIndex("PHONE");
				int mail = cursor.getColumnIndex("MAIL");
				int state = cursor.getColumnIndex("STATE");
				int startYear = cursor.getColumnIndex("START_YEAR");
				int major = cursor.getColumnIndex("MAJOR_NAME");
				user.setId(cursor.getInt(idIndex));
				user.setUserId(cursor.getInt(userId));
				user.setAidingNum(cursor.getString(aidingNum));
				user.setStuName(cursor.getString(stuName));
				user.setGender(cursor.getString(gender));
				user.setHeadPhoto(cursor.getString(headPhoto));
				user.setMac(cursor.getString(mac));
				user.setProvince(cursor.getString(province));
				user.setCityName(cursor.getString(cityName));
				user.setSchoolName(cursor.getString(schoolName));
				user.setCategory(cursor.getInt(category));
				user.setPhone(cursor.getString(phone));
				user.setMail(cursor.getString(mail));
				user.setState(cursor.getInt(state));
				// 下列信息在我的爱丁V1.2.1及以后版本提供
				if (className != -1) {
					user.setClassName(cursor.getString(className));
					user.setStartYear(cursor.getString(startYear));
					user.setMajorName(cursor.getString(major));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d(TAG, "getUserInfo:" + user);

		return user;
	}

	/**
	 * 获取爱丁用户中心的ip地址和端口号信息,若未注册则返回null
	 * 
	 * @param context
	 * @return
	 */
	public static ServiceInfoData getServiceInfo(Context context) {
		ServiceInfoData service = null;
		Cursor cursor = null;
		ContentResolver cr = context.getContentResolver();
		try {
			cursor = cr.query(Uri.parse("content://com.edu.usercenter.serviceinfo/items"), null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();

				service = new ServiceInfoData();
				String ip = cursor.getString(cursor.getColumnIndex("IP"));
				int port = cursor.getInt(cursor.getColumnIndex("PORT"));
				String nameSpace = cursor.getString(cursor.getColumnIndex("NAME_SPACE"));

				service.setIp(ip);
				service.setPort(port);
				service.setNameSpace(nameSpace);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return service;
	}

	/**
	 * 根据子域名获取全域名，ip和port从爱丁用户中心获取
	 * 
	 * @param context
	 * @param subUrl
	 *            子域名需要已/开头，例如应用市场的子域名：/interface/appmarket/findUpdataAppInfos
	 * @return
	 */
	public static String getServerUrl(Context context, String subUrl) {
		String url = "";
		ServiceInfoData serviceInfo = getServiceInfo(context);
		if (serviceInfo != null) {
			String ip = serviceInfo.getIp();
			int port = serviceInfo.getPort();
			url = "http://" + ip + ":" + port + subUrl;
		} else {
			Log.e(TAG, "获取ip和port失败....");
		}

		return url;
	}
}
