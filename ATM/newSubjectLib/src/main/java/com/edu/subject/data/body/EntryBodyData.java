package com.edu.subject.data.body;

import com.edu.library.data.BaseData;
import com.edu.subject.entry.view.GroupEntryItemData;

import java.util.List;

/**
 * 分录题型body对象
 * @author lucher
 *
 */
public class EntryBodyData extends BaseData {

	//多组借贷数据
	private List<GroupEntryItemData> groups;

	public List<GroupEntryItemData> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupEntryItemData> groups) {
		this.groups = groups;
	}

}
