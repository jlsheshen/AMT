package com.edu.accountingteachingmaterial.util;


import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;
import com.lucher.net.req.impl.UrlReqEntity;

import org.apache.http.Header;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.TOKEN;


/**
 * 加载经典示例web
 *
 *
 * @author lucher
 *
 */
public class GetWebViewUrlManager extends JsonNetReqManager {

	private Context mContext;
	GetWebUrlListener getWebUrlListener;
	private static GetWebViewUrlManager intance;

	public GetWebViewUrlManager(Context context) {
		mAsyncClient.addHeader(TOKEN,PreferenceHelper.getInstance(BaseApplication.getContext()).getStringValue(TOKEN));
		mContext = context;
	}

	public GetWebViewUrlManager setGetWebUrlListener(GetWebUrlListener getWebUrlListener) {
		this.getWebUrlListener = getWebUrlListener;

		return this;
	}

	/**
	 * 创建实例
	 *
	 * @param context
	 * @return
	 */
	public static GetWebViewUrlManager newInstance(Context context) {
		if (intance == null){
			intance =  new GetWebViewUrlManager(context);
		}
		return intance;
	}

	/**
	 * 下载题目数据
	 *
	 * @param url
	 */
	public void getSubjects(String url) {
		UrlReqEntity entity = new UrlReqEntity(mContext, RequestMethod.GET, url);
		sendRequest(entity);
	}

	@Override
	public void onConnectionSuccess(JSONObject json, Header[] arg1) {
		Log.d(TAG, "onConnectionSuccess:" + json);
		parseSubjectJson(json);
	}

	@Override
	public void onConnectionError(String arg0) {
		Log.e(TAG, "onConnectionError:" + arg0);
		getWebUrlListener.onFail();
	}

	@Override
	public void onConnectionFailure(String arg0, Header[] arg1) {
		Log.e(TAG, "onConnectionFailure:" + arg0);
		getWebUrlListener.onFail();
	}

	/**
	 * 解析json
	 *
	 * @param json
	 */
	private void parseSubjectJson(JSONObject json) {
		long start = System.currentTimeMillis();
		Log.d(TAG, "parse start:" + start);
		boolean result = json.getBoolean("result");
		String message = json.getString("message");
		if (result) {
			getWebUrlListener.onSuccess(message);

		} else {
			Log.e(TAG, "parseSubjectJson:" + json);
		}
		Log.d(TAG, "parse end:" + (System.currentTimeMillis() - start) / 1000);
	}
	public interface GetWebUrlListener{
		void onSuccess(String text);
		void onFail();
	}
}

