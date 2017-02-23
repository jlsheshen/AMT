package com.edu.library.html;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;

/**
 * 图片资源获取帮助类
 * 
 * @author WZG
 * 
 */
public class DrawableUtils {
	/**
	 * 实体类单例模式获取
	 */
	private static DrawableUtils loader;
	private Context context;

	public static DrawableUtils getInstance(Context context) {
		if (loader == null) {
			synchronized (DrawableUtils.class) {
				loader = new DrawableUtils(context);
			}
		}
		return loader;
	}

	private DrawableUtils(Context context) {
		this.context = context;
	}

	/**
	 * 通过图片名得到图片在工程中的id(drawable文件下)
	 * 
	 * @param context
	 * @param imageName
	 * @return
	 */
	public int getResource(String imageName) {
		int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
		return resId;
	}

	/**
	 * 得到assets文件下的文件
	 * 
	 * @param context
	 * @param pathString
	 *            路径
	 * @return
	 */
	public Drawable getInAssetsBy(String pathString) {
		// String path = "wenmi/" + question.split("<<<")[1] + ".png";
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(pathString), null, opt);
			Drawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到本地指定文件中的土图片
	 * 
	 * @param SDpath
	 *            文件绝对路径
	 * @return
	 */
	public Drawable getInSDBy(String SDpath) {
		Bitmap bitmap = BitmapFactory.decodeFile(SDpath);
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * 获得图片缩略图
	 * 
	 * @param namePath
	 * @param width
	 * @param height
	 * @return
	 */
	@SuppressWarnings("unused")
	public  Drawable getImageThumbnail(String namePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		String pathString="file:///android_asset/images"+namePath;
		bitmap = BitmapFactory.decodeFile(pathString, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(pathString, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		@SuppressWarnings("deprecation")
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		return bitmapDrawable;
	}
}
