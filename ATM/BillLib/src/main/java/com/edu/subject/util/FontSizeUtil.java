package com.edu.subject.util;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.Log;

/**
 * 字体大小工具类
 * 录入单据数据之前，可以先通过该工具类计算出合适的字体大小
 * 
 * @author lucher
 * 
 */
public class FontSizeUtil {

	private static final String TAG = "FontSizeUtil";

	/**
	 * 根据字体大小获取字体高度
	 * 
	 * @param textSize
	 * @return
	 */
	public static int getFontHeight(float textSize) {
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		FontMetrics fm = paint.getFontMetrics();

		int height = (int) Math.ceil(fm.descent - fm.top) + 2;
		Log.i("lucher", "size:" + textSize + ",height:" + height);

		return height;
	}
	
	/**
	 * 根据当前字体大小和显示文字的控件高度计算出一个合适的字体大小
	 * @param textSize
	 * @param viewHeight
	 * @return 返回的字体大小所需要的控件高度不会大于viewHeight
	 */
	public static float getCalculateTextSize(float textSize, int viewHeight) {
		float size = 0;
		
		if(getFontHeight(textSize) > viewHeight) {
			size = -1;
		}
		
		return size;
	}
}
