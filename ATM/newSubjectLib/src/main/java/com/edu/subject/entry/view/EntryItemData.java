package com.edu.subject.entry.view;

import com.edu.subject.sort.SortableData;

/**
 * 一个借或贷条目数据的封装
 * 
 * @author lucher
 * 
 */
public class EntryItemData extends SortableData {

	// 一级科目
	private String primary;
	// 二级科目
	private String secondary;
	// 金额
	private String amount;

	/************答案上传使用**************/
	//一级科目是否正确
	private boolean primaryRight;
	//二级科目是否正确
	private boolean secondaryRight;
	//金额是否正确
	private boolean amountRight;

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	public String getSecondary() {
		return secondary;
	}

	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public boolean isPrimaryRight() {
		return primaryRight;
	}

	public void setPrimaryRight(boolean isPrimaryRight) {
		this.primaryRight = isPrimaryRight;
	}

	public boolean isSecondaryRight() {
		return secondaryRight;
	}

	public void setSecondaryRight(boolean isSecondaryRight) {
		this.secondaryRight = isSecondaryRight;
	}

	public boolean isAmountRight() {
		return amountRight;
	}

	public void setAmountRight(boolean isAmountRight) {
		this.amountRight = isAmountRight;
	}

	@Override
	public String toString() {
		return String.format("%s-%s-%s", primary, secondary, amount);
	}
}
