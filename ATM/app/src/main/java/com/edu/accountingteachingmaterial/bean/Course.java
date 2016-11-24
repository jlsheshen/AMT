package com.edu.accountingteachingmaterial.bean;


import com.edu.library.data.BaseData;

/**
 * 课程类
 * @author xd
 *
 */

public class Course  extends BaseData {
	/**
	 * 课程ID
	 */
	private int id;
	/**
	 * 课程名称
	 */
	private String title;
	/**
	 * 课程描述
	 */
	private String summary;
	/**
	 * 课程状态
	 */
	private int status;
	/**
	 * 暂无实际用处
	 */
	private int type;
	/**
	 * 课程封面
	 */
	private String picture;
	/**
	 * 创建人
	 */
	private int creator;
	/**
	 * 创建时间
	 */
	private String create_date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
}
