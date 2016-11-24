package com.edu.library.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作相关类
 * 
 * @author lucher
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	/**
	 * 数据库版本号
	 */
	static final int DATABASE_VERSION = 1;

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public DBHelper(Context context, String name, CursorFactory factory) {
		super(context, name, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
