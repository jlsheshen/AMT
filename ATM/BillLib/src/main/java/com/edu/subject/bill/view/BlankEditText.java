package com.edu.subject.bill.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.edu.R;
import com.edu.subject.SubjectConstant;
import com.edu.subject.SubjectState;
import com.edu.subject.TestMode;
import com.edu.subject.bill.BillAnswerHandler;
import com.edu.subject.bill.element.ElementLayoutParams;
import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.element.info.BlankInfo;
import com.edu.subject.bill.scale.IScaleable;
import com.edu.subject.bill.scale.ScaleUtil;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * 单据里对应空的封装,支持缩放
 * 
 * @author lucher
 * 
 */
public class BlankEditText extends EditText implements IScaleable {

	private static final String TAG = "BlankEditText";
	// 背景色-正常-灰色
	private static int BG_COLOR_NORMAL = 0x33000000;
	// 背景色-获取焦点
	private static int BG_COLOR_FOCUSED = 0x992e92e3;
	// 背景色-正确-蓝色
	private static int BG_COLOR_CORRECT = 0x990066FF;
	// 背景色-错误-红色
	private static int BG_COLOR_ERROR = 0x88ff0000;

	// 默认字体大小
	private static int DEFAULT_TEXT_SIZE = 16;

	// 对应空的信息
	protected BlankInfo mData;
	// 缩放比重
	private float mScaleWeight;

	// 是否初始化
	private boolean init;
	// 当前测试模式
	private int mTestMode;
	// 答题状态
	protected int mState;

	// 换行符
	private String newLine = "\n";

	public BlankEditText(Context context, int testMode, int state) {
		super(context);
		mTestMode = testMode;
		mState = state;
		init();
	}

	/**
	 * 初始化
	 */
	protected void init() {
		setPadding(1, 1, 1, 1);
		setGravity(Gravity.CENTER_VERTICAL);
		setTextColor(R.drawable.blank_edit_text_color_black);
		setImeOptions(EditorInfo.IME_ACTION_NEXT);
		setCursorVisible(false);
		setFocusableInTouchMode(true);
	}

	/**
	 * 根据空的不同类型初始化空的属性
	 */
	protected void initBlank() {
		if (init)
			return;
		setLines();
		setInputType();
		// 判断是否需要用户填写
		if (mData.isEditable()) {
			setFocusable(true);
			setBackgroundColor(BG_COLOR_NORMAL);
		} else {
			setFocusable(false);
			setTextChecked(mData.getAnswer());
			setBackground(null);
		}

		if (mTestMode == TestMode.MODE_SHOW_DETAILS) {// 查看详情
			judgeAnswer();
			showUAnswer(true);
		} else if (mTestMode == TestMode.MODE_PRACTICE) {// 练习
			// 状态初始化
			if (mState == SubjectState.STATE_CORRECT || mState == SubjectState.STATE_WRONG) {
				judgeAnswer();
				showUAnswer(true);
			} else {
				showUAnswer(true);
			}
		} else if (mTestMode == TestMode.MODE_EXAM) {// 测试模式
			showUAnswer(true);
		}else if (mTestMode == TestMode.MODE_LOOK) {// 测试模式
			showUAnswer(true);
		}

		init = !init;
	}

