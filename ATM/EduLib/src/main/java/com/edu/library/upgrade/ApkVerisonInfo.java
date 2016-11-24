package com.edu.library.upgrade;

/**
 * apk相关信息封装，用于检测是否存在新版本
 * 
 * @author lucher
 * 
 */
public class ApkVerisonInfo {
	private Integer userId;
	private String packageName;
	private int apkVersionCode;
	private String apkName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getApkVersionCode() {
		return apkVersionCode;
	}

	public void setApkVersionCode(int apkVersionCode) {
		this.apkVersionCode = apkVersionCode;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	@Override
	public String toString() {
		return String.format("userId:%s,packageName:%s,apkName:%s,apkVersionCode:%s", userId, packageName, apkName, apkVersionCode);
	}
}
