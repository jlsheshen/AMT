package com.edu.accountingteachingmaterial.oldsubject.subject.bill.element.info;

import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.element.info.BaseElementInfo;

/**
 * 对应印章的数据
 * 
 * @author lucher
 * 
 */
public class SignInfo extends BaseElementInfo {

	// 是否为用户印章
	private boolean user;
	// 印章图片
	private String bitmap;
	//是否正确
	private boolean correct;

	public SignInfo() {

	}

	/**
	 * 构造
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public SignInfo(int x, int y) {
		super(x, y, 0, 0, ElementType.TYPE_SIGN);
	}

	public String getBitmap() {
		return bitmap;
	}

	public void setBitmap(String bitmap) {
		this.bitmap = bitmap;
	}

	public boolean isUser() {
		return user;
	}

	public void setUser(boolean user) {
		this.user = user;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

}
