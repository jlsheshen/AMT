package com.edu.subject.common.rich;

import org.xml.sax.XMLReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;

/**
 * html中base64图片标签解析
 * @author lucher
 *
 */
public class Img64TagHandler implements TagHandler {

	private final Context mContext;
	//标签开始，截止索引
	private int sIndex;
	private int eIndex;

	public Img64TagHandler(Context context) {
		mContext = context;
	}

	@Override
	public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
		if (tag.toLowerCase().equals("img64")) {
			if (opening) {
				sIndex = output.length();
			} else {
				eIndex = output.length();
				String src = output.subSequence(sIndex, eIndex).toString();

				if (src != null) {
					Bitmap b = stringtoBitmap(src.replace("data:image/png;base64,", ""));
					ImageSpan imgSpan = new ImageSpan(mContext, b);
					output.setSpan(imgSpan, sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
		}
	}

	/**
	 * base64转bitmap
	 * @param string
	 * @return
	 */
	public Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			Options options = new Options();
			options.inSampleSize = 1;
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, options);

			float density = mContext.getResources().getDisplayMetrics().density;
			bitmap = zoomImage(bitmap, density);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 图片缩放
	 * @param bgimage
	 * @param multi
	 * @return
	 */
	public Bitmap zoomImage(Bitmap bgimage, float multi) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = multi;
		float scaleHeight = multi;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		return bitmap;
	}
}
