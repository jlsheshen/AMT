package com.edu.library.usercenter;

import com.edu.library.data.BaseData;

/**
 * 用户信息数据封装
 * 
 * @author lyq
 *
 */
public class UserData extends BaseData {
	private static final long serialVersionUID = 1L;
	public int userId;// 用户id
	public String aidingNum;// 爱丁号
	public String stuName;// 学生姓名
	public String gender;// 性别
	public String headPhoto;// 头像
	public String mac;// mac地址
	public String province;// 省
	public String cityName;// 城市
	public String schoolName;// 学校
	public String className;// 班级
	public int category;// 学生、老师分类
	public String phone;// 电话
	public String mail;// 邮箱
	public int state;// 当前用户状态
	public String startYear;// 入学年份
	public String majorName;// 专业

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAidingNum() {
		return aidingNum;
	}

	public void setAidingNum(String aidingNum) {
		this.aidingNum = aidingNum;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

}
