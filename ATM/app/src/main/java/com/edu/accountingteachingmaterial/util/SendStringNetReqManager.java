package com.edu.accountingteachingmaterial.util;

import com.lucher.net.req.impl.StringNetReqManager;

import org.apache.http.Header;


/**
 * SendStringNetReqManager网络访问管理类
 */
public class SendStringNetReqManager extends StringNetReqManager {

    private StringResponseListener mListener;

    public SendStringNetReqManager() {
        initAsyncClient();
        initSyncClient();
        mAsyncClient.setMaxRetriesAndTimeout(0, RETRY_TIME_OUT);
        mSyncClient.setMaxRetriesAndTimeout(0, RETRY_TIME_OUT);
    }

    public static SendStringNetReqManager newInstance() {
        return new SendStringNetReqManager();
    }

    @Override
    public void onConnectionSuccess(String responseString, Header[] headers) {
        if (mListener != null) {
            // 经过一系列处理。。。。
            mListener.onSuccess(responseString);
        }
    }

    @Override
    public void onConnectionFailure(String errorInfo, Header[] headers) {
        if (mListener != null) {
            mListener.onFailure(errorInfo);
        }

    }

    @Override
    public void onConnectionError(String errorInfo) {
        if (mListener != null) {
            mListener.onFailure(errorInfo);
        }
    }

    public void setOnStringResponseListener(StringResponseListener listener) {
        mListener = listener;
    }

    /**
     * String响应监听
     *
     * @author lucher
     */
    public interface StringResponseListener {

        /**
         * 响应成功
         *
         * @param response
         */
        void onSuccess(String response);

        /**
         * 响应失败
         *
         * @param errorInfo
         */
        void onFailure(String errorInfo);
    }
}
