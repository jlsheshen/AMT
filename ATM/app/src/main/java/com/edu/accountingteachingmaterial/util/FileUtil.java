package com.edu.accountingteachingmaterial.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class FileUtil {

	/**
	 * 获取文件名
	 */
	public static String getFileName(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf(File.separator);
		return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
	}
	/**
	 * 获取扩展名
	 */
	public static String getExtension(String path) {
		if (path == null) {
			return null;
		}
		int dot = path.lastIndexOf(".");
		if (dot >= 0) {
			return path.substring(dot);
		} else {
			return "";
		}
	}

	/**
	 * 创建临时文件
	 * 
	 * @param namePart
	 * @param byteSize
	 * @param dir
	 * @return
	 */
	public static File createTempFile(String namePart, int byteSize, File dir) {
		try {
			File f = File.createTempFile(namePart, "_handled", dir);
			FileOutputStream fos = new FileOutputStream(f);
			Random r = new Random();
			byte[] buffer = new byte[byteSize];
			r.nextBytes(buffer);
			fos.write(buffer);
			fos.flush();
			fos.close();
			return f;
		} catch (Throwable t) {
			Log.e("FileUtil", "createTempFile failed", t);
		}
		return null;
	}
	public static File getUriFile(Context context, Uri uri) {
		String path = getUriPath(context, uri);
		if (path == null) {
			return null;
		}
		return new File(path);
	}

	public static String getUriPath(Context context, Uri uri) {
		if (uri == null) {
			return null;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
			if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			} else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
				return getDataColumn(context, contentUri, null, null);
			} else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				final String selection = "_id=?";
				final String[] selectionArgs = new String[]{split[1]};
				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		} else if ("content".equalsIgnoreCase(uri.getScheme())) {
			if ("com.google.android.apps.photos.content".equals(uri.getAuthority())) {
				return uri.getLastPathSegment();
			}
			return getDataColumn(context, uri, null, null);
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}

	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {column};
		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null) cursor.close();
		}
		return null;
	}

}
