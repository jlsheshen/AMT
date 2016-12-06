package com.edu.library.upgrade2;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.library.usercenter.UserCenterHelper;
import com.edu.library.usercenter.UserData;
import com.edu.library.util.NetworkUtil;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.JsonReqEntity;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * apk版本升级管理类，如果检测到新版本，弹出对话框，然后可进行下载操作,下载完毕后弹出安装确认界面,用于http类型
 *
 * @author lucher
 */
public class ApkUpgradeManager {

    public static final String TAG = "ApkUpgradeManager";

    private static ApkUpgradeManager mInstance;
    private Context mContext;

    private ApkDownloadDialog dialog;

    private static String SERVER_URL = "/eduMember/interface/appmarket/findUpdataAppInfos";
    // 默认id
    private static int DEFAULT_ID = 0;

    /**
     * @param context
     * @param iconResId 通知栏图标
     */
    private ApkUpgradeManager(Context context, int iconResId) {
        mContext = context;
        // 初始化服务器地址
        SERVER_URL = UserCenterHelper.getServerUrl(mContext, SERVER_URL);
        UpgradeUtil.ICON_RES_ID = iconResId;
    }

    /**
     * 单例模式获取唯一实例
     *
     * @param context
     * @param iconResId 通知栏图标icon的资源id
     * @return
     */
    public static ApkUpgradeManager getSingleton(Context context, int iconResId) {
        if (mInstance == null) {
            mInstance = new ApkUpgradeManager(context, iconResId);
        }
        return mInstance;
    }

    /**
     * 检测是否有新版本，若有弹出提示框
     */
    public void checkNewVersion() {
        if (NetworkUtil.isNetworkAvailable(mContext)) {
            UserData userData = UserCenterHelper.getUserInfo(mContext);
            if (userData != null) {// 没有用户中心或未注册
                DEFAULT_ID = userData.getUserId();
            }
            // 如果后面是2代表查所有需要更新的apk信息，如果是非2的数代表查自身更新信息
            String url = SERVER_URL + "/" + DEFAULT_ID + "-1";

            List<AppVerisonInfo> info = getApkInfo();
            JsonReqEntity reqEntity = new JsonReqEntity(mContext, RequestMethod.POST, url, JSON.toJSONString(info));
            new JsonNetReqManager() {
                @Override
                public void onConnectionFailure(String arg0, Header[] arg1) {
                    Log.d(TAG, "onConnectionFailure..." + arg0);
                }

                @Override
                public void onConnectionError(String arg0) {
                    Log.d(TAG, "onConnectionError..." + arg0);
                }

                @Override
                public void onConnectionSuccess(JSONObject arg0, Header[] arg1) {
                    Log.d(TAG, arg0.toJSONString());
                    boolean success = arg0.getBoolean("success");
                    String msg = arg0.getString("message");
                    if (success) {
                        Log.d(TAG, "new version found...");
                        List<UpdateAppInfo> infos = JSONObject.parseArray(msg, UpdateAppInfo.class);
                        if (infos != null && infos.size() > 0) {
                            dialog = new ApkDownloadDialog(mContext, infos.get(0));
                            dialog.show();
                        }

                    } else {
                        Log.d(TAG, "no new version...");
                    }
                }
            }.sendRequest(reqEntity);
        }

    }

    /**
     * 检测是否有新版本，若有弹出提示框
     *
     * @param defualtUserId 默认用户id，用于未注册或没有安装用户中心情况
     * @param url           服务器提供的版本检测接口url,接口需要为如下格式:http://192.168.1.159/interface/
     *                      appmarket/findUpdataAppInfos
     */
    public void checkNewVersion(int defualtUserId, String url) {
        DEFAULT_ID = defualtUserId;
        SERVER_URL = url;
        checkNewVersion();
    }

    /**
     * 获取apk相关信息
     *
     * @return
     */
    private List<AppVerisonInfo> getApkInfo() {
        List<AppVerisonInfo> infos = new ArrayList<AppVerisonInfo>();
        PackageManager pkgManager = mContext.getPackageManager();
        // 从数据库获取用户注册信息
        AppVerisonInfo apkInfo = new AppVerisonInfo();
        // 用来存储获取的应用信息数据
        PackageInfo packageInfo;
        try {
            packageInfo = pkgManager.getPackageInfo(mContext.getPackageName(), 0);
            apkInfo.setApkName(packageInfo.applicationInfo.loadLabel(pkgManager).toString());
            apkInfo.setPackageName(packageInfo.packageName);
            apkInfo.setApkVersionCode(packageInfo.versionCode);
            apkInfo.setModel(android.os.Build.MODEL);
            infos.add(apkInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "getApkInfo:" + apkInfo);
        return infos;
    }
}
