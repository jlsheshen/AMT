package com.edu.subject.data.body;

import java.util.List;

import com.edu.subject.entry.view.GroupEntryItemData;

/**
 * 分录题型body对象
 * @author lucher
 *
 */
public class EntryBodyData {

	//多组借贷数据
	private List<GroupEntryItemData> groups;

	public List<GroupEntryItemData> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupEntryItemData> groups) {
		this.groups = groups;
	}

}
