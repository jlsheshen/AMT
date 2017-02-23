package com.edu.subject.entry.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.EditText;

import com.edu.subject.R;

/**
 * 分录中使用的EditText定义
 * @author lucher
 *
 */
public class EntryEditText extends EditText {

	public EntryEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			setHintTextColor(Color.GRAY);
		} else {
			setHintTextColor(Color.TRANSPARENT);
		}
	}
	
	/**
	 * 设置答案判断样式
	 * @param right 是否答对
	 */
	public void setJudgeStyle(boolean right) {
		if(right) {
			setBackgroundResource(R.drawable.shape_entry_edittext_right);
			setTextColor(Color.BLUE);
		} else {
			setBackgroundResource(R.drawable.shape_entry_edittext_wrong);
			setTextColor(Color.RED);
		}
	}
}
