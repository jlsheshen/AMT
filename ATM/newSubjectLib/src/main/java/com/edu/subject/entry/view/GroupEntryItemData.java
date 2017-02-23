package com.edu.subject.entry.view;

import java.util.List;

import com.edu.subject.sort.SortableData;

/**
 * 一组借贷数据封装
 * @author lucher
 *
 */
public class GroupEntryItemData extends SortableData {

	//借方数据
	private List<EntryItemData> borrows;
	//贷方数据
	private List<EntryItemData> loans;

	public List<EntryItemData> getBorrows() {
		return borrows;
	}

	public void setBorrows(List<EntryItemData> borrows) {
		this.borrows = borrows;
	}

	public List<EntryItemData> getLoans() {
		return loans;
	}

	public void setLoans(List<EntryItemData> loans) {
		this.loans = loans;
	}

}
