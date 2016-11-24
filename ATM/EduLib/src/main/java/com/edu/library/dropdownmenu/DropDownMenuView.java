package com.edu.library.dropdownmenu;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.edu.library.R;
import com.edu.library.dropdownmenu.PopupMenuWindow.MenuItemListener;
import com.edu.library.util.DensityUtil;

/**
 * 自定义下拉菜单视图
 * 
 * @author lucher
 * 
 */
public class DropDownMenuView extends TextView implements OnClickListener, MenuItemListener, OnDismissListener {

	/**
	 * 菜单item显示的最大个数
	 */
	private static final int MAX_ITEM_COUNT = 5;

	/**
	 * 下拉菜单窗体
	 */
	private PopupMenuWindow mPopupWindow;

	private Context mContext;
	// 上下箭头
	Drawable arrowUp, arrowDown;

	private MenuItemListener mListener;
	// 菜单item个数
	private int itemCount;

	public DropDownMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		// 初始化箭头图标
		Resources res = mContext.getResources();
		arrowUp = res.getDrawable(R.drawable.arrow_up);
		arrowUp.setBounds(0, 0, arrowUp.getMinimumWidth(), arrowUp.getMinimumHeight());
		arrowDown = res.getDrawable(R.drawable.arrow_down);
		arrowDown.setBounds(0, 0, arrowDown.getMinimumWidth(), arrowDown.getMinimumHeight());

		mPopupWindow = new PopupMenuWindow(mContext);
		setOnClickListener(this);
		mPopupWindow.setItemListener(this);
		mPopupWindow.setOnDismissListener(this);

		setSingleLine(true);
		setBackgroundResource(R.drawable.tv_bg_cornor);
		int padding = DensityUtil.dip2px(mContext, 8);
		setPadding(padding, padding, padding, padding);
		setGravity(Gravity.CENTER);
		// 默认菜单窗体是关闭的
		menuWindowClosed();
	}

	/**
	 * 设置menu item数据
	 * 
	 * @param menuList
	 */
	public void setMenuItems(List<MenuItemData> menuList) {
		itemCount = menuList.size();
		mPopupWindow.refreshData(menuList, 0);
	}

	/**
	 * 显示下拉菜单
	 */
	private void showMenu() {
		mPopupWindow.setWidth(getWidth());
		mPopupWindow.setHeight(calcMenuWindowHeight());
		mPopupWindow.showAsDropDown(this);
		menuWindowOpened();
	}

	/**
	 * 计算菜单窗体的高度，根据item的个数来，最多显示MAX_ITEM_COUNT个item的高度
	 * 
	 * @return
	 */
	private int calcMenuWindowHeight() {
		int itemHeight = mPopupWindow.getMenuItemHeight();
		int count = Math.min(itemCount, MAX_ITEM_COUNT);
		return itemHeight * count + mPopupWindow.getListViewPadding();
	}

	@Override
	public void onClick(View v) {
		showMenu();
	}

	/**
	 * 获取当前选中项 - getSelectedItem 索引不好用； ytao add [2015-7-10]
	 * 
	 * @return
	 */
	// public int getSelectedIndex(){
	// return mPopupWindow.getSelectedItem();
	// }

	/**
	 * 设置菜单项相关监听
	 * 
	 * @param listener
	 */
	public void setMenuItemListener(MenuItemListener listener) {
		mListener = listener;
	}

	@Override
	public void onItemClick(int pos, MenuItemData itemData) {
		setText(itemData.getText());
		if (mListener != null) {
			mListener.onItemClick(pos, itemData);
		}
	}

	@Override
	public void onItemSelected(int pos, MenuItemData itemData) {
		if (mListener != null) {
			mListener.onItemSelected(pos, itemData);
		}
	}

	/**
	 * 菜单窗体打开
	 */
	private void menuWindowOpened() {
		setCompoundDrawables(null, null, arrowUp, null);
	}

	/**
	 * 菜单窗体关闭
	 */
	private void menuWindowClosed() {
		setCompoundDrawables(null, null, arrowDown, null);
	}

	@Override
	public void onDismiss() {
		menuWindowClosed();
	}

	/**
	 * 设置默认第一个选项为选中
	 */
	public void setDefaultItem() {
		int def = 0;
		if (mPopupWindow.mAdapter.getCount() > 0) {
			MenuItemData itemData = (MenuItemData) mPopupWindow.mAdapter.getData(def);
			setText(itemData.getText());
			if (mListener != null) {
				mListener.onItemClick(def, itemData);
			}
		}
	}

	/**
	 * 设置指定选项为选中
	 */
	public void setItem(int index) {
		if (mPopupWindow.mAdapter.getCount() > 0) {
			MenuItemData itemData = (MenuItemData) mPopupWindow.mAdapter.getData(index);
			if (itemData == null)
				return;
			setText(itemData.getText());
			if (mListener != null) {
				mListener.onItemClick(index, itemData);
			}
		}
	}
}
