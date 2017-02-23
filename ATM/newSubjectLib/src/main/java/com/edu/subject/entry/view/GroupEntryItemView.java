package com.edu.subject.entry.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.edu.library.util.ToastUtil;
import com.edu.subject.R;
import com.edu.subject.entry.EntryType;
import com.edu.subject.entry.view.EntryItemView.EntryItemListener;

/**
 * 一组借贷视图封装
 * @author lucher
 *
 */
public class GroupEntryItemView extends LinearLayout {

	/**
	 * 一组借贷视图包含条目的最大个数
	 */
	public static int MAX_ITEM_COUNT = 10;
	//视图id
	private int id;

	//借贷视图容器
	private LinearLayout borrowContainer;
	private LinearLayout loanContainer;

	//EntryItem相关监听
	private EntryItemListener mListener;

	public GroupEntryItemView(Context context, int id, EntryItemListener listener) {
		super(context);
		setId(id);
		mListener = listener;
		init();
	}

	/**
	 * 初始化用户答案，包括控件初始化
	 * @param data
	 * @param judge 
	 */
	public void initUAnswer(GroupEntryItemData data, boolean judge) {
		//借方item初始化
		borrowContainer.removeAllViews();
		List<EntryItemData> borrows = data.getBorrows();
		for (int i = 0; i < borrows.size(); i++) {
			EntryItemView entryItem = addEntryItem(EntryType.BORROW, i == 0);
			entryItem.setUAswer(borrows.get(i), judge);
		}
		//贷方item初始化
		loanContainer.removeAllViews();
		List<EntryItemData> loans = data.getLoans();
		for (int i = 0; i < loans.size(); i++) {
			EntryItemView entryItem = addEntryItem(EntryType.LOAN, i == 0);
			entryItem.setUAswer(loans.get(i), judge);
		}
	}

	/**
	 * 刷新用户答案
	 * @param data
	 * @param judge 
	 */
	public void refreshUAnswer(GroupEntryItemData data, boolean judge) {
		//借方item
		List<EntryItemData> borrows = data.getBorrows();
		for (int i = 0; i < borrows.size(); i++) {
			EntryItemView entryItem = (EntryItemView) borrowContainer.getChildAt(i);
			entryItem.setUAswer(borrows.get(i), judge);
		}
		//贷方item
		List<EntryItemData> loans = data.getLoans();
		for (int i = 0; i < loans.size(); i++) {
			EntryItemView entryItem = (EntryItemView) loanContainer.getChildAt(i);
			entryItem.setUAswer(loans.get(i), judge);
		}
	}

	/**
	 * 初始化
	 */
	private void init() {
		View.inflate(getContext(), R.layout.layout_entry_group, this);
		borrowContainer = (LinearLayout) findViewById(R.id.borrowContainer);
		loanContainer = (LinearLayout) findViewById(R.id.loanContainer);

		createDefaultEntry();
	}

	/**
	 * 创建默认分录，一组借贷视图
	 */
	private void createDefaultEntry() {
		addEntryItem(EntryType.BORROW, true);
		addEntryItem(EntryType.LOAN, true);
	}

	/**
	 * 加入一个EntryItem
	 * @param type
	 * @param root
	 * @return
	 */
	public EntryItemView addEntryItem(int type, boolean root) {
		EntryItemView entryItem = new EntryItemView(getContext(), 1, type, root);
		entryItem.setEntryItemListener(mListener);
		if (type == EntryType.BORROW) {
			if (borrowContainer.getChildCount() >= MAX_ITEM_COUNT) {
				ToastUtil.showToast(getContext(), "每组分录最多只支持" + MAX_ITEM_COUNT + "个借方条目");
			} else {
				borrowContainer.addView(entryItem);
			}
		} else if (type == EntryType.LOAN) {
			if (loanContainer.getChildCount() >= MAX_ITEM_COUNT) {
				ToastUtil.showToast(getContext(), "每组分录最多只支持" + MAX_ITEM_COUNT + "个贷方条目");
			} else {
				loanContainer.addView(entryItem);
			}
		}
		return entryItem;
	}

	/**
	 * 移除指定EntryItem
	 * @param type
	 * @param entryItem
	 */
	public void removeEntryItem(int type, EntryItemView entryItem) {
		if (type == EntryType.BORROW) {
			borrowContainer.removeView(entryItem);
		} else if (type == EntryType.LOAN) {
			loanContainer.removeView(entryItem);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 获取用户答案
	 * @return
	 */
	public GroupEntryItemData getUserAnswer() {
		GroupEntryItemData groupAnswer = new GroupEntryItemData();
		//借方答案获取
		int borrowCount = borrowContainer.getChildCount();
		List<EntryItemData> borrows = new ArrayList<EntryItemData>(borrowCount);
		for (int i = 0; i < borrowCount; i++) {
			EntryItemView entryItem = (EntryItemView) borrowContainer.getChildAt(i);
			EntryItemData entryItemAnswer = entryItem.getUserAnswer();
			entryItemAnswer.setOrder(i + 1);
			borrows.add(entryItemAnswer);
		}
		groupAnswer.setBorrows(borrows);

		//贷方答案获取
		int loanCount = loanContainer.getChildCount();
		List<EntryItemData> loans = new ArrayList<EntryItemData>(loanCount);
		for (int i = 0; i < loanCount; i++) {
			EntryItemView entryItem = (EntryItemView) loanContainer.getChildAt(i);
			EntryItemData entryItemAnswer = entryItem.getUserAnswer();
			entryItemAnswer.setOrder(i + 1);
			loans.add(entryItemAnswer);
		}
		groupAnswer.setBorrows(borrows);
		groupAnswer.setLoans(loans);
		return groupAnswer;
	}

	@Override
	public void setEnabled(boolean enabled) {
		int borrowCount = borrowContainer.getChildCount();
		for (int i = 0; i < borrowCount; i++) {
			EntryItemView entryItem = (EntryItemView) borrowContainer.getChildAt(i);
			entryItem.setEnabled(enabled);
		}
		int loanCount = loanContainer.getChildCount();
		for (int i = 0; i < loanCount; i++) {
			EntryItemView entryItem = (EntryItemView) loanContainer.getChildAt(i);
			entryItem.setEnabled(enabled);
		}
	}
}
