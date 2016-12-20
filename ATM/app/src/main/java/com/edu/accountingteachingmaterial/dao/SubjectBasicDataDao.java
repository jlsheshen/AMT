package com.edu.accountingteachingmaterial.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.edu.accountingteachingmaterial.bean.SubjectBasicData;
import com.edu.subject.data.SubjectData;
import com.edu.library.data.BaseDataDao;
import com.edu.library.util.ToastUtil;

import static com.edu.accountingteachingmaterial.dao.SubjectTestDataDao.FLAG;

/**
 * 基础题型数据库操作类：单多判
 *
 * @author lucher
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

	public SubjectBasicData getData(String subjectId, SQLiteDatabase db) {
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
		}finally {
			if (curs != null){
				curs.close();
			}
		}

		return data;
	}
	/**
	 * 插入题目数据
	 *
	 * @param subject
	 * @param db
	 * @return 新增id
	 */
	public int insertData(SubjectData subject, SQLiteDatabase db) {
		String sql = "select count(*) from " + TABLE_NAME + " where flag = " + subject.getFlag();
		Cursor curs = db.rawQuery(sql, null);
		int id = 0;
		try {
			if (!(curs != null && curs.moveToNext() && curs.getInt(0) > 0)) {
				String option = JSON.parseObject(subject.getOption()).getString("text");
				String question = JSON.parseObject(subject.getQuestion()).getString("text");
				String analysis = "" + JSON.parseObject( subject.getAnalysis());
				ContentValues values = new ContentValues();
				values.put("CHAPTER_ID", subject.getChapterId());
				values.put("FLAG", subject.getFlag());
				values.put("TYPE", subject.getSubjectType());
				values.put("QUESTION", question);
				values.put("OPTION", option);
				values.put("ANSWER", subject.getAnswer());
				values.put("ANALYSIS", analysis);
				values.put("SCORE", subject.getScore());
				values.put("REMARK", subject.getRemark());
				id = (int) db.replace(TABLE_NAME, null, values);
				if (id < 0) {
					ToastUtil.showToast(mContext, "题目格式出错：" + subject);
				}
				Log.d(TAG, "insert:" + id + "," + values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (curs != null) {
				curs.close();
			}
		}
		return id;
	}

	@Override
	public SubjectBasicData parseCursor(Cursor curs) {
		SubjectBasicData subjectData = new SubjectBasicData();
		subjectData.setId(curs.getInt(curs.getColumnIndex(ID)));
		subjectData.setChapterId(curs.getInt(curs.getColumnIndex(CHAPTER_ID)));
		subjectData.setFlag(curs.getInt(curs.getColumnIndex(FLAG)));
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
