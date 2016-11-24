package com.edu.subject.util;

import java.io.FileInputStream;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * bitmap解析工具类，目前支持assets,file,http,https,自动使用缓存机制
 * 
 * @author lucher
 * 
 */
public class BitmapParseUtil {

	private static final String TAG = "BitmapParseUtil";
	// assets图片资源前缀
	private static String ASSETS_PREFIX = "assets://";
	// file图片资源前缀
	private static String FILE_PREFIX = "file://";
	// http图片资源前缀
	private static String FILE_HTTP = "http://";
	// https图片资源前缀
	private static String FILE_HTTPS = "https://";

	/**
	 * bitmap图片缓存
	 */
	private static ConcurrentHashMap<String, SoftReference<Bitmap>> CACHE = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

	/**
	 * 解析图片
	 * 
	 * @param uri
	 * @param context
	 *            assets资源需要
	 * @param cache
	 *            是否缓存
	 * @return
	 */
	public static Bitmap parse(String uri, Context context, boolean cache) {
		Log.d(TAG, "parsing...uri:" + uri);
		Bitmap bitmap = null;

		if (cache) {
			// 从缓存获取，若存在直接使用，否则根据uri进行初始化
			if (CACHE.get(uri) == null || CACHE.get(uri).get() == null || CACHE.get(uri).get().isRecycled()) {
				Log.e(TAG, "uri:" + uri + "缓存不存在，需要初始化bitmap");
				// BitmapFactory.Options opt = new BitmapFactory.Options();
				// opt.inJustDecodeBounds = true;

				bitmap = createBitmap(uri, context);
				CACHE.put(uri, new SoftReference<Bitmap>(bitmap));
			} else {
				bitmap = CACHE.get(uri).get();
				Log.i(TAG, "uri:" + uri + " 缓存存在，直接使用");
			}

		} else {
			bitmap = createBitmap(uri, context);
		}

		return bitmap;
	}

	/**
	 * 释放图片
	 * 
	 * @param uri
	 */
	public static void release(String uri) {
		if (CACHE.get(uri) != null && CACHE.get(uri).get() != null) {
			CACHE.get(uri).get().recycle();
		}
	}

	/**
	 * 根据uri创建图片
	 * 
	 * @param uri
	 * @param context
	 * @return
	 */
	private static Bitmap createBitmap(String uri, Context context) {
		Bitmap bitmap = null;
		// 图片配置
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		try {
			if (uri.startsWith(ASSETS_PREFIX)) {
				uri = uri.substring(ASSETS_PREFIX.length(), uri.length());
				bitmap = BitmapFactory.decodeStream(context.getAssets().open(uri), null, opt);
			} else if (uri.startsWith(FILE_PREFIX)) {
				uri = uri.substring(FILE_PREFIX.length(), uri.length());
				bitmap = BitmapFactory.decodeStream(new FileInputStream(uri), null, opt);
			} else if (uri.startsWith(FILE_HTTP) || uri.startsWith(FILE_HTTPS)) {
				// 待实现
			} else {
				Log.e(TAG, "parse error, invalid uri:" + uri);
			}
		} catch (Exception e) {
			Log.e(TAG, "parse error, uri:" + uri);
			e.printStackTrace();
		}

		return bitmap;
	}
}
