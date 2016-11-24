package com.edu.library.upgrade2;

import java.text.DecimalFormat;

import android.os.Environment;

import com.alibaba.fastjson.JSON;

/**
 * 升级工具类
 * 
 * @author lucher
 * 
 */
public class UpgradeUtil {

	public static UpdateAppInfo updateInfo;

	public static final int DOWNLOAD_WAIT = 0;
	public static final int DOWNLOADING = 1;
	public static final int DOWNLOAD_FINISH = 2;

	// apk存放路径
	public static String APK_PATH = Environment.getExternalStorageDirectory() + "/Android/data/com.edu.data/market/";
	// 通知图标id
	public static int ICON_RES_ID;

	/**
	 * 更新下载进度的广播
	 */
	public static final String ACTION_UPDATE_UI = "com.edu.action.apkUpgrade.updateui";

	/**
	 * 通过JSON把对象转成字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String entityToJSON(Object obj) {
		return JSON.toJSONString(obj);
	}

	/**
	 * 计算文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	public static String formetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

}
