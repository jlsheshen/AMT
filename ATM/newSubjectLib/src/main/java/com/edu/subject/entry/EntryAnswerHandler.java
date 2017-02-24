package com.edu.subject.entry;

import java.util.List;

import android.text.TextUtils;

import com.edu.subject.data.TestEntryData;
import com.edu.subject.data.answer.EntryAnswerData;
import com.edu.subject.entry.view.EntryItemData;
import com.edu.subject.entry.view.GroupEntryItemData;

/**
 * 分录题答案判分处理类
 * @author lucher
 *
 */
public class EntryAnswerHandler {

	/**
	 * 对分录答案进行判分操作
	 * @param testData
	 */
	public void judgeAnswer(TestEntryData testData) {
		EntryAnswerData answerData = testData.getUAnswerData();
		if (answerData != null) {
			//题目分录数据
			List<GroupEntryItemData> entrySubjectGroups = testData.getSubjectData().getEntryBody().getGroups();
			//用户答案分录数据
			List<GroupEntryItemData> entryAnswerGroups = answerData.getAnswers();

			//具体算分逻辑待确定，目前实现一个假判分逻辑
			for (GroupEntryItemData entryGroup : entryAnswerGroups) {
				List<EntryItemData> borrows = entryGroup.getBorrows();
				for (int i = 0; i < borrows.size(); i++) {
					EntryItemData entryItem = borrows.get(i);
					entryItem.setPrimaryRight(true);
					entryItem.setSecondaryRight(false);
					entryItem.setAmountRight(false);
				}
				List<EntryItemData> loans = entryGroup.getLoans();
				for (int i = 0; i < loans.size(); i++) {
					EntryItemData entryItem = loans.get(i);
					entryItem.setPrimaryRight(false);
					entryItem.setSecondaryRight(true);
					entryItem.setAmountRight(false);
				}
			}
		}
	}

	/**
	 * 获取格式化后的答案
	 * @param entryAnswerData
	 * @return
	 */
	public String getFormatedAnswer(EntryAnswerData entryAnswerData) {
		return getFormatedAnswer(entryAnswerData.getAnswers());
	}

	/**
	 * 获取格式化后的答案
	 * @param groups
	 * @return
	 */
	public String getFormatedAnswer(List<GroupEntryItemData> groups) {
		StringBuilder builder = new StringBuilder();
		if (groups != null) {
			//遍历各组，拼接答案
			for (int j = 0; j < groups.size(); j++) {
				GroupEntryItemData group = groups.get(j);
				//借方item
				List<EntryItemData> borrows = group.getBorrows();
				for (int i = 0; i < borrows.size(); i++) {
					EntryItemData item = borrows.get(i);
					if (i == 0) {
						builder.append("借：" + getItemAnswer(item));
					} else {
						builder.append("\n         " + getItemAnswer(item));
					}
				}
				//贷方item
				List<EntryItemData> loans = group.getLoans();
				for (int i = 0; i < loans.size(); i++) {
					EntryItemData item = loans.get(i);
					if (i == 0) {
						builder.append("\n        贷：" + getItemAnswer(item));
					} else {
						builder.append("\n                 " + getItemAnswer(item));
					}
				}
				if (j < groups.size() - 1) {
					builder.append("\n");
				}
			}
		}
		return builder.toString();
	}

	/**
	 * 获取item的答案
	 * @param item
	 * @return
	 */
	private String getItemAnswer(EntryItemData item) {
		return checkEmpty(item.getPrimary()) + "--" + checkEmpty(item.getSecondary()) + "    " + checkEmpty(item.getAmount());
	}

	/**
	 * 检查答案是否为空
	 * @param text
	 * @return
	 */
	private String checkEmpty(String text) {
		String result = "空";
		if (!TextUtils.isEmpty(text)) {
			result = text;
		}
		return result;
	}
}
