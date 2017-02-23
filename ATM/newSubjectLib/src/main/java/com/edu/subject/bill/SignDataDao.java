package com.edu.subject.bill;

import android.content.Context;
import android.database.Cursor;

import com.edu.library.data.BaseDataDao2;

/**
 * 印章数据库操作dao层
 * 
 * @author lucher
 * 
 */
public class SignDataDao extends BaseDataDao2 {

	private static final String TAG = "SignDataDao";

	/**
	 * 自身引用
	 */
	private static SignDataDao instance = null;

	private SignDataDao(Context context, String db) {
		super(context, db);
	}

	@Override
	public void setTableName() {
		TABLE_NAME = "TB_SIGN";
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static SignDataDao getInstance(Context context, String db) {
		if (instance == null)
			instance = new SignDataDao(context, db);
		return instance;
	}

	@Override
	public SignData parseCursor(Cursor curs) {
		SignData data = new SignData();
		data.setId(curs.getInt(0));
		data.setFlag(curs.getInt(1));
		data.setName(curs.getString(2));
		data.setPic(curs.getString(3));
		data.setRemark(curs.getString(4));

		return data;
	}

}
