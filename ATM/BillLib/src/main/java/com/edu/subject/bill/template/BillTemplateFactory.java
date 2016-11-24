package com.edu.subject.bill.template;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edu.subject.dao.TemplateDataDao;

/**
 * 模板工厂类
 * @author lucher
 *
 */
public class BillTemplateFactory {

	private static final String TAG = "BillTemplateFactory";
	/**
	 * 缓存ConcurrentHashMap
	 */
	private static ConcurrentHashMap<Integer, SoftReference<BillTemplate>> TEMPLATE_CACHE = new ConcurrentHashMap<Integer, SoftReference<BillTemplate>>();

	/**
	 * 根据模板id获取模板实例，如果缓存中已存在，则直接返回缓存数据(提高效率，减少内存开支)
	 * 
	 * @param db
	 * 
	 * @param templateId
	 * @return
	 */
	public static BillTemplate createTemplate(SQLiteDatabase db, int templateId, Context context) {
//		if (TEMPLATE_CACHE.get(templateId) != null && TEMPLATE_CACHE.get(templateId).get() != null) {
//			Log.d(TAG, "template:" + templateId + " exists");
//			return TEMPLATE_CACHE.get(templateId).get();
//		} ***缓存机制暂时取消，目前存在bug
		Log.d(TAG, "create template:" + templateId);
		BillTemplate template = TemplateDataDao.getInstance(context).getBillTemplate(templateId, db);
//		if (template != null) {
//			TEMPLATE_CACHE.put(templateId, new SoftReference<BillTemplate>(template));
//		}***缓存机制暂时取消，目前存在bug

		return template;
	}
}
