package com.edu.accountingteachingmaterial.util.net;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.dao.TemplateElementsDao;
import com.edu.accountingteachingmaterial.entity.BillTemplateListBean;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.library.data.DBHelper;
import com.edu.subject.BASE_URL;
import com.edu.subject.data.TemplateData;
import com.edu.subject.util.SubjectImageLoader;
import com.edu.testbill.Constant;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.JsonReqEntity;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.TOKEN;


/**
 *模板下载管理类
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

    GetBillListener getBillListener;

    SQLiteDatabase mDb = null;
    private static GetBillTemplatesManager instance;
    ProgressDialog myDialog;


    private GetBillTemplatesManager(Context context) {
        mAsyncClient.addHeader(TOKEN, PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(TOKEN));

        this.mContext = context;
    }

    /**
     * 创建实例
     * @param context
     * @return
     */
    public static GetBillTemplatesManager newInstance(Context context) {
        if (instance == null) {
            synchronized (GetBillTemplatesManager.class){
                if (instance == null){
                    instance = new GetBillTemplatesManager(context);
                }
            }
        }
        return instance;
    }

    public GetBillTemplatesManager setGetBillListener(GetBillListener getBillListener) {
        this.getBillListener = getBillListener;
        return this;
    }

    /**
     * 发送请求
     */
    public void sendLocalTemplates() {
        List<BillTemplateListBean> datas = getTemplates();
        String url = NetUrlContstant.getLocalTemplates();
        Log.d("GetBillTemplatesManager", url);
        JsonReqEntity entity = new JsonReqEntity(mContext, RequestMethod.POST, url, JSON.toJSONString(datas));

        sendRequest(entity, "耐心等待");
        Log.d("GetBillTemplatesManager", "uploadResult:" + JSON.toJSONString(datas));
    }
    /**
     * 获取本地模板数据
     * @return
     */
    private List<BillTemplateListBean> getTemplates() {
        long start = System.currentTimeMillis();
        List<BillTemplateListBean> datas = null;
        Cursor curs = null;
        try {
            DBHelper helper = new DBHelper(mContext, Constant.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            String sql = "SELECT * FROM " + TB_NAME;
            Log.d(TAG, "sql:" + sql);
            curs = mDb.rawQuery(sql, null);
            if (curs != null) {
                datas = new ArrayList<>(curs.getCount());
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
            if (curs != null) {
                curs.close();
            }
        }
        return datas;
    }


    @Override
    public void onConnectionSuccess(JSONObject json, Header[] arg1) {
        Log.d("GetBillTemplatesManager", "onConnectionSuccess:" + json);

        boolean result = json.getBoolean("result");
        final String message = json.getString("message");
        if (result) {

            myDialog = new ProgressDialog(mContext);
            myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            myDialog.setTitle("正在加载模板..");
            myDialog.setMessage("第一次加载时间会比较久^-^");
            myDialog.setCancelable(false);
            myDialog.show();
            Log.d(TAG, "------" + message + "---");
            Log.d("GetBillTemplatesManager", "context:2" + mContext);

            Observable.create(new Observable.OnSubscribe<List<TemplateData>>() {
                @Override
                public void call(Subscriber<? super List<TemplateData>> subscriber) {
                    Log.d("GetBillTemplatesManager", "插入数据");

                    List<TemplateData> billTemplates = JSON.parseArray(message, TemplateData.class);
                    saveTemplates(billTemplates);
                    subscriber.onCompleted();
                }
            })
                    .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                    .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                    .subscribe(new Observer<List<TemplateData>>() {
                        @Override
                        public void onNext(List<TemplateData> data) {
                            Log.d("GetBillTemplatesManager", "插入数据完成一点");
                            myDialog.dismiss();

                        }
                        @Override
                        public void onCompleted() {
                            Log.d("GetBillTemplatesManager", "插入数据完成");
                            getBillListener.onGetBillSuccess();
//                            EventBus.getDefault().post("1");
                            myDialog.dismiss();
                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "----------e:" + e);
                            getBillListener.onGetBillFail();
                            myDialog.dismiss();

//                            EventBus.getDefault().post("0");
                        }
                    });

        } else {

        }

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
            String s = BASE_URL.getBaseImageUrl() + billTemplate.getBitmap();
            urls.add(s);
            contentValues.put(BACKGROUND, s);
            contentValues.put(FLAG, billTemplate.getFlag());
            contentValues.put(REMARK, billTemplate.getRemark());
            int i = updateTemplateInfo(contentValues, billTemplate);
            if (i !=0){
                insertData(billTemplate);


            }else {
                updateData(billTemplate);
            }
        }
        SubjectImageLoader.getInstance(mContext).preDownloadAllPic(urls);

    }

    /**
     * 对获取的数据与本地数据进行对比,判断
     *
     * @param values
     * @param billTemplate
     */

    public int updateTemplateInfo(ContentValues values, TemplateData billTemplate) {
        int i = 0;
        Cursor curs = null;
        String sql = "SELECT * FROM " + TB_NAME + " WHERE ID = " + values.get(ID);

        try {
            DBHelper helper = new DBHelper(mContext, Constant.DATABASE_NAME, null);
            mDb = helper.getWritableDatabase();
            curs = mDb.rawQuery(sql, null);

            if (curs != null) {
                if (curs.getCount() == 0) {

                    i = (int) mDb.insert(TB_NAME, null, values);

                } else {
                    mDb.update(TB_NAME, values, ID + " =?", new String[]{String.valueOf(values.get(ID))});
                    i=0;
                    curs.moveToLast();
                }
            }

        } finally {

            if (mDb != null) {
                mDb.close();
            }
        }
        return i;
    }

    /**
     * 插入数据
     *
     * @param billTemplate
     */
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
            TemplateElementsDao.getInstance(mContext).insertData(contentValues);
        }
    }



    interface GetBillListener{

        void onGetBillSuccess();
        void onGetBillFail();

    }


    /**
     * 更新数据
     *
     * @param billTemplate
     */
    private void updateData(TemplateData billTemplate) {
        TemplateElementsDao.getInstance(mContext).deleteData(billTemplate.getId());

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
            TemplateElementsDao.getInstance(mContext).insertData(contentValues);
        }
    }


}
