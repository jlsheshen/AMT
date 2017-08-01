package com.edu.subject.bill.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
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
	// 以空格开头答案或结尾的占位符前后缀
	public static final String ANSWER_FLAG = "*@*";
//	public static final String ANSWER_FLAG = "\\";

	// 金额空的长度，默认为10
	private int mLength = 10;

	// 画布
	private Canvas mCanvas;
	// 绘制文字的画笔
	private Paint textPaint;
	private Paint bgPaint;
	// 是否显示用户答案
	private boolean isUser;

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
		bgPaint = new Paint();
		bgPaint.setStyle(Style.FILL);
		bgPaint.setAntiAlias(true);
		bgPaint.setColor(Color.parseColor("#33000000"));
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
			String uAnswer = mData.getuAnswer();
			//去掉前后缀
			uAnswer = uAnswer.substring(ANSWER_FLAG.length());
			uAnswer = uAnswer.substring(0, uAnswer.length() - ANSWER_FLAG.length());
			// 用户答案去掉前面空格后进行答案判断
			uAnswer = (uAnswer + "*").trim();
			uAnswer = uAnswer.substring(0, uAnswer.length() - 1);
			if (mData.getAnswer().equals(uAnswer)) {
				mData.setRight(true);
			} else {
				mData.setRight(false);
			}
		}
	}

	/**
	 * 保存用户答案
	 */
	@Override
	public void saveAnswer() {
		if (!mData.isEditable()) {
			return;
		}
		if (mState == SubjectState.STATE_INIT) {
			mState = SubjectState.STATE_UNFINISH;
		}

		String answer = getText().toString();
		//剩余没填的空用空格补上
		int left = mLength - answer.length();
		for(int i=0;i<left;i++) {
			answer += " ";
		}
		answer = ANSWER_FLAG + answer + ANSWER_FLAG;// 前后用占位符，否则前后有空格的答案保存到数据库会丢失，从而影响答案的判断
		mData.setuAnswer(answer);
	}

	@Override
	public void showUAnswer(boolean user) {
		super.showUAnswer(user);
		isUser = user;
	}

	@Override
	protected void setTextChecked(String text) {
		if (text == null) {
			super.setText(text);
		} else if(text.startsWith(ANSWER_FLAG)) {
			//去除前后缀后显示
			text = text.substring(ANSWER_FLAG.length());
			text = text.substring(0, text.length() - ANSWER_FLAG.length());
			if(text.trim().equals("")) {//防止未做答保存后，默认用空字符串填充的问题
				super.setText("");
			} else {
				super.setText(text);
			}
		} else {
			super.setText(text);
		}
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
		int offsetIndex = 0;// 绘制单元格起始索引，用于让正确答案内容居右显示
		if (!isUser) {
			offsetIndex = mLength - userChars.length;
		}
		for (int i = 0; i < userChars.length; i++) {
			float fontWidth = textPaint.measureText(String.valueOf(userChars[i]));// 字体宽度
			float offsetX = (cellWidth - fontWidth) / 2;// 单元格内x坐标的间距值，用于让字符在单元格里居中显示
			float startX = (offsetIndex + i) * cellWidth + offsetX;// 当前字符对应x坐标

			// 如果以空格开头
			if (needBackground(i) && hasFocus()) {
				mCanvas.drawRect(new Rect((int) ((offsetIndex + i) * cellWidth), 0, (int) ((offsetIndex + i + 1) * cellWidth), getHeight()), bgPaint);
			}
			mCanvas.drawText(userChars, i, 1, startX, startY, textPaint);
		}
	}

	/**
	 * 当前字符是否需要绘制灰色背景，对于内容前面的空格需要，其他字符均不需要
	 * 
	 * @param index
	 * @return
	 */
	private boolean needBackground(int index) {
		String text = getText().toString().substring(0, index + 1).replace(" ", "");
		return text.equals("");
	}
}
