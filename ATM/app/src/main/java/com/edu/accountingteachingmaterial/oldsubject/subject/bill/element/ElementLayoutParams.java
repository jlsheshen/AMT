package com.edu.accountingteachingmaterial.oldsubject.subject.bill.element;

import android.view.ViewGroup.LayoutParams;

/**
 * 单据中元素的布局参数
 * 
 * @author lucher
 * 
 */
public class ElementLayoutParams extends LayoutParams {
	// x坐标
	private int x;
	// y坐标
	private int y;
	/**
	 * 子控件类型，见{@link}BlankType
	 */
	private int type;

	/**
	 * 构造
	 * 
	 * @param x
	 *            控件x坐标
	 * @param y
	 *            控件y坐标
	 * @param type
	 *            子控件类型
	 * @param width
	 *            控件宽度
	 * @param height
	 *            控件高度
	 */
	public ElementLayoutParams(int type, int x, int y, int width, int height) {
		super(width, height);
		setType(type);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}