package com.edu.library.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作dao层基类,注：需要在子类里对TABLE_NAME进行赋值
 * 
 * @author lucher
 * 
 */
public abstract class BaseDataDao {

	public static final String TAG = "BaseDataDao";
	/**
	 * 数据库
	 */
	public SQLiteDatabase mDb;

	public Context mContext;

	/**
	 * 数据库名称
	 */
	protected String dbName;

	/**
	 * id
	 */
	public static String ID = "ID";
	/**
	 * 备注，预留字段
	 */
	public final static String REMARK = "REMARK";
	/**
	 * 数据库表名
	 */
	public String TABLE_NAME;

	protected BaseDataDao(Context context, String dbName) {
		mContext = context;
		this.dbName = dbName;
		setTableName();
	}

	/**
	 * 设置table名称
	 */
	public abstract void setTableName();

	/**
	 * 解析cursor
	 * 
	 * @param curs
	 * @return
	 */
	public abstract BaseData parseCursor(Cursor curs);

	/**
	 * 根据id获取对应数据
	 * 
	 * @param id
	 * @return
	 */
	public synchronized BaseData getDataById(long id) {
		BaseData data = null;
		Cursor curs = null;
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id;
		try {
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
			curs = mDb.rawQuery(sql, null);
			if (curs != null) {
				if (curs.getCount() == 0)
					return null;
				curs.moveToFirst();
				data = parseCursor(curs);

				Log.d(TAG, "data:" + data);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeDb(mDb, curs);
		}

		return data;
	}

	/**
	 * 获取所有数据
	 * 
	 * @param id
	 * @return
	 */
	public synchronized List<? extends BaseData> getAllDatas() {
		Cursor curs = null;
		List<BaseData> datas = null;
		try {
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
			String sql = "SELECT * FROM " + TABLE_NAME;
			Log.d(TAG, "sql:" + sql);
			curs = mDb.rawQuery(sql, null);
			if (curs != null) {
				datas = new ArrayList<BaseData>(curs.getCount());
				while (curs.moveToNext()) {
					BaseData data = parseCursor(curs);
					datas.add(data);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDb(mDb, curs);
		}
		return datas;
	}

	/**
	 * 更新数据
	 * 
	 * @param id
	 * @param values
	 */
	public synchronized void updateData(String id, ContentValues values) {
		try {
			Log.d(TAG, TABLE_NAME + "-updateData:" + id);
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
			mDb.update(TABLE_NAME, values, ID + "=?", new String[] { String.valueOf(id) });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDb(mDb);
		}
	}
	/**
	 * 更新数据
	 *
	 * @param id
	 * @param values
	 */
	public synchronized void updateData(int id, ContentValues values) {
		try {
			Log.d(TAG, TABLE_NAME + "-updateData:" + id);
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
			mDb.update(TABLE_NAME, values, ID + "=?", new String[] { String.valueOf(id) });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDb(mDb);
		}
	}

	/**
	 * 插入数据
	 * 
	 * @param values
	 */
	public synchronized void insertData(ContentValues values) {
		try {
			Log.d(TAG, TABLE_NAME + "-insertData");
			DBHelper helper = new DBHelper(mContext, dbName, null);
			Log.d(TAG, "values.getAsInteger(ID):" + values.getAsInteger(ID));

			mDb = helper.getWritableDatabase();
			mDb.insert(TABLE_NAME, null, values);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDb(mDb);
		}
	}
	/**
	 * 插入数据带返回值
	 *
	 * @param values
	 */
	public synchronized int insertDataGetId(ContentValues values) {
		int id = 0;
		try {
			Log.d(TAG, TABLE_NAME + "-insertData");
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
			 id = (int) mDb.insert(TABLE_NAME, null, values);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDb(mDb);
		}
		return id;
	}

	/**
	 * 插入数据
	 * 
	 * @param values
	 */
	public synchronized void bulkInsertData(List<ContentValues> valuesList) {
		try {
			Log.d(TAG, TABLE_NAME + "-bulkInsertData");
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
			mDb.beginTransaction();
			for (ContentValues values : valuesList) {
				mDb.insert(TABLE_NAME, null, values);
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mDb.endTransaction();
			closeDb(mDb);
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 */
	public synchronized void deleteData(long id) {
		try {
			Log.e(TAG, TABLE_NAME + "-deleteData:" + id);
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
			mDb.delete(TABLE_NAME, ID + "=?", new String[] { String.valueOf(id) });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDb(mDb);
		}
	}

	/**
	 * 关闭数据库以及cursor
	 * 
	 * @param db
	 * @param curs
	 */
	public void closeDb(SQLiteDatabase db, Cursor curs) {
		closeDb(db);
		if (curs != null) {

			curs.close();
			curs = null;
		}
	}

	/**
	 * 关闭数据库
	 * 
	 * @param db
	 */
	public void closeDb(SQLiteDatabase db) {
		if (mDb != null) {
			mDb.close();
		}
	}

	/**
	 * 获取最大id
	 * 
	 * @return
	 */
	public long getMaxItemId() {
		final int maxIdIndex = 0;
		long id = -1;
		Cursor curs = null;
		try {
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
			curs = mDb.rawQuery("SELECT MAX(" + ID + ") FROM " + TABLE_NAME, null);

			if (curs != null && curs.moveToNext()) {
				id = curs.getLong(maxIdIndex);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDb(mDb, curs);
		}
		return id;
	}
}
