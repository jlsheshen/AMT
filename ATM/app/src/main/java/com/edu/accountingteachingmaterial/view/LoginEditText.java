package com.edu.accountingteachingmaterial.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by jl on 2017/6/1.
 */

public class LoginEditText extends EditText {
    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            clearFocus();
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
