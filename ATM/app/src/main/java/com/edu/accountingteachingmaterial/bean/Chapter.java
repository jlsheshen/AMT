package com.edu.accountingteachingmaterial.bean;


import com.edu.library.data.BaseData;

/**
 * 章节类
 * @author xd
 *
 */
public class Chapter extends BaseData {

	/**
	 * 章节ID
	 */
	private int id;
	/**
	 * 对应的课程ID
	 */
	private int course_id;
	/**
	 * 章节名称
	 */
	private String title;
	/**
	 * 改章节在同层级中的排序
	 */
	private int sub_menu;
	/**
	 * 创建人ID
	 */
	private int creator;
	/**
	 * 创建日期
	 */
	private String create_date;
	/**
	 * 父章节ID
	 */
	private int p_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSub_menu() {
		return sub_menu;
	}

	public void setSub_menu(int sub_menu) {
		this.sub_menu = sub_menu;
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

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

}
