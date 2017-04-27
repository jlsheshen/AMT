package com.edu.subject.bill.template;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edu.library.data.DBHelper;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectConstant;
import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.element.info.BaseElementInfo;
import com.edu.subject.bill.element.info.BlankInfo;
import com.edu.subject.bill.element.info.FlashInfo;
import com.edu.subject.bill.element.info.SignInfo;
import com.edu.subject.data.answer.BillAnswerData.SignResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 单据模板数据库操作类
 * 
 * @author lucher
 * 
 */
public class BillTemplateDao {
	private static final String CONTENT = "CONTENT";
	private static final String TEMPLATE_ID = "TEMPLATE_ID";
	private static final String TYPE = "TYPE";
	private static final String X = "X";
	private static final String Y = "Y";
	private static final String WIDTH = "WIDTH";
	private static final String HEIGHT = "HEIGHT";
	private static final String SCORE = "SCORE";
	private static final String ID = "ID";
	private static final String REMARK = "REMARK";
	private static final String NAME = "NAME";
	private static final String BACKGROUND = "BACKGROUND";
	private static final String FLAG = "FLAG";

	private static final String TAG = "BillTestDataDao";

	/**
	 * 自身引用
	 */
	private static BillTemplateDao instance = null;
	private Context mContext;

	private BillTemplateDao(Context context) {
		mContext = context;
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static BillTemplateDao getInstance(Context context) {
		if (instance == null)
			instance = new BillTemplateDao(context);
		return instance;
	}

	/**
	 * 根据id获取模板数据
	 * 
	 * @param id
	 * @return
	 */
	public BillTemplate getBillTemplate(int id, SQLiteDatabase db) {
		BillTemplate template = null;
		Cursor curs = null;

		String sql = "SELECT * FROM TB_BILL_TEMPLATE LEFT JOIN TB_TEMPLATE_ELEMENTS ON TB_BILL_TEMPLATE.ID = TB_TEMPLATE_ELEMENTS.TEMPLATE_ID WHERE TB_BILL_TEMPLATE.ID = " + id;
		// Log.d(TAG, "sql:" + sql);
		curs = db.rawQuery(sql, null);
		if (curs != null) {
			List<BaseElementInfo> elements = new ArrayList<BaseElementInfo>(curs.getCount());
			while (curs.moveToNext()) {
				if (curs.isFirst()) {
					template = new BillTemplate();
					template.setId(curs.getInt(0));
					template.setName(curs.getString(curs.getColumnIndex(NAME)));
					template.setBitmap(curs.getString(curs.getColumnIndex(BACKGROUND)));
					template.setFlag(curs.getInt(curs.getColumnIndex(FLAG)));
				}
				BaseElementInfo element;
				int type = curs.getInt(curs.getColumnIndex(TYPE));
				switch (type) {
				case ElementType.TYPE_SIGN:
					element = new SignInfo();
					initElement(element, curs);
					((SignInfo) element).setUser(false);
					((SignInfo) element).setBitmap(getSignBitmap(curs.getColumnIndex(CONTENT), db));

					break;
				case ElementType.TYPE_FLASH:
					element = new FlashInfo();
					initElement(element, curs);

					break;

				default:
					element = new BlankInfo();
					initElement(element, curs);
					try {
						((BlankInfo) element).setTextSize(curs.getInt(curs.getColumnIndex(CONTENT)));
					} catch (Exception e) {
						e.printStackTrace();
						ToastUtil.showToast(mContext, "字体大小必须为整数：" + element);
						((BlankInfo) element).setTextSize(0);
					}

					break;
				}
				elements.add(element);
			}
			template.setElementDatas(elements);
		}

		return template;
	}

	/**
	 * 初始化element
	 * 
	 * @param element
	 * @param curs
	 */
	private void initElement(BaseElementInfo element, Cursor curs) {
		element.setId(curs.getInt(curs.getColumnIndex(ID)));
		element.setType(curs.getInt(curs.getColumnIndex(TYPE)));
		element.setX(curs.getInt(curs.getColumnIndex(X)));
		element.setY(curs.getInt(curs.getColumnIndex(Y)));
		element.setWidth(curs.getInt(curs.getColumnIndex(WIDTH)));
		element.setHeight(curs.getInt(curs.getColumnIndex(HEIGHT)));
		element.setScore(curs.getFloat(curs.getColumnIndex(SCORE)));
		element.setRemark(curs.getString(curs.getColumnIndex(REMARK)));
	}

	/**
	 * 加载印章到模板里,用于加载题目里的印章
	 */
	public List<SignInfo> loadSigns(String sign) {
		List<SignInfo> list = new ArrayList<SignInfo>(1);
		SQLiteDatabase db = null;
		try {
			DBHelper helper = new DBHelper(mContext, SubjectConstant.DATABASE_NAME, null);
			db = helper.getWritableDatabase();

			String[] signs = sign.split(SubjectConstant.SEPARATOR_ITEM);
			for (int i = 0; i < signs.length; i++) {
				String[] infos = signs[i].split(SubjectConstant.SEPARATOR_SIGN_INFO);
				SignInfo info = new SignInfo();
				info.setType(ElementType.TYPE_SIGN);
				info.setId(Integer.parseInt(infos[0]));
				info.setX(Float.parseFloat(infos[1]));
				info.setY(Float.parseFloat(infos[2]));
				info.setUser(false);
				info.setBitmap(getSignBitmap(info.getId(), db));
				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.showToast(mContext, "印章数据录入有问题：" + sign);
		} finally {
			if (db != null) {
				db.close();
			}
		}

		return list;
	}
	
	/**
	 * 加载印章到模板里,用于加载用户印章
	 */
	public List<SignInfo> loadSigns(List<SignResult> signs) {
		List<SignInfo> list = new ArrayList<SignInfo>(1);
		SQLiteDatabase db = null;
		try {
			DBHelper helper = new DBHelper(mContext, SubjectConstant.DATABASE_NAME, null);
			db = helper.getWritableDatabase();

			for (int i = 0; i < signs.size(); i++) {
				SignInfo info = new SignInfo();
				info.setType(ElementType.TYPE_SIGN);
				info.setId(signs.get(i).getSignId());
				info.setX(signs.get(i).getX());
				info.setY(signs.get(i).getY());
				info.setCorrect(signs.get(i).isRight());
				info.setUser(true);
				info.setBitmap(getSignBitmap(info.getId(), db));
				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}

		return list;
	}

	/**
	 * 加载闪电符
	 * @param flash
	 * @return
	 */
	public List<FlashInfo> loadFlashs(String flash) {
		List<FlashInfo> list = new ArrayList<FlashInfo>(1);
		try {
			String[] signs = flash.split(SubjectConstant.SEPARATOR_ITEM);
			for (int i = 0; i < signs.length; i++) {
				String[] infos = signs[i].split(SubjectConstant.SEPARATOR_SIGN_INFO);
				FlashInfo info = new FlashInfo();
				info.setType(ElementType.TYPE_FLASH);
				info.setId(Integer.parseInt(infos[0]));
				info.setX(Float.parseFloat(infos[1]));
				info.setY(Float.parseFloat(infos[2]));
				info.setWidth(Float.parseFloat(infos[3]));
				info.setHeight(Float.parseFloat(infos[4]));
				info.setShow(false);
				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.showToast(mContext, "闪电符数据录入有问题：" + flash);
		}
		return list;
	}

	/**
	 * 获取印章图片
	 * 
	 * @param id
	 * @return
	 */
	public String getSignBitmap(int id, SQLiteDatabase db) {
		String bitmap = null;
		String sql = "SELECT * FROM TB_SIGN WHERE ID = " + id;
		Cursor curs = db.rawQuery(sql, null);
		if (curs != null) {
			curs.moveToFirst();
			try {
				bitmap = curs.getString(3);
			} catch (Exception e) {
				e.printStackTrace();
				ToastUtil.showToast(mContext, "印章类型对应的content值必须是存在的印章id");
				Log.e(TAG, "印章字段不合法");
			}
		}
		return bitmap;
	}

}
