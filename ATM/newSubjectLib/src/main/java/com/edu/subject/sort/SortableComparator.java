package com.edu.subject.sort;

import java.util.Comparator;

/**
 * 可排序数据排序类
 * @author lucher
 *
 */
public class SortableComparator implements Comparator<SortableData> {

	@Override
	public int compare(SortableData lhs, SortableData rhs) {
		if (lhs.getOrder() > rhs.getOrder()) {
			return 1;
		} else if (lhs.getOrder() < rhs.getOrder()) {
			return -1;
		}
		return 0;
	}

}
