package com.edu.accountingteachingmaterial.newsubject.dao;

import android.content.Context;

import com.edu.subject.dao.BaseSubjectTestDataDao;

/**
 * 测试数据数据库操作dao层
 * 
 * @author lucher
 * 
 */
public class SubjectOnlineTestDataDao extends BaseSubjectTestDataDao {

	/**
	 * 自身引用
	 */
	private static SubjectOnlineTestDataDao instance = null;


	private SubjectOnlineTestDataDao(Context context) {
		super(context);
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static BaseSubjectTestDataDao getInstance(Context context) {
		if (instance == null)
			instance = new SubjectOnlineTestDataDao(context);
		return instance;
	}
	
	@Override
	public void setTableName() {
		TABLE_NAME = "TB_SUBJECT_ONLINE_TEST";
	}

}
