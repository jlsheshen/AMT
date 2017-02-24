package com.edu.accountingteachingmaterial.oldsubject.subject.bill.element.info;

import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.element.info.BaseElementInfo;

/**
 * 闪电符数据
 * 
 * @author lucher
 * 
 */
public class FlashInfo extends BaseElementInfo {

	// 是否显示
	private boolean show;

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
	 */
	public FlashInfo(int x, int y, int width, int height) {
		super(x, y, width, height, ElementType.TYPE_FLASH);
	}

	public FlashInfo() {
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

}
