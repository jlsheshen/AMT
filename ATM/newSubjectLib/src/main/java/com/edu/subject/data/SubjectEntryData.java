package com.edu.subject.data;

import com.alibaba.fastjson.JSON;
import com.edu.subject.data.body.EntryBodyData;

/**
 * 分录题目数据
 * 
 * @author lucher
 * 
 */
public class SubjectEntryData extends CommonSubjectData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234322L;
	//单据题目body内容
	private EntryBodyData entryBody;

	public EntryBodyData getEntryBody() {
		return entryBody;
	}

	public void setEntryBody(EntryBodyData entryBody) {
		this.entryBody = entryBody;
	}

	/**
	 * 制作body
	 */
	public void makeBody() {
		setEntryBody(JSON.parseObject(body, EntryBodyData.class));
	}
}
