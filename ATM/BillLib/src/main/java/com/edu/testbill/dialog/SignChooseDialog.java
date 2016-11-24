package com.edu.testbill.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.edu.R;
import com.edu.subject.data.SignData;
import com.edu.testbill.adapter.SignsAdapter;

import java.util.List;

/**
 * 印章选择对话框
 * 
 * @author lucher
 * 
 */
public class SignChooseDialog extends Dialog implements View.OnClickListener {

	// 取消按钮
	private Button btnCancel;
	private Context mContext;
	// 印章表格
	private GridView gvSigns;
	// 印章数据
	private List<SignData> datas;

	private OnItemClickListener itemClickListener;

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public SignChooseDialog(Context context, List<SignData> datas, OnItemClickListener itemClickListener) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.dialog_sign_select);
		
		mContext = context;
		this.datas = datas;
		this.itemClickListener = itemClickListener;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		btnCancel = (Button) this.findViewById(R.id.btn_cancel);
		gvSigns = (GridView) this.findViewById(R.id.gv_sign);
		btnCancel.setOnClickListener(this);
		SignsAdapter signsAdapter = new SignsAdapter(mContext, datas);
		gvSigns.setAdapter(signsAdapter);
		gvSigns.setOnItemClickListener(itemClickListener);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn_cancel){
			this.dismiss();
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
		 * 点击取消
		 */
		public void onCancelClicked();

	}
}
