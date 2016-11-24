package com.edu.library.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * 图片操作工具类
 * 
 * @author lucher
 */
public class ImageUtil {

	private static String TAG = "ImageUtil";

	/**
	 * 使用BitmapFactory.Options的inSampleSize参数来缩放
	 * 
	 * @param path
	 *            图片path
	 * @param width
	 *            目标宽度
	 * @param height
	 *            目标高度
	 * @return
	 */
	public static Bitmap getScaleBitmapWithBitmapFactory(String path, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 不加载bitmap到内存中,也不给其分配内存空间，这样可以有效避免内存溢出
		BitmapFactory.decodeFile(path, options);
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		options.inDither = false;
		options.inPreferredConfig = Config.ARGB_8888;
		options.inSampleSize = 1;

		if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
			int sampleSize = (outWidth / width + outHeight / height) / 2;
			options.inSampleSize = sampleSize;
		}

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 根据文件名获取图片资源id
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getImageResId(Context context, String name) {
		return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
	}

	/**
	 * drawable转为二进制
	 * 
	 * @param drawable
	 * @return
	 */
	public static byte[] drawableToByte(Drawable drawable) {
		Bitmap bitmap = drawableToBitmap(drawable);
		return getBitmapByte(bitmap);
	}

	/**
	 * bitmap转为二进制
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 二进制转为bitmap
	 * 
	 * @param temp
	 * @return
	 */
	public static Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

