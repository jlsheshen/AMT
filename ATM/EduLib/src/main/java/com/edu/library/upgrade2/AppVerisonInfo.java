package com.edu.library.upgrade2;

/**
 * apk相关信息封装，用于检测是否存在新版本
 * 
 * @author lucher
 * 
 */
public class AppVerisonInfo {
	// 包名
	private String packageName;
	// apk版本号
	private int apkVersionCode;
	// apk名称
	private String apkName;
	
	//型号
	private String model;

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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return String.format("packageName:%s,apkName:%s,apkVersionCode:%s\n", packageName, apkName, apkVersionCode);
	}
}
