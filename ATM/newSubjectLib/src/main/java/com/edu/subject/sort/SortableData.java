package com.edu.subject.sort;

import com.edu.library.data.BaseData;

/**
 * 可排序数据封装
 * @author lucher
 *
 */
public class SortableData extends BaseData{
	// 排序
	private int order;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
