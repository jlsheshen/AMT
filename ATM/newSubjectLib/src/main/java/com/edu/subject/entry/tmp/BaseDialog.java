package com.edu.subject.entry.tmp;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * 对话框基类，主要自定义一些样式
 * 
 * @author lucher
 * 
 */
public class BaseDialog extends Dialog {

	protected Context mContext;
	/**
	 * 对话框的window
	 */
	protected Window dialogWindow = getWindow();

	/**
	 * 处理对话框的自动关闭
	 */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dismiss();
		};
	};

	public BaseDialog(Context context) {
		super(context);
		mContext = context;

		initDialogState();
	}

	/**
	 * 初始化对话框的状态
	 */
	private void initDialogState() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}

	/**
	 * 设置对话框全屏显示
	 */
	public void setFullScreen() {
		// 设置宽度铺满屏幕
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		dialogWindow.setAttributes(lp);
	}

	/**
	 * 设置对话框宽全屏显示
	 */
	public void setWidthFullScreen() {
		// 设置宽度铺满屏幕
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		dialogWindow.setAttributes(lp);
	}

	/**
	 * 延时自动关闭对话框
	 * 
	 * @param delay
	 */
	public void closeDialogDelayed(long delay) {
		// delay ms后自动关闭
		handler.sendEmptyMessageDelayed(0, delay);
	}
}
