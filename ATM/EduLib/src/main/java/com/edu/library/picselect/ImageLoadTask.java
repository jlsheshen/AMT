/**
 * ImageLoadTask.java
 * ImageSelector
 * 
 * Created by likebamboo on 2014-4-22
 * Copyright (c) 1998-2014 http://likebamboo.github.io/ All rights reserved.
 */

package com.edu.library.picselect;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

/**
 * 使用contentProvider扫描图片异步任务
 * 
 * @author lucher
 */
public class ImageLoadTask extends BaseTask {

    /**
     * 上下文对象
     */
    private Context mContext = null;

    /**
     * 存放所有图片地址
     */
    private ArrayList<String> mImgList = new ArrayList<String>();

    public ImageLoadTask(Context context) {
        super();
        mContext = context;
        result = mImgList;
    }

    public ImageLoadTask(Context context, OnTaskResultListener listener) {
        super();
        mContext = context;
        result = mImgList;
        setOnResultListener(listener);
    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        Uri mImageUri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = mContext.getContentResolver();
        // 构建查询条件，且只查询jpeg和png的图片
        StringBuilder selection = new StringBuilder();
        selection.append(Media.MIME_TYPE).append("=?");
        selection.append(" or ");
        selection.append(Media.MIME_TYPE).append("=?");

        Cursor mCursor = null;
        try {
            // 初始化游标
            mCursor = mContentResolver.query(mImageUri, null, selection.toString(), new String[] {
                    "image/jpeg", "image/png"
            }, Media.DATE_TAKEN +" DESC");
            // 遍历结果
            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(mCursor.getColumnIndex(Media.DATA));
                mImgList.add(path);
            }
        } catch (Exception e) {
            // 输出日志
            return false;
        } finally {
            // 关闭游标
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
        }
        return true;
    }
}
