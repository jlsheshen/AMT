package com.edu.accountingteachingmaterial.oldsubject.subject.bill.element.info;

import java.io.Serializable;

/**
 * 单据中各种元素数据封装基类,目前元素包括：底图，空，印章
 * 
 * @author lucher
 * 
 */
public abstract class BaseElementInfo implements Serializable {
	// 空对应的id
	private int id;
	// x坐标
	private float x;
	// y坐标
	private float y;
	// 宽度
	private float width;
	// 高度
	private float height;
	/**
	 * 数据类型,见{@link}BlankType
	 */
	private int type;
	//分数
	private float score;
	//备注
	private String remark;

	public BaseElementInfo() {

	}

	/**
	 * 构造
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param type
	 *            类别
	 */
	public BaseElementInfo(int x, int y, int width, int height, int type) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setType(type);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return String.format("id:%s,x:%s,y:%s,width:%s,height:%s,type:%s,answer", id, x, y, width, height, type);
	}
}
