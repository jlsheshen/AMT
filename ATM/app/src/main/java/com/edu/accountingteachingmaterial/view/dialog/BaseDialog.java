package com.edu.accountingteachingmaterial.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
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
	public void show(int x,int y) {
 /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        /*
         * lp.x与lp.y表示相对于原始位置的偏移.
         * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
         * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
         * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
         * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
         * 当参数值包含Gravity.CENTER_HORIZONTAL时
         * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 当参数值包含Gravity.CENTER_VERTICAL时
         * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
         * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         *
         * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
         * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
         */
		lp.x = x; // 新位置X坐标
		lp.y = y + 100; // 新位置Y坐标
		Log.d("BaseDialog", "lp.y:" + lp.y);
		Log.d("BaseDialog", "lp.x:" + lp.x);
		dialogWindow.setAttributes(lp);
		super.show();
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
