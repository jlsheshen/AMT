package com.edu.library.upgrade;

import java.util.LinkedHashMap;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.edu.library.superonekey.SuperOneKeyHelper;
import com.edu.library.superonekey.UserInfo;
import com.edu.library.util.NetworkUtil;

/**
 * apk版本升级管理类，如果检测到新版本，弹出对话框，然后可进行下载操作,下载完毕后弹出安装确认界面,用于webservice类型
 * 
 * @author lucher
 * 
 */
public class ApkUpgradeManager {

	/**
	 * 提供的一键注册id号，对应学校为-爱丁数码公司，用于没有一键注册的场景
	 */
	public static final int DEFAULT_USER_ID = 95191;

	public static final String TAG = "ApkUpgradeManager";

	/**
	 * 服务器地址，若有必要可重新赋值
	 */
	public static String SERVICE_URL = "http://121.199.29.14:8080/services/WSCompetingService";
	/**
	 * 命名空间，若有必要可重新赋值
	 */
	public static String NAME_SPACE = "http://services.edu.com/";

	private static ApkUpgradeManager mInstance;
	private Context mContext;

	ApkDownloadInfo upInfo = new ApkDownloadInfo();
	private ApkDownloadDialog dialog;

	private ApkUpgradeManager(Context context, String apkPath, int iconResId) {
		mContext = context;
		UpgradeUtil.APK_PATH = apkPath;
		UpgradeUtil.ICON_RES_ID = iconResId;
	}

	/**
	 * 单例模式获取唯一实例
	 * 
	 * @param context
	 * @param apkPath
	 *            apk缓存文件存放路径
	 * @param iconResId
	 *            通知栏图标icon的资源id
	 * @return
	 */
	public static ApkUpgradeManager getSingleton(Context context, String apkPath, int iconResId) {
		if (mInstance == null) {
			mInstance = new ApkUpgradeManager(context, apkPath, iconResId);
		}
		return mInstance;
	}

	/**
	 * 检测是否有新版本，若有弹出提示框
	 * 
	 * @param defualtUserId
	 *            默认一键注册用户id，用于未注册或没有安装一键注册情况
	 */
	public void checkNewVersion(int defualtUserId) {
		if (NetworkUtil.isNetworkAvailable(mContext)) {
			CheckVersionTask task = new CheckVersionTask();
			task.execute(getApkInfo(defualtUserId));
		}
	}

	/**
	 * 获取apk相关信息
	 * 
	 * @param defaultUserId
	 *            默认一键注册id号
	 * @return
	 */
	private ApkVerisonInfo getApkInfo(int defaultUserId) {
		PackageManager pkgManager = mContext.getPackageManager();
		// 从数据库获取用户注册信息
		ApkVerisonInfo apkInfo = new ApkVerisonInfo();
		// 用来存储获取的应用信息数据
		PackageInfo packageInfo;
		try {
			packageInfo = pkgManager.getPackageInfo(mContext.getPackageName(), 0);
			apkInfo.setApkName(packageInfo.applicationInfo.loadLabel(pkgManager).toString());
			apkInfo.setPackageName(packageInfo.packageName);
			apkInfo.setApkVersionCode(packageInfo.versionCode);
			UserInfo userInfo = SuperOneKeyHelper.getUserInfo(mContext);
			if (userInfo == null) {// 没有一键注册或未注册
				apkInfo.setUserId(defaultUserId);
			} else {
				apkInfo.setUserId(userInfo.getUserId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.i(TAG, "getApkInfo:" + apkInfo);
		return apkInfo;
	}

	/**
	 * apk新版本检测
	 * 
	 * @author lucher
	 * 
	 */
	class CheckVersionTask extends AsyncTask<ApkVerisonInfo, String, ApkDownloadInfo> {

		@Override
		protected ApkDownloadInfo doInBackground(ApkVerisonInfo... params) {
			// 获取所有开发应用版本信息list
			ApkVerisonInfo apkInfo = params[0];
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(1);
			map.put("jsonStr", UpgradeUtil.entityToJSON(apkInfo));
			upInfo = (ApkDownloadInfo) WebServiceParser.getObjectValue("pushSingleApkUpdate", map, upInfo);

			return upInfo;
		}

		@Override
		protected void onPostExecute(ApkDownloadInfo result) {
			if (upInfo != null && "Success".equals(upInfo.getState())) {
				Log.d(TAG, "new version found...");
				dialog = new ApkDownloadDialog(mContext, upInfo);
				dialog.show();
			} else {
				Log.d(TAG, "no new version...");
			}
		}

	}
}
