package com.edu.subject.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自动换行，整齐排版的TextView，在开源控件基础上定制实现
 * 参考：http://www.cnblogs.com/snser/p/5159125.html
 * @author lucher
 *
 */
public class AutoSplitTextView extends TextView {
	private boolean mEnabled = true;
	private String mText = null;

	public AutoSplitTextView(Context context) {
		super(context);
	}

	public AutoSplitTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoSplitTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setAutoSplitEnabled(boolean enabled) {
		mEnabled = enabled;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//		if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY && getWidth() > 0 && getHeight() > 0 && mEnabled) {
//			String newText = autoSplitText();
//			if (!TextUtils.isEmpty(newText)) {
//				super.setText(newText);
//			}
//		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
if (getWidth() > 0 && getHeight() > 0 && mEnabled) {

	String newText = autoSplitText();
	if (!TextUtils.isEmpty(newText)) {
		super.setText(newText);
	}
}
	}

	public void setText(String text) {
		mText = text;
	}

	private String autoSplitText() {
		final Paint tvPaint = getPaint(); // paint，包含字体等信息
		final float tvWidth = getWidth() - getPaddingLeft() - getPaddingRight(); // 控件可用宽度

		// 将原始文本按行拆分
		String[] rawTextLines = mText.replaceAll("\r", "").split("\n");
		StringBuilder sbNewText = new StringBuilder();
		for (String rawTextLine : rawTextLines) {
			if (tvPaint.measureText(rawTextLine) <= tvWidth) {
				// 如果整行宽度在控件可用宽度之内，就不处理了
				sbNewText.append(rawTextLine);
			} else {
				// 如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
				float lineWidth = 0;
				for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
					char ch = rawTextLine.charAt(cnt);
					lineWidth += tvPaint.measureText(String.valueOf(ch));
					if (lineWidth <= tvWidth) {
						sbNewText.append(ch);
					} else {
						sbNewText.append("\n");
						lineWidth = 0;
						--cnt;
					}
				}
			}
			sbNewText.append("\n");
		}

		// 把结尾多余的\n去掉
		if (!mText.endsWith("\n")) {
			sbNewText.deleteCharAt(sbNewText.length() - 1);
		}

		return sbNewText.toString();
	}
}
