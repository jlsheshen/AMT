package com.edu.subject.bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectListener;
import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.view.BlankEditText;
import com.edu.subject.bill.view.ZoomableBillView;

/**
 * 单据焦点事件处理类
 * 
 * @author lucher
 * 
 */
public class FocusHandler implements OnKeyListener, OnFocusChangeListener, OnEditorActionListener {

	private static final String TAG = FocusHandler.class.getSimpleName();
	// 需要获取焦点的所有控件
	private List<View> mViews;

	// 当前获取焦点控件
	private View mCurrentView;
	// 当前获取焦点控件的index
	private int mCurrentIndex = -1;
	private Context mContext;
	private SubjectListener mListener;
	// 单据控件
	private ZoomableBillView mBillView;

	// 输入法管理
	private InputMethodManager imm;

	// 存放需要分组且自动跳转焦点的填空控件，key-分组id，value-对应组的组件
	private HashMap<Integer, List<BlankEditText>> mGroups;

	// 初始化后是否获取焦点，用于获取焦点是控件还没初始化的情况
	private boolean focusWhenInit;

	public FocusHandler(Context context, ZoomableBillView zoomableBillView) {
		mContext = context;
		mBillView = zoomableBillView;
		imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		mGroups = new HashMap<Integer, List<BlankEditText>>();
	}

	/**
	 * 设置填制完毕监听
	 * 
	 * @param listener
	 */
	public void setSubjectListener(SubjectListener listener) {
		mListener = listener;
	}

