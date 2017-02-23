package com.edu.subject.data.answer;

import java.util.List;

import com.edu.subject.entry.view.GroupEntryItemData;

/**
 * 分录题答案数据
 * @author lucher
 *
 */
public class EntryAnswerData extends CommonAnswerData {

	//多组借贷数据
	private List<GroupEntryItemData> answers;

	public List<GroupEntryItemData> getAnswers() {
		return answers;
	}

	public void setAnswers(List<GroupEntryItemData> answers) {
		this.answers = answers;
	}
}
