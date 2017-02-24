package com.edu.accountingteachingmaterial.newsubject.dao;

import android.content.Context;

import com.edu.subject.dao.BaseSubjectTestDataDao;

/**
 * 测试数据数据库操作dao层
 * 
 * @author lucher
 * 
 */
public class SubjectTestDataDao extends BaseSubjectTestDataDao {

	/**
	 * 自身引用
	 */
	private static SubjectTestDataDao instance = null;


	private SubjectTestDataDao(Context context) {
		super(context);
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static BaseSubjectTestDataDao getInstance(Context context) {
		if (instance == null)
			instance = new SubjectTestDataDao(context);
		return instance;
	}
	
	@Override
	public void setTableName() {
		TABLE_NAME = "TB_SUBJECT_TEST";
	}

}
