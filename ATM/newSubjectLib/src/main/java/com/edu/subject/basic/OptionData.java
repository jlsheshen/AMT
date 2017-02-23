package com.edu.subject.basic;

import com.edu.subject.data.body.SelectBodyData;


/**
 * 选项数据封装
 * 
 * @author lucher
 * 
 */
public class OptionData {
	// 选项内容
	private SelectBodyData option;
	// 是否选中
	private boolean selected;
	// 是否可用
	private boolean enabled = true;

	// 选项标识，例如ABCD，对错
	private String flag;

	public OptionData(SelectBodyData data) {
		option = data;
	}

	public SelectBodyData getOption() {
		return option;
	}

	public void setOption(SelectBodyData option) {
		this.option = option;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return String.format("id:%s,option:%s,selected:%s", option.getId(), option, selected);
	}
}