	/**
	 * 设置控件行数
	 */
	private void setLines() {
		setSingleLine();
		if (!BillAnswerHandler.isGroupBlank(mData.getType()) && mData.getType() != ElementType.TYPE_AMOUNT_LOWER_GROUP) {
			try {
				if (mData.getRemark() != null && !mData.getRemark().equals("")) {
					int lines = Integer.parseInt(mData.getRemark());
					if (lines > 1) {
						setSingleLine(false);
						addTextChangedListener(new MultiLineTextWather(lines));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG,"行数-remark字段必须为合法的数字："+mData);
			}
		}

	}

	/**
	 * 设置输入方式
	 */
	protected void setInputType() {
		switch (mData.getType()) {
		case ElementType.TYPE_DATE_UPPER:
		case ElementType.TYPE_NORMAL_CENTER:
		case ElementType.TYPE_AMOUNT_LOWER_CENTER:
			setGravity(Gravity.CENTER);

			break;
		case ElementType.TYPE_DATE_LOWER:
			setGravity(Gravity.CENTER);
			setInputType(InputType.TYPE_CLASS_NUMBER);
			// 对于年限制长度4位，月/日限制长度2位
			if (mData.getAnswer() != null) {
				int length = mData.getAnswer().length();
				if (length != 4) {
					length = 2;
				}
				setFilters(new InputFilter[] { new LengthFilter(length) });
			}

			break;
		case ElementType.TYPE_AMOUNT_LOWER_COMMA_CENTER:
			setGravity(Gravity.CENTER);
		case ElementType.TYPE_AMOUNT_LOWER_COMMA:
			addTextChangedListener(new CashierWatcher());
			setFilters(new InputFilter[] { new LengthFilter(18) });

			break;
		case ElementType.TYPE_AMOUNT_LOWER_SEP:
			setGravity(Gravity.CENTER);

			break;
		case ElementType.TYPE_VERTICAL:
			setSingleLine(false);
			setGravity(Gravity.CENTER_HORIZONTAL);
			setFilters(new InputFilter[] { new VerticalInputFilter() });

			break;
		default:
			break;
		}
	}

	@Override
	public void setTextSize(float size) {
		super.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);// 为了方便字体大小的自适应，采用px方式设置字体大小
		// setText(getText());// 如果不调用，设置字体大小后，内容可能不会居中
		int selection = getText().toString().length();
		setSelection(selection);// 设置把光标定位到最后
	}

