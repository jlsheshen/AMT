package com.edu.library.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 数据库操作dao层基类,注：需要在子类里对TABLE_NAME进行赋值
 * 程序退出的时候别忘了closeDb
 * 
 * @author lucher
 * 
 */
public abstract class BaseDataDao2 {

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

	protected BaseDataDao2(Context context, String dbName) {
		mContext = context;
		this.dbName = dbName;
		openDatabase();
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
	 * 打开数据库
	 */
	protected void openDatabase() {
		if (mDb == null) {
			DBHelper helper = new DBHelper(mContext, dbName, null);
			mDb = helper.getWritableDatabase();
		}
	}

	/**
	 * 根据id获取对应数据
	 * 
	 * @param id
	 * @return
	 */
	public BaseData getDataById(long id) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id;
		return query(sql);
	}

	/**
	 * 获取所有数据
	 * 
	 * @param id
	 * @return
	 */
	public synchronized List<? extends BaseData> getAllDatas() {
		String sql = "SELECT * FROM " + TABLE_NAME;
		return queryList(sql);
	}

	/**
	 * 更新数据
	 * 
	 * @param id
	 * @param values
	 * @return 返回更新的数据条数
	 */
	public synchronized long updateData(long id, ContentValues values) {
		long result = -1;
		try {
			Log.d(TAG, TABLE_NAME + "-updateData:" + values);
			result = mDb.update(TABLE_NAME, values, ID + "=?", new String[] { String.valueOf(id) });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 插入数据
	 * @param values 
	 * @return  返回新插入数据的id
	 */
	public synchronized long insertData(ContentValues values) {
		long result = -1;
		try {
			Log.d(TAG, TABLE_NAME + "-insertData：" + values);
			result = mDb.insert(TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 插入数据
	 * 
	 * @param valuesList
	 * @return 返回新插入数据的id
	 */
	public synchronized long bulkInsertData(List<ContentValues> valuesList) {
		long result = -1;
		try {
			Log.d(TAG, TABLE_NAME + "-bulkInsertData:" + valuesList);
			mDb.beginTransaction();
			for (ContentValues values : valuesList) {
				result = mDb.insert(TABLE_NAME, null, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mDb.setTransactionSuccessful();
			mDb.endTransaction();
		}
		return result;
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return
	 */
	public synchronized int deleteData(long id) {
		int result = -1;
		try {
			Log.e(TAG, TABLE_NAME + "-deleteData:" + id);
			result = mDb.delete(TABLE_NAME, ID + "=?", new String[] { String.valueOf(id) });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
			curs = mDb.rawQuery("SELECT MAX(" + ID + ") FROM " + TABLE_NAME, null);
			if (curs != null && curs.moveToNext()) {
				id = curs.getLong(maxIdIndex);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(curs);
		}
		return id;
	}

	/**
	 * 执行sql获取数据
	 * @param sql
	 * @return
	 */
	public synchronized BaseData query(String sql) {
		BaseData data = null;
		Cursor curs = null;
		try {
			curs = mDb.rawQuery(sql, null);
			if (curs != null) {
				if (curs.getCount() == 0)
					return null;
				curs.moveToLast();
				data = parseCursor(curs);

				Log.d(TAG, "data:" + data);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeCursor(curs);
		}

		return data;
	}

	/**
	 * 执行sql获取数据
	 * @param sql
	 * @param db
	 * @return
	 */
	public synchronized List<? extends BaseData> queryList(String sql) {

		Cursor curs = null;
		List<BaseData> datas = null;
		try {
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
//			closeDb(mDb,curs);
			closeCursor(curs);
		}
		return datas;
	}

	/**
	 * 执行sql
	 * @param sql
	 */
	public synchronized void execSQL(String sql) {
		try {
			Log.d(TAG, "execSQL:" + sql);
			mDb.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 执行sql
	 * @param sql
	 * @param params
	 */
	public synchronized void execSQL(String sql, Object[] params) {
		try {
			Log.d(TAG, "execSQL:" + sql);
			mDb.execSQL(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 关闭cursor
	 * @param curs
	 */
	public void closeCursor(Cursor curs) {
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
}
