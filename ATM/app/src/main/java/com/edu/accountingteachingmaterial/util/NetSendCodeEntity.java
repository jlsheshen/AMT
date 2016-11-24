package com.edu.accountingteachingmaterial.util;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.lucher.net.req.BaseReqParamsEntity;

/**
 * NetSendCodeEntity网络访问实体类
 */
public class NetSendCodeEntity extends BaseReqParamsEntity {

    public NetSendCodeEntity(Context context, int reqMethod, String url) {
        super(context, reqMethod, url);
    }

    @Override
    public RequestParams getReqParams() {
        return null;
    }

}
