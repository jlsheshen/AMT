package com.edu.library.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.util.Base64;
import android.util.Log;

/**
 * 图片缩放工具
 * 
 * @author lucher
 * 
 */
public class ImageScaleUtil {

	// 把bitmap转换成String
	public static String bitmapToString(String filePath) {
		// Bitmap bm = getScaledBitmap(BitmapFactory.decodeFile(filePath), 640,
		// 480);
		String result = null;
		try {
			Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(new File(filePath)));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
			byte[] b = baos.toByteArray();
			result = Base64.encodeToString(b, Base64.DEFAULT);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 根据指定的宽和高 以及对应的比例缩放图片
	 * 
	 */
	public static Bitmap getScaledBitmap(Bitmap bm, int tWidth, int tHeight) {
		float scaleX = (float) tWidth / bm.getWidth();
		float scaleY = (float) tHeight / bm.getHeight();
		float scale = Math.min(scaleX, scaleY);

		int width = (int) (bm.getWidth() * scale);
		int height = (int) (bm.getHeight() * scale);

		Bitmap bitmap = Bitmap.createScaledBitmap(bm, width, height, false);

		return bitmap;
	}

	/*
	 * 压缩图片，处理某些手机拍照角度旋转的问题
	 */
	public static String compressImage(Context context, String filePath, String fileName, int q) throws FileNotFoundException {

		Bitmap bm = getSmallBitmap(filePath);
		int degree = readPictureDegree(filePath);
		if (degree != 0) {// 旋转照片角度
			bm = rotateBitmap(bm, degree);
		}
		File imageDir = new File("");
		File outputFile = new File(imageDir, fileName);
		FileOutputStream out = new FileOutputStream(outputFile);
		bm.compress(Bitmap.CompressFormat.JPEG, q, out);

		return outputFile.getPath();
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
		if (bitmap != null) {
			Matrix m = new Matrix();
			m.postRotate(degress);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
			return bitmap;
		}
		return bitmap;
	}

	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 400, 300);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	// 计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
}
