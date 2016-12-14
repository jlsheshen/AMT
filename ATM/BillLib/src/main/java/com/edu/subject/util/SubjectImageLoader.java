package com.edu.subject.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;

import com.edu.library.data.DBHelper;
import com.edu.subject.SubjectConstant;
import com.edu.subject.common.ProgressImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.FailReason.FailType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 图片下载类
 *
 * @author lucher
 */
public class SubjectImageLoader {
    // universal-image-loader提供的
    protected static final String TAG = "SubjectImageLoader";
    private ImageLoader mImageLoader;
    private Context mContext;

    /**
     * 构造
     *
     * @param context
     */
    private SubjectImageLoader(Context context) {
        mContext = context;
        // imageloader配置
        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(3)// 线程池内加载的数量
                    .threadPriority(Thread.NORM_PRIORITY - 2) // 降低线程的优先级保证主UI线程不受太大影响
                    .memoryCache(null) // 建议内存设在5-10M,可以有比较好的表现
                    .tasksProcessingOrder(QueueProcessingType.LIFO).diskCache(null).writeDebugLogs().build();
            mImageLoader.init(config);
        }
    }

    /**
     * 获取实例
     *
     * @param context
     * @return
     */
    public static SubjectImageLoader getInstance(Context context) {
        return new SubjectImageLoader(context);
    }

    /**
     * 预先加载所有网络图片，主要包括单据背景图，印章图片，资料图片
     */
    public void preloadAllPics() {
        List<String> urls = new ArrayList<String>();
        SQLiteDatabase db = null;
        Cursor curs = null;
        try {
            DBHelper helper = new DBHelper(mContext, SubjectConstant.DATABASE_NAME, null);
            db = helper.getWritableDatabase();
            String sql = "SELECT BACKGROUND FROM TB_BILL_TEMPLATE";
            curs = db.rawQuery(sql, null);
            parseCursor(curs, urls);

            sql = "SELECT PIC FROM TB_SIGN";
            curs = db.rawQuery(sql, null);
            parseCursor(curs, urls);

            sql = "SELECT PIC FROM TB_BILL_SUBJECT";
            curs = db.rawQuery(sql, null);
            parseCursor(curs, urls);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
            if (curs != null) {
                curs.close();
                curs = null;
            }
        }

        for (String url : urls) {
            Log.d("查看图片log11111", url);
            preDownloadPic(url);
        }
    }

    /**
     * 解析cursor
     *
     * @param curs
     * @param urls
     */
    private void parseCursor(Cursor curs, List<String> urls) {
        if (curs != null) {
            while (curs.moveToNext()) {
                String[] array = curs.getString(0).split(SubjectConstant.SEPARATOR_ITEM);
                for (int i = 0; i < array.length; i++) {
                    String url = array[i];
                    if (URLUtil.isNetworkUrl(url)) {
                        urls.add(url);
                    }
                }
            }
        }
    }

    /**
     * 下载图片并显示
     *
     * @param uri       图片地址
     * @param imageView 显示图片的加载视图
     */
    public void displayImage(String uri, final ProgressImageView imageView) {
        mImageLoader.loadImage(uri, imageView);
    }

    /**
     * 下载所有图片图片
     *
     * @param s
     */
    public void preDownloadAllPic(List<String> s) {
        List<String> allUrl = new ArrayList<String>();
        allUrl = s;
        for (String url : allUrl) {
            Log.d("查看图片log11111", url);
            preDownloadPic(url);
        }
    }

    /**
     * 预下载图片
     *
     * @param uri
     * @param context
     */
    public void preDownloadPic(final String uri) {
        if (BitmapParseUtil.existCache(mContext, uri)) {
            return;
        }
        mImageLoader.loadImage(uri, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.d(TAG, "downloadPic onLoadingStarted:" + imageUri);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String reason = parseErrorMsg(failReason.getType());
                Log.e(TAG, "downloadPic failure:" + reason + "," + imageUri);
                preDownloadPic(uri);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.i(TAG, "downloadPic onLoadingComplete:" + imageUri);
                BitmapParseUtil.saveBitmap(mContext, imageUri, loadedImage);
            }
        });
    }

    private static String parseErrorMsg(FailType type) {
        String reason = null;
        switch (type) {
            case IO_ERROR:
                reason = "图片输入输出错误";
                break;
            case DECODING_ERROR:
                reason = "图片解析出错";
                break;
            case NETWORK_DENIED:
                reason = "图片下载失败";
                break;
            case OUT_OF_MEMORY:
                reason = "图片内存溢出";
                break;
            case UNKNOWN:
                reason = "图片加载失败，未知错误";
                break;
            default:
                reason = "图片加载失败，未知错误";
                break;
        }
        return reason;
    }
}
