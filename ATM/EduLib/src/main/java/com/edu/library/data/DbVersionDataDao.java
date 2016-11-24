package com.edu.library.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

/**
 * 从数据库获取数据库版本号的dao层
 * 
 * @author lucher
 * 
 */
public class DbVersionDataDao extends BaseDataDao {

	/**
	 * 对应数据库表里的VERSION字段
	 */
	public static final String VERSION = "VERSION";

	/**
	 * 自身实例
	 */
	private static DbVersionDataDao instance = null;

	@Override
	public void setTableName() {
		TABLE_NAME = "TB_DB_VERSION";
	}

	public DbVersionDataDao(Context context, String db) {
		super(context, db);
	}

	@Override
	public BaseData parseCursor(Cursor curs) {
		DbVersionData data = new DbVersionData();
		int idIndex = curs.getColumnIndex(ID);
		int versionIndex = curs.getColumnIndex(VERSION);
		int remarkIndex = curs.getColumnIndex(REMARK);
		data.setId(curs.getInt(idIndex));
		data.setVersion(curs.getInt(versionIndex));
		data.setRemark(curs.getString(remarkIndex));
		return data;
	}

	/**
	 * 获取数据库版本号数据
	 * 
	 * @return
	 */
	public DbVersionData getDbVersionData() {
		DbVersionData data = null;
		Cursor curs = null;
		String sql = "SELECT * FROM " + TABLE_NAME;
		try {
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
			curs = mDb.rawQuery(sql, null);
			if (curs != null) {
				if (curs.getCount() == 0)
					return null;
				curs.moveToLast();
				data = (DbVersionData) parseCursor(curs);

				Log.d(TAG, "data:" + data);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeDb(mDb, curs);
		}

		return data;
	}

}
