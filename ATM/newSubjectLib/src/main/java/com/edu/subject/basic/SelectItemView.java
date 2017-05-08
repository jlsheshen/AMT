package com.edu.subject.basic;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edu.subject.R;
import com.edu.subject.common.rich.RichContentView;

/**
 * 单多判使用的选项视图
 * 
 * @author lucher
 * 
 */
public class SelectItemView extends LinearLayout {

	private static final String TAG = "SelectItemView";
	// 选项数据
	private OptionData mData;
	// true-选择题，false-判断题
	private boolean isSelect;
	// 是否多选
	private boolean isMulti;

	//选项标签
	private TextView tvLabel;
	//选项内容
	private RichContentView richContent;

	/**
	 * 构造
	 * 
	 * @param context
	 * @param data
	 *            选项数据
	 * @param select
	 *            是否为选择题
	 * @param multi
	 *            是否多选
	 */
	public SelectItemView(Context context) {
		super(context);
		init();
	}

	/**加载数据
	 * @param data
	 * @param select
	 * @param multi
	 */
	public void attach(OptionData data, boolean select, boolean multi) {
		mData = data;
		isSelect = select;
		isMulti = multi;
		if (isSelect) {// 选择题
			tvLabel.setText(mData.getFlag() + ".");
		}
		richContent.setRichData(mData.getOption());
		refreshSelectState();
	}

	/**
	 * 初始化
	 */
	private void init() {
		setFocusable(false);
		setFocusableInTouchMode(false);
		setOrientation(LinearLayout.HORIZONTAL);
		
		tvLabel = new TextView(getContext());
		addView(tvLabel);
		tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.select_item_size));
		richContent = new RichContentView(getContext());
		richContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.select_item_size));
		addView(richContent);
	}

	/**
	 * 刷新选择状态
	 */
	private void refreshSelectState() {
		if (mData.isEnabled()) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
		if (mData.isSelected()) {
			richContent.setTextColor(getResources().getColor(R.color.yellow));
			tvLabel.setTextColor(getResources().getColor(R.color.yellow));
		} else {
			richContent.setTextColor(getResources().getColor(R.color.blue));
			tvLabel.setTextColor(getResources().getColor(R.color.blue));
		}
	}

	/**
	 * 处理选择事件
	 */
	public void handleSelect() {
		if (mData.isSelected()) {// 当前选中，如果是多选，取消选中
			if (isMulti) {
				richContent.setTextColor(getResources().getColor(R.color.blue));
				tvLabel.setTextColor(getResources().getColor(R.color.blue));
				mData.setSelected(!mData.isSelected());
			}
		} else {// 当前未选中，变为选中
			richContent.setTextColor(getResources().getColor(R.color.yellow));
			tvLabel.setTextColor(getResources().getColor(R.color.yellow));
			mData.setSelected(!mData.isSelected());
		}

	}

	/**
	 * 获取选项数据
	 * @return
	 */
	public OptionData getOptionData() {
		return mData;
	}
}
