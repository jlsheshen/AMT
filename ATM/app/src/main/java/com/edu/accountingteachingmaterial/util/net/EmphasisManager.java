package com.edu.accountingteachingmaterial.util.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.bean.EmphasisBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.lucher.net.req.RequestMethod;

import org.apache.http.Header;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/3/3.
 */

public class EmphasisManager extends BaseNetManager {
    private static EmphasisManager mSingleton;
    public EmphasisListener emphasisListener;

    private EmphasisManager(Context context) {
        super(context);
    }


    /**
     * 单例模式获取实例
     * @param context
     * @return
     */
    public static EmphasisManager getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (EmphasisManager.class) {
                if (mSingleton == null) {
                    mSingleton = new EmphasisManager(context);
                }
            }
        }
        return mSingleton;
    }
    /**
     * 发送答案和密码
     *
     * @param
     */
    public void getEmphasis(EmphasisListener emphasisListener, String chapterId) {
        boolean isBook = PreferenceHelper.getInstance(mContext).getBooleanValue(PreferenceHelper.IS_TEXKBOOK);
        String bookStr = isBook? ClassContstant.TEXT_BOOK_TYPE:ClassContstant.CLASS_TYPE;
        String url = NetUrlContstant.getClassicCaseUrl() + chapterId + "-" + 1 + "-" + bookStr;
        NetSendCodeEntity entity = new NetSendCodeEntity(mContext, RequestMethod.POST, url);
        Log.d(TAG, "url");
        sendRequest(entity);
        this.emphasisListener = emphasisListener;
    }
    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {

        boolean result = jsonObject.getBoolean("result");
        String message = jsonObject.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d(TAG, "有问题啊");
            }
            List<EmphasisBean> datas =  JSON.parseArray(jsonObject.getString("message"), EmphasisBean.class);

            emphasisListener.onSuccess(datas.get(0).getUri());
        }else {

            emphasisListener.onFailure("链接失败");
        }
    }

    @Override
    public void onConnectionFailure(String s, Header[] headers) {
        emphasisListener.onFailure(s);
    }

    @Override
    public void onConnectionError(String s) {
        emphasisListener.onFailure(s);
    }
    public interface EmphasisListener {
        void onSuccess(String message);
        void onFailure(String message);
    }
}
