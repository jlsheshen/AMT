package com.edu.library.upgrade;

import java.io.Serializable;

/**
 * apk文件下载相关信息封装
 * @author lucher
 *
 */
public class ApkDownloadInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;
	private String message;
	private String apkUrl;
	private String apkName;
	private String apkIconUrl;
	private String apkVersionCode;
	private String packageName;
	private String userId;
	private int downloadState;
	private String filePath;
	private int percent;
	private int downloadSpeed;
	private int fileSize;

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

	public int getDownloadState() {
		return downloadState;
	}

	public void setDownloadState(int downloadState) {
		this.downloadState = downloadState;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getApkVersionCode() {
		return apkVersionCode;
	}

	public void setApkVersionCode(String apkVersionCode) {
		this.apkVersionCode = apkVersionCode;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
