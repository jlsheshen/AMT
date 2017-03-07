package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.GetWebViewUrlManager;

/**
 * Created by Administrator on 2017/3/6.
 */

public class WebActivity extends BaseActivity implements GetWebViewUrlManager.GetWebUrlListener {
    private WebView webView;
    ExampleBean exampleBeans;
    @Override
    public int setLayout() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        webView = bindView(R.id.aty_wv);

    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        exampleBeans = (ExampleBean) bundle.getSerializable("exampleBeans");
        String url = NetUrlContstant.getMediaorPdfUrl() + exampleBeans .getUrl();
        Log.d("WebActivity", "----------" + url);
        GetWebViewUrlManager.newInstance(this).setGetWebUrlListener(this).getSubjects(url);



    }


    @Override
    public void onSuccess(String text) {
        String encoding = "UTF-8";
        String mimeType = "text/html";
//        String html = "<p>请问图片中是否有红色？<img src=\\\"http://www.2cto.com/uploadfile/2011/0811/20110811112952212.jpg\\\" title=\\\"1488849962905013463.jpg\\\" alt=\\\"8901974_45_thumb.jpg\\\"/></p>";
//        Log.d("WebActivity", " ++++ " + html);
        webView.loadDataWithBaseURL("file://", text,mimeType, encoding, "about:blank");


    }

    @Override
    public void onFail() {

    }
}
