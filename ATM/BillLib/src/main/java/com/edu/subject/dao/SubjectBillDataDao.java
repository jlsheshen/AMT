package com.edu.subject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.edu.library.data.BaseDataDao;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectConstant;
import com.edu.subject.SubjectType;
import com.edu.subject.data.SubjectBillData;
import com.edu.subject.data.SubjectData;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作dao层
 * 
 * @author lucher
 * 
 */
public class SubjectBillDataDao extends BaseDataDao {

	private static final String TAG = "SubjectBillDataDao";

	/**
	 * 自身引用
	 */
	private static SubjectBillDataDao instance = null;

	private SubjectBillDataDao(Context context, String db) {
		super(context, db);
	}

	@Override
	public void setTableName() {
		TABLE_NAME = "TB_BILL_SUBJECT";
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static SubjectBillDataDao getInstance(Context context, String db) {
		if (instance == null)
			instance = new SubjectBillDataDao(context, db);
		return instance;
	}

	/**
	 * 获取题目数据
	 * 
	 * @param id
	 * @param db
	 * @return
	 */
	public SubjectBillData getData(String id, SQLiteDatabase db) {
		SubjectBillData data = null;
		Cursor curs = null;
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id;
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

	/**
	 * 获取题目数据，用于分组单据
	 * 
	 * @param subjectId
	 * @param mDb
	 * @return
	 */
	public List<SubjectBillData> getDatas(String id, SQLiteDatabase db) {
		List<SubjectBillData> datas = null;
		Cursor curs = null;
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id;
		try {
			curs = db.rawQuery(sql, null);
			if (curs != null) {
				if (curs.getCount() == 0)
					return null;
				curs.moveToFirst();
				datas = parseCursors(curs);

				Log.d(TAG, "data:" + datas);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return datas;

	}

	/**
	 * 解析cursor
	 * 
	 * @param curs
	 * @return
	 */
	public List<SubjectBillData> parseCursors(Cursor curs) {
		int id = curs.getInt(0);
		// 对多组单据的处理
		String[] templateIds = curs.getString(3).split(SubjectConstant.SEPARATOR_GROUP);
		String[] labels = curs.getString(6).split(SubjectConstant.SEPARATOR_GROUP);
		String[] answers = curs.getString(7).split(SubjectConstant.SEPARATOR_GROUP);
		int size = templateIds.length;
		if (size <= 1 || labels.length != size || answers.length != size) {
			ToastUtil.showToast(mContext, "多组单据题目数据不合法：" + id);
			return null;
		}

		List<SubjectBillData> subjectDatas = new ArrayList<SubjectBillData>(size);
		for (int i = 0; i < size; i++) {
			SubjectBillData subjectData = new SubjectBillData();
			subjectData.setId(id);
			subjectData.setChapterId(curs.getInt(1));
			subjectData.setFlag(curs.getInt(2));
			subjectData.setQuestion(curs.getString(4));
			subjectData.setPic(curs.getString(5));
			subjectData.setScore(curs.getInt(8));
			subjectData.setErrorCount(curs.getInt(9));
			subjectData.setFavorite(curs.getInt(10));
			subjectData.setRemark(curs.getString(11));
			subjectData.setSubjectType(SubjectType.SUBJECT_BILL);

			subjectData.setLabel(labels[i]);
			subjectData.setAnswer(answers[i]);
			subjectData.setTemplateId(templateIds[i]);

			subjectDatas.add(subjectData);
		}

		return subjectDatas;
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
				String question = JSON.parseObject(subject.getQuestion()).getString("text");
				ContentValues values = new ContentValues();
				values.put("CHAPTER_ID", subject.getChapterId());
				values.put("FLAG", subject.getFlag());
				values.put("TEMPLATE_ID", subject.getTemplateId());
				values.put("QUESTION", question);
				values.put("PIC", subject.getPic());
				values.put("LABELS", subject.getLabel());
				values.put("BLANKS", subject.getAnswer());
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
	public SubjectBillData parseCursor(Cursor curs) {
		SubjectBillData subjectData = new SubjectBillData();
		subjectData.setId(curs.getInt(0));
		subjectData.setChapterId(curs.getInt(1));
		subjectData.setFlag(curs.getInt(2));
		subjectData.setTemplateId(curs.getString(3));
		subjectData.setQuestion(curs.getString(4));
		subjectData.setPic(curs.getString(5));
		subjectData.setLabel(curs.getString(6));
		subjectData.setAnswer(curs.getString(7));
		subjectData.setScore(curs.getInt(8));
		subjectData.setErrorCount(curs.getInt(9));
		subjectData.setFavorite(curs.getInt(10));
		subjectData.setRemark(curs.getString(11));
		subjectData.setSubjectType(SubjectType.SUBJECT_BILL);

		return subjectData;
	}

}
