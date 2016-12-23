package com.edu.accountingteachingmaterial.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2016/12/23.
 */

public abstract class BasePopupWindow extends PopupWindow {
    protected Context mContext;
    protected View conentView;
    protected WindowManager windowManager;


    public BasePopupWindow(final Activity context) {
        super(context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(setWindowLayout(), null);
        // 设置SPopupWindow的View


    }

    protected abstract int setWindowLayout();
}