	/**
	 * drawable转为bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {

		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);

		return bitmap;
	}

	/**
	 * bitmap转为drawable
	 * 
	 * @param bitmap
	 * @param context
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap, Context context) {
		Drawable drawable = null;

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		Bitmap temp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		drawable = new BitmapDrawable(context.getResources(), temp);

		bitmap = null;
		temp = null;

		return drawable;
	}

	/**
	 * drawable转为二进制，用于jpeg格式的图片
	 * 
	 * @param drawable
	 * @return
	 */
	public static byte[] drawableToByteJPEG(Drawable drawable) {
		Bitmap bitmap = drawableToBitmap(drawable);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * bitmap转为二进制，用于png格式的图片
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] drawableToBytePNG(Drawable drawable) {
		Bitmap bm = drawableToBitmap(drawable);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 根据指定的宽和高 以及对应的比例缩放图片
	 * 
	 * @param context
	 * @param tDrawable
	 *            待转换drawable
	 * @param cellWidth
	 *            缩放前的宽度
	 * @param cellHeight
	 *            缩放前的高度
	 * @param scaleX
	 *            x方向的缩放参数
	 * @param scaleY
	 *            y方向的缩放参数
	 * @return 缩放后的drawable
	 */
	public static Drawable getScaleDrawableWithThumbnail(Context context, Drawable tDrawable, int cellWidth, int cellHeight, float scaleX, float scaleY) {

		Drawable drawable = null;

		if (tDrawable instanceof BitmapDrawable) {
			Bitmap bitmap1 = ((BitmapDrawable) tDrawable).getBitmap();
			int width = (int) (cellWidth * scaleX);
			int height = (int) (cellHeight * scaleY);

			Bitmap bitmap = ThumbnailUtils.extractThumbnail(bitmap1, width, height);
			drawable = new BitmapDrawable(context.getResources(), bitmap);

			bitmap = null;
		} else if (tDrawable instanceof StateListDrawable) {
			Log.e(TAG, "this icon is a StateListDrawable");
			return tDrawable;
		}

		return drawable;
	}

	/**
	 * 根据指定的参数获取指定大小的缩放图片
	 * 
	 * @param resId
	 * @param dstWidth
	 *            目标宽度
	 * @param dstHeight
	 *            目标高度
	 * @param context
	 * @return
	 */
	public static Drawable getScaledDrawable(int resId, int dstWidth, int dstHeight, Context context) {
		Bitmap tBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(tBitmap, dstWidth, dstHeight, true);
		return bitmapToDrawable(scaledBitmap, context);
	}

	/**
	 * 根据指定的参数获取指定大小的缩放图片
	 * 
	 * @param drawable
	 * @param dstWidth
	 *            目标宽度
	 * @param dstHeight
	 *            目标高度
	 * @param context
	 * @return
	 */
	public static Drawable getScaledDrawable(Drawable drawable, int dstWidth, int dstHeight, Context context) {
		Bitmap tBitmap = ((BitmapDrawable) drawable).getBitmap();
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(tBitmap, dstWidth, dstHeight, true);
		return bitmapToDrawable(scaledBitmap, context);
	}

	/**
	 * 根据指定的参数获取指定大小的缩放图片
	 * 
	 * @param tBitmap
	 * @param dstWidth
	 *            目标宽度
	 * @param dstHeight
	 *            目标高度
	 * @param context
	 * @return
	 */
	public static Drawable getScaledDrawable(Bitmap tBitmap, int dstWidth, int dstHeight, Context context) {
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(tBitmap, dstWidth, dstHeight, true);
		return bitmapToDrawable(scaledBitmap, context);
	}

	/**
	 * 绘制.9图
	 * 
	 * @param c
	 * @param bmp
	 * @param r1
	 */
	public void drawNinepath(Canvas c, Bitmap bmp, Rect r1) {
		NinePatch patch = new NinePatch(bmp, bmp.getNinePatchChunk(), null);
		patch.draw(c, r1);
	}

	/**
	 * 保存view对应的图片到sdcard
	 * 
	 * @param path
	 *            存储路径
	 * @param fileName
	 *            图片名
	 * @param view
	 *            待保存视图
	 * @return
	 */
	public static boolean saveViewImage(String path, String fileName, View view) {
		Bitmap bm = convertViewToBitmap(view);
		return saveBitmap(path, fileName, bm);
	}

	/**
	 * 把view转换为bitmap
	 * 
	 * @param v
	 * @return
	 */
	public static Bitmap convertViewToBitmap(View v) {
		if (v == null) {
			return null;
		}
		Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		v.draw(c);

		return bm;
	}

	/**
	 * 截取scrollview的屏幕
	 * 
	 * @param scrollView
	 * @return
	 */
	public static Bitmap convertViewToBitmap(ScrollView scrollView) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取scrollview实际高度
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
		}
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		return bitmap;
	}

	/**
	 * 保存bitmap到sdcard
	 * 
	 * @param path
	 * @param bitName
	 * @param bm
	 * @return 是否成功
	 */
	public static boolean saveBitmap(String path, String bitName, Bitmap bm) {
		boolean success = true;
		File f = new File(path + bitName);
		try {
			FileUtil.createFile(f);
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}

		return success;
	}
	
	/**
	 * 获取view的相关信息
	 * 
	 * @param view
	 * @return
	 */
	public static String getViewInfo(View view) {
		StringBuffer stringBuffer = new StringBuffer();

		int top = view.getTop();
		int left = view.getLeft();
		int right = view.getRight();
		int bottom = view.getBottom();
		int width = view.getWidth();
		int height = view.getHeight();

		stringBuffer.append("Info relative to Parent: " + "left: " + left + ", right: " + right + ", top: " + top + ", bottom: " + bottom + ", width: " + width + ", height: " + height);

		// API Level 11
		stringBuffer.append("\n view.getTranslationX(): " + view.getTranslationX());
		stringBuffer.append("\n view.getTranslationY(): " + view.getTranslationY());

		Rect rect = new Rect();
		view.getLocalVisibleRect(rect);
		stringBuffer.append("\ngetLocalVisibleRect(): ");
		stringBuffer.append("\n " + rect.toString());

		Rect globalRect = new Rect();
		view.getGlobalVisibleRect(globalRect);
		stringBuffer.append("\ngetGlobalVisibleRect(): ");
		stringBuffer.append("\n " + globalRect.toString());

		int[] locationInWindow = new int[2];
		view.getLocationInWindow(locationInWindow);
		stringBuffer.append("\ngetLocationInWindow(): ");
		stringBuffer.append("\n " + locationInWindow[0] + ", " + locationInWindow[1]);

		int[] locationOnScreen = new int[2];
		view.getLocationOnScreen(locationOnScreen);
		stringBuffer.append("\ngetLocationOnScreen(): ");
		stringBuffer.append("\n " + locationOnScreen[0] + ", " + locationOnScreen[1]);

		return stringBuffer.toString();
	}

}
