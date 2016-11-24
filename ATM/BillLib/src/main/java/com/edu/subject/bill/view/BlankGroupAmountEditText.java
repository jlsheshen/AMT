package com.edu.subject.bill.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.Log;

import com.edu.subject.SubjectState;

/**
 * 小写金额组合空
 * 
 * @author lucher
 * 
 */
public class BlankGroupAmountEditText extends BlankEditText {

	private static final String TAG = "BlankGroupAmountEditText";

	// 金额空的长度，默认为10
	private int mLength = 10;

	// 画布
	private Canvas mCanvas;
	// 绘制文字的画笔
	private Paint textPaint;

	public BlankGroupAmountEditText(Context context, int testMode, int state) {
		super(context, testMode, state);
		initPaints();
	}

	/**
	 * 初始化画笔
	 */
	private void initPaints() {
		textPaint = new Paint();
		textPaint.setStyle(Style.STROKE);
		textPaint.setAntiAlias(true);
		textPaint.setTypeface(Typeface.DEFAULT);
	}

	@Override
	protected void setInputType() {
		try {
			mLength = Integer.parseInt(mData.getRemark());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "空数-remark字段必须为合法的数字：" + mData);
		}
		setFilters(new InputFilter[] { new LengthFilter(mLength) });
	}

	@Override
	public void postScale(float scale, int scaleTimes) {
		super.postScale(scale, scaleTimes);
	}

	@Override
	public void judgeAnswer() {
		if (!mData.isEditable()) {
			return;
		}
		if (mData.getuAnswer() != null) {
			if (mData.getAnswer().equals(mData.getuAnswer())) {
				mData.setRight(true);
			} else {
				mData.setRight(false);
			}
		}
	}

	/**
	 * 保存用户答案
	 */
	public void saveAnswer() {
		if (!mData.isEditable()) {
			return;
		}
		if (mState == SubjectState.STATE_INIT) {
			mState = SubjectState.STATE_UNFINISH;
		}
		mData.setuAnswer(getText().toString());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		mCanvas = canvas;
		drawText();
	}

	/**
	 * 绘制文本
	 */
	private void drawText() {
		textPaint.setTextSize(getTextSize());
		FontMetricsInt metr = textPaint.getFontMetricsInt();
		int startY = (getHeight() - metr.bottom - metr.top) / 2; // y坐标起始值
		float cellWidth = ((float) getWidth() / mLength);// 单元格宽度

		String text = getText().toString();
		char[] userChars = text.toCharArray();// 用戶输入的字符
		Log.d(TAG, "drawText:" + text + ",size:" + getTextSize() + ",width:" + getWidth());

		// 开始绘制
		int offsetIndex = mLength - userChars.length;// 绘制单元格起始索引，用于让内容居右显示
		for (int i = 0; i < userChars.length; i++) {
			float fontWidth = textPaint.measureText(String.valueOf(userChars[i]));// 字体宽度
			float offsetX = (cellWidth - fontWidth) / 2;// 单元格内x坐标的间距值，用于让字符在单元格里居中显示
			float startX = (offsetIndex + i) * cellWidth + offsetX;// 当前字符对应x坐标
			mCanvas.drawText(userChars, i, 1, startX, startY, textPaint);
//			Log.d(TAG, "char:" + userChars[i] + ",x:" + startX + ",y:" + startY + ",fWidth:" + fontWidth + ",cellWidth:" + cellWidth + ",offsetX:" + offsetX);
		}
	}
}
