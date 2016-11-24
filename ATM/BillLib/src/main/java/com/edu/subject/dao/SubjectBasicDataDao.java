package com.edu.subject.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edu.library.data.BaseDataDao;
import com.edu.subject.data.SubjectBasicData;

/**
 * 基础题型数据库操作类：单多判
 * 
 * @author lucher
 * 
 */
public class SubjectBasicDataDao extends BaseDataDao {

	private static final String TAG = "SubjectBasicDataDao";
	private static final String ID = "ID";
	private static final String TYPE = "TYPE";
	private static final String QUESTION = "QUESTION";
	private static final String OPTION = "OPTION";
	private static final String ANSWER = "ANSWER";
	private static final String ANALYSIS = "ANALYSIS";
	private static final String SCORE = "SCORE";
	private static final String CHAPTER_ID = "CHAPTER_ID";


	/**
	 * 自身引用
	 */
	private static SubjectBasicDataDao instance = null;

	private SubjectBasicDataDao(Context context, String db) {
		super(context, db);
	}

	@Override
	public void setTableName() {
		TABLE_NAME = "TB_SUBJECT_BASIC";
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static SubjectBasicDataDao getInstance(Context context, String db) {
		if (instance == null)
			instance = new SubjectBasicDataDao(context, db);
		return instance;
	}

	public SubjectBasicData getData(int subjectId, SQLiteDatabase db) {
		SubjectBasicData data = null;
		Cursor curs = null;
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID = " + subjectId;
		try {
			curs = db.rawQuery(sql, null);
			if (curs != null) {
				if (curs.getCount() == 0)
					return null;
				curs.moveToFirst();
				data = parseCursor(curs);

				Log.d(TAG, "data:" + data);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return data;
	}

	@Override
	public SubjectBasicData parseCursor(Cursor curs) {
		SubjectBasicData subjectData = new SubjectBasicData();
		subjectData.setId(curs.getInt(curs.getColumnIndex(ID)));
		subjectData.setChapterId(curs.getInt(curs.getColumnIndex(CHAPTER_ID)));
//		subjectData.setFlag(curs.getInt(curs.getColumnIndex(FLAG)));
		subjectData.setSubjectType(curs.getInt(curs.getColumnIndex(TYPE)));
		subjectData.setQuestion(curs.getString(curs.getColumnIndex(QUESTION)));
		subjectData.setOption(curs.getString(curs.getColumnIndex(OPTION)));
		subjectData.setAnswer(curs.getString(curs.getColumnIndex(ANSWER)));
		subjectData.setAnalysis(curs.getString(curs.getColumnIndex(ANALYSIS)));
		subjectData.setScore(curs.getInt(curs.getColumnIndex(SCORE)));
//		subjectData.setFavorite(curs.getInt(10));
		subjectData.setRemark(curs.getString(curs.getColumnIndex(REMARK)));

		return subjectData;
	}

}
