package com.edu.subject.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.library.data.DBHelper;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectConstant;
import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.element.info.BaseElementInfo;
import com.edu.subject.bill.element.info.BlankInfo;
import com.edu.subject.bill.element.info.FlashInfo;
import com.edu.subject.bill.element.info.SignInfo;
import com.edu.subject.bill.template.BillTemplate;
import com.edu.testbill.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 单据模板数据库操作类
 * 
 * @author lucher
 * 
 */
public class TemplateDataDao {

	private static final String TAG = "BillTestDataDao";

	/**
	 * 自身引用
	 */
	private static TemplateDataDao instance = null;
	private Context mContext;

	private TemplateDataDao(Context context) {
		mContext = context;
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static TemplateDataDao getInstance(Context context) {
		if (instance == null)
			instance = new TemplateDataDao(context);
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
					template.setName(curs.getString(1));
					template.setBitmap(curs.getString(2));
					template.setFlag(curs.getInt(3));
				}
				BaseElementInfo element;
				int type = curs.getInt(7);
				switch (type) {
				case ElementType.TYPE_SIGN:
					element = new SignInfo();
					initElement(element, curs);
					((SignInfo) element).setUser(false);
					((SignInfo) element).setBitmap(getSignBitmap(curs.getInt(12), db));

					break;
				case ElementType.TYPE_FLASH:
					element = new FlashInfo();
					initElement(element, curs);

					break;

				default:
					element = new BlankInfo();
					initElement(element, curs);
					try {
						((BlankInfo) element).setTextSize(curs.getInt(12));
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
		element.setId(curs.getInt(6));
		element.setType(curs.getInt(8));
		element.setX(curs.getInt(9));
		element.setY(curs.getInt(10));
		element.setWidth(curs.getInt(11));
		element.setHeight(curs.getInt(12));
		element.setScore(curs.getFloat(14));
		element.setRemark(curs.getString(15));
	}

	/**
	 * 加载用户印章到模板里
	 * 
	 * @param uSign
	 */
	public List<SignInfo> loadUserSigns(String uSign) {
		List<SignInfo> list = new ArrayList<SignInfo>(1);
		SQLiteDatabase db = null;
		try {
			DBHelper helper = new DBHelper(mContext, Constant.DATABASE_NAME, null);
			db = helper.getWritableDatabase();

			String[] signs = uSign.split(SubjectConstant.SEPARATOR_ITEM);
			for (int i = 0; i < signs.length; i++) {
				String[] infos = signs[i].split(SubjectConstant.SEPARATOR_SIGN_INFO);
				SignInfo info = new SignInfo();
				info.setId(Integer.parseInt(infos[0]));
				info.setType(ElementType.TYPE_SIGN);
				info.setX(Float.parseFloat(infos[1]));
				info.setY(Float.parseFloat(infos[2]));
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
			bitmap = curs.getString(3);
		}
		return bitmap;
	}
}
