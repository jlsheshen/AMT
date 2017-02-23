package com.edu.subject.data.body;

import com.edu.subject.common.rich.RichTextData;

/**
 * 选择类题型的body对象，包括单多判
 * @author lucher
 *
 */
public class SelectBodyData extends RichTextData {

	//对应的id
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
