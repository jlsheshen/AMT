package com.edu.subject.blank;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectConstant;
import com.edu.subject.util.StringUtil;

/**
 * 填空控件
 * 该控件只支持\n换行符
 * 关于text绘制相关知识可参考http://blog.csdn.net/hursing/article/details/18703599
 * @author lucher
 *
 */
public class FillInBlankView extends ViewGroup {

	private static final String TAG = "FillInBlankView";
	//填空题对应的题目
	private String mQuestion;
	//填空题的正确答案
	private List<String> mAnswers;

	//把题目的文本部分解析为TextInfo对象
	private List<TextInfo> mTextInfos;
	//填空题所有空的编辑框
	private List<EditText> mBlanks;
	//题目画笔
	private Paint mTextPaint;

	//空的宽度在正确答案基础上默认增加的字符数
	private int blankWidthExtra = 2;

	//控件宽度
	private int mWidth;
	//控件高度
	private int mHeight;
	//字体宽度
	private int mFontWidth;
	//字体高度
	private int mFontHeight;
	//行高
	private int lineHeight;

	private Context mContex;
	//是否初始化
	private boolean mInit;
	//字体大小
	private float mTextSize;
	//文字绘制基线,具体概念参考类头注释
	private int mBaseline;

	/**
	 * @param context
	 * @param question 题目
	 * @param answers 空对应的正确答案
	 */
	public FillInBlankView(Context context, String question, List<String> answers) {
		super(context);
		mContex = context;
		mQuestion = question;
		mAnswers = answers;

		init();
	}
	 
	/**
	 * 初始化
	 */
	private void init() {
		setWillNotDraw(false);//ViewGroup默认不会调用onDraw方法，除非为其设置背景或者把WILL_NOT_DRAW去掉
		mTextSize = 30;
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.BLACK);

