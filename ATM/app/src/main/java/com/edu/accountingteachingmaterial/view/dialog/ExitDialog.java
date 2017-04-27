package com.edu.accountingteachingmaterial.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;

/**
 * Created by Administrator on 2016/12/5.
 */

public class ExitDialog extends Dialog implements android.view.View.OnClickListener {
    // 各控件定义
    Button btnOk;
    ImageView btnCancel;

    // 对话框相关监听
    private SetDialogListener mListener;

    private String info;
    private TextView tvTitle,tvText;
    private int type;
    public ExitDialog(Context context ,int type) {
        super(context);
        this.type = type;
        init();
    }

    private void init() {
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 窗口全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置窗口弹出动画
        getWindow().setWindowAnimations(R.style.TranAnimation);
        setContentView(R.layout.dialog_exit);
        setCancelable(false);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvText = (TextView) findViewById(R.id.tv_text);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancel = (ImageView) findViewById(R.id.btn_cancel);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (mListener != null) {
                    mListener.onOkClicked();
                }
                break;
            case R.id.btn_cancel:
                if (mListener != null) {
                    mListener.onCancelClicked();
                }
                break;
            default:
                dismiss();
                break;
        }
    }
    public void setTvTitle(String message){
        tvTitle.setText(message);
    }
    public void setTvText(String message){
        tvText.setText(message);
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setDialogListener(SetDialogListener listener) {
        mListener = listener;
    }

    /**
     * 设置对话框监听
     *
     * @author edu_lx
     */
    public interface SetDialogListener {

        /**
         * 确定点击
         */
        void onOkClicked();

        /**
         * 取消点击
         */
        void onCancelClicked();
    }
}
