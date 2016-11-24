package com.edu.accountingteachingmaterial.subject.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 录入数字的键盘
 *
 * @author lucher
 */
public class KeyboardView extends RelativeLayout implements OnClickListener, OnFocusChangeListener {

    private static final String TAG = "KeyboardView";

    // 当前与输入法窗口绑定的编辑框
    private EditText currentEt;

    /**
     * 绑定的所有编辑框
     */
    private List<EditText> ets;

    private Button numkeyzero, numkey1, numkey2, numkey3, numkey4, numkey5, numkey6, numkey7, numkey8, numkey9;
    private Button numkeydec, numkeynegative;
    private Button btnDelete, btnSure;
    // 控件的宽高
    private int width, height;
    public static int TITLE_HEIGHT = 0;
    /**
     * 金额点击事件
     */
    private OnClickListener amountClickListener;

    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_input_view, this);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        numkeyzero = (Button) findViewById(R.id.numkeyzero);
        numkey1 = (Button) findViewById(R.id.numkey1);
        numkey2 = (Button) findViewById(R.id.numkey2);
        numkey3 = (Button) findViewById(R.id.numkey3);
        numkey4 = (Button) findViewById(R.id.numkey4);
        numkey5 = (Button) findViewById(R.id.numkey5);
        numkey6 = (Button) findViewById(R.id.numkey6);
        numkey7 = (Button) findViewById(R.id.numkey7);
        numkey8 = (Button) findViewById(R.id.numkey8);
        numkey9 = (Button) findViewById(R.id.numkey9);
        numkeydec = (Button) findViewById(R.id.numkeydec);
        numkeynegative = (Button) findViewById(R.id.numkeynegative);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnSure = (Button) findViewById(R.id.btn_sure);

        numkeyzero.setOnClickListener(this);
        numkey1.setOnClickListener(this);
        numkey2.setOnClickListener(this);
        numkey3.setOnClickListener(this);
        numkey4.setOnClickListener(this);
        numkey5.setOnClickListener(this);
        numkey6.setOnClickListener(this);
        numkey7.setOnClickListener(this);
        numkey8.setOnClickListener(this);
        numkey9.setOnClickListener(this);
        numkeydec.setOnClickListener(this);
        numkeynegative.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        ets = new ArrayList<EditText>();

        amountClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                bindEditText((EditText) view);
                resetKeyboardLocation(view);
            }
        };
    }

    /**
     * 初始化大小
     */
    private void initSize() {
        setVisibility(View.VISIBLE);
        measure(0, 0);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    /**
     * 加入编辑框
     *
     * @param et
     */
    public void addEditText(EditText et) {
        if (!ets.contains(et)) {
            et.setOnFocusChangeListener(this);
            et.setOnClickListener(amountClickListener);
            ets.add(et);
        }
    }

    /**
     * 綁定edittext控件
     *
     * @param et
     */
    private void bindEditText(EditText et) {
        if (et == null || et == currentEt) {
            return;
        }
        this.currentEt = et;
    }

    /**
     * 插入指定text到绑定的edittext
     */
    private void insertText(String text) {
        if (currentEt == null)
            return;
        int index = currentEt.getSelectionStart();
        Editable editable = currentEt.getText();
        editable.insert(index, text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.numkeynegative:
                negativeData();

                break;
            case R.id.numkeydec:
                insertDec();

                break;
            case R.id.btn_delete:
                deleteNum();

                break;
            case R.id.btn_sure:
                hideKeyboard();

                break;

            default:
                TextView tv = (TextView) v;
                insertNum(tv.getText().toString());

                break;
        }

    }

    /**
     * 删除数字
     */
    private void deleteNum() {
        int index = currentEt.getSelectionStart();
        if (index > 0) {// 根据当前光标的位置进行删除操作
            Editable editable = currentEt.getText();
            editable.delete(index - 1, index);
        }
    }

    /**
     * 加入数字
     */
    private void insertNum(String num) {
        String text = currentEt.getText().toString();
        if (text.contains("-")) {
            text = text + num;
            currentEt.setText(text);
            currentEt.setSelection(text.length());
        } else {
            insertText(num);
        }
    }

    /**
     * 加入小数点
     */
    private void insertDec() {
        String text = currentEt.getText().toString();
        if (text.equals("")) {
            text = "0." + text;
            currentEt.setText(text);
            currentEt.setSelection(text.length());
        } else if (!text.contains(".")) {
            if (text.contains("-")) {
                if (text.equals("-")) {
                    text = text + "0.";
                    currentEt.setText(text);
                    currentEt.setSelection(text.length());
                } else {
                    text = text + ".";
                    currentEt.setText(text);
                    currentEt.setSelection(text.length());
                }
            } else {
                insertText(".");
            }
        }

    }

    /**
     * 切换正负号
     */
    private void negativeData() {
        String text = currentEt.getText().toString();
        if (text != null && !text.equals("")) {
            if (text.startsWith("-")) {
                text = text.replace("-", "");
            } else {
                text = "-" + text;
            }

            currentEt.setText(text);
            currentEt.setSelection(text.length());
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            bindEditText((EditText) view);
            resetKeyboardLocation(view);
        }
    }

    /**
     * 重新设置键盘的布局
     */
    private void resetKeyboardLocation(final View view) {
        if (getVisibility() != View.VISIBLE) {
            initSize();
        }
        Rect globalRect = new Rect();
        view.getGlobalVisibleRect(globalRect);

        // 注意，需要实时改变lp里的参数，否则在重绘的时候会回到初始位置

        LayoutParams lp = (LayoutParams) getLayoutParams();
        int screenHeight = PreferenceHelper.getInstance(view.getContext()).getIntValue(PreferenceHelper.SCREEN_HEIGHT);
        if (globalRect.bottom + height > screenHeight) {
            layout(550, 100, 909, 305);
            lp.setMargins(550, 100, 0, 0);
        } else {
            layout(globalRect.left, globalRect.bottom - TITLE_HEIGHT, globalRect.left + width, globalRect.bottom + height);
            lp.setMargins(globalRect.left, globalRect.bottom - TITLE_HEIGHT, 0, 0);
        }

    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        LayoutParams lp = (LayoutParams) getLayoutParams();
        lp.setMargins(-width, 0, 0, 0);
        layout(-width, 0, 0, height);
    }
}
