package com.edu.accountingteachingmaterial.util.net;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.lucher.net.req.impl.JsonNetReqManager;

import org.apache.http.Header;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.TOKEN;


/**
 * SendJsonNetReqManager网络访问管理类
 */
public class SendJsonNetReqManager extends JsonNetReqManager {
    static SendJsonNetReqManager instance;

    private JsonResponseListener mListener;

    public SendJsonNetReqManager() {
        initAsyncClient();
        initSyncClient();
        mAsyncClient.addHeader(TOKEN, PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(TOKEN));
        mAsyncClient.setMaxRetriesAndTimeout(0, RETRY_TIME_OUT);
        mSyncClient.setMaxRetriesAndTimeout(0, RETRY_TIME_OUT);
    }

    public static SendJsonNetReqManager newInstance() {
        return new SendJsonNetReqManager();
    }

    @Override
    public void onConnectionSuccess(JSONObject jsonObject, Header[] headers) {
        if (mListener != null) {
            // 经过一系列处理。。。。
            mListener.onSuccess(jsonObject);
        }
    }

    @Override
    public void onConnectionFailure(String errorInfo, Header[] headers) {
        if (mListener != null) {
            mListener.onFailure(errorInfo + "链接失败,请检查网络");
        }
    }

    @Override
    public void onConnectionError(String errorInfo) {
        if (mListener != null) {
            mListener.onFailure("链接错误,可能当前章节并没有练习题");
        }
    }

    public void setOnJsonResponseListener(JsonResponseListener listener) {
        mListener = listener;
    }

    /**
     * Json响应监听
     *
     * @author lucher
     */
    public interface JsonResponseListener {

        /**
         * 响应成功
         *
         * @param jsonObject
         */
        void onSuccess(JSONObject jsonObject);

        /**
         * 响应失败
         *
         * @param errorInfo
         */
        void onFailure(String errorInfo);
    }

}
