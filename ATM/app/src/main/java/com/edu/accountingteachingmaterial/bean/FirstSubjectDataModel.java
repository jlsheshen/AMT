package com.edu.accountingteachingmaterial.bean;

/**
 * 一级科目First_Subject表 数据处理模型封装
 * 
 * @author edu_lx
 * 
 */

import android.content.Context;

import com.edu.library.data.BaseData;

import java.util.List;

public class FirstSubjectDataModel {

	// 自身实例
	private static FirstSubjectDataModel instance;

	private Context mContext;

	public static synchronized FirstSubjectDataModel getInstance(Context context) {
		if (instance == null) {
			instance = new FirstSubjectDataModel(context);
		}
		return instance;
	}

	private FirstSubjectDataModel(Context context) {
		this.mContext = context;
	}

	/**
	 * 获取所有数据
	 * 
	 * @return
	 */
	public List<BaseData> getAllDatas() {
		List<BaseData> datas = FirstSubjectDataDao.getInstance(mContext).getAllDatas();

		return datas;
	}

	/**
	 * 根据id获取对应的数据
	 * 
	 * @param id
	 * @return
	 */
	public FirstSubjectData getDataById(int id) {
		return (FirstSubjectData) FirstSubjectDataDao.getInstance(mContext).getDataById(id);

	}

//	/**
//	 * 插入数据
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public void insertData(Topic topic) {
//		ContentValues values = new ContentValues();
//		values.put(FirstSubjectDataDao.SUBTYPE, "SUBTYPE");
//		values.put(FirstSubjectDataDao.NAME, "一级科目");
//		values.put(FirstSubjectDataDao.PARENTID, "PARENTID");
//		values.put(BaseDataDao.REMARK, "REMARK");
//		FirstSubjectDataDao.getInstance(mContext).insertData(values);
//	}

	/**
	 * 根据SUB_TYPE获取所有填制类别的数据，已FirstSubjectData的形式返回
	 * 
	 * @return
	 */
	public List<FirstSubjectData> getDatasByType(int subType) {
		List<FirstSubjectData> datas = FirstSubjectDataDao.getInstance(mContext).getDatasByType(subType);

		return datas;
	}

	/**
	 * 根据PARENT_ID获取所有填制类别的数据，已FirstSubjectData的形式返回
	 * 
	 * @return
	 */
	public List<FirstSubjectData> getDatasByParentid(int parentId) {
		List<FirstSubjectData> datas = FirstSubjectDataDao.getInstance(mContext).getDatasByParentid(parentId);

		return datas;
	}

}
