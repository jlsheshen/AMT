package com.edu.library.common;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * 获取一键注册相关信息
 * 
 * @author Daibw
 * 
 */
public class SuperOneKeyInfo {

	/**
	 * 获取所有用户信息
	 * 
	 * @param context
	 * @return { 姓名, 性别, 学校, 入学年份, 专业名, 班级, 学号, userId }
	 */
	public static String[] getUserInfo(Context context) {
		String[] userinfo = { "未注册", "-1", "未注册", "-1", "未注册", "-1", "-1", "-1" };
		Cursor cursor = null;
		try {
			ContentResolver cr = context.getContentResolver();
			cursor = cr.query(Uri.parse("content://com.edu.superonekey.UsingInfoProvider/student_num"), null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				// 姓名
				String name = cursor.getString(cursor.getColumnIndex("student_name"));
				// 性别
				String gender = cursor.getString(cursor.getColumnIndex("gender"));
				// 学校名
				String school = cursor.getString(cursor.getColumnIndex("school_name"));
				// 入学年份
				String year = cursor.getString(cursor.getColumnIndex("start_year"));
				// 专业名
				String majorName = cursor.getString(cursor.getColumnIndex("career_name"));
				// 班级
				String className = cursor.getString(cursor.getColumnIndex("class_name"));
				// 学号
				String studentNum = cursor.getString(cursor.getColumnIndex("student_num"));
				// UserId
				String userId = cursor.getString(cursor.getColumnIndex("user_id"));
				userinfo = new String[] { name, gender, school, year, majorName, className, studentNum, userId };
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return userinfo;
	}

	/**
	 * 获取姓名
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserNameBy(Context context) {
		String name = "未注册";
		Cursor cursor = null;
		try {
			ContentResolver cr = context.getContentResolver();
			cursor = cr.query(Uri.parse("content://com.edu.superonekey.UsingInfoProvider/student_num"), null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				// 姓名
				name = cursor.getString(cursor.getColumnIndex("student_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return name;
	}

	/**
	 * 获取学号
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserNO(Context context) {
		String studentNum = "-1";
		Cursor cursor = null;
		try {
			ContentResolver cr = context.getContentResolver();
			cursor = cr.query(Uri.parse("content://com.edu.superonekey.UsingInfoProvider/student_num"), null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				// 学号
				studentNum = cursor.getString(cursor.getColumnIndex("student_num"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return studentNum;
	}

	/**
	 * 获取userId
	 * 
	 * @param context
	 * @return
	 */
	public static int getUserUserId(Context context) {
		int userId = -1;
		Cursor cursor = null;
		try {
			ContentResolver cr = context.getContentResolver();
			cursor = cr.query(Uri.parse("content://com.edu.superonekey.UsingInfoProvider/student_num"), null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				// userId
				String id = cursor.getString(cursor.getColumnIndex("user_id"));
				userId = Integer.valueOf(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return userId;
	}

	/**
	 * 获取连接地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getUrl(Context context) {
		String[] ipPortStrings = getIpAndPort(context);
		return "http://" + ipPortStrings[0] + ":" + ipPortStrings[1];
	}

	/**
	 * 获取ip与端口
	 * 
	 * @param context
	 * @return {ip,port}
	 */
	public static String[] getIpAndPort(Context context) {
		String url[] = { "121.199.29.14", "8080" };
		ContentResolver cResolver = context.getContentResolver();
		Cursor cursorRegister = cResolver.query(Uri.parse("content://com.edu.superonekey.UsingInfoProvider/service_url"), null, null, null, null);
		if (cursorRegister != null && cursorRegister.getCount() > 0) {
			cursorRegister.moveToFirst();
			String serviceUrl = cursorRegister.getString(cursorRegister.getColumnIndex("service_url"));
			if (serviceUrl.contains("EduApkManagerTest1.0")) {
				serviceUrl = serviceUrl.replace("http://", "");
				String ip = serviceUrl.substring(0, serviceUrl.indexOf(":"));
				String port = serviceUrl.substring(serviceUrl.indexOf(":") + 1, serviceUrl.indexOf("/"));
				url = new String[] { ip, port };
			}
		}
		return url;
	}

}
