package com.edu.subject.entry.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edu.subject.R;
import com.edu.subject.entry.EntryType;
import com.edu.subject.entry.view.EntrySubjectEditText.ArrowClickListener;

/**
 * 对一个借贷条目视图的封装
 * 
 * @author lucher
 * 
 */
public class EntryItemView extends LinearLayout implements OnClickListener, ArrowClickListener {

	// item对应的id
	private int id;

	// 标签，借或贷
	private TextView tvLabel;
	// 一级科目
	private EntrySubjectEditText etPSubject;
	// 二级科目
	private EntrySubjectEditText etSSubject;
	// 金额
	private EntryEditText etAmount;
	// 添加或移除按钮
	private ImageButton btnAddOrRemove;

	// 是否为根节点
	private boolean mRoot;
	// 借贷类别
	public int mType;

	private EntryItemListener mListener;

	/**
	 * @param context
	 * @param id
	 * @param type 借贷类型
	 * @param root 是否为根节点
	 */
	public EntryItemView(Context context, int id, int type, boolean root) {
		super(context);
		setId(id);
		mType = type;
		mRoot = root;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		View.inflate(getContext(), R.layout.layout_entry_item, this);
		initView();
		initState(mType);
	}

	/**
	 * 初始化视图
	 * 
	 * @param view
	 */
	private void initView() {
		tvLabel = (TextView) findViewById(R.id.tvLabel);
		etPSubject = (EntrySubjectEditText) findViewById(R.id.etPrimary);
		etSSubject = (EntrySubjectEditText) findViewById(R.id.etSecondary);
		etAmount = (EntryEditText) findViewById(R.id.etAmount);
		btnAddOrRemove = (ImageButton) findViewById(R.id.btnAddOrRemove);
		etPSubject.setOnArrowClickListener(this);
		etSSubject.setOnArrowClickListener(this);
		btnAddOrRemove.setOnClickListener(this);
	}

	/**
	 * 初始化状态
	 */
	private void initState(int type) {
		if (type == EntryType.BORROW) {
			tvLabel.setText("借：");
		} else if (type == EntryType.LOAN) {
			tvLabel.setText("贷：");
		}
		if (mRoot) {// 根节点的item只能新增item
			btnAddOrRemove.setImageResource(R.drawable.btn_selector_plus);
			tvLabel.setVisibility(View.VISIBLE);
		} else {// 子节点的item只能移除自身
			tvLabel.setVisibility(View.INVISIBLE);
			btnAddOrRemove.setImageResource(R.drawable.btn_selector_minus);
		}
	}

	/**
	 * 设置用户答案
	 * @param entryItemData
	 * @param judge 
	 */
	public void setUAswer(EntryItemData entryItemData, boolean judge) {
		etPSubject.setText(entryItemData.getPrimary());
		etSSubject.setText(entryItemData.getSecondary());
		etAmount.setText(entryItemData.getAmount());
		if (judge) {
			setJudgeStyle(entryItemData.isPrimaryRight(), entryItemData.isSecondaryRight(), entryItemData.isAmountRight());
		}
	}

	/**
	 * 设置空为判断模式
	 * 
	 * @param primarySubject
	 * @param sencondarySubject
	 * @param amount
	 */
	public void setJudgeStyle(boolean primarySubject, boolean sencondarySubject, boolean amount) {
		etPSubject.setJudgeStyle(primarySubject);
		etSSubject.setJudgeStyle(sencondarySubject);
		etAmount.setJudgeStyle(amount);
	}

	@Override
	public void onClick(View view) {
		if (mListener != null) {
			if (view == btnAddOrRemove) {
				if (mRoot) {
					mListener.onItemAdd(mType, this);
				} else {
					mListener.onItemRemove(mType, this);
				}
			} else if (view == etPSubject) {
				mListener.onPSubjectClicked(etPSubject);
			} else if (view == etSSubject) {
				mListener.onSSubjectClicked(etSSubject);
			}
		}
	}

	/**
	 * 获取用户答案
	 * 
	 * @return
	 */
	public EntryItemData getUserAnswer() {
		EntryItemData itemData = new EntryItemData();
		itemData.setPrimary(etPSubject.getText().toString().trim());
		itemData.setSecondary(etSSubject.getText().toString().trim());
		itemData.setAmount(etAmount.getText().toString().trim());
		return itemData;
	}

	@Override
	public void setEnabled(boolean enabled) {
		etPSubject.setEnabled(enabled);
		etSSubject.setEnabled(enabled);
		etAmount.setEnabled(enabled);
		if (enabled) {
			btnAddOrRemove.setVisibility(View.VISIBLE);
		} else {
			btnAddOrRemove.setVisibility(View.INVISIBLE);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEntryItemListener(EntryItemListener listener) {
		mListener = listener;
	}

	/**
	 * 借贷item监听器
	 * @author lucher
	 *
	 */
	public interface EntryItemListener {
		/**
		 * 添加按钮点击
		 * @param type
		 * @param entryItem
		 */
		void onItemAdd(int type, EntryItemView entryItem);

		/**
		 * 移除按钮点击
		 * @param type
		 * @param entryItem
		 */
		void onItemRemove(int type, EntryItemView entryItem);

		/**
		 * 一级科目点击
		 * @param editText
		 */
		void onPSubjectClicked(EntryEditText editText);

		/**
		 * 二级科目点击
		 * @param editText
		 */
		void onSSubjectClicked(EntryEditText editText);
	}

}