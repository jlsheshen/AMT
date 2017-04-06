package com.edu.subject.common.rich;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 支持富文本显示的textview
 * 
 * @author lucher
 * 
 */
public class RichTextView extends TextView {

	private static final String TAG = "RichTextView";

	public RichTextView(Context context) {
		super(context);
	}

	public RichTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置文本
	 * @param text
	 */
	public void setRichText(String text) {
		String tmp =  text.replace("<p>", "").replace("</p>", "");
		super.setText(Html.fromHtml(tmp, null, new Img64TagHandler(getContext())));
	}
}
