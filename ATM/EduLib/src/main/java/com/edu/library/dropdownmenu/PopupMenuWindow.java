package com.edu.library.dropdownmenu;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.edu.library.R;

/**
 * 自定义下拉菜单控件的显示窗体
 * 
 * @author lucher
 * 
 */
public class PopupMenuWindow extends PopupWindow implements OnItemClickListener {

	private Context mContext;
	// 显示数据的list
	private ListView mListView;
	// mListView对应的adapter
	public PopupMenuAdapter mAdapter;
	// menu item的监听
	private MenuItemListener mListener;
	private View mView;

	private int mSelectedItem;

	public PopupMenuWindow(Context context) {
		super(context);

		mContext = context;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		mView = LayoutInflater.from(mContext).inflate(R.layout.popup_menu_layout, null);
		setContentView(mView);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());

		// 支持键盘选择item
		mView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN && event.getAction() == KeyEvent.ACTION_DOWN) {
					mSelectedItem = mAdapter.getSelectItem();
					if (mSelectedItem < mAdapter.getCount() - 1) {
						mAdapter.setSelectItem(++mSelectedItem);
						mListView.setSelection(mSelectedItem);
						mAdapter.notifyDataSetChanged();
					}
				} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP && event.getAction() == KeyEvent.ACTION_DOWN) {
					mSelectedItem = mAdapter.getSelectItem();
					if (mSelectedItem > 0) {
						mAdapter.setSelectItem(--mSelectedItem);
						mListView.setSelection(mSelectedItem);
						mAdapter.notifyDataSetChanged();
					}
				} else if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
					mSelectedItem = mAdapter.getSelectItem();
					dismiss();
					if (mListener != null) {
						MenuItemData item = (MenuItemData) v.getTag();
						mListener.onItemSelected(mSelectedItem, item);
					}
				}
				return true;
			}
		});

		setFocusable(true);

		mListView = (ListView) mView.findViewById(R.id.listview);
		mAdapter = new PopupMenuAdapter(mContext);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	/**
	 * 刷新数据
	 * 
	 * @param list
	 * @param selIndex
	 *            默认选中的item索引
	 */
	public void refreshData(List<MenuItemData> list, int selIndex) {
		if (list != null && selIndex != -1) {
			mAdapter.refreshData(list, selIndex);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		if (mListener != null) {
			MenuItemData item = (MenuItemData) view.getTag();
			mListener.onItemClick(pos, item);
		}
	}

	/**
	 * 获取当前选中项 ytao add [2015-7-10]
	 * 
	 * @return
	 */
	public int getSelectedItem() {
		return mSelectedItem;
	}

	/**
	 * 获取菜单item高度
	 * 
	 * @return
	 */
	public int getMenuItemHeight() {
		return mAdapter.getItemHeight();
	}

	/**
	 * 控件所有的上下间距
	 * 
	 * @return
	 */
	public int getListViewPadding() {
		return mListView.getPaddingTop() + mListView.getPaddingBottom() + mView.getPaddingTop() + mView.getPaddingBottom();
	}

	/**
	 * 设置菜单项监听
	 * 
	 * @param listener
	 */
	public void setItemListener(MenuItemListener listener) {
		mListener = listener;
	}

	/**
	 * item点击，选择监听
	 * 
	 * @author lucher
	 * 
	 */
	public static interface MenuItemListener {
		/**
		 * item点击
		 * 
		 * @param pos
		 *            选中项索引
		 * @param itemData
		 *            选中项数据
		 */
		public void onItemClick(int pos, MenuItemData itemData);

		/**
		 * item选中
		 * 
		 * @param pos
		 *            选中项索引
		 * @param itemData
		 *            选中项数据
		 */
		public void onItemSelected(int pos, MenuItemData itemData);
	}
}
