package com.edu.library.upgrade2;

import java.io.Serializable;

/**
 * 应用更新信息实体类
 * 
 * @author lucher
 * 
 */
public class UpdateAppInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// apk下载地址
	private String apkUrl;
	// apk名称
	private String apkName;
	// apk图标地址
	private String apkIconUrl;
	// 程序详情
	private String apkIntro;
	// 包名
	private String packageName;
	// apk代码版本号
	private int apkVersionCode;
	// 更新类型,1-更新，2-新应用（当前未安装）
	private int type;
	// apk用户版本号
	private String apkVersionUser;

	/*********** 以下信息不需要服务器返回 *************/
	// 每个apk的索引
	private int index;
	// 当前是否正在下载
	private boolean loading;
	// 下载状态
	private int downloadState;
	// 文件路径
	private String filePath;
	// 百分比
	private int percent;
	// 下载速度KB
	private int downloadSpeed;
	// 文件大小BK
	private int fileSize;
	// 通知ID
	private long notificationId;

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public int getDownloadState() {
		return downloadState;
	}

	public void setDownloadState(int downloadState) {
		this.downloadState = downloadState;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public int getDownloadSpeed() {
		return downloadSpeed;
	}

	public void setDownloadSpeed(int downloadSpeed) {
		this.downloadSpeed = downloadSpeed;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getApkIconUrl() {
		return apkIconUrl;
	}

	public void setApkIconUrl(String apkIconUrl) {
		this.apkIconUrl = apkIconUrl;
	}

	public String getApkIntro() {
		return apkIntro;
	}

	public void setApkIntro(String apkIntro) {
		this.apkIntro = apkIntro;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getApkVersionUser() {
		return apkVersionUser;
	}

	public void setApkVersionUser(String apkVersionUser) {
		this.apkVersionUser = apkVersionUser;
	}

	public boolean isLoading() {
		return loading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return String.format("apkName:%s,downloadState:%s,progress:%s", apkName, downloadState, percent);
	}

}