	/**
	 * 添加需要控制焦点的视图
	 * 
	 * @param blank
	 */
	public void add(BlankEditText blank) {
		if (!blank.getData().isEditable()) {// 不能编辑的空不需要添加
			return;
		}
		blank.setOnKeyListener(this);
		blank.setOnFocusChangeListener(this);
		blank.setOnEditorActionListener(this);
		// 独立金额空填写内容后自动跳转到下一空，删除内容后自动跳转到上一空
		if (blank.getData().getType() == ElementType.TYPE_AMOUNT_LOWER_SEP) {
			try {
				String remark = blank.getData().getRemark();
				int groupId = Integer.valueOf(remark);
				blank.addTextChangedListener(new AutoJumpWatcher(groupId, blank));
				if (mGroups.get(groupId) == null) {
					List<BlankEditText> list = new ArrayList<BlankEditText>(11);
					mGroups.put(groupId, list);
					list.add(blank);
				} else {
					mGroups.get(groupId).add(blank);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ToastUtil.showToast(mContext, blank + "，该空必须要有groupId");
			}
		}
		if (mViews == null) {
			mViews = new ArrayList<View>();
		}
		mViews.add(blank);
		if (focusWhenInit) {
			mViews.get(0).requestFocus();
		}
	}

	/**
	 * 获取默认焦点：第一个空
	 */
	public void requestDefaultFocus() {
		if (mViews != null && mViews.get(0) != null) {
			focusWhenInit = false;
			mViews.get(0).requestFocus();
		} else {
			focusWhenInit = true;
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (mViews == null) {
			return false;
		}

		boolean handleKeyEvent = (event.getAction() == KeyEvent.ACTION_UP);
		// 是否跳转到下一个空
		boolean next = keyCode == KeyEvent.KEYCODE_ENTER | keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER | keyCode == KeyEvent.KEYCODE_DPAD_RIGHT | keyCode == KeyEvent.KEYCODE_TAB
				| keyCode == KeyEvent.KEYCODE_DPAD_DOWN;
		// 是否跳转到上一个空
		boolean previous = keyCode == KeyEvent.KEYCODE_DPAD_LEFT | keyCode == KeyEvent.KEYCODE_DPAD_UP;
		// 是否截返回键
		boolean back = keyCode == KeyEvent.KEYCODE_BACK && mBillView.isSigning();

		// 需要切换焦点
		if (handleKeyEvent && next) {
			changeFocus(true);
		} else if (handleKeyEvent && previous) {
			changeFocus(false);
		} else if (handleKeyEvent && keyCode == KeyEvent.KEYCODE_DEL) {// 处理刪除按鍵
			handleDelete(v);
		} else if (handleKeyEvent && back) {// 处理返回键
			mBillView.cancelSign();
		}
		// 需要拦截的按键事件
		if (next || previous || back) {
			return true;
		}

		return false;
	}

	/**
	 * 处理删除事件
	 * 
	 * @param v
	 */
	private void handleDelete(View v) {
		((BlankEditText) v).setSelection(((BlankEditText) v).getText().toString().length());
		// 处理独立金额空删除自动跳转到前一个空的逻辑
		if (((BlankEditText) v).getData().getType() == ElementType.TYPE_AMOUNT_LOWER_SEP) {
			try {
				String remark = ((BlankEditText) v).getData().getRemark();
				int groupId = Integer.valueOf(remark);
				View view = getPreviousFocusView(groupId, (BlankEditText) v);
				if (view != null) {
					view.requestFocus();
				}
			} catch (Exception e) {
				e.printStackTrace();
				ToastUtil.showToast(mContext, v + ",该空必须要有groupId");
			}
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_NEXT) {
			changeFocus(true);
			return true;
		}
		return false;
	}

	/**
	 * 变换焦点
	 * 
	 * @param next
	 *            true-下一空，false-上一空
	 */
	private void changeFocus(boolean next) {
		if (next) {
			mCurrentView = getNextFocusView();
		} else {
			mCurrentView = getPreviousFocusView();
		}

		if (mCurrentView != null) {
			mCurrentView.requestFocus();
		}
	}

	/**
	 * 获取上一个焦点控件
	 * 
	 * @return
	 */
	View getPreviousFocusView() {
		View view = null;
		mCurrentIndex = mViews.indexOf(mCurrentView);
		int nextIndex = mCurrentIndex;
		try {
			int size = mViews.size();
			if (size <= 1) {
				nextIndex = 0;
			} else {
				if (mCurrentIndex == -1) {
					nextIndex = 0;
				} else {
					if (mCurrentIndex > 0) {
						nextIndex -= 1;
					} else {
						nextIndex = size - 1;
					}
				}
			}
			view = mViews.get(nextIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	/**
	 * 获取下一个焦点控件
	 * 
	 * @return
	 */
	View getNextFocusView() {
		View view = null;
		mCurrentIndex = mViews.indexOf(mCurrentView);
		int nextIndex = mCurrentIndex;
		try {
			int size = mViews.size();
			if (size <= 1) {
				nextIndex = 0;
			} else {
				if (mCurrentIndex == -1) {
					nextIndex = 0;
				} else {
					if (mCurrentIndex >= size - 1) {
						nextIndex = 0;
					} else {
						nextIndex += 1;
					}
				}
			}
			view = mViews.get(nextIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	/**
	 * 获取该组内上一个焦点控件
	 * 
	 * @param groupId
	 * @param blank
	 * @return
	 */
	private View getPreviousFocusView(int groupId, BlankEditText blank) {
		View view = null;
		int size = mGroups.get(groupId).size();
		int currentIndex = mGroups.get(groupId).indexOf(blank);
		int nextIndex = currentIndex;
		try {
			if (size <= 1) {
				nextIndex = 0;
			} else {
				if (currentIndex == -1) {
					nextIndex = 0;
				} else {
					if (currentIndex > 0) {
						nextIndex -= 1;
					} else {
						nextIndex = -1;
					}
				}
			}
			view = mGroups.get(groupId).get(nextIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	/**
	 * 获取该组内下一个焦点控件
	 * 
	 * @param groupId
	 * @param blank
	 * @return
	 */
	private View getNextFocusView(int groupId, BlankEditText blank) {
		View view = null;
		int size = mGroups.get(groupId).size();
		int currentIndex = mGroups.get(groupId).indexOf(blank);
		int nextIndex = currentIndex;
		try {
			if (size <= 1) {
				nextIndex = 0;
			} else {
				if (currentIndex == -1) {
					nextIndex = 0;
				} else {
					if (currentIndex >= size - 1) {
						nextIndex = -1;
					} else {
						nextIndex += 1;
					}
				}
			}
			view = mGroups.get(groupId).get(nextIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			mCurrentView = v;
			// 处理空获取焦点后，如果显示不全，自动滚动单据的操作
			mBillView.scrollToWrapBlank(v);
		}
	}

	/**
	 * 处理单击事件，主要用于处理空获取焦点以及弹出软件盘
	 * 
	 * @param e
	 *            touch事件对象
	 * @param border
	 *            当前单据的边界值，用于计算
	 */
	public void handleSingleTab(MotionEvent e, int[] border) {
		float x = e.getX();
		float y = e.getY();
		Log.d("lucher", "x:" + x + ",y" + y);
		for (View view : mViews) {
			int left = view.getLeft() - border[0];
			int top = view.getTop() - border[1];
			int right = view.getRight() - border[0];
			int bottom = view.getBottom() - border[1];
			Rect rect = new Rect(left, top, right, bottom);
			if (rect.contains((int) x, (int) y)) {// 找到当前点击的空
				Log.e("lucher", "find view" + view);
				requestFocus(view);
				break;
			}
		}
	}

	/**
	 * 获取焦点
	 * 
	 * @param view
	 */
	private void requestFocus(View view) {
		view.requestFocus();
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 自动跳转watcher，填写内容后自动跳转到下一空，删除内容后自动跳转到上一空
	 * 
	 * @author lucher
	 * 
	 */
	public class AutoJumpWatcher implements TextWatcher {

		// 对应控件
		private BlankEditText mBlank;
		// 对应的组id
		private int mGroupId;

		public AutoJumpWatcher(int groupId, BlankEditText blank) {
			mGroupId = groupId;
			mBlank = blank;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// Log.d("lucher", "afterTextChanged ：" + s);
			// 内容替换
			if (s.length() > 1) {
				mBlank.setText("");
				Editable editable = mBlank.getText();
				editable.append(s.subSequence(s.length() - 1, s.length()));
			}
			// 切换焦点
			if (!s.toString().equals("")) {
				View view = getNextFocusView(mGroupId, mBlank);
				if (view != null) {
					view.requestFocus();
				}
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

	}
}
