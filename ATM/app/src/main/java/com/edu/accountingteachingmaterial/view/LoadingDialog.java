package com.edu.accountingteachingmaterial.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.edu.accountingteachingmaterial.R;

/**
 * Created by Administrator on 2016/12/5.
 */

public class LoadingDialog extends Dialog {


    private ImageView imageView;

    public LoadingDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 窗口全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置窗口弹出动画
        getWindow().setWindowAnimations(com.edu.R.style.TranAnimation);
        setContentView(R.layout.dialog_boy_run);
        setCancelable(false);
        imageView = (ImageView) findViewById(R.id.dialog_boy_run_iv);
        //播放帧动画
        AnimationDrawable drawable = (AnimationDrawable) imageView.getDrawable();
        drawable.start();//播放
    }


    @Override
    public void show() {
        super.show();
    }

}
