package com.edu.subject.blank;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.edu.subject.R;
import com.edu.subject.blank.FillInBlankView.BlankLayoutParams;

/**
 * 填空题控件里的空视图
 * @author lucher
 *
 */
public class BlankEditText extends EditText {

	public BlankEditText(Context context, float textSize) {
		super(context);
		setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		init();
	}

	private void init() {
		setPadding(2, 2, 2, 2);
		setSingleLine();
		setGravity(Gravity.CENTER);
		setImeOptions(EditorInfo.IME_ACTION_NEXT);
		setFocusableInTouchMode(true);
		setBackgroundResource(R.drawable.shape_simple_answer_edittext);
	}

	@Override
	public void setLayoutParams(LayoutParams params) {
		super.setLayoutParams(params);
		setWidth(params.width);
		setHeight(params.height);
	}

	@Override
	public String toString() {
		BlankLayoutParams lp = (BlankLayoutParams) getLayoutParams();
		return String.format("x:%s,y:%s,w:%s,h:%s", lp.getX(), lp.getY(), lp.getWidth(), lp.getHeight());
	}
}
