package com.edu.accountingteachingmaterial.fragment;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.util.net.EmphasisManager;
import com.edu.accountingteachingmaterial.util.net.GetWebViewUrlManager;

import java.util.Map;

/**
 * 重点难点
 */
public class ClassEmphasisFragment extends BaseFragment implements EmphasisManager.EmphasisListener {

    WebView wView;
    TextView nothingTv;

    int chapter;
    Map<String, String> extraHeaders;


    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
        Log.d("ClassEmphasisFragment", "chapter:" + chapter);

    }

    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_class_emphasis;
    }

    @Override
    public void onResume() {
        super.onResume();
        EmphasisManager.getSingleton(context).getEmphasis(this,chapter);

    }

    @Override
    protected void initView(View view) {
        wView = bindView(R.id.emphasis_wv);
        nothingTv = bindView(R.id.no_content_tv);

    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSuccess(String message) {

//        String before = "<html> \n" +
//                "<header> \n" +
//                "<meta charset=\"UTF-8\"> \n" +
//                "<meta name=\"viewport\" content=\"width=device-width , user-scalable=yes, minimum-scale=1, maximum-scale=2,target-densitydpi=device-dpi \"> \n" +
//                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"/> \n" +
//                "<style type=\"text/css\"> \n" +
//                ".view p img { \n" +
//                "max-width:100%;\n"+
//                "width:auto; \n" +
//                "\n" +
//                "} \n" +
//                "</style></header> \n" +
//                "<body class=view> \n";
//        String after = "</body></html>\n";
//        message = before + message + after;


        GetWebViewUrlManager.newInstance(context).setGetWebUrlListener(new GetWebViewUrlManager.GetWebUrlListener() {
            @Override
            public void onSuccess(String text) {
                Log.d("ClassEmphasisFragment", text);
                String encoding = "UTF-8";
                String mimeType = "text/html";
                Log.d("WebActivity", " ++++ " + text);
                WebSettings webSettings = wView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                wView.getSettings().setBuiltInZoomControls(true);
                wView.loadDataWithBaseURL("file://", text,mimeType, encoding, "about:blank");

            }

            @Override
            public void onFail() {
                nothingTv.setVisibility(View.VISIBLE);
                wView.setVisibility(View.GONE);

            }
        }).getSubjects(NetUrlContstant.getHTMLUrl() + message);

    }

    @Override
    public void onFailure(String message) {
        nothingTv.setVisibility(View.VISIBLE);
        wView.setVisibility(View.GONE);

    }

}
