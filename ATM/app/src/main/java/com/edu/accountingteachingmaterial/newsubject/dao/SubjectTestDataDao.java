//package com.edu.accountingteachingmaterial.newsubject.dao;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//
//import com.edu.subject.TestMode;
//import com.edu.subject.dao.BaseSubjectTestDataDao;
//import com.edu.subject.data.BaseTestData;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 测试数据数据库操作dao层
// *
// * @author lucher
// *
// */
//public class SubjectTestDataDao extends BaseSubjectTestDataDao {
//	//题目类型
//	public static final String SUBJECT_ID = "SUBJECT_ID";
//	//试卷类型
//	public static final String FLAG = "FLAG";
//
//	//试卷id
//	public static final String CHAPTER_ID = "CHAPTER_ID";
//
//	// 用户答案
//	public static final String UANSWER = "UANSWER";
//	// 用户印章-单据题
//	public static final String USIGNS = "USIGNS";
//	// 用户得分
//	public static final String USCORE = "USCORE";
//	// 题目状态
//	public static final String STATE = "STATE";
//	//题目类型
//	public static final String SUBJECT_TYPE = "SUBJECT_TYPE";
//	//错误次数
//	public static final String ERROR_COUNT = "ERROR_COUNT";
//
//	//REMARK
//	public static final String REMARK = "REMARK";
//	/**
//	 * 自身引用
//	 */
//	private static SubjectTestDataDao instance = null;
//
//
//	private SubjectTestDataDao(Context context) {
//		super(context);
//	}
//
//	/**
//	 * 获取实例
//	 *
//	 * @return
//	 */
//	public static SubjectTestDataDao getInstance(Context context) {
//		if (instance == null)
//			instance = new SubjectTestDataDao(context);
//		return instance;
//	}
//
//	@Override
//	public void setTableName() {
////		TABLE_NAME = "TB_SUBJECT_TEST";
//		TABLE_NAME = "TB_TEST";
//
//	}
//	/**
//	 * 获取所有题目
//	 *
//	 * @param testMode
//	 *            测试模式，见{@link TestMode}
//	 *
//	 * @return
//	 */
//	public List<BaseTestData> getSubjects(int testMode,int chapter) {
//		Cursor curs = null;
//		List<BaseTestData> datas = null;
//		try {
//			String sql = "SELECT * FROM " + TABLE_NAME+ " WHERE " + CHAPTER_ID + " = " + chapter;
//			curs = mDb.rawQuery(sql, null);
//			if (curs != null) {
//				datas = new ArrayList<BaseTestData>(curs.getCount());
//				int index = 1;
//
//				while (curs.moveToNext()) {
//					// 初始化测试数据
//					BaseTestData testData = initTestData(curs, testMode);
//					testData.setSubjectIndex(String.valueOf(index++));
//
//					datas.add(testData);
//				}
//			}
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeCursor(curs);
//		}
//		return datas;
//	}
//
//}
