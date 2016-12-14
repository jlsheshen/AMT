package com.edu.accountingteachingmaterial.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.TemplateElementsDao;
import com.edu.accountingteachingmaterial.entity.BillTemplateListBean;
import com.edu.library.data.DBHelper;
import com.edu.subject.data.TemplateData;
import com.edu.subject.util.SubjectImageLoader;
import com.edu.testbill.Constant;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.JsonReqEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/12/13.
 */

public class GetBillTemplatesManager extends JsonNetReqManager {

    private static final String TB_NAME = "TB_BILL_TEMPLATE";

    private static final String ID = "ID";
    private static final String TIME = "TIME";
    private static final String NAME = "NAME";
    private static final String BACKGROUND = "BACKGROUND";
    private static final String FLAG = "FLAG";
    private static final String REMARK = "REMARK";


    private static final String CONTENT = "CONTENT";
    private static final String TEMPLATE_ID = "TEMPLATE_ID";
    private static final String TYPE = "TYPE";
    private static final String X = "X";
    private static final String Y = "Y";
    private static final String WIDTH = "WIDTH";
    private static final String HEIGHT = "HEIGHT";
    private static final String SCORE = "SCORE";


    SQLiteDatabase mDb = null;
    private Context context;
    private static GetBillTemplatesManager instance;

    private GetBillTemplatesManager(Context context) {
        this.context = context;
    }

    /**
     * 创建实例
     *
     * @param context
     * @return
     */
    public static GetBillTemplatesManager newInstance(Context context) {
        if (instance == null) {
            instance = new GetBillTemplatesManager(context);
        }
        return instance;
    }

    public void sendLocalTemplates() {


        List<BillTemplateListBean> datas = getTemplates();

        String url = NetUrlContstant.localTemplates;
        JsonReqEntity entity = new JsonReqEntity(context, RequestMethod.POST, url, JSON.toJSONString(datas));
        sendRequest(entity);
        Log.d(TAG, "uploadResult:" + JSON.toJSONString(datas));

    }

    private List<BillTemplateListBean>  getTemplates() {
        List<BillTemplateListBean> datas = null;
        Cursor curs = null;
        try {
            DBHelper helper = new DBHelper(context, Constant.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TB_NAME;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);
            if (curs != null) {
                datas = new ArrayList<BillTemplateListBean>(curs.getCount());
                while (curs.moveToNext()) {
                    BillTemplateListBean data = new BillTemplateListBean();
                    data.setId(curs.getInt(curs.getColumnIndex(ID)));
                    data.setTimestamp(curs.getString(curs.getColumnIndex(TIME)));
                    datas.add(data);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (curs !=null)
            { curs.close();}
        }
        return datas;
    }


    @Override
    public void onConnectionSuccess(JSONObject json, Header[] arg1) {
        Log.d(TAG, "onConnectionSuccess:" + json);

        boolean result = json.getBoolean("result");
        String message = json.getString("message");
        if (result) {
            List<TemplateData> billTemplates = JSON.parseArray(message, TemplateData.class);
            Log.d(TAG, "------" + message + "---");
            saveTemplates(billTemplates);
        } else {

        }
        EventBus.getDefault().post(ClassContstant.EXAM_UNDONE);
    }

    @Override
    public void onConnectionError(String arg0) {
        Log.e(TAG, "onConnectionError:" + arg0);
    }

    @Override
    public void onConnectionFailure(String arg0, Header[] arg1) {
        Log.e(TAG, "onConnectionFailure:" + arg0);
    }


    /**
     * 模板数据到数据库
     *
     * @param billTemplates 获取的模板数据
     */
    private void saveTemplates(List<TemplateData> billTemplates) {
        if (billTemplates == null) {
            Toast.makeText(mContext, "无需更新模板", Toast.LENGTH_SHORT).show();
            return;
        }
        List<String> urls = new ArrayList<>();
        for (TemplateData billTemplate : billTemplates) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, billTemplate.getId());
            contentValues.put(TIME, billTemplate.getTimeStamp());
            contentValues.put(NAME, billTemplate.getName());
            contentValues.put(BACKGROUND, billTemplate.getBitmap());
            //String[] s = billTemplate.getBitmap().split("background/");
            String s = billTemplate.getBitmap();
            Log.d("billTemplate", "s:" + s);
            urls.add(s);
            contentValues.put(FLAG, billTemplate.getFlag());
            contentValues.put(REMARK, billTemplate.getRemark());
            updateTemplateInfo(contentValues, billTemplate);
        }
        SubjectImageLoader.getInstance(context).preDownloadAllPic(urls);

    }

    public void updateTemplateInfo(ContentValues values, TemplateData billTemplate) {
        Cursor curs = null;
        String sql = "SELECT * FROM "+TB_NAME+" WHERE ID = " + values.get(ID);

        try {
            DBHelper helper = new DBHelper(mContext, Constant.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            curs = mDb.rawQuery(sql, null);

            if (curs != null) {
                if (curs.getCount() == 0) {
                    mDb.insert("TB_BILL_TEMPLATE", null, values);
                    insertData(billTemplate);
                } else {
                    mDb.update("TB_BILL_TEMPLATE", values, ID + " =?", new String[]{String.valueOf(values.get(ID))});
                    curs.moveToLast();
                    updateData(billTemplate);
                }
            }
        } finally {
            if (curs != null) {
                curs.close();
            }
        }
    }

    private void insertData(TemplateData billTemplate) {

        for (TemplateData.BlanksDatasBean blanksDatasBean : billTemplate.getBlanksDatas()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, blanksDatasBean.getId());
            contentValues.put(TEMPLATE_ID, billTemplate.getId());
            contentValues.put(TYPE, blanksDatasBean.getType());
            contentValues.put(X, blanksDatasBean.getX());
            contentValues.put(Y, blanksDatasBean.getY());
            contentValues.put(WIDTH, blanksDatasBean.getWidth());
            contentValues.put(HEIGHT, blanksDatasBean.getHeight());
            contentValues.put(SCORE, blanksDatasBean.getScore());
            contentValues.put(REMARK, blanksDatasBean.getRemark());
            contentValues.put(CONTENT, blanksDatasBean.getContent());
            TemplateElementsDao.getInstance(context).insertData(contentValues);
        }
    }

    private void updateData(TemplateData billTemplate) {
        TemplateElementsDao.getInstance(context).deleteData(billTemplate.getId());

        for (TemplateData.BlanksDatasBean blanksDatasBean : billTemplate.getBlanksDatas()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, blanksDatasBean.getId());
            contentValues.put(TEMPLATE_ID, billTemplate.getId());
            contentValues.put(TYPE, blanksDatasBean.getType());
            contentValues.put(X, blanksDatasBean.getX());
            contentValues.put(Y, blanksDatasBean.getY());
            contentValues.put(WIDTH, blanksDatasBean.getWidth());
            contentValues.put(HEIGHT, blanksDatasBean.getHeight());
            contentValues.put(SCORE, blanksDatasBean.getScore());
            contentValues.put(REMARK, blanksDatasBean.getRemark());
            contentValues.put(CONTENT, blanksDatasBean.getContent());
            TemplateElementsDao.getInstance(context).insertData(contentValues);
        }


    }


}
