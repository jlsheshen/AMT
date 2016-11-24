package com.edu.library.behavior;

/**
 * 用户行为数据
 * 
 * @author lucher
 * 
 */
public class UserBehaviorData {

	private int id;
	//包名
	private String pkgName;
	//应用名
	private String appName;
	//activity名
	private String activityName;
	//版本号
	private String version;
	//开始使用时间
	private String startTime;
	//结束使用时间
	private String endTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s", appName, version, activityName, startTime, endTime);
	}
}
