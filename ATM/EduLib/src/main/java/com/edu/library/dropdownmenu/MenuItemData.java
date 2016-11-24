package com.edu.library.dropdownmenu;

/**
 * 菜单项数据封装
 * 
 * @author lucher
 * 
 */
public class MenuItemData {

	// 数据对应的id
	private String id;
	// 数据对应的文本内容
	private String text;

	public MenuItemData() {
	}

	public MenuItemData(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		// 返回的内容为在menu item里的显示内容
		return text;
	}
}
