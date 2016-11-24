package com.edu.library.data;

/**
 * 列表选择数据基类,与EduArrayAdapter配套使用
 * 
 * @author lucher
 * 
 */
public abstract class BaseListItemData extends BaseData {

	// 对应文字
	protected String text;
	
	public abstract String getText();

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return String.format("id:%s,text:%s", id, text);
	}
}