		//字体宽高初始化
		FontMetricsInt metr = mTextPaint.getFontMetricsInt();
		mFontHeight = metr.bottom - metr.top;
		mFontWidth = (int) mTextPaint.measureText("中");
		lineHeight = mFontHeight;
		mBaseline = - metr.top;  
	}

	/**
	 * 题目解析
	 * 思路：把问题里的文本和空分离出来，并且根据行宽限制，把文本部分拆分成若干段，根据顺序分别计算出若干段文本和空的坐标情况
	 * 然后对文本部分进行绘制操作，空部分则初始化对应的EditText加入布局中
	 */
	private void parseQuestion() {
		try {
			if (mWidth > 0) {
				Log.d(TAG, "parseQuestion...");
				mInit = true;

				mTextInfos = new ArrayList<TextInfo>();
				// 空个数校验
				int blankCount = StringUtil.countStr(mQuestion, SubjectConstant.BLANK_FLAG);
				if (blankCount != mAnswers.size()) {
					throw new Exception("空和答案个数不相等");
				}
				mBlanks = new ArrayList<EditText>(blankCount);

				int lines = splitQuestion(mQuestion);
				mHeight = lines * lineHeight;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.showToast(mContex, "填空题题目录入出错：" + e.getMessage());
			Log.e(TAG, "填空题题目录入出错：" + mQuestion);
		}
	}

	/**
	 * 把问题内容拆分到对应的行
	 * @param text 存放题目内容
	 */
	private int splitQuestion(String text) {
		//当前拆分空索引
		int currentBlank = 0;
		//当前拆分行
		int currentLine = 0;

		//当前行总宽度
		int tmpWidth = 0;
		//拆分具体过程
		int length = text.length();

		int startIndex = 0;//题目内容截取起始index
		int startX = 0;//题目内容截取起始坐标
		for (int i = 0; i < length; i++) {
			int flagLength = SubjectConstant.BLANK_FLAG.length();
			if ((i + flagLength) <= length && text.substring(i, i + flagLength).equals(SubjectConstant.BLANK_FLAG)) {//当前字符为空占位符的开始字符
				//空前面内容截取
				if (startIndex < i) {//空前面有内容，需要把该内容截取出来
					TextInfo textInfo = new TextInfo(startX, lineHeight * currentLine, text.substring(startIndex, i));
					mTextInfos.add(textInfo);
				}
				i += flagLength;//循环处理跳过占位符
				startIndex = i;//截取完后更新下一次截取的起始index
				i--;//减1是因为for循环执行一次后i会加1，用于抵消

				//当前宽度计算
				int blankWidth = (mAnswers.get(currentBlank).length() + blankWidthExtra) * mFontWidth;//对应空的宽度
				blankWidth = Math.min(blankWidth, mWidth);//空的宽度不能超过控件宽度
				tmpWidth += blankWidth;
				//把有占位符的地方替换为空视图
				BlankEditText blank = new BlankEditText(mContex, mTextSize);
				if (tmpWidth <= mWidth) {//未超出限制
					blank.setLayoutParams(new BlankLayoutParams(tmpWidth - blankWidth, lineHeight * currentLine, blankWidth, lineHeight));
					startX = tmpWidth;
				} else {//超出限制，换行
					tmpWidth = blankWidth;
					startX = tmpWidth;
					currentLine++;
					blank.setLayoutParams(new BlankLayoutParams(0, lineHeight * currentLine, blankWidth, lineHeight));
				}
				addView(blank);
				mBlanks.add(blank);
				currentBlank++;
			} else {//该字符不是空占位符
				int charWidth = (int) mTextPaint.measureText(text, i, i + 1);
				if (tmpWidth + charWidth > mWidth) {//超出限制，换行
					if (startIndex < i) {//换行位置前面有内容，需要把该内容截取出来
						TextInfo textInfo = new TextInfo(startX, lineHeight * currentLine, text.substring(startIndex, i));
						mTextInfos.add(textInfo);
					}
					startX = 0;
					startIndex = i;
					tmpWidth = charWidth;
					currentLine++;
				} else {
					tmpWidth += charWidth;
				}
			}
		}
		//检测最后是否有未截取的内容
		if (startIndex < length) {
			TextInfo textInfo = new TextInfo(startX, lineHeight * currentLine, text.substring(startIndex, length));
			mTextInfos.add(textInfo);
		}

		return currentLine + 1;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		if (!mInit) {
			parseQuestion();
		}
		// 计算子控件的尺寸
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			BlankLayoutParams params = (BlankLayoutParams) child.getLayoutParams();
			child.measure(params.getWidth(), params.getHeight());
			Log.i(TAG, String.format("child.measure width:%s,height:%s", params.getWidth(), params.getHeight()));
		}

		setMeasuredDimension(mWidth, mHeight);
		Log.i(TAG, String.format("onMeasure mWidth:%s,mHeight:%s", mWidth, mHeight));

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			BlankLayoutParams params = (BlankLayoutParams) child.getLayoutParams();
			int left = params.getX();
			int top = params.getY();
			int right = params.getX() + params.getWidth();
			int bottom = params.getY() + params.getHeight();
			child.layout(left, top, right, bottom);
			Log.d(TAG, String.format("child.layout left:%s,top:%s,right:%s,bottom:%s", left, top, right, bottom));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mTextInfos != null) {
			for (TextInfo textInfo : mTextInfos) {
				canvas.drawText(textInfo.getText(), textInfo.getX(), textInfo.getY()+mBaseline, mTextPaint);
			}
		}
	}

	/**
	 * 文本对应的信息
	 * @author lucher
	 *
	 */
	public class TextInfo {
		// x坐标
		private int x;
		// y坐标
		private int y;
		//对应的问题内容
		private String text;

		public TextInfo(int x, int y, String text) {
			setX(x);
			setY(y);
			setText(text);
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return String.format("x:%s,y:%s,text:%s", x, y, text);
		}

	}

	/**
	 * 空布局参数
	 * @author lucher
	 *
	 */
	public class BlankLayoutParams extends LayoutParams {
		// x坐标
		private int x;
		// y坐标
		private int y;

		public BlankLayoutParams(int x, int y, int width, int height) {
			super(width, height);
			setX(x);
			setY(y);
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}
}