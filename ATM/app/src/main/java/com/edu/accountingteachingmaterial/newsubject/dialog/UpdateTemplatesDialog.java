package com.edu.accountingteachingmaterial.newsubject.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/5/9.
 */

public class UpdateTemplatesDialog extends ProgressDialog {
    private static UpdateTemplatesDialog instance;

    public static UpdateTemplatesDialog getDialogInstance(Context context){
        if (instance == null){
            synchronized (UpdateTemplatesDialog.class) {
                if (instance ==null){
                    instance = new UpdateTemplatesDialog(context);

                }
            }
        }
        return instance;
    }


    public UpdateTemplatesDialog(Context context) {
        super(context);

       getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        setCancelable(false);
    }

    public UpdateTemplatesDialog(Context context, int theme) {
        super(context, theme);
    }
}
