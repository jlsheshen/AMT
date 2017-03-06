package com.edu.accountingteachingmaterial.util;

import android.content.Context;

import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.lucher.net.req.impl.JsonNetReqManager;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.TOKEN;

/**
 * newManager基类
 * Created by Administrator on 2017/3/3.
 */

public abstract class BaseNetManager extends JsonNetReqManager {

    protected Context mContext;


    public BaseNetManager(Context context) {
        mAsyncClient.addHeader(TOKEN,PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(TOKEN));
        mContext = context;
    }
}