	/**
	 * 重写设置文本方法，主要添加对特殊空的处理
	 * 
	 * @param text
	 */
	protected void setTextChecked(String text) {
		if (text == null) {
			super.setText(text);
		} else if (mData.getType() == ElementType.TYPE_VERTICAL) {// 竖直空
			StringBuilder builder = new StringBuilder();
			char[] chars = text.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (i == 0) {
					builder.append(chars[i]);
				} else {
					builder.append(newLine + chars[i]);
				}
			}
			super.setText(builder.toString());
		} else if (mData.getType() == ElementType.TYPE_MULTI_ANSWER) {// 多答案空
			if (mData.isRight()) {
				super.setText(mData.getuAnswer());
			} else {
				String[] answers = text.split(SubjectConstant.SEPARATOR_MULTI_ANSWER);
				super.setText(answers[0]);
			}
		} else {
			super.setText(text);
		}
	}

	/**
	 * 设置对应空数据，并把数据应用到该空上
	 * 
	 * @param data
	 *            数据
	 * @param textSize
	 *            字体大小
	 * @param scale
	 *            缩放比例
	 * @param scaleWeight
	 *            缩放比重
	 */
	public void apply(BlankInfo data, float scale, float scaleWeight) {
		mData = data;
		if (data.getTextSize() <= 0) {
			data.setTextSize(DEFAULT_TEXT_SIZE);
		}
		initBlank();

		mScaleWeight = scaleWeight;
		postScale(scale, 0);
	}

	/**
	 * 获取对应空数据
	 * 
	 * @return
	 */
	public BlankInfo getData() {
		return mData;
	}

	@Override
	public void postScale(float scale, int scaleTimes) {
		// 布局参数缩放
		int scaledX = ScaleUtil.getScaledValue(mData.getX(), scale);
		int scaledY = ScaleUtil.getScaledValue(mData.getY(), scale);
		int scaledWidth = ScaleUtil.getScaledValue(mData.getWidth(), scale);
		int scaledHeight = ScaleUtil.getScaledValue(mData.getHeight(), scale);
		setLayoutParams(new ElementLayoutParams(mData.getType(), scaledX, scaledY, scaledWidth, scaledHeight));
		// 字体缩放
		float textSize = mData.getTextSize() * (float) Math.pow(mScaleWeight, (scaleTimes + 1));
		setTextSize(textSize);

		// 宽高缩放-如果不缩放，可能引起内容显示不全
		setWidth(scaledWidth);
		setHeight(scaledHeight);
		// Log.d("lucher", "scaledHeight:" + scaledHeight);
	}

	/**
	 * 判断正误处理，先保存答案，然后判断，然后显示
	 * 
	 * @param submit
	 *            是否提交
	 */
	public void judge(boolean submit) {
		if (!mData.isEditable()) {
			return;
		}
		if (submit) {
			if (mData.isRight()) {
				mState = SubjectState.STATE_CORRECT;
			} else {
				mState = SubjectState.STATE_WRONG;
			}
		} else {
			mState = SubjectState.STATE_UNFINISH;
		}

		saveAnswer();
		judgeAnswer();
		if (submit) {
			showUAnswer(true);
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
		if (mData.getType() == ElementType.TYPE_VERTICAL) {// 去掉换行符
			mData.setuAnswer(getText().toString().replace(newLine, "").trim());
		} else {
			mData.setuAnswer(getText().toString().trim());
		}
	}

	/**
	 * 重置
	 */
	public void reset() {
		if (!mData.isEditable()) {
			return;
		}
		mState = SubjectState.STATE_INIT;
		mData.setuAnswer(null);
		mData.setRight(false);
		showUAnswer(true);
		setFocusableInTouchMode(true);
	}

	/**
	 * 判断答案正确与否
	 */
	public void judgeAnswer() {
		if (!mData.isEditable()) {
			return;
		}
		if (mData.getuAnswer() != null) {
			switch (mData.getType()) {
			case ElementType.TYPE_KEY_WORD:// 关键字匹配
				if (replaceSpace(mData.getuAnswer()).contains(replaceSpace(mData.getAnswer()))) {
					mData.setRight(true);
				} else {
					mData.setRight(false);
				}
				break;

			case ElementType.TYPE_MULTI_ANSWER:// 多个答案匹配
				String[] answers = mData.getAnswer().trim().split(SubjectConstant.SEPARATOR_MULTI_ANSWER);
				if (Arrays.asList(answers).contains(replaceSpace(mData.getuAnswer()))) {
					mData.setRight(true);
				} else {
					mData.setRight(false);
				}

				break;

			case ElementType.TYPE_AMOUNT_LOWER_COMMA:// 答案匹配
			case ElementType.TYPE_AMOUNT_LOWER_COMMA_CENTER:// 答案匹配
				if (replaceSpace(mData.getAnswer()).equals(replaceSpace(mData.getuAnswer()))) {
					mData.setRight(true);
				} else {
					mData.setRight(false);
				}
				break;
			default:// 答案对比匹配
				if (replaceSpace(mData.getAnswer()).equals(replaceSpace(mData.getuAnswer()))) {
					mData.setRight(true);
				} else {
					mData.setRight(false);
				}
				break;
			}
		}
	}

	/**
	 * String去空格,去掉前后空格以及中间空格
	 * 
	 * @param text
	 * @return
	 */
	private String replaceSpace(String text) {
		return text.replace(" ", "").trim();
	}

	/**
	 * 显示答案
	 * 
	 * @param user
	 *            true-显示用户答案，false-显示正确答案
	 */
	public void showUAnswer(boolean user) {
		if (!mData.isEditable()) {
			return;
		}
		if (user) {
			setTextChecked(mData.getuAnswer());
			refreshState();
		} else {
			setTextChecked(mData.getAnswer());
			setBackgroundColor(BG_COLOR_NORMAL);
		}
	}

	/**
	 * 刷新该空的作答状态
	 */
	public void refreshState() {
		if (!mData.isEditable()) {
			return;
		}
		if (mTestMode == TestMode.MODE_PRACTICE) {// 练习模式
			if (mState == SubjectState.STATE_INIT || mState == SubjectState.STATE_UNFINISH) {
				setFocusable(true);
				setBackgroundColor(BG_COLOR_NORMAL);
			} else {
				setFocusable(false);
				if (mData.isRight()) {
					setBackgroundColor(BG_COLOR_CORRECT);
				} else {
					setBackgroundColor(BG_COLOR_ERROR);
				}
			}

		} else if (mTestMode == TestMode.MODE_SHOW_DETAILS) {// 显示详情模式
			setFocusable(false);
			if (mData.isRight()) {
				setBackgroundColor(BG_COLOR_CORRECT);
			} else {
				setBackgroundColor(BG_COLOR_ERROR);
			}
		} else if (mTestMode == TestMode.MODE_EXAM) {// 考试模式
			setBackgroundColor(BG_COLOR_NORMAL);
		}else if(mTestMode == TestMode.MODE_LOOK){
			setBackgroundColor(BG_COLOR_NORMAL);
			setFocusable(false);
		}
	}

	// @Override
	// public boolean onKeyPreIme(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// clearFocus();// 解决readme中所说的输入法软件盘可能会遮挡的问题
	// }
	// return super.onKeyPreIme(keyCode, event);
	// }

	@Override
	public String toString() {
		return "BlankEditText：" + mData;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (focused) {
			// 设置获取焦点的背景色
			setBackgroundColor(BG_COLOR_FOCUSED);
		} else {
			setBackgroundColor(BG_COLOR_NORMAL);
		}
	}

	/**
	 * 竖直空filter
	 * 
	 * @author lucher
	 * 
	 */
	public class VerticalInputFilter implements InputFilter {

		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			String sourceText = source.toString();
			String destText = dest.toString();
			if (source.equals("")) {// 删除
				return "";
			} else {
				if (destText.length() > 0) {
					return newLine + sourceText;
				} else {
					return source;
				}
			}
		}

	}

	/**
	 * 金额控件监听类，用于加入逗号
	 * 
	 * @author lucher
	 * 
	 */
	public class CashierWatcher implements TextWatcher {

		// 人民币符号
		private String rmbSymbol = "￥";
		// 负号
		private String minusSymbol = "-";
		// 金额格式化
		private DecimalFormat dFormat = new DecimalFormat("###,###,###,###,###.##########");
		// 当前字符
		private String current;

		@Override
		public void afterTextChanged(Editable s) {
			if (current != null && !current.equals(getText().toString())) {
				setText(current);
				setSelection(getText().toString().length());
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			current = formatStr(s.toString().trim().replace(",", ""));
		}

		/**
		 * 格式化金额
		 * 
		 * @param str
		 * @return
		 */
		private String formatStr(String str) {
			String result = null;
			boolean containsRmb = false;
			boolean containsMinus = false;

			if (str.equals("")) {
				return "";
			}
			if (str.equals(rmbSymbol)) {
				return rmbSymbol;
			}
			if (str.equals(minusSymbol)) {
				return minusSymbol;
			}
			if (str.startsWith(rmbSymbol)) {
				containsRmb = true;
				str = str.substring(1, str.length());
			}else if (str.startsWith(minusSymbol)) {
				containsMinus = true;
				str = str.substring(1, str.length());
			}

			try {
				if (str.contains(".") && str.lastIndexOf(".") == str.length() - 4) {// 有两位小数情况
					result = current;
				} else if (str.lastIndexOf(".") == str.length() - 1) {// 小数点结尾
					result = dFormat.format(Double.parseDouble(str)) + ".";
				} else if (str.contains(".") && str.lastIndexOf("0") == str.length() - 1) {// 有小数点，且以0结尾
					result = formatStr(str.substring(0, str.length() - 1)) + "0";
				} else {
					result = dFormat.format(Double.parseDouble(str));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				result = current;
			}

			// 以人民币开头情况
			if (containsRmb && !result.startsWith(rmbSymbol)) {
				result = rmbSymbol + result;
			}else if(containsMinus && !result.startsWith(minusSymbol)){
				result = minusSymbol + result;
			}
			return result;
		}
	}

	/**
	 * 控制多行输入的监听类
	 * 
	 * @author lucher
	 * 
	 */
	public class MultiLineTextWather implements TextWatcher {

		private int maxLines = 2;

		public MultiLineTextWather(int lines) {
			maxLines = lines;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// 限制最大输入行数
			if (getLineCount() > maxLines) {
				String str = s.toString();
				int cursorStart = getSelectionStart();
				int cursorEnd = getSelectionEnd();
				if (cursorStart == cursorEnd && cursorStart < str.length() && cursorStart >= 1) {
					str = str.substring(0, cursorStart - 1) + str.substring(cursorStart);
				} else {
					str = str.substring(0, s.length() - 1);
				}
				// setText会触发afterTextChanged的递归
				setText(str);
				// setSelection用的索引不能使用str.length()否则会越界
				setSelection(getText().toString().length());
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}
	}
}
