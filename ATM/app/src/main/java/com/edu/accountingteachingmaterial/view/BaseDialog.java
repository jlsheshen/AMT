package com.edu.accountingteachingmaterial.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Window;

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
		@Override
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
	 * 延时自动关闭对话框
	 * 
	 * @param delay
	 */
	public void closeDialogDelayed(long delay) {
		// delay ms后自动关闭
		handler.sendEmptyMessageDelayed(0, delay);
	}
}
