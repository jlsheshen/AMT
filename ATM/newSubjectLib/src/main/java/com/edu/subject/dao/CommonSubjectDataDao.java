package com.edu.subject.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.edu.library.data.BaseDataDao2;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectConstant;
import com.edu.subject.SubjectType;
import com.edu.subject.common.rich.RichTextUtil;
import com.edu.subject.data.CommonSubjectData;
import com.edu.subject.data.SubjectBillData;
import com.edu.subject.data.SubjectBlankData;
import com.edu.subject.data.SubjectComprehensiveData;
import com.edu.subject.data.SubjectEntryData;
import com.edu.subject.data.SubjectSelectData;

/**
 * 题型数据库操作类
 * 
 * @author lucher
 * 
 */
public class CommonSubjectDataDao extends BaseDataDao2 {

	private static final String TAG = "EntrySubjectDataDao";

	/**
	 * 自身引用
	 */
	private static CommonSubjectDataDao instance = null;

	private CommonSubjectDataDao(Context context, String db) {
		super(context, db);
	}

	@Override
	public void setTableName() {
		TABLE_NAME = "TB_SUBJECT";
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static CommonSubjectDataDao getInstance(Context context) {
		if (instance == null)
			instance = new CommonSubjectDataDao(context, SubjectConstant.DATABASE_NAME);
		return instance;
	}

	/**
	 * 根据id获取题目数据
	 * @param subjectId
	 * @return
	 */
	public CommonSubjectData getData(int subjectId) {
		CommonSubjectData data = (CommonSubjectData) getDataById(subjectId);
		return data;
	}

	/**
	 * 根据parentId获取题目数据
	 * @param parentId
	 * @return
	 */
	public List<CommonSubjectData> getDatasByParentId(int parentId) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE PARENT_ID = " + parentId;
		List<CommonSubjectData> datas = (List<CommonSubjectData>) queryList(sql);
		return datas;
	}

	@Override
	public CommonSubjectData parseCursor(Cursor curs) {
		CommonSubjectData subjectData = null;
		int subjectType = curs.getInt(4);
		switch (subjectType) {
		case SubjectType.SUBJECT_BILL:
			subjectData = new SubjectBillData();
			break;

		case SubjectType.SUBJECT_SINGLE:
		case SubjectType.SUBJECT_MULTI:
		case SubjectType.SUBJECT_JUDGE:
			subjectData = new SubjectSelectData();

			break;

		case SubjectType.SUBJECT_ENTRY:
			subjectData = new SubjectEntryData();

			break;

		case SubjectType.SUBJECT_COMPREHENSIVE:
			subjectData = new SubjectComprehensiveData();

			break;
		case SubjectType.SUBJECT_BLANK:
			subjectData = new SubjectBlankData();

			break;

		default:
			subjectData = new CommonSubjectData();
			break;
		}

		subjectData.setId(curs.getInt(0));
		subjectData.setParentId(curs.getInt(1));
		subjectData.setChapterId(curs.getInt(2));
		subjectData.setFlag(curs.getInt(3));
		subjectData.setSubjectType(subjectType);
		subjectData.setQuestion(RichTextUtil.parse(curs.getString(5)));
		subjectData.setBody(curs.getString(6));
		subjectData.setAnswer(RichTextUtil.parse(curs.getString(7)));
		subjectData.setAnalysis(RichTextUtil.parse(curs.getString(8)));
		subjectData.setScore(curs.getInt(9));
		subjectData.setRemark(curs.getString(10));

		return subjectData;
	}

	/**
	 * 插入题目数据
	 * 
	 * @param subject
	 * @param db
	 * @return 如果存在则返回当前题目id，否则返回新增id
	 */
	public int insertData(CommonSubjectData subject) {
		String sql = "select id from " + TABLE_NAME + " where flag = " + subject.getFlag();
		Cursor curs = mDb.rawQuery(sql, null);
		int id = 0;//如果存在代表原題id，否則代表新增题的id
		try {
			boolean exist = false;//是否存在该题
			if (curs != null && curs.moveToNext()) {
				id = curs.getInt(0);
				if (id > 0) {
					exist = true;
					Log.d(TAG, "insertData subject exists:" + id);
				}
			}
			//不存在则插入新数据
			if (!exist) {
				ContentValues values = new ContentValues();
				values.put("PARENT_ID", subject.getParentId());
				values.put("CHAPTER_ID", subject.getChapterId());
				values.put("FLAG", subject.getFlag());
				values.put("SUBJECT_TYPE", subject.getSubjectType());
				values.put("QUESTION", subject.getQuestion().toJsonString());
				values.put("BODY", subject.getBody());
				values.put("ANSWER", subject.getAnswer().toJsonString());
				values.put("ANALYSIS", subject.getAnalysis().toJsonString());
				values.put("SCORE", subject.getScore());
				values.put("REMARK", subject.getRemark());
				id = (int) mDb.replace(TABLE_NAME, null, values);
				if (id < 0) {
					ToastUtil.showToast(mContext, "题目格式出错：" + subject);
					Log.e(TAG, "insert error:" + id);
				} else {
					Log.d(TAG, "insert success:" + id);
				}
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
}
