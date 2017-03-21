package com.edu.accountingteachingmaterial.view.dialog;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;


/**
 * 加入小组的dialog
 */
public class JoinGroupDialog extends BaseDialog implements View.OnClickListener {
	
	public static JoinGroupDialog intance;
	int pos;

	/**
	 * 返回键
	 */
	private Button btnOk;
	// 标题
	private TextView tvTitle;
	//內容
	private TextView tvContent;


	// 按钮点击监听
	private OnButtonClickListener mListener;
	
	public static JoinGroupDialog getIntance(Context context) {
		if (intance == null) {
			intance  = new JoinGroupDialog(context);
		}
		return intance;
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param
	 * @param
	 * 
	 */
	public JoinGroupDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_sure_or_cance);
		init();
	}
	


	public void setTitle(String title) {
		tvTitle.setText(title);

	}
	public void setTvContent(String content) {
		tvContent.setText(content);
	}


	/**
	 * 初始化
	 */
	private void init() {
		findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_text);
	}

	/**
	 * 设置按钮点击监听
	 * @param listener
	 * @param pos
	 */
	public void setOnButtonClickListener(OnButtonClickListener listener, int pos) {
		this.mListener = listener;
		this.pos = pos;
	}
	/**
	 * 设置按钮点击监听
	 * @param listener
	 */
	public void setOnButtonClickListener(OnButtonClickListener listener) {
		this.mListener = listener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			mListener.onOkClick(pos);
			break;
		case R.id.btn_cancel:
			mListener.onCancelClick();
			break;
		}
		
	}

	/**
	 * 按钮点击监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface OnButtonClickListener {
		/**
		 * 继续按钮点击
		 */
		public void onOkClick(int pos);

		public void onCancelClick();
			
	}
}
