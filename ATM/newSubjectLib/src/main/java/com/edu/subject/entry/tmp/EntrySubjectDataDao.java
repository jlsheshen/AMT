package com.edu.subject.entry.tmp;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.edu.library.data.BaseDataDao2;
import com.edu.subject.SubjectConstant;

/**
 * 分录科目数据库操作类
 * 
 * @author lucher
 * 
 */
public class EntrySubjectDataDao extends BaseDataDao2 {

	private static final String TAG = "EntrySubjectDataDao";

	/**
	 * 自身引用
	 */
	private static EntrySubjectDataDao instance = null;

	private EntrySubjectDataDao(Context context) {
		super(context, SubjectConstant.DATABASE_NAME);
	}

	@Override
	public void setTableName() {
		TABLE_NAME = "TB_ENTRY_SUBJECT";
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static EntrySubjectDataDao getInstance(Context context) {
		if (instance == null)
			instance = new EntrySubjectDataDao(context);
		return instance;
	}

	@Override
	public EntrySubjectData parseCursor(Cursor curs) {
		EntrySubjectData subjectData = new EntrySubjectData();
		subjectData.setId(curs.getInt(0));
		subjectData.setFlag(curs.getInt(1));
		subjectData.setSubType(curs.getInt(2));
		subjectData.setName(curs.getString(3));
		subjectData.setParentId(curs.getInt(4));
		subjectData.setRemark(curs.getString(5));

		return subjectData;
	}

	/**
	 * 根据parentId获取对应科目数据
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EntrySubjectData> getDatasByParentId(int parentId) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE PARENT_ID = " + parentId;
		return (List<EntrySubjectData>) queryList(sql);
	}

	/**
	 * 根据type获取对应科目数据
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EntrySubjectData> getDatasByType(int type) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE SUB_TYPE = " + type;
		return (List<EntrySubjectData>) queryList(sql);
	}
}
