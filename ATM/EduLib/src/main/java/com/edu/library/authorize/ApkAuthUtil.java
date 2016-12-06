package com.edu.library.authorize;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.edu.library.common.PreferenceHelper;
import com.edu.library.usercenter.UserCenterHelper;
import com.edu.library.usercenter.UserData;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.UrlReqEntity;
import com.lucher.net.util.NetworkUtil;

import cz.msebera.android.httpclient.Header;

/**
 * apk授权工具
 *
 * @author lucher
 *
 */
public class ApkAuthUtil {

	private static final String TAG = "ApkAuthUtil";
	public static final String PRI_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN09WLUOUydudbkfUB8l2leR88il5JKYfP+NP4DBWUCN6MXNvkoWAosNqgobxNm5oZDF0HcLg8v+mR3iJd5Tcen/xW3np/HIE+pR/d8hFX+0VlZ8t/0/TjTIXfhqOQa/WeNaTG3CeiMaJR8fxVlpzxJWgymxjAifkVRcXrkFmhCZAgMBAAECgYEAqFxz8iy5iQtdWQiEP3/d3cA7JdlgzVJv5UXHKqN42VBk8Ip2ogiH3OwEF3c0YYALfJGc58sCfC3+tagQq1UTHNZsnG0RKlihKIthgv4MU+bmMjDbLLeFUyCFpyTQgqhUNx7qyDPuBPxY/Dt1R7no0sSYP70SuN/ybfVyYt6YOQECQQDz/lcVUvxJC3li/fXbL67CoGHmVWXBFzlkLu1oZoBPiunrqg2MTXgE11/v4Gu2H0itdvxkVLdhu7qtcYcsFtPhAkEA6CBebIu7Q1FYRD5kzpbAlDJSxfYcwi5djRxQcxhKfSO/0Xn/rRHlEbszZEOV/3KOsJpymeLKuMJBH6bDP4lTuQJBAO4zVvcFfjEdl5MSFiy3H2j4xLrmkiFxN+FbgwDSWN/O4VHmQbXAh7RKQ2ne8ajqX7yhlgOpRSKP8M6VL/7WBmECQHiAxUQIThCmW/IhieeNby//5+SI3WkY9MvalREK3TCVrHCsqsRH8+j+i7FTPL091UFtDG1CxQahIXmy8s07F1ECQD9jEZcuzXo6VWRT9XkNNY0Zm8Fp/i8sPiOPBiYWMQBKDD0wcYQnfFwAZEBFSRWOyEp/47sdl/8/O5WiNCkmvUk=";

	/**
	 * 服务器地址
	 */
	private static final String SERVER_URL = "/eduMember/interface/firmware/apkAuthorizeCheck"; // 192.168.1.157:8080/eduMember/

	/**
	 * 检测授权
	 *
	 * @param context
	 * @return
	 */
	public static boolean checkAuth(final Context context) {
		String verificationCode = PreferenceHelper.getInstance(context).getStringValue("verificationCode");
		UserData user = UserCenterHelper.getUserInfo(context);
		if (user == null || user.getState() == 0 || user.getMac() == null) {
			ToastUtil.showToast(context, "请先完成爱丁用户中心注册后再使用该应用");
			finishActivity(context);
			return false;
		}

		String mac = user.getMac();
		String code = SignUtils.sign(mac + context.getPackageName(), PRI_KEY);
		if (!verificationCode.equals("") && verificationCode.equals(code)) {
			Log.d(TAG, "checkAuth success");
			return true;
		} else {
			Log.e(TAG, "checkAuth failure");
			if (getVerificationCode(context, code)) {
				Log.d(TAG, "checkAuth success");
				return true;
			} else {
				if (NetworkUtil.isNetworkAvailable(context)) {
					sendAuthRequst(context, code);
				} else {
					ToastUtil.showToast(context, "当前平板未连接到有效网络，请连接后重试", 3000);
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							if (context instanceof Activity) {
								((Activity) context).finish();
							}
						}
					}, 2000);
				}
			}
		}

		return false;
	}

	/**
	 * 获取用户中心共享的包名是否包含当前应用
	 *
	 * @param context
	 * @param locationCode
	 * @return
	 */
	private static boolean getVerificationCode(Context context, String locationCode) {
		Cursor cursor = null;
		ContentResolver cr = context.getContentResolver();
		try {
			cursor = cr.query(Uri.parse("content://com.edu.usercenter.authlist/items"), null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					String code = cursor.getString(cursor.getColumnIndex("VALUE"));
					Log.d(TAG, "code:" + code);
					if (!code.equals("") && code.equals(locationCode)) {
						PreferenceHelper.getInstance(context).setStringValue("verificationCode", code);
						return true;
					}
				}
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 申请授权
	 *
	 * @param context
	 * @param code
	 */
	private static void sendAuthRequst(final Context context, final String code) {
		Log.d(TAG, "send auth request...");
		UserData user = UserCenterHelper.getUserInfo(context);
		if (user == null || user.getState() == 0) {
			ToastUtil.showToast(context, "请先完成爱丁用户中心注册后再使用该应用");
			finishActivity(context);
			return;
		}

		// 初始化服务器地址
		String subUrl = UserCenterHelper.getServerUrl(context, SERVER_URL);
		String packageName = context.getPackageName().replace(".", "_");
		String url = subUrl + "/" + user.getUserId() + "-" + packageName + "-" + getSysVersion(context);
		UrlReqEntity entity = new UrlReqEntity(context, RequestMethod.POST, url);
		new JsonNetReqManager() {

			@Override
			public void onConnectionFailure(String arg0, Header[] arg1) {
				ToastUtil.showToast(mContext, "激活失败:" + arg0);
				((Activity) context).finish();
			}

			@Override
			public void onConnectionError(String arg0) {
				ToastUtil.showToast(mContext, "激活出错:" + arg0);
				((Activity) context).finish();
			}

			@Override
			public void onConnectionSuccess(JSONObject arg0, Header[] arg1) {
				boolean success = arg0.getBoolean("success");
				if (success) {
					// 成功后，保存激活码，重启应用
					PreferenceHelper.getInstance(context).setStringValue("verificationCode", code);
					ToastUtil.showToast(mContext, "激活成功");
					((Activity) context).finish();
					Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(intent);
				} else {
					ToastUtil.showToast(mContext, "激活失败：" + arg0.getString("message"));
					((Activity) context).finish();
				}
			}
		}.sendRequest(entity, "正在申请授权，请稍等...", false);

	}

	/**
	 * 获取当前系统版本号
	 *
	 * @param context
	 * @return
	 */
	private static int getSysVersion(Context context) {
		int version = 1;
		try {
			Context userCenterContext = context.createPackageContext("com.edu.usercenter", Context.CONTEXT_IGNORE_SECURITY);
			SharedPreferences sharedPreferences = userCenterContext.getSharedPreferences("EDU_USER_CENTER_PREF", Context.MODE_WORLD_READABLE);
			version = sharedPreferences.getInt("version_code", version);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 退出页面
	 */
	private static void finishActivity(final Context context) {
		new Handler().postDelayed(new Runnable() {

			public void run() {
				((Activity) context).finish();
			}

		}, 2000);
	}
}
