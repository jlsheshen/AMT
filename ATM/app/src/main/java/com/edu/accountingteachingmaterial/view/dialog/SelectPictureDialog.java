package com.edu.accountingteachingmaterial.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
/**
 * 加入小组的dialog
 */
public class SelectPictureDialog extends BaseDialog implements View.OnClickListener {

	public static SelectPictureDialog intance;
	int pos;
	Context context;



	private Button albumBtn,photographBtn;
	// 标题
	private TextView tvTitle;
	//內容
	// 按钮点击监听
	private OnButtonClickListener mListener;

	public static SelectPictureDialog getIntance(Context context) {
		if (intance == null) {
			intance  = new SelectPictureDialog(context);
		}
		return intance;
	}

	/**
	 * 构造方法
	 * @param context
	 * @param
	 * @param
	 *
	 */
	public SelectPictureDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_chose_photo);
		init();
	}
	


	public void setTitle(String title) {
		tvTitle.setText(title);
	}


	/**
	 * 初始化
	 */
	private void init() {
		findViewById(R.id.select_in_album).setOnClickListener(this);
        findViewById(R.id.select_photograph).setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_text);
		setCanceledOnTouchOutside(true);
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
		case R.id.select_photograph:
			mListener.onPhotograph();
			break;
		case R.id.select_in_album:
			mListener.onAlbum();
			break;
		}
		
	}

	@Override
	public void setOnDismissListener(OnDismissListener listener) {

		super.setOnDismissListener(listener);
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
		public void onPhotograph();

		public void onAlbum();
			
	}
}
