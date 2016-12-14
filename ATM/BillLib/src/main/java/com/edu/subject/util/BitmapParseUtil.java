package com.edu.subject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import com.edu.library.util.FileUtil;
import com.edu.subject.common.ProgressImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bitmap解析工具类，目前支持assets,file,http,https,自动使用缓存机制
 *
 * @author lucher
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

    private static ConcurrentHashMap<String, SoftReference<Bitmap>> CACHE = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

    /**
     * 解析图片
     *
     * @param uri
     * @param context
     * @param cache     是否缓存
     * @param imageView
     * @return
     * @throws IOException
     */
    public static Bitmap parse(String uri, Context context, boolean cache, ProgressImageView imageView) throws IOException, IllegalArgumentException {
        Log.d(TAG, "parsing...uri:" + uri);
        Bitmap bitmap = null;
        // cache = false;// test by lucher;

        if (cache) {
            // 从缓存获取，若存在直接使用，否则根据uri进行初始化
            if (CACHE.get(uri) == null || CACHE.get(uri).get() == null || CACHE.get(uri).get().isRecycled()) {
                Log.e(TAG, "uri:" + uri + "缓存不存在，需要初始化bitmap");
                bitmap = createBitmap(uri, context, imageView);
                CACHE.put(uri, new SoftReference<Bitmap>(bitmap));
            } else {
                bitmap = CACHE.get(uri).get();
                Log.i(TAG, "uri:" + uri + " 缓存存在，直接使用");
            }
        } else {
            bitmap = createBitmap(uri, context, imageView);
        }

        return bitmap;
    }

    /**
     * 根据uri创建图片
     *
     * @param uri
     * @param context
     * @param imageView
     * @return
     * @throws IOException
     */
    private static Bitmap createBitmap(String uri, Context context, ProgressImageView imageView) throws IOException, IllegalArgumentException {
        Bitmap bitmap = null;
        // 图片配置
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        if (uri.startsWith(ASSETS_PREFIX)) {
            uri = uri.substring(ASSETS_PREFIX.length(), uri.length());
            bitmap = BitmapFactory.decodeStream(context.getAssets().open(uri), null, opt);
        } else if (uri.startsWith(FILE_PREFIX)) {
            uri = uri.substring(FILE_PREFIX.length(), uri.length());
            bitmap = BitmapFactory.decodeStream(new FileInputStream(uri), null, opt);
        } else if (uri.startsWith(FILE_HTTP) || uri.startsWith(FILE_HTTPS)) {
            bitmap = getLocalBitmap(uri, context, imageView);
        } else {
            throw new IllegalArgumentException("parse error, invalid uri:" + uri);
        }

        return bitmap;
    }

    /**
     * 从本地获取bitmap
     *
     * @param uri
     * @param context
     * @param imageView
     * @return
     */
    private static Bitmap getLocalBitmap(String uri, Context context, ProgressImageView imageView) {
        File file = new File(getCacheDir(context) + uri.hashCode());
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            if (bitmap != null) {
                CACHE.put(uri, new SoftReference<Bitmap>(bitmap));
            } else {
                Log.e(TAG, "getLocalBitmap " + uri + " is null");
                SubjectImageLoader.getInstance(context).displayImage(uri, imageView);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "getLocalBitmap: " + uri + " file doesnt exists");
            SubjectImageLoader.getInstance(context).displayImage(uri, imageView);
        }
//		SubjectImageLoader.getInstance(context).displayImage(uri, imageView);
        return bitmap;
    }

    /**
     * 保存bitmap到sdcard,并且put到缓存
     *
     * @param uri
     * @param bm
     * @return 是否成功
     */
    public static boolean saveBitmap(Context context, String uri, Bitmap bm) {
        CACHE.put(uri, new SoftReference<Bitmap>(bm));
        boolean success = true;
        try {
            File file = new File(getCacheDir(context) + uri.hashCode());
            FileUtil.createFile(file);
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 80, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    /**
     * 是否存在对应缓存（内存缓存或者文件缓存）
     *
     * @param uri
     * @return
     */
    public static boolean existCache(Context context, String uri) {
        boolean exist = false;
        if (CACHE.get(uri) != null && CACHE.get(uri).get() != null && !CACHE.get(uri).get().isRecycled()) {
            Log.v(TAG, "memory cache found:" + uri);
            exist = true;
        } else {
            if (URLUtil.isNetworkUrl(uri)) {
                File file = new File(getCacheDir(context) + uri.hashCode());
                if (file.exists()) {
                    Log.v(TAG, "disk cache found:" + uri);
                    exist = true;
                }
            }
        }

        return exist;
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
     * 清空缓存后虚拟机回收内存
     */
    public void clearCache() {
        CACHE.clear();
        System.gc();
    }

    /**
     * 获取图片缓存目录
     *
     * @param context
     * @return
     */
    private static String getCacheDir(Context context) {
//		return Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + context.getPackageName() + "/cache/pic/";
        return Environment.getExternalStorageDirectory().getPath() + "/EduResources/AccCourse/pic/";
    }
}